import dayjs from 'dayjs/esm';
import { IRequestConfig } from 'app/entities/request-config/request-config.model';
import { IRequestParamDict } from 'app/entities/request-param-dict/request-param-dict.model';

export interface IRequestContentConfig {
  id: number;
  isMandatory?: boolean | null;
  createdAt?: dayjs.Dayjs | null;
  createdBy?: string | null;
  updatedAt?: dayjs.Dayjs | null;
  updatedBy?: string | null;
  requestConfigId?: IRequestConfig | null;
  parameter?: IRequestParamDict | null;
}

export type NewRequestContentConfig = Omit<IRequestContentConfig, 'id'> & { id: null };
