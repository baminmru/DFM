import { IDataField } from 'app/entities/data-field/data-field.model';
import { StereoTypeEnum } from 'app/entities/enumerations/stereo-type-enum.model';

export interface IDataTreeLeaf {
  id: number;
  stereoType?: keyof typeof StereoTypeEnum | null;
  name?: string | null;
  caption?: string | null;
  documentation?: string | null;
  leafToFields?: Pick<IDataField, 'id'>[] | null;
}

export type NewDataTreeLeaf = Omit<IDataTreeLeaf, 'id'> & { id: null };
