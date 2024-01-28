import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { DataTreeLeafFormService } from './data-tree-leaf-form.service';
import { DataTreeLeafService } from '../service/data-tree-leaf.service';
import { IDataTreeLeaf } from '../data-tree-leaf.model';
import { IDataTreeLeafToField } from 'app/entities/data-tree-leaf-to-field/data-tree-leaf-to-field.model';
import { DataTreeLeafToFieldService } from 'app/entities/data-tree-leaf-to-field/service/data-tree-leaf-to-field.service';

import { DataTreeLeafUpdateComponent } from './data-tree-leaf-update.component';

describe('DataTreeLeaf Management Update Component', () => {
  let comp: DataTreeLeafUpdateComponent;
  let fixture: ComponentFixture<DataTreeLeafUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let dataTreeLeafFormService: DataTreeLeafFormService;
  let dataTreeLeafService: DataTreeLeafService;
  let dataTreeLeafToFieldService: DataTreeLeafToFieldService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), DataTreeLeafUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(DataTreeLeafUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DataTreeLeafUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    dataTreeLeafFormService = TestBed.inject(DataTreeLeafFormService);
    dataTreeLeafService = TestBed.inject(DataTreeLeafService);
    dataTreeLeafToFieldService = TestBed.inject(DataTreeLeafToFieldService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call DataTreeLeafToField query and add missing value', () => {
      const dataTreeLeaf: IDataTreeLeaf = { id: 456 };
      const leafToField: IDataTreeLeafToField = { id: 46117 };
      dataTreeLeaf.leafToField = leafToField;

      const dataTreeLeafToFieldCollection: IDataTreeLeafToField[] = [{ id: 12492 }];
      jest.spyOn(dataTreeLeafToFieldService, 'query').mockReturnValue(of(new HttpResponse({ body: dataTreeLeafToFieldCollection })));
      const additionalDataTreeLeafToFields = [leafToField];
      const expectedCollection: IDataTreeLeafToField[] = [...additionalDataTreeLeafToFields, ...dataTreeLeafToFieldCollection];
      jest.spyOn(dataTreeLeafToFieldService, 'addDataTreeLeafToFieldToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ dataTreeLeaf });
      comp.ngOnInit();

      expect(dataTreeLeafToFieldService.query).toHaveBeenCalled();
      expect(dataTreeLeafToFieldService.addDataTreeLeafToFieldToCollectionIfMissing).toHaveBeenCalledWith(
        dataTreeLeafToFieldCollection,
        ...additionalDataTreeLeafToFields.map(expect.objectContaining)
      );
      expect(comp.dataTreeLeafToFieldsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const dataTreeLeaf: IDataTreeLeaf = { id: 456 };
      const leafToField: IDataTreeLeafToField = { id: 15656 };
      dataTreeLeaf.leafToField = leafToField;

      activatedRoute.data = of({ dataTreeLeaf });
      comp.ngOnInit();

      expect(comp.dataTreeLeafToFieldsSharedCollection).toContain(leafToField);
      expect(comp.dataTreeLeaf).toEqual(dataTreeLeaf);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDataTreeLeaf>>();
      const dataTreeLeaf = { id: 123 };
      jest.spyOn(dataTreeLeafFormService, 'getDataTreeLeaf').mockReturnValue(dataTreeLeaf);
      jest.spyOn(dataTreeLeafService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dataTreeLeaf });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: dataTreeLeaf }));
      saveSubject.complete();

      // THEN
      expect(dataTreeLeafFormService.getDataTreeLeaf).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(dataTreeLeafService.update).toHaveBeenCalledWith(expect.objectContaining(dataTreeLeaf));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDataTreeLeaf>>();
      const dataTreeLeaf = { id: 123 };
      jest.spyOn(dataTreeLeafFormService, 'getDataTreeLeaf').mockReturnValue({ id: null });
      jest.spyOn(dataTreeLeafService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dataTreeLeaf: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: dataTreeLeaf }));
      saveSubject.complete();

      // THEN
      expect(dataTreeLeafFormService.getDataTreeLeaf).toHaveBeenCalled();
      expect(dataTreeLeafService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDataTreeLeaf>>();
      const dataTreeLeaf = { id: 123 };
      jest.spyOn(dataTreeLeafService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dataTreeLeaf });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(dataTreeLeafService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareDataTreeLeafToField', () => {
      it('Should forward to dataTreeLeafToFieldService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(dataTreeLeafToFieldService, 'compareDataTreeLeafToField');
        comp.compareDataTreeLeafToField(entity, entity2);
        expect(dataTreeLeafToFieldService.compareDataTreeLeafToField).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
