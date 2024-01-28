import { StereoTypeEnum } from 'app/entities/enumerations/stereo-type-enum.model';

import { IDataTreeRoot, NewDataTreeRoot } from './data-tree-root.model';

export const sampleWithRequiredData: IDataTreeRoot = {
  id: 46822,
  stereoType: 'COLLECTION',
  name: 'Coordinator blue Southeast',
  caption: 'fooey transmitting Liaison',
};

export const sampleWithPartialData: IDataTreeRoot = {
  id: 28940,
  stereoType: 'COLLECTION',
  name: 'composite Myrtice',
  caption: 'Kina virtual',
  documentation: 'Forks Volvo Kroon',
};

export const sampleWithFullData: IDataTreeRoot = {
  id: 72851,
  stereoType: 'TREE',
  name: 'Southeast',
  caption: 'infomediaries',
  documentation: 'Northwest Interactions',
};

export const sampleWithNewData: NewDataTreeRoot = {
  stereoType: 'SINGLE_ROW',
  name: 'deposit',
  caption: 'Wesley orchestrate Realigned',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
