import { IRequestContentConfig, NewRequestContentConfig } from './request-content-config.model';

export const sampleWithRequiredData: IRequestContentConfig = {
  id: 170,
  requestConfigId: 24037,
};

export const sampleWithPartialData: IRequestContentConfig = {
  id: 25891,
  requestConfigId: 24889,
  isMandatory: true,
};

export const sampleWithFullData: IRequestContentConfig = {
  id: 25263,
  requestConfigId: 12834,
  parameter: 6203,
  isMandatory: false,
};

export const sampleWithNewData: NewRequestContentConfig = {
  requestConfigId: 11617,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
