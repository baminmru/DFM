import { IRequestContentConfig, NewRequestContentConfig } from './request-content-config.model';

export const sampleWithRequiredData: IRequestContentConfig = {
  id: 20080,
};

export const sampleWithPartialData: IRequestContentConfig = {
  id: 26647,
};

export const sampleWithFullData: IRequestContentConfig = {
  id: 29644,
  isMandatory: true,
};

export const sampleWithNewData: NewRequestContentConfig = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
