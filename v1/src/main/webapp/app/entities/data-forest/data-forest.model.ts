import { IDataTreeRoot } from 'app/entities/data-tree-root/data-tree-root.model';

export interface IDataForest {
  id: number;
  name?: string | null;
  caption?: string | null;
  documentation?: string | null;
  forestTrees?: Pick<IDataTreeRoot, 'id'> | null;
}

export type NewDataForest = Omit<IDataForest, 'id'> & { id: null };
