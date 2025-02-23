import dayjs from 'dayjs/esm';

import { IRequestInfo, NewRequestInfo } from './request-info.model';

export const sampleWithRequiredData: IRequestInfo = {
  id: 28182,
  contract: 601,
  requestDate: dayjs('2025-02-20'),
  codeAtSource: 'but mayor',
};

export const sampleWithPartialData: IRequestInfo = {
  id: 15331,
  contract: 24351,
  requestDate: dayjs('2025-02-21'),
  codeAtSource: 'when following before',
  effectiveDateEnd: dayjs('2025-02-21'),
  createdAt: dayjs('2025-02-21'),
  updatedAt: dayjs('2025-02-21'),
  updatedBy: 'supposing via painfully',
};

export const sampleWithFullData: IRequestInfo = {
  id: 19218,
  contract: 9668,
  requestDate: dayjs('2025-02-21'),
  codeAtSource: 'vacantly',
  effectiveDateStart: dayjs('2025-02-21'),
  effectiveDateEnd: dayjs('2025-02-21'),
  createdAt: dayjs('2025-02-20'),
  createdBy: 'amidst excepting stunning',
  updatedAt: dayjs('2025-02-21'),
  updatedBy: 'entrench after',
};

export const sampleWithNewData: NewRequestInfo = {
  contract: 2480,
  requestDate: dayjs('2025-02-21'),
  codeAtSource: 'dear carefully',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
