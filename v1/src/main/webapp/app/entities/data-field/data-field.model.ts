import { IDataTreeRoot } from 'app/entities/data-tree-root/data-tree-root.model';
import { IDataTreeBranch } from 'app/entities/data-tree-branch/data-tree-branch.model';
import { IDataTreeLeaf } from 'app/entities/data-tree-leaf/data-tree-leaf.model';
import { InputTypeEnum } from 'app/entities/enumerations/input-type-enum.model';
import { FieldTypeEnum } from 'app/entities/enumerations/field-type-enum.model';

export interface IDataField {
  id: number;
  inputType?: keyof typeof InputTypeEnum | null;
  fieldType?: keyof typeof FieldTypeEnum | null;
  referenceRoot?: string | null;
  allowNull?: boolean | null;
  name?: string | null;
  caption?: string | null;
  documentation?: string | null;
  dataTreeRoots?: Pick<IDataTreeRoot, 'id'>[] | null;
  dataTreeBranches?: Pick<IDataTreeBranch, 'id'>[] | null;
  dataTreeLeaves?: Pick<IDataTreeLeaf, 'id'>[] | null;
}

export type NewDataField = Omit<IDataField, 'id'> & { id: null };
