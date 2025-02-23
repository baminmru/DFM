import dayjs from 'dayjs/esm';

import { IRequestContent, NewRequestContent } from './request-content.model';

export const sampleWithRequiredData: IRequestContent = {
  id: 24859,
  paramCode: 'hybridization aw',
};

export const sampleWithPartialData: IRequestContent = {
  id: 8817,
  paramCode: 'throne nippy',
  paramValue: 'beyond spandex',
  createdAt: dayjs('2025-02-20'),
  updatedBy: 'babyish ah',
};

export const sampleWithFullData: IRequestContent = {
  id: 17847,
  paramCode: 'vein gah consequently',
  paramValue: 'bitterly string dimly',
  createdAt: dayjs('2025-02-21'),
  createdBy: 'which',
  updatedAt: dayjs('2025-02-20'),
  updatedBy: 'anenst even',
};

export const sampleWithNewData: NewRequestContent = {
  paramCode: 'broadly',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
