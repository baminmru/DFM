import { IRequestContentConfig } from 'app/entities/request-content-config/request-content-config.model';

export interface IRequestParamDict {
  id: number;
  code?: string | null;
  name?: string | null;
  paramtype?: string | null;
  valueArray?: boolean | null;
  referenceTo?: string | null;
  requestContentConfig?: IRequestContentConfig | null;
}

export type NewRequestParamDict = Omit<IRequestParamDict, 'id'> & { id: null };
