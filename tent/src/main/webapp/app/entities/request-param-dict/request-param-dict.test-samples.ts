import { IRequestParamDict, NewRequestParamDict } from './request-param-dict.model';

export const sampleWithRequiredData: IRequestParamDict = {
  id: 2506,
  name: 'atop',
};

export const sampleWithPartialData: IRequestParamDict = {
  id: 2919,
  name: 'urgently',
  paramtype: 'abdomen cloak transfigure',
};

export const sampleWithFullData: IRequestParamDict = {
  id: 2221,
  code: 'lest',
  name: 'astride yieldingly',
  paramtype: 'decision rake recognize',
  valueArray: true,
  referenceTo: 'membership within',
};

export const sampleWithNewData: NewRequestParamDict = {
  name: 'freely ha ew',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
