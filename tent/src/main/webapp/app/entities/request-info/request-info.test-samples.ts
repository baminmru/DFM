import dayjs from 'dayjs/esm';

import { IRequestInfo, NewRequestInfo } from './request-info.model';

export const sampleWithRequiredData: IRequestInfo = {
  id: 7688,
  requestType: 26371,
  requestDate: dayjs('2025-02-20'),
};

export const sampleWithPartialData: IRequestInfo = {
  id: 2230,
  requestType: 4938,
  contract: 16136,
  requestDate: dayjs('2025-02-21'),
  effectiveDateStart: dayjs('2025-02-20'),
  createdAt: dayjs('2025-02-20'),
  createdBy: 'whoever behind butler',
  updatedAt: dayjs('2025-02-20'),
};

export const sampleWithFullData: IRequestInfo = {
  id: 20833,
  requestType: 8442,
  contract: 17026,
  requestDate: dayjs('2025-02-21'),
  effectiveDateStart: dayjs('2025-02-21'),
  effectiveDateEnd: dayjs('2025-02-20'),
  createdAt: dayjs('2025-02-20'),
  createdBy: 'quirkily below highly',
  updatedAt: dayjs('2025-02-20'),
  updatedBy: 'mmm gah',
};

export const sampleWithNewData: NewRequestInfo = {
  requestType: 14720,
  requestDate: dayjs('2025-02-21'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
