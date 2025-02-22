import dayjs from 'dayjs/esm';

import { IRequestInfo, NewRequestInfo } from './request-info.model';

export const sampleWithRequiredData: IRequestInfo = {
  id: 26968,
  requestDate: dayjs('2025-02-21'),
};

export const sampleWithPartialData: IRequestInfo = {
  id: 27814,
  contract: 14735,
  requestDate: dayjs('2025-02-21'),
  effectiveDateStart: dayjs('2025-02-21'),
  effectiveDateEnd: dayjs('2025-02-20'),
  updatedAt: dayjs('2025-02-21'),
  updatedBy: 'onto',
};

export const sampleWithFullData: IRequestInfo = {
  id: 4096,
  contract: 3989,
  requestDate: dayjs('2025-02-21'),
  effectiveDateStart: dayjs('2025-02-21'),
  effectiveDateEnd: dayjs('2025-02-20'),
  createdAt: dayjs('2025-02-21'),
  createdBy: 'consist',
  updatedAt: dayjs('2025-02-21'),
  updatedBy: 'psst white',
};

export const sampleWithNewData: NewRequestInfo = {
  requestDate: dayjs('2025-02-21'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
