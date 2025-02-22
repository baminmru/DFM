import { IRequestParamDict, NewRequestParamDict } from './request-param-dict.model';

export const sampleWithRequiredData: IRequestParamDict = {
  id: 18996,
  name: 'indeed ick',
};

export const sampleWithPartialData: IRequestParamDict = {
  id: 29140,
  name: 'abdomen cloak transfigure',
  valueArray: true,
  referenceTo: 'lest',
};

export const sampleWithFullData: IRequestParamDict = {
  id: 16717,
  code: 'optimist',
  name: 'ugh when',
  valueArray: true,
  referenceTo: 'matchmaker front',
};

export const sampleWithNewData: NewRequestParamDict = {
  name: 'that drat',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
