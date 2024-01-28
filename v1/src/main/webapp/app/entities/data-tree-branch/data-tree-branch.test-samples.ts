import { StereoTypeEnum } from 'app/entities/enumerations/stereo-type-enum.model';

import { IDataTreeBranch, NewDataTreeBranch } from './data-tree-branch.model';

export const sampleWithRequiredData: IDataTreeBranch = {
  id: 57485,
};

export const sampleWithPartialData: IDataTreeBranch = {
  id: 14574,
};

export const sampleWithFullData: IDataTreeBranch = {
  id: 91840,
  stereoType: 'COLLECTION',
  name: 'South transitional Netherlands',
  caption: 'impactful Other',
  documentation: 'navigating',
};

export const sampleWithNewData: NewDataTreeBranch = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
