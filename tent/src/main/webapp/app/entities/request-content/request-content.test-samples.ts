import dayjs from 'dayjs/esm';

import { IRequestContent, NewRequestContent } from './request-content.model';

export const sampleWithRequiredData: IRequestContent = {
  id: 30374,
  paramCode: 'how',
};

export const sampleWithPartialData: IRequestContent = {
  id: 11191,
  paramCode: 'carelessly',
  createdAt: dayjs('2025-02-21'),
};

export const sampleWithFullData: IRequestContent = {
  id: 8602,
  paramCode: 'icky loyally of',
  paramValue: 'willing',
  createdAt: dayjs('2025-02-21'),
  createdBy: 'boohoo since',
  updatedAt: dayjs('2025-02-21'),
  updatedBy: 'minor internationalise monthly',
};

export const sampleWithNewData: NewRequestContent = {
  paramCode: 'nitrogen when diligently',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
