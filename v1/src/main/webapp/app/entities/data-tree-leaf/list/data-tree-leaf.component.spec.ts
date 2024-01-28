import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { DataTreeLeafService } from '../service/data-tree-leaf.service';

import { DataTreeLeafComponent } from './data-tree-leaf.component';

describe('DataTreeLeaf Management Component', () => {
  let comp: DataTreeLeafComponent;
  let fixture: ComponentFixture<DataTreeLeafComponent>;
  let service: DataTreeLeafService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'data-tree-leaf', component: DataTreeLeafComponent }]),
        HttpClientTestingModule,
        DataTreeLeafComponent,
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
      .overrideTemplate(DataTreeLeafComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DataTreeLeafComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(DataTreeLeafService);

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
    expect(comp.dataTreeLeaves?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to dataTreeLeafService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getDataTreeLeafIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getDataTreeLeafIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
