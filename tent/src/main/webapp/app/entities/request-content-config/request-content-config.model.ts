import { IRequestConfig } from 'app/entities/request-config/request-config.model';
import { IRequestParamDict } from 'app/entities/request-param-dict/request-param-dict.model';

export interface IRequestContentConfig {
  id: number;
  isMandatory?: boolean | null;
  requestConfigId?: IRequestConfig | null;
  parameter?: IRequestParamDict | null;
}

export type NewRequestContentConfig = Omit<IRequestContentConfig, 'id'> & { id: null };
