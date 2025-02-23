import dayjs from 'dayjs/esm';

export interface IRequestType {
  id: number;
  code?: string | null;
  name?: string | null;
  createdAt?: dayjs.Dayjs | null;
  createdBy?: string | null;
  updatedAt?: dayjs.Dayjs | null;
  updatedBy?: string | null;
}

export type NewRequestType = Omit<IRequestType, 'id'> & { id: null };
