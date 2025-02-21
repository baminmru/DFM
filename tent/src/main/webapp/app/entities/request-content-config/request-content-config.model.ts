export interface IRequestContentConfig {
  id: number;
  requestConfigId?: number | null;
  parameter?: number | null;
  isMandatory?: boolean | null;
}

export type NewRequestContentConfig = Omit<IRequestContentConfig, 'id'> & { id: null };
