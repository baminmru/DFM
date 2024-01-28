import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { DataTreeBranchFormService } from './data-tree-branch-form.service';
import { DataTreeBranchService } from '../service/data-tree-branch.service';
import { IDataTreeBranch } from '../data-tree-branch.model';
import { IDataTreeLeaf } from 'app/entities/data-tree-leaf/data-tree-leaf.model';
import { DataTreeLeafService } from 'app/entities/data-tree-leaf/service/data-tree-leaf.service';
import { IDataField } from 'app/entities/data-field/data-field.model';
import { DataFieldService } from 'app/entities/data-field/service/data-field.service';

import { DataTreeBranchUpdateComponent } from './data-tree-branch-update.component';

describe('DataTreeBranch Management Update Component', () => {
  let comp: DataTreeBranchUpdateComponent;
  let fixture: ComponentFixture<DataTreeBranchUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let dataTreeBranchFormService: DataTreeBranchFormService;
  let dataTreeBranchService: DataTreeBranchService;
  let dataTreeLeafService: DataTreeLeafService;
  let dataFieldService: DataFieldService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), DataTreeBranchUpdateComponent],
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
      .overrideTemplate(DataTreeBranchUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DataTreeBranchUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    dataTreeBranchFormService = TestBed.inject(DataTreeBranchFormService);
    dataTreeBranchService = TestBed.inject(DataTreeBranchService);
    dataTreeLeafService = TestBed.inject(DataTreeLeafService);
    dataFieldService = TestBed.inject(DataFieldService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call DataTreeLeaf query and add missing value', () => {
      const dataTreeBranch: IDataTreeBranch = { id: 456 };
      const dataTreeLeaf: IDataTreeLeaf = { id: 11901 };
      dataTreeBranch.dataTreeLeaf = dataTreeLeaf;

      const dataTreeLeafCollection: IDataTreeLeaf[] = [{ id: 2959 }];
      jest.spyOn(dataTreeLeafService, 'query').mockReturnValue(of(new HttpResponse({ body: dataTreeLeafCollection })));
      const additionalDataTreeLeaves = [dataTreeLeaf];
      const expectedCollection: IDataTreeLeaf[] = [...additionalDataTreeLeaves, ...dataTreeLeafCollection];
      jest.spyOn(dataTreeLeafService, 'addDataTreeLeafToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ dataTreeBranch });
      comp.ngOnInit();

      expect(dataTreeLeafService.query).toHaveBeenCalled();
      expect(dataTreeLeafService.addDataTreeLeafToCollectionIfMissing).toHaveBeenCalledWith(
        dataTreeLeafCollection,
        ...additionalDataTreeLeaves.map(expect.objectContaining)
      );
      expect(comp.dataTreeLeavesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call DataField query and add missing value', () => {
      const dataTreeBranch: IDataTreeBranch = { id: 456 };
      const branchToFields: IDataField[] = [{ id: 22951 }];
      dataTreeBranch.branchToFields = branchToFields;

      const dataFieldCollection: IDataField[] = [{ id: 90631 }];
      jest.spyOn(dataFieldService, 'query').mockReturnValue(of(new HttpResponse({ body: dataFieldCollection })));
      const additionalDataFields = [...branchToFields];
      const expectedCollection: IDataField[] = [...additionalDataFields, ...dataFieldCollection];
      jest.spyOn(dataFieldService, 'addDataFieldToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ dataTreeBranch });
      comp.ngOnInit();

      expect(dataFieldService.query).toHaveBeenCalled();
      expect(dataFieldService.addDataFieldToCollectionIfMissing).toHaveBeenCalledWith(
        dataFieldCollection,
        ...additionalDataFields.map(expect.objectContaining)
      );
      expect(comp.dataFieldsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call DataTreeBranch query and add missing value', () => {
      const dataTreeBranch: IDataTreeBranch = { id: 456 };
      const branchParents: IDataTreeBranch[] = [{ id: 21876 }];
      dataTreeBranch.branchParents = branchParents;

      const dataTreeBranchCollection: IDataTreeBranch[] = [{ id: 42884 }];
      jest.spyOn(dataTreeBranchService, 'query').mockReturnValue(of(new HttpResponse({ body: dataTreeBranchCollection })));
      const additionalDataTreeBranches = [...branchParents];
      const expectedCollection: IDataTreeBranch[] = [...additionalDataTreeBranches, ...dataTreeBranchCollection];
      jest.spyOn(dataTreeBranchService, 'addDataTreeBranchToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ dataTreeBranch });
      comp.ngOnInit();

      expect(dataTreeBranchService.query).toHaveBeenCalled();
      expect(dataTreeBranchService.addDataTreeBranchToCollectionIfMissing).toHaveBeenCalledWith(
        dataTreeBranchCollection,
        ...additionalDataTreeBranches.map(expect.objectContaining)
      );
      expect(comp.dataTreeBranchesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const dataTreeBranch: IDataTreeBranch = { id: 456 };
      const dataTreeLeaf: IDataTreeLeaf = { id: 40057 };
      dataTreeBranch.dataTreeLeaf = dataTreeLeaf;
      const branchToField: IDataField = { id: 34750 };
      dataTreeBranch.branchToFields = [branchToField];
      const branchParent: IDataTreeBranch = { id: 42162 };
      dataTreeBranch.branchParents = [branchParent];

      activatedRoute.data = of({ dataTreeBranch });
      comp.ngOnInit();

      expect(comp.dataTreeLeavesSharedCollection).toContain(dataTreeLeaf);
      expect(comp.dataFieldsSharedCollection).toContain(branchToField);
      expect(comp.dataTreeBranchesSharedCollection).toContain(branchParent);
      expect(comp.dataTreeBranch).toEqual(dataTreeBranch);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDataTreeBranch>>();
      const dataTreeBranch = { id: 123 };
      jest.spyOn(dataTreeBranchFormService, 'getDataTreeBranch').mockReturnValue(dataTreeBranch);
      jest.spyOn(dataTreeBranchService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dataTreeBranch });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: dataTreeBranch }));
      saveSubject.complete();

      // THEN
      expect(dataTreeBranchFormService.getDataTreeBranch).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(dataTreeBranchService.update).toHaveBeenCalledWith(expect.objectContaining(dataTreeBranch));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDataTreeBranch>>();
      const dataTreeBranch = { id: 123 };
      jest.spyOn(dataTreeBranchFormService, 'getDataTreeBranch').mockReturnValue({ id: null });
      jest.spyOn(dataTreeBranchService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dataTreeBranch: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: dataTreeBranch }));
      saveSubject.complete();

      // THEN
      expect(dataTreeBranchFormService.getDataTreeBranch).toHaveBeenCalled();
      expect(dataTreeBranchService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDataTreeBranch>>();
      const dataTreeBranch = { id: 123 };
      jest.spyOn(dataTreeBranchService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dataTreeBranch });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(dataTreeBranchService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareDataTreeLeaf', () => {
      it('Should forward to dataTreeLeafService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(dataTreeLeafService, 'compareDataTreeLeaf');
        comp.compareDataTreeLeaf(entity, entity2);
        expect(dataTreeLeafService.compareDataTreeLeaf).toHaveBeenCalledWith(entity, entity2);
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

    describe('compareDataTreeBranch', () => {
      it('Should forward to dataTreeBranchService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(dataTreeBranchService, 'compareDataTreeBranch');
        comp.compareDataTreeBranch(entity, entity2);
        expect(dataTreeBranchService.compareDataTreeBranch).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
