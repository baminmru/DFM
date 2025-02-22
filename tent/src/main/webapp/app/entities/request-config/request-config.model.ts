import { IRequestType } from 'app/entities/request-type/request-type.model';

export interface IRequestConfig {
  id: number;
  requestType?: IRequestType | null;
}

export type NewRequestConfig = Omit<IRequestConfig, 'id'> & { id: null };
