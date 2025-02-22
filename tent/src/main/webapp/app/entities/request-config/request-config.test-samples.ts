import { IRequestConfig, NewRequestConfig } from './request-config.model';

export const sampleWithRequiredData: IRequestConfig = {
  id: 22060,
};

export const sampleWithPartialData: IRequestConfig = {
  id: 2456,
};

export const sampleWithFullData: IRequestConfig = {
  id: 25704,
};

export const sampleWithNewData: NewRequestConfig = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
