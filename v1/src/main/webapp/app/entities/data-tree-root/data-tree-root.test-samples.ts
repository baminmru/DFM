import { StereoTypeEnum } from 'app/entities/enumerations/stereo-type-enum.model';

import { IDataTreeRoot, NewDataTreeRoot } from './data-tree-root.model';

export const sampleWithRequiredData: IDataTreeRoot = {
  id: 46822,
};

export const sampleWithPartialData: IDataTreeRoot = {
  id: 78182,
  stereoType: 'SINGLE_ROW',
  name: 'Human',
  caption: 'West',
};

export const sampleWithFullData: IDataTreeRoot = {
  id: 21045,
  stereoType: 'COLLECTION',
  name: 'Liaison hertz Ouguiya',
  caption: 'Keyboard Oregon',
  documentation: 'HTTP Kina',
};

export const sampleWithNewData: NewDataTreeRoot = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
