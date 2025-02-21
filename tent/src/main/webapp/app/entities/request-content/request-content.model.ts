export interface IRequestContent {
  id: number;
  requestInfoId?: number | null;
  paramCode?: string | null;
  paramValue?: string | null;
}

export type NewRequestContent = Omit<IRequestContent, 'id'> & { id: null };
