import { IDataTreeBranch } from 'app/entities/data-tree-branch/data-tree-branch.model';
import { IDataField } from 'app/entities/data-field/data-field.model';
import { StereoTypeEnum } from 'app/entities/enumerations/stereo-type-enum.model';

export interface IDataTreeRoot {
  id: number;
  stereoType?: keyof typeof StereoTypeEnum | null;
  name?: string | null;
  caption?: string | null;
  documentation?: string | null;
  dataTreeBranch?: Pick<IDataTreeBranch, 'id'> | null;
  rootToFields?: Pick<IDataField, 'id'>[] | null;
}

export type NewDataTreeRoot = Omit<IDataTreeRoot, 'id'> & { id: null };
