import { IAuthority, NewAuthority } from './authority.model';

export const sampleWithRequiredData: IAuthority = {
  name: 'b55128d4-f958-4259-8ec9-a77d925b54a5',
};

export const sampleWithPartialData: IAuthority = {
  name: 'db372333-3766-4ed3-b9b8-5e47400276ad',
};

export const sampleWithFullData: IAuthority = {
  name: '63df592b-364f-4ad6-9dd7-32140d6da94e',
};

export const sampleWithNewData: NewAuthority = {
  name: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
