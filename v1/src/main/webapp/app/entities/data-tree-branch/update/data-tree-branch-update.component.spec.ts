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
import { IDataTreeBranchToField } from 'app/entities/data-tree-branch-to-field/data-tree-branch-to-field.model';
import { DataTreeBranchToFieldService } from 'app/entities/data-tree-branch-to-field/service/data-tree-branch-to-field.service';
import { IDataTreeBranchLink } from 'app/entities/data-tree-branch-link/data-tree-branch-link.model';
import { DataTreeBranchLinkService } from 'app/entities/data-tree-branch-link/service/data-tree-branch-link.service';

import { DataTreeBranchUpdateComponent } from './data-tree-branch-update.component';

describe('DataTreeBranch Management Update Component', () => {
  let comp: DataTreeBranchUpdateComponent;
  let fixture: ComponentFixture<DataTreeBranchUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let dataTreeBranchFormService: DataTreeBranchFormService;
  let dataTreeBranchService: DataTreeBranchService;
  let dataTreeLeafService: DataTreeLeafService;
  let dataTreeBranchToFieldService: DataTreeBranchToFieldService;
  let dataTreeBranchLinkService: DataTreeBranchLinkService;

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
    dataTreeBranchToFieldService = TestBed.inject(DataTreeBranchToFieldService);
    dataTreeBranchLinkService = TestBed.inject(DataTreeBranchLinkService);

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

    it('Should call DataTreeBranchToField query and add missing value', () => {
      const dataTreeBranch: IDataTreeBranch = { id: 456 };
      const branchToField: IDataTreeBranchToField = { id: 77874 };
      dataTreeBranch.branchToField = branchToField;

      const dataTreeBranchToFieldCollection: IDataTreeBranchToField[] = [{ id: 56948 }];
      jest.spyOn(dataTreeBranchToFieldService, 'query').mockReturnValue(of(new HttpResponse({ body: dataTreeBranchToFieldCollection })));
      const additionalDataTreeBranchToFields = [branchToField];
      const expectedCollection: IDataTreeBranchToField[] = [...additionalDataTreeBranchToFields, ...dataTreeBranchToFieldCollection];
      jest.spyOn(dataTreeBranchToFieldService, 'addDataTreeBranchToFieldToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ dataTreeBranch });
      comp.ngOnInit();

      expect(dataTreeBranchToFieldService.query).toHaveBeenCalled();
      expect(dataTreeBranchToFieldService.addDataTreeBranchToFieldToCollectionIfMissing).toHaveBeenCalledWith(
        dataTreeBranchToFieldCollection,
        ...additionalDataTreeBranchToFields.map(expect.objectContaining)
      );
      expect(comp.dataTreeBranchToFieldsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call DataTreeBranchLink query and add missing value', () => {
      const dataTreeBranch: IDataTreeBranch = { id: 456 };
      const branchParent: IDataTreeBranchLink = { id: 11273 };
      dataTreeBranch.branchParent = branchParent;

      const dataTreeBranchLinkCollection: IDataTreeBranchLink[] = [{ id: 83865 }];
      jest.spyOn(dataTreeBranchLinkService, 'query').mockReturnValue(of(new HttpResponse({ body: dataTreeBranchLinkCollection })));
      const additionalDataTreeBranchLinks = [branchParent];
      const expectedCollection: IDataTreeBranchLink[] = [...additionalDataTreeBranchLinks, ...dataTreeBranchLinkCollection];
      jest.spyOn(dataTreeBranchLinkService, 'addDataTreeBranchLinkToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ dataTreeBranch });
      comp.ngOnInit();

      expect(dataTreeBranchLinkService.query).toHaveBeenCalled();
      expect(dataTreeBranchLinkService.addDataTreeBranchLinkToCollectionIfMissing).toHaveBeenCalledWith(
        dataTreeBranchLinkCollection,
        ...additionalDataTreeBranchLinks.map(expect.objectContaining)
      );
      expect(comp.dataTreeBranchLinksSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const dataTreeBranch: IDataTreeBranch = { id: 456 };
      const dataTreeLeaf: IDataTreeLeaf = { id: 40057 };
      dataTreeBranch.dataTreeLeaf = dataTreeLeaf;
      const branchToField: IDataTreeBranchToField = { id: 12384 };
      dataTreeBranch.branchToField = branchToField;
      const branchParent: IDataTreeBranchLink = { id: 48849 };
      dataTreeBranch.branchParent = branchParent;

      activatedRoute.data = of({ dataTreeBranch });
      comp.ngOnInit();

      expect(comp.dataTreeLeavesSharedCollection).toContain(dataTreeLeaf);
      expect(comp.dataTreeBranchToFieldsSharedCollection).toContain(branchToField);
      expect(comp.dataTreeBranchLinksSharedCollection).toContain(branchParent);
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

    describe('compareDataTreeBranchToField', () => {
      it('Should forward to dataTreeBranchToFieldService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(dataTreeBranchToFieldService, 'compareDataTreeBranchToField');
        comp.compareDataTreeBranchToField(entity, entity2);
        expect(dataTreeBranchToFieldService.compareDataTreeBranchToField).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareDataTreeBranchLink', () => {
      it('Should forward to dataTreeBranchLinkService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(dataTreeBranchLinkService, 'compareDataTreeBranchLink');
        comp.compareDataTreeBranchLink(entity, entity2);
        expect(dataTreeBranchLinkService.compareDataTreeBranchLink).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
