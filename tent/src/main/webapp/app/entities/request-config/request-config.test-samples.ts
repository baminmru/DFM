import { IRequestConfig, NewRequestConfig } from './request-config.model';

export const sampleWithRequiredData: IRequestConfig = {
  id: 18075,
  requestType: 4662,
};

export const sampleWithPartialData: IRequestConfig = {
  id: 22162,
  requestType: 29764,
};

export const sampleWithFullData: IRequestConfig = {
  id: 3461,
  requestType: 26022,
};

export const sampleWithNewData: NewRequestConfig = {
  requestType: 7334,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
