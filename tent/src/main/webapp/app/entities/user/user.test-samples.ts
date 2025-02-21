import { IUser } from './user.model';

export const sampleWithRequiredData: IUser = {
  id: 22428,
  login: 'mDIQy',
};

export const sampleWithPartialData: IUser = {
  id: 2221,
  login: 'gd`g`@nx\\GOCp\\:6zQwv',
};

export const sampleWithFullData: IUser = {
  id: 3595,
  login: 'T',
};
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
