import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { DataForestService } from '../service/data-forest.service';

import { DataForestComponent } from './data-forest.component';

describe('DataForest Management Component', () => {
  let comp: DataForestComponent;
  let fixture: ComponentFixture<DataForestComponent>;
  let service: DataForestService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'data-forest', component: DataForestComponent }]),
        HttpClientTestingModule,
        DataForestComponent,
      ],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({
              defaultSort: 'id,asc',
            }),
            queryParamMap: of(
              jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'id,desc',
              })
            ),
            snapshot: { queryParams: {} },
          },
        },
      ],
    })
      .overrideTemplate(DataForestComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DataForestComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(DataForestService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.dataForests?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to dataForestService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getDataForestIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getDataForestIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
