import dayjs from 'dayjs/esm';

import { IRequestConfig, NewRequestConfig } from './request-config.model';

export const sampleWithRequiredData: IRequestConfig = {
  id: 12389,
};

export const sampleWithPartialData: IRequestConfig = {
  id: 12397,
  createdAt: dayjs('2025-02-22'),
  updatedAt: dayjs('2025-02-23'),
};

export const sampleWithFullData: IRequestConfig = {
  id: 842,
  version: 'fooey ick',
  effectiveDateStart: dayjs('2025-02-23'),
  effectiveDateEnd: dayjs('2025-02-23'),
  createdAt: dayjs('2025-02-23'),
  createdBy: 'keyboard round',
  updatedAt: dayjs('2025-02-23'),
  updatedBy: 'collard yahoo bathroom',
};

export const sampleWithNewData: NewRequestConfig = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
