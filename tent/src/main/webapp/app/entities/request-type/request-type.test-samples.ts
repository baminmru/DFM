import dayjs from 'dayjs/esm';

import { IRequestType, NewRequestType } from './request-type.model';

export const sampleWithRequiredData: IRequestType = {
  id: 25977,
  code: 'golden',
  name: 'gigantic',
};

export const sampleWithPartialData: IRequestType = {
  id: 21087,
  code: 'geez although surcharge',
  name: 'hallway step-mother harness',
  createdBy: 'supposing how',
  updatedBy: 'fairly',
};

export const sampleWithFullData: IRequestType = {
  id: 20844,
  code: 'selfishly softly',
  name: 'when colorless',
  createdAt: dayjs('2025-02-21'),
  createdBy: 'apropos',
  updatedAt: dayjs('2025-02-21'),
  updatedBy: 'fooey yahoo praises',
};

export const sampleWithNewData: NewRequestType = {
  code: 'epauliere',
  name: 'arbitrate uh-huh indeed',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
