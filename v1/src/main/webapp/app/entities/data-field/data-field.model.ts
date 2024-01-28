import { IDataTreeRoot } from 'app/entities/data-tree-root/data-tree-root.model';
import { InputTypeEnum } from 'app/entities/enumerations/input-type-enum.model';
import { FieldTypeEnum } from 'app/entities/enumerations/field-type-enum.model';

export interface IDataField {
  id: number;
  inputType?: keyof typeof InputTypeEnum | null;
  fieldType?: keyof typeof FieldTypeEnum | null;
  sequence?: number | null;
  isBrief?: boolean | null;
  briefSequence?: number | null;
  allowNull?: boolean | null;
  name?: string | null;
  caption?: string | null;
  documentation?: string | null;
  tabName?: string | null;
  groupName?: string | null;
  generationStyle?: string | null;
  refToRoot?: Pick<IDataTreeRoot, 'id'> | null;
}

export type NewDataField = Omit<IDataField, 'id'> & { id: null };
