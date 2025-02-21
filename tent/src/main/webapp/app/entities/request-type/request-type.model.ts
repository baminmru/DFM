import { IRequestInfo } from 'app/entities/request-info/request-info.model';
import { IRequestConfig } from 'app/entities/request-config/request-config.model';

export interface IRequestType {
  id: number;
  code?: string | null;
  name?: string | null;
  requestInfo?: IRequestInfo | null;
  requestConfig?: IRequestConfig | null;
}

export type NewRequestType = Omit<IRequestType, 'id'> & { id: null };
