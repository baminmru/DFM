import { IDataTreeLeaf } from 'app/entities/data-tree-leaf/data-tree-leaf.model';
import { IDataTreeBranchToField } from 'app/entities/data-tree-branch-to-field/data-tree-branch-to-field.model';
import { IDataTreeBranchLink } from 'app/entities/data-tree-branch-link/data-tree-branch-link.model';
import { StereoTypeEnum } from 'app/entities/enumerations/stereo-type-enum.model';

export interface IDataTreeBranch {
  id: number;
  stereoType?: keyof typeof StereoTypeEnum | null;
  name?: string | null;
  caption?: string | null;
  documentation?: string | null;
  dataTreeLeaf?: Pick<IDataTreeLeaf, 'id'> | null;
  branchToField?: Pick<IDataTreeBranchToField, 'id'> | null;
  branchParent?: Pick<IDataTreeBranchLink, 'id'> | null;
}

export type NewDataTreeBranch = Omit<IDataTreeBranch, 'id'> & { id: null };
