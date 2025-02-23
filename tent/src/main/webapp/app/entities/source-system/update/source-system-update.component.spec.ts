import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { SourceSystemService } from '../service/source-system.service';
import { ISourceSystem } from '../source-system.model';
import { SourceSystemFormService } from './source-system-form.service';

import { SourceSystemUpdateComponent } from './source-system-update.component';

describe('SourceSystem Management Update Component', () => {
  let comp: SourceSystemUpdateComponent;
  let fixture: ComponentFixture<SourceSystemUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let sourceSystemFormService: SourceSystemFormService;
  let sourceSystemService: SourceSystemService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [SourceSystemUpdateComponent],
      providers: [
        provideHttpClient(),
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(SourceSystemUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SourceSystemUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    sourceSystemFormService = TestBed.inject(SourceSystemFormService);
    sourceSystemService = TestBed.inject(SourceSystemService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const sourceSystem: ISourceSystem = { id: 456 };

      activatedRoute.data = of({ sourceSystem });
      comp.ngOnInit();

      expect(comp.sourceSystem).toEqual(sourceSystem);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISourceSystem>>();
      const sourceSystem = { id: 123 };
      jest.spyOn(sourceSystemFormService, 'getSourceSystem').mockReturnValue(sourceSystem);
      jest.spyOn(sourceSystemService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sourceSystem });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: sourceSystem }));
      saveSubject.complete();

      // THEN
      expect(sourceSystemFormService.getSourceSystem).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(sourceSystemService.update).toHaveBeenCalledWith(expect.objectContaining(sourceSystem));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISourceSystem>>();
      const sourceSystem = { id: 123 };
      jest.spyOn(sourceSystemFormService, 'getSourceSystem').mockReturnValue({ id: null });
      jest.spyOn(sourceSystemService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sourceSystem: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: sourceSystem }));
      saveSubject.complete();

      // THEN
      expect(sourceSystemFormService.getSourceSystem).toHaveBeenCalled();
      expect(sourceSystemService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISourceSystem>>();
      const sourceSystem = { id: 123 };
      jest.spyOn(sourceSystemService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sourceSystem });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(sourceSystemService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
