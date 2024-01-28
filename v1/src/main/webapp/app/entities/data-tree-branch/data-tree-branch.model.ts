import { IDataTreeLeaf } from 'app/entities/data-tree-leaf/data-tree-leaf.model';
import { IDataField } from 'app/entities/data-field/data-field.model';
import { StereoTypeEnum } from 'app/entities/enumerations/stereo-type-enum.model';

export interface IDataTreeBranch {
  id: number;
  stereoType?: keyof typeof StereoTypeEnum | null;
  name?: string | null;
  caption?: string | null;
  documentation?: string | null;
  dataTreeLeaf?: Pick<IDataTreeLeaf, 'id'> | null;
  branchToFields?: Pick<IDataField, 'id'>[] | null;
  branchParents?: Pick<IDataTreeBranch, 'id'>[] | null;
  branchChildren?: Pick<IDataTreeBranch, 'id'>[] | null;
}

export type NewDataTreeBranch = Omit<IDataTreeBranch, 'id'> & { id: null };
