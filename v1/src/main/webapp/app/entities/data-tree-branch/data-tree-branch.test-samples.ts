import { StereoTypeEnum } from 'app/entities/enumerations/stereo-type-enum.model';

import { IDataTreeBranch, NewDataTreeBranch } from './data-tree-branch.model';

export const sampleWithRequiredData: IDataTreeBranch = {
  id: 57485,
  stereoType: 'COLLECTION',
  name: 'Concrete',
  caption: 'than',
};

export const sampleWithPartialData: IDataTreeBranch = {
  id: 384,
  stereoType: 'COLLECTION',
  name: 'and Implementation',
  caption: 'impactful Other',
  documentation: 'navigating',
};

export const sampleWithFullData: IDataTreeBranch = {
  id: 21876,
  stereoType: 'COLLECTION',
  name: 'Buckinghamshire Garden',
  caption: 'firewall',
  documentation: 'lime',
};

export const sampleWithNewData: NewDataTreeBranch = {
  stereoType: 'TREE',
  name: 'up matrix',
  caption: 'firewall Avon',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
