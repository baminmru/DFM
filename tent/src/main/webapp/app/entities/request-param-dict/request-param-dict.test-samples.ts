import dayjs from 'dayjs/esm';

import { IRequestParamDict, NewRequestParamDict } from './request-param-dict.model';

export const sampleWithRequiredData: IRequestParamDict = {
  id: 32370,
  code: 'unlike than',
  name: 'in drain drown',
};

export const sampleWithPartialData: IRequestParamDict = {
  id: 2101,
  code: 'forenenst empty',
  name: 'our',
  createdAt: dayjs('2025-02-20'),
  createdBy: 'till',
};

export const sampleWithFullData: IRequestParamDict = {
  id: 16915,
  code: 'headline',
  name: 'create concerning tack',
  paramtype: 'outflank old',
  valueArray: false,
  referenceTo: 'rosy',
  createdAt: dayjs('2025-02-21'),
  createdBy: 'timely',
  updatedAt: dayjs('2025-02-20'),
  updatedBy: 'beautifully imagine',
};

export const sampleWithNewData: NewRequestParamDict = {
  code: 'why because afterwards',
  name: 'inferior hub periodic',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
