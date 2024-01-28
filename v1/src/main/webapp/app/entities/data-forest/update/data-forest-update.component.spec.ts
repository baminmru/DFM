import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { DataForestFormService } from './data-forest-form.service';
import { DataForestService } from '../service/data-forest.service';
import { IDataForest } from '../data-forest.model';
import { IDataTreeRoot } from 'app/entities/data-tree-root/data-tree-root.model';
import { DataTreeRootService } from 'app/entities/data-tree-root/service/data-tree-root.service';

import { DataForestUpdateComponent } from './data-forest-update.component';

describe('DataForest Management Update Component', () => {
  let comp: DataForestUpdateComponent;
  let fixture: ComponentFixture<DataForestUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let dataForestFormService: DataForestFormService;
  let dataForestService: DataForestService;
  let dataTreeRootService: DataTreeRootService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), DataForestUpdateComponent],
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
      .overrideTemplate(DataForestUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DataForestUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    dataForestFormService = TestBed.inject(DataForestFormService);
    dataForestService = TestBed.inject(DataForestService);
    dataTreeRootService = TestBed.inject(DataTreeRootService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call DataTreeRoot query and add missing value', () => {
      const dataForest: IDataForest = { id: 456 };
      const forestTrees: IDataTreeRoot = { id: 47904 };
      dataForest.forestTrees = forestTrees;

      const dataTreeRootCollection: IDataTreeRoot[] = [{ id: 54682 }];
      jest.spyOn(dataTreeRootService, 'query').mockReturnValue(of(new HttpResponse({ body: dataTreeRootCollection })));
      const additionalDataTreeRoots = [forestTrees];
      const expectedCollection: IDataTreeRoot[] = [...additionalDataTreeRoots, ...dataTreeRootCollection];
      jest.spyOn(dataTreeRootService, 'addDataTreeRootToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ dataForest });
      comp.ngOnInit();

      expect(dataTreeRootService.query).toHaveBeenCalled();
      expect(dataTreeRootService.addDataTreeRootToCollectionIfMissing).toHaveBeenCalledWith(
        dataTreeRootCollection,
        ...additionalDataTreeRoots.map(expect.objectContaining)
      );
      expect(comp.dataTreeRootsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const dataForest: IDataForest = { id: 456 };
      const forestTrees: IDataTreeRoot = { id: 22855 };
      dataForest.forestTrees = forestTrees;

      activatedRoute.data = of({ dataForest });
      comp.ngOnInit();

      expect(comp.dataTreeRootsSharedCollection).toContain(forestTrees);
      expect(comp.dataForest).toEqual(dataForest);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDataForest>>();
      const dataForest = { id: 123 };
      jest.spyOn(dataForestFormService, 'getDataForest').mockReturnValue(dataForest);
      jest.spyOn(dataForestService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dataForest });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: dataForest }));
      saveSubject.complete();

      // THEN
      expect(dataForestFormService.getDataForest).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(dataForestService.update).toHaveBeenCalledWith(expect.objectContaining(dataForest));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDataForest>>();
      const dataForest = { id: 123 };
      jest.spyOn(dataForestFormService, 'getDataForest').mockReturnValue({ id: null });
      jest.spyOn(dataForestService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dataForest: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: dataForest }));
      saveSubject.complete();

      // THEN
      expect(dataForestFormService.getDataForest).toHaveBeenCalled();
      expect(dataForestService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDataForest>>();
      const dataForest = { id: 123 };
      jest.spyOn(dataForestService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dataForest });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(dataForestService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareDataTreeRoot', () => {
      it('Should forward to dataTreeRootService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(dataTreeRootService, 'compareDataTreeRoot');
        comp.compareDataTreeRoot(entity, entity2);
        expect(dataTreeRootService.compareDataTreeRoot).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
