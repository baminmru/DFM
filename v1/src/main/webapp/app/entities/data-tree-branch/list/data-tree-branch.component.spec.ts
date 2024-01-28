import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { DataTreeBranchService } from '../service/data-tree-branch.service';

import { DataTreeBranchComponent } from './data-tree-branch.component';

describe('DataTreeBranch Management Component', () => {
  let comp: DataTreeBranchComponent;
  let fixture: ComponentFixture<DataTreeBranchComponent>;
  let service: DataTreeBranchService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'data-tree-branch', component: DataTreeBranchComponent }]),
        HttpClientTestingModule,
        DataTreeBranchComponent,
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
      .overrideTemplate(DataTreeBranchComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DataTreeBranchComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(DataTreeBranchService);

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
    expect(comp.dataTreeBranches?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to dataTreeBranchService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getDataTreeBranchIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getDataTreeBranchIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
