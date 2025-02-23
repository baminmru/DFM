import dayjs from 'dayjs/esm';

import { IRequestType, NewRequestType } from './request-type.model';

export const sampleWithRequiredData: IRequestType = {
  id: 30248,
  code: 'hmph stair',
  name: 'upwardly',
};

export const sampleWithPartialData: IRequestType = {
  id: 15099,
  code: 'paperwork pace a',
  name: 'phew ultimately meh',
  updatedAt: dayjs('2025-02-23'),
  updatedBy: 'duh light',
};

export const sampleWithFullData: IRequestType = {
  id: 2261,
  code: 'solemnly manoeuvre',
  name: 'meh gosh',
  createdAt: dayjs('2025-02-23'),
  createdBy: 'what given',
  updatedAt: dayjs('2025-02-23'),
  updatedBy: 'lest liner reorganise',
};

export const sampleWithNewData: NewRequestType = {
  code: 'pig versus',
  name: 'failing oof',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
