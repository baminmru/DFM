import { IRequestContent, NewRequestContent } from './request-content.model';

export const sampleWithRequiredData: IRequestContent = {
  id: 23969,
  paramCode: 'and',
};

export const sampleWithPartialData: IRequestContent = {
  id: 25241,
  paramCode: 'well-to-do',
  paramValue: 'open than',
};

export const sampleWithFullData: IRequestContent = {
  id: 9883,
  paramCode: 'stow',
  paramValue: 'replication trustworthy',
};

export const sampleWithNewData: NewRequestContent = {
  paramCode: 'guilty onto',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
