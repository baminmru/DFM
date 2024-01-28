import { StereoTypeEnum } from 'app/entities/enumerations/stereo-type-enum.model';

import { IDataTreeLeaf, NewDataTreeLeaf } from './data-tree-leaf.model';

export const sampleWithRequiredData: IDataTreeLeaf = {
  id: 71625,
  stereoType: 'TREE',
  name: 'Streets',
  caption: 'Tactics violently',
};

export const sampleWithPartialData: IDataTreeLeaf = {
  id: 10270,
  stereoType: 'COLLECTION',
  name: 'Ball',
  caption: 'integrated Michigan',
};

export const sampleWithFullData: IDataTreeLeaf = {
  id: 33340,
  stereoType: 'COLLECTION',
  name: 'Shoes',
  caption: 'Transexual plum openly',
  documentation: 'how',
};

export const sampleWithNewData: NewDataTreeLeaf = {
  stereoType: 'COLLECTION',
  name: 'methodical Kids',
  caption: 'Borders',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
