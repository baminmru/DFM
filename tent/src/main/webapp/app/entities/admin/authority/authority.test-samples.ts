import { IAuthority, NewAuthority } from './authority.model';

export const sampleWithRequiredData: IAuthority = {
  name: '80e9e1ab-6911-47b4-be90-b3587f76d255',
};

export const sampleWithPartialData: IAuthority = {
  name: '01fd9dfe-d0cc-45fb-a2be-c865b17ff0c6',
};

export const sampleWithFullData: IAuthority = {
  name: '5383b3cd-adcb-4d25-be5a-e8fd14a99435',
};

export const sampleWithNewData: NewAuthority = {
  name: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
