import { IDataTreeBranch } from 'app/entities/data-tree-branch/data-tree-branch.model';
import { IDataTreeRootToField } from 'app/entities/data-tree-root-to-field/data-tree-root-to-field.model';
import { StereoTypeEnum } from 'app/entities/enumerations/stereo-type-enum.model';

export interface IDataTreeRoot {
  id: number;
  stereoType?: keyof typeof StereoTypeEnum | null;
  name?: string | null;
  caption?: string | null;
  documentation?: string | null;
  dataTreeBranch?: Pick<IDataTreeBranch, 'id'> | null;
  rootToField?: Pick<IDataTreeRootToField, 'id'> | null;
}

export type NewDataTreeRoot = Omit<IDataTreeRoot, 'id'> & { id: null };
