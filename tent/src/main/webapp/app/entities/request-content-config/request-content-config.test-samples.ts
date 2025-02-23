import dayjs from 'dayjs/esm';

import { IRequestContentConfig, NewRequestContentConfig } from './request-content-config.model';

export const sampleWithRequiredData: IRequestContentConfig = {
  id: 21276,
};

export const sampleWithPartialData: IRequestContentConfig = {
  id: 28600,
  isMandatory: true,
  createdAt: dayjs('2025-02-20'),
  updatedAt: dayjs('2025-02-21'),
};

export const sampleWithFullData: IRequestContentConfig = {
  id: 18594,
  isMandatory: false,
  createdAt: dayjs('2025-02-21'),
  createdBy: 'finally',
  updatedAt: dayjs('2025-02-21'),
  updatedBy: 'everyone circa bah',
};

export const sampleWithNewData: NewRequestContentConfig = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
