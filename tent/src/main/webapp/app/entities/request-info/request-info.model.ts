import dayjs from 'dayjs/esm';
import { IRequestType } from 'app/entities/request-type/request-type.model';
import { ISourceSystem } from 'app/entities/source-system/source-system.model';

export interface IRequestInfo {
  id: number;
  contract?: number | null;
  requestDate?: dayjs.Dayjs | null;
  codeAtSource?: string | null;
  effectiveDateStart?: dayjs.Dayjs | null;
  effectiveDateEnd?: dayjs.Dayjs | null;
  createdAt?: dayjs.Dayjs | null;
  createdBy?: string | null;
  updatedAt?: dayjs.Dayjs | null;
  updatedBy?: string | null;
  requestType?: IRequestType | null;
  requestSource?: ISourceSystem | null;
}

export type NewRequestInfo = Omit<IRequestInfo, 'id'> & { id: null };
