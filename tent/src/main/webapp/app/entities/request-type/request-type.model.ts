export interface IRequestType {
  id: number;
  code?: string | null;
  name?: string | null;
}

export type NewRequestType = Omit<IRequestType, 'id'> & { id: null };
