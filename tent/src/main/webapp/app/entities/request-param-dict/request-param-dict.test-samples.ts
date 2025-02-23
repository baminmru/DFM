import dayjs from 'dayjs/esm';

import { IRequestParamDict, NewRequestParamDict } from './request-param-dict.model';

export const sampleWithRequiredData: IRequestParamDict = {
  id: 14647,
  code: 'however',
  name: 'cross apricot',
};

export const sampleWithPartialData: IRequestParamDict = {
  id: 26044,
  code: 'kayak although before',
  name: 'beach',
  paramtype: 'elastic',
  createdAt: dayjs('2025-02-21'),
  createdBy: 'about vicious hm',
};

export const sampleWithFullData: IRequestParamDict = {
  id: 10581,
  code: 'exhausted to voluminous',
  name: 'except',
  paramtype: 'off',
  valueArray: false,
  referenceTo: 'after',
  createdAt: dayjs('2025-02-21'),
  createdBy: 'bunch down',
  updatedAt: dayjs('2025-02-21'),
  updatedBy: 'why woot gorgeous',
};

export const sampleWithNewData: NewRequestParamDict = {
  code: 'blowgun',
  name: 'rout unfit influence',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
