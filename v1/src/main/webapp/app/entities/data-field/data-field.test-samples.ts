import { InputTypeEnum } from 'app/entities/enumerations/input-type-enum.model';
import { FieldTypeEnum } from 'app/entities/enumerations/field-type-enum.model';

import { IDataField, NewDataField } from './data-field.model';

export const sampleWithRequiredData: IDataField = {
  id: 94973,
};

export const sampleWithPartialData: IDataField = {
  id: 76522,
  fieldType: 'STRING_TYPE',
  allowNull: false,
  name: 'Pop Car Seamless',
};

export const sampleWithFullData: IDataField = {
  id: 56485,
  inputType: 'DATETIME_INPUT',
  fieldType: 'INTEGER_TYPE',
  referenceRoot: 'Convertible',
  allowNull: false,
  name: 'Fish',
  caption: 'migration Concrete UDP',
  documentation: 'Kertzmann',
};

export const sampleWithNewData: NewDataField = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
