import { IRequestType, NewRequestType } from './request-type.model';

export const sampleWithRequiredData: IRequestType = {
  id: 6459,
  code: 'to shy yieldingly',
  name: 'ashamed plus',
};

export const sampleWithPartialData: IRequestType = {
  id: 1779,
  code: 'analyst derivation',
  name: 'modulo which',
};

export const sampleWithFullData: IRequestType = {
  id: 3083,
  code: 'ancient boyhood scarily',
  name: 'private disinhibit liken',
};

export const sampleWithNewData: NewRequestType = {
  code: 'about opposite outvote',
  name: 'strictly beseech',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
