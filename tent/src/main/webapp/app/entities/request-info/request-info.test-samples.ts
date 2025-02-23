import dayjs from 'dayjs/esm';

import { IRequestInfo, NewRequestInfo } from './request-info.model';

export const sampleWithRequiredData: IRequestInfo = {
  id: 21637,
  contract: 30542,
  requestDate: dayjs('2025-02-23'),
  codeAtSource: 'brr',
};

export const sampleWithPartialData: IRequestInfo = {
  id: 27219,
  contract: 6513,
  requestDate: dayjs('2025-02-22'),
  codeAtSource: 'gah',
  createdBy: 'jol',
  updatedBy: 'readily unionise',
};

export const sampleWithFullData: IRequestInfo = {
  id: 22921,
  contract: 30121,
  requestDate: dayjs('2025-02-23'),
  codeAtSource: 'nutty over',
  effectiveDateStart: dayjs('2025-02-23'),
  effectiveDateEnd: dayjs('2025-02-23'),
  createdAt: dayjs('2025-02-23'),
  createdBy: 'recklessly screamer pardon',
  updatedAt: dayjs('2025-02-23'),
  updatedBy: 'gee pester phew',
};

export const sampleWithNewData: NewRequestInfo = {
  contract: 5470,
  requestDate: dayjs('2025-02-22'),
  codeAtSource: 'fragment phooey',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
