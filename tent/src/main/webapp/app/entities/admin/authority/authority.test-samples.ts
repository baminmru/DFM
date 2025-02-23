import { IAuthority, NewAuthority } from './authority.model';

export const sampleWithRequiredData: IAuthority = {
  name: 'd50f6ab6-f68c-46b5-a351-2ec4c1a776fb',
};

export const sampleWithPartialData: IAuthority = {
  name: '9a3ba791-5a40-4a4d-b7b5-58d7a5856187',
};

export const sampleWithFullData: IAuthority = {
  name: '4dd8e3e8-130d-432e-9ab1-11b136474cb6',
};

export const sampleWithNewData: NewAuthority = {
  name: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
