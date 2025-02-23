import dayjs from 'dayjs/esm';

import { IRequestContentConfig, NewRequestContentConfig } from './request-content-config.model';

export const sampleWithRequiredData: IRequestContentConfig = {
  id: 18469,
};

export const sampleWithPartialData: IRequestContentConfig = {
  id: 13434,
  isMandatory: false,
  createdBy: 'canonise from phooey',
  updatedAt: dayjs('2025-02-20'),
};

export const sampleWithFullData: IRequestContentConfig = {
  id: 8789,
  isMandatory: false,
  createdAt: dayjs('2025-02-21'),
  createdBy: 'yum onto',
  updatedAt: dayjs('2025-02-21'),
  updatedBy: 'near understated',
};

export const sampleWithNewData: NewRequestContentConfig = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
