import { IUser } from './user.model';

export const sampleWithRequiredData: IUser = {
  id: 12264,
  login: 'f9',
};

export const sampleWithPartialData: IUser = {
  id: 30081,
  login: 'lS@8\\&RG\\mnn2Vp8',
};

export const sampleWithFullData: IUser = {
  id: 13861,
  login: 'Sp26R6',
};
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
