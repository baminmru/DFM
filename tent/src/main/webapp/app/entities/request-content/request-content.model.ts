import dayjs from 'dayjs/esm';
import { IRequestInfo } from 'app/entities/request-info/request-info.model';

export interface IRequestContent {
  id: number;
  paramCode?: string | null;
  paramValue?: string | null;
  createdAt?: dayjs.Dayjs | null;
  createdBy?: string | null;
  updatedAt?: dayjs.Dayjs | null;
  updatedBy?: string | null;
  requestInfoId?: IRequestInfo | null;
}

export type NewRequestContent = Omit<IRequestContent, 'id'> & { id: null };
