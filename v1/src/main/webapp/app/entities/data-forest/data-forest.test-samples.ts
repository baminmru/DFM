import { IDataForest, NewDataForest } from './data-forest.model';

export const sampleWithRequiredData: IDataForest = {
  id: 80118,
};

export const sampleWithPartialData: IDataForest = {
  id: 63621,
  name: 'Buckinghamshire Lehner grey',
  caption: 'API male female',
  documentation: 'Assistant cost',
};

export const sampleWithFullData: IDataForest = {
  id: 21091,
  name: 'Concrete boiling',
  caption: 'unless',
  documentation: 'minima Tobago',
};

export const sampleWithNewData: NewDataForest = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
