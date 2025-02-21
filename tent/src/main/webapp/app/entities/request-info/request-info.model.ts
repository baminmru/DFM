import dayjs from 'dayjs/esm';
import { IRequestContent } from 'app/entities/request-content/request-content.model';

export interface IRequestInfo {
  id: number;
  requestType?: number | null;
  contract?: number | null;
  requestDate?: dayjs.Dayjs | null;
  effectiveDateStart?: dayjs.Dayjs | null;
  effectiveDateEnd?: dayjs.Dayjs | null;
  createdAt?: dayjs.Dayjs | null;
  createdBy?: string | null;
  updatedAt?: dayjs.Dayjs | null;
  updatedBy?: string | null;
  requestContent?: IRequestContent | null;
}

export type NewRequestInfo = Omit<IRequestInfo, 'id'> & { id: null };
