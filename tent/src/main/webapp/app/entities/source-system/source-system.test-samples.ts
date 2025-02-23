import dayjs from 'dayjs/esm';

import { ISourceSystem, NewSourceSystem } from './source-system.model';

export const sampleWithRequiredData: ISourceSystem = {
  id: 176,
  code: 'since',
};

export const sampleWithPartialData: ISourceSystem = {
  id: 32639,
  code: 'considering',
  createdBy: 'among',
  updatedAt: dayjs('2025-02-23'),
};

export const sampleWithFullData: ISourceSystem = {
  id: 27252,
  code: 'yahoo',
  name: 'supervision guilty',
  createdAt: dayjs('2025-02-22'),
  createdBy: 'neuropathologist moot',
  updatedAt: dayjs('2025-02-23'),
  updatedBy: 'though yippee plus',
};

export const sampleWithNewData: NewSourceSystem = {
  code: 'at furiously railroad',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
