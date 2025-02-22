import dayjs from 'dayjs/esm';
import { IRequestType } from 'app/entities/request-type/request-type.model';

export interface IRequestInfo {
  id: number;
  contract?: number | null;
  requestDate?: dayjs.Dayjs | null;
  effectiveDateStart?: dayjs.Dayjs | null;
  effectiveDateEnd?: dayjs.Dayjs | null;
  createdAt?: dayjs.Dayjs | null;
  createdBy?: string | null;
  updatedAt?: dayjs.Dayjs | null;
  updatedBy?: string | null;
  requestType?: IRequestType | null;
}

export type NewRequestInfo = Omit<IRequestInfo, 'id'> & { id: null };
