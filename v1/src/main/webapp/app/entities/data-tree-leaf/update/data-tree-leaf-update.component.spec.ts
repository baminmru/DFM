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
import { IDataField } from 'app/entities/data-field/data-field.model';
import { DataFieldService } from 'app/entities/data-field/service/data-field.service';

import { DataTreeLeafUpdateComponent } from './data-tree-leaf-update.component';

describe('DataTreeLeaf Management Update Component', () => {
  let comp: DataTreeLeafUpdateComponent;
  let fixture: ComponentFixture<DataTreeLeafUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let dataTreeLeafFormService: DataTreeLeafFormService;
  let dataTreeLeafService: DataTreeLeafService;
  let dataFieldService: DataFieldService;

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
    dataFieldService = TestBed.inject(DataFieldService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call DataField query and add missing value', () => {
      const dataTreeLeaf: IDataTreeLeaf = { id: 456 };
      const leafToFields: IDataField[] = [{ id: 85944 }];
      dataTreeLeaf.leafToFields = leafToFields;

      const dataFieldCollection: IDataField[] = [{ id: 69078 }];
      jest.spyOn(dataFieldService, 'query').mockReturnValue(of(new HttpResponse({ body: dataFieldCollection })));
      const additionalDataFields = [...leafToFields];
      const expectedCollection: IDataField[] = [...additionalDataFields, ...dataFieldCollection];
      jest.spyOn(dataFieldService, 'addDataFieldToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ dataTreeLeaf });
      comp.ngOnInit();

      expect(dataFieldService.query).toHaveBeenCalled();
      expect(dataFieldService.addDataFieldToCollectionIfMissing).toHaveBeenCalledWith(
        dataFieldCollection,
        ...additionalDataFields.map(expect.objectContaining)
      );
      expect(comp.dataFieldsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const dataTreeLeaf: IDataTreeLeaf = { id: 456 };
      const leafToField: IDataField = { id: 57192 };
      dataTreeLeaf.leafToFields = [leafToField];

      activatedRoute.data = of({ dataTreeLeaf });
      comp.ngOnInit();

      expect(comp.dataFieldsSharedCollection).toContain(leafToField);
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
    describe('compareDataField', () => {
      it('Should forward to dataFieldService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(dataFieldService, 'compareDataField');
        comp.compareDataField(entity, entity2);
        expect(dataFieldService.compareDataField).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
