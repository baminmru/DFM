import { IDataTreeLeafToField } from 'app/entities/data-tree-leaf-to-field/data-tree-leaf-to-field.model';
import { StereoTypeEnum } from 'app/entities/enumerations/stereo-type-enum.model';

export interface IDataTreeLeaf {
  id: number;
  stereoType?: keyof typeof StereoTypeEnum | null;
  name?: string | null;
  caption?: string | null;
  documentation?: string | null;
  leafToField?: Pick<IDataTreeLeafToField, 'id'> | null;
}

export type NewDataTreeLeaf = Omit<IDataTreeLeaf, 'id'> & { id: null };
