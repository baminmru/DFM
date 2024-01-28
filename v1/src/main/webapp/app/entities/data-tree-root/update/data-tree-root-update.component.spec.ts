import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { DataTreeRootFormService } from './data-tree-root-form.service';
import { DataTreeRootService } from '../service/data-tree-root.service';
import { IDataTreeRoot } from '../data-tree-root.model';
import { IDataTreeBranch } from 'app/entities/data-tree-branch/data-tree-branch.model';
import { DataTreeBranchService } from 'app/entities/data-tree-branch/service/data-tree-branch.service';
import { IDataField } from 'app/entities/data-field/data-field.model';
import { DataFieldService } from 'app/entities/data-field/service/data-field.service';

import { DataTreeRootUpdateComponent } from './data-tree-root-update.component';

describe('DataTreeRoot Management Update Component', () => {
  let comp: DataTreeRootUpdateComponent;
  let fixture: ComponentFixture<DataTreeRootUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let dataTreeRootFormService: DataTreeRootFormService;
  let dataTreeRootService: DataTreeRootService;
  let dataTreeBranchService: DataTreeBranchService;
  let dataFieldService: DataFieldService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), DataTreeRootUpdateComponent],
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
      .overrideTemplate(DataTreeRootUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DataTreeRootUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    dataTreeRootFormService = TestBed.inject(DataTreeRootFormService);
    dataTreeRootService = TestBed.inject(DataTreeRootService);
    dataTreeBranchService = TestBed.inject(DataTreeBranchService);
    dataFieldService = TestBed.inject(DataFieldService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call DataTreeBranch query and add missing value', () => {
      const dataTreeRoot: IDataTreeRoot = { id: 456 };
      const dataTreeBranch: IDataTreeBranch = { id: 25451 };
      dataTreeRoot.dataTreeBranch = dataTreeBranch;

      const dataTreeBranchCollection: IDataTreeBranch[] = [{ id: 72982 }];
      jest.spyOn(dataTreeBranchService, 'query').mockReturnValue(of(new HttpResponse({ body: dataTreeBranchCollection })));
      const additionalDataTreeBranches = [dataTreeBranch];
      const expectedCollection: IDataTreeBranch[] = [...additionalDataTreeBranches, ...dataTreeBranchCollection];
      jest.spyOn(dataTreeBranchService, 'addDataTreeBranchToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ dataTreeRoot });
      comp.ngOnInit();

      expect(dataTreeBranchService.query).toHaveBeenCalled();
      expect(dataTreeBranchService.addDataTreeBranchToCollectionIfMissing).toHaveBeenCalledWith(
        dataTreeBranchCollection,
        ...additionalDataTreeBranches.map(expect.objectContaining)
      );
      expect(comp.dataTreeBranchesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call DataField query and add missing value', () => {
      const dataTreeRoot: IDataTreeRoot = { id: 456 };
      const rootToFields: IDataField[] = [{ id: 96244 }];
      dataTreeRoot.rootToFields = rootToFields;

      const dataFieldCollection: IDataField[] = [{ id: 633 }];
      jest.spyOn(dataFieldService, 'query').mockReturnValue(of(new HttpResponse({ body: dataFieldCollection })));
      const additionalDataFields = [...rootToFields];
      const expectedCollection: IDataField[] = [...additionalDataFields, ...dataFieldCollection];
      jest.spyOn(dataFieldService, 'addDataFieldToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ dataTreeRoot });
      comp.ngOnInit();

      expect(dataFieldService.query).toHaveBeenCalled();
      expect(dataFieldService.addDataFieldToCollectionIfMissing).toHaveBeenCalledWith(
        dataFieldCollection,
        ...additionalDataFields.map(expect.objectContaining)
      );
      expect(comp.dataFieldsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const dataTreeRoot: IDataTreeRoot = { id: 456 };
      const dataTreeBranch: IDataTreeBranch = { id: 26512 };
      dataTreeRoot.dataTreeBranch = dataTreeBranch;
      const rootToField: IDataField = { id: 22120 };
      dataTreeRoot.rootToFields = [rootToField];

      activatedRoute.data = of({ dataTreeRoot });
      comp.ngOnInit();

      expect(comp.dataTreeBranchesSharedCollection).toContain(dataTreeBranch);
      expect(comp.dataFieldsSharedCollection).toContain(rootToField);
      expect(comp.dataTreeRoot).toEqual(dataTreeRoot);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDataTreeRoot>>();
      const dataTreeRoot = { id: 123 };
      jest.spyOn(dataTreeRootFormService, 'getDataTreeRoot').mockReturnValue(dataTreeRoot);
      jest.spyOn(dataTreeRootService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dataTreeRoot });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: dataTreeRoot }));
      saveSubject.complete();

      // THEN
      expect(dataTreeRootFormService.getDataTreeRoot).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(dataTreeRootService.update).toHaveBeenCalledWith(expect.objectContaining(dataTreeRoot));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDataTreeRoot>>();
      const dataTreeRoot = { id: 123 };
      jest.spyOn(dataTreeRootFormService, 'getDataTreeRoot').mockReturnValue({ id: null });
      jest.spyOn(dataTreeRootService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dataTreeRoot: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: dataTreeRoot }));
      saveSubject.complete();

      // THEN
      expect(dataTreeRootFormService.getDataTreeRoot).toHaveBeenCalled();
      expect(dataTreeRootService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDataTreeRoot>>();
      const dataTreeRoot = { id: 123 };
      jest.spyOn(dataTreeRootService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dataTreeRoot });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(dataTreeRootService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareDataTreeBranch', () => {
      it('Should forward to dataTreeBranchService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(dataTreeBranchService, 'compareDataTreeBranch');
        comp.compareDataTreeBranch(entity, entity2);
        expect(dataTreeBranchService.compareDataTreeBranch).toHaveBeenCalledWith(entity, entity2);
      });
    });

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
