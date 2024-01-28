import { StereoTypeEnum } from 'app/entities/enumerations/stereo-type-enum.model';

import { IDataTreeLeaf, NewDataTreeLeaf } from './data-tree-leaf.model';

export const sampleWithRequiredData: IDataTreeLeaf = {
  id: 71625,
};

export const sampleWithPartialData: IDataTreeLeaf = {
  id: 45786,
  stereoType: 'TREE',
  documentation: 'Nuevo violently Gertrude',
};

export const sampleWithFullData: IDataTreeLeaf = {
  id: 26146,
  stereoType: 'COLLECTION',
  name: 'City',
  caption: 'Rock Shoes',
  documentation: 'Transexual plum openly',
};

export const sampleWithNewData: NewDataTreeLeaf = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
