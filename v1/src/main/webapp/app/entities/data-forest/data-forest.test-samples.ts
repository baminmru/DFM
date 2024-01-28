import { IDataForest, NewDataForest } from './data-forest.model';

export const sampleWithRequiredData: IDataForest = {
  id: 80118,
  name: 'connecting Data Buckinghamshire',
  caption: 'voluptatum',
};

export const sampleWithPartialData: IDataForest = {
  id: 38355,
  name: 'regional API male',
  caption: 'Hampshire Volkswagen Bike',
  documentation: 'Concrete boiling',
};

export const sampleWithFullData: IDataForest = {
  id: 17018,
  name: 'Planner minima Tobago',
  caption: 'Kautzer tan kaleidoscopic',
  documentation: 'red atop Cruiser',
};

export const sampleWithNewData: NewDataForest = {
  name: 'Handmade',
  caption: 'withdrawal',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
