export interface IRequestParamDict {
  id: number;
  code?: string | null;
  name?: string | null;
  valueArray?: boolean | null;
  referenceTo?: string | null;
}

export type NewRequestParamDict = Omit<IRequestParamDict, 'id'> & { id: null };
