import { IRequestContentConfig } from 'app/entities/request-content-config/request-content-config.model';

export interface IRequestConfig {
  id: number;
  requestType?: number | null;
  requestContentConfig?: IRequestContentConfig | null;
}

export type NewRequestConfig = Omit<IRequestConfig, 'id'> & { id: null };
