import { IRequestContent, NewRequestContent } from './request-content.model';

export const sampleWithRequiredData: IRequestContent = {
  id: 14452,
  requestInfoId: 19724,
  paramCode: 'whether',
};

export const sampleWithPartialData: IRequestContent = {
  id: 32329,
  requestInfoId: 25629,
  paramCode: 'outrageous unlike hmph',
};

export const sampleWithFullData: IRequestContent = {
  id: 9012,
  requestInfoId: 736,
  paramCode: 'for unwilling tender',
  paramValue: 'during eek accurate',
};

export const sampleWithNewData: NewRequestContent = {
  requestInfoId: 1872,
  paramCode: 'wing eek',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
