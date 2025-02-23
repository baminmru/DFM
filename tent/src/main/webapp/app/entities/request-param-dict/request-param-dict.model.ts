import dayjs from 'dayjs/esm';

export interface IRequestParamDict {
  id: number;
  code?: string | null;
  name?: string | null;
  paramtype?: string | null;
  valueArray?: boolean | null;
  referenceTo?: string | null;
  createdAt?: dayjs.Dayjs | null;
  createdBy?: string | null;
  updatedAt?: dayjs.Dayjs | null;
  updatedBy?: string | null;
}

export type NewRequestParamDict = Omit<IRequestParamDict, 'id'> & { id: null };
