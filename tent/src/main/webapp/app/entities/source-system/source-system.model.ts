import dayjs from 'dayjs/esm';

export interface ISourceSystem {
  id: number;
  code?: string | null;
  name?: string | null;
  createdAt?: dayjs.Dayjs | null;
  createdBy?: string | null;
  updatedAt?: dayjs.Dayjs | null;
  updatedBy?: string | null;
}

export type NewSourceSystem = Omit<ISourceSystem, 'id'> & { id: null };
