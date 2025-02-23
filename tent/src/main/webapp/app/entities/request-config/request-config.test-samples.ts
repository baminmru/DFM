import dayjs from 'dayjs/esm';

import { IRequestConfig, NewRequestConfig } from './request-config.model';

export const sampleWithRequiredData: IRequestConfig = {
  id: 19488,
};

export const sampleWithPartialData: IRequestConfig = {
  id: 20674,
  effectiveDateEnd: dayjs('2025-02-21'),
  createdAt: dayjs('2025-02-21'),
  createdBy: 'alphabetize',
};

export const sampleWithFullData: IRequestConfig = {
  id: 24145,
  version: 'till',
  effectiveDateStart: dayjs('2025-02-21'),
  effectiveDateEnd: dayjs('2025-02-20'),
  createdAt: dayjs('2025-02-21'),
  createdBy: 'viciously saloon fussy',
  updatedAt: dayjs('2025-02-21'),
  updatedBy: 'as exactly',
};

export const sampleWithNewData: NewRequestConfig = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
