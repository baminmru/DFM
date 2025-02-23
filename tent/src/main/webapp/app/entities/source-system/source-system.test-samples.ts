import dayjs from 'dayjs/esm';

import { ISourceSystem, NewSourceSystem } from './source-system.model';

export const sampleWithRequiredData: ISourceSystem = {
  id: 3424,
  code: 'playfully pish comfortable',
};

export const sampleWithPartialData: ISourceSystem = {
  id: 23966,
  code: 'bony frightened',
  name: 'lysine sparse',
};

export const sampleWithFullData: ISourceSystem = {
  id: 31630,
  code: 'bottle out',
  name: 'failing',
  createdAt: dayjs('2025-02-22'),
  createdBy: 'wiggly',
  updatedAt: dayjs('2025-02-23'),
  updatedBy: 'mercury',
};

export const sampleWithNewData: NewSourceSystem = {
  code: 'provided whoa',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
