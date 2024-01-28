import { InputTypeEnum } from 'app/entities/enumerations/input-type-enum.model';
import { FieldTypeEnum } from 'app/entities/enumerations/field-type-enum.model';

import { IDataField, NewDataField } from './data-field.model';

export const sampleWithRequiredData: IDataField = {
  id: 34750,
  inputType: 'COMBOBOX_INPUT',
  fieldType: 'DATE_TYPE',
  sequence: 57192,
  isBrief: true,
  allowNull: false,
  name: 'Stamford coliseum Steel',
  caption: 'Pop Car Seamless',
};

export const sampleWithPartialData: IDataField = {
  id: 14676,
  inputType: 'DATETIME_INPUT',
  fieldType: 'STRING_TYPE',
  sequence: 29723,
  isBrief: false,
  briefSequence: 21580,
  allowNull: true,
  name: 'migration Concrete UDP',
  caption: 'Kertzmann',
  tabName: 'Program Southwest impugn',
  generationStyle: 'indexing',
};

export const sampleWithFullData: IDataField = {
  id: 93169,
  inputType: 'TIME_INPUT',
  fieldType: 'DATE_TYPE',
  sequence: 17970,
  isBrief: false,
  briefSequence: 44719,
  allowNull: false,
  name: 'for',
  caption: 'Minivan boohoo rich',
  documentation: 'Hoover',
  tabName: 'Algeria Regional',
  groupName: 'Electric pink Colorado',
  generationStyle: 'Russian Southwest',
};

export const sampleWithNewData: NewDataField = {
  inputType: 'HTML_INPUT',
  fieldType: 'DATE_TYPE',
  sequence: 28599,
  isBrief: true,
  allowNull: false,
  name: 'aside along Technician',
  caption: 'who bemoan Account',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
