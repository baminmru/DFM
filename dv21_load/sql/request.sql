CREATE SCHEMA IF NOT EXISTS tent;

 -- start request_type
CREATE TABLE IF NOT EXISTS  tent.request_type(
		id integer PRIMARY KEY
		,code varchar(64) not null
		,name varchar(255) not null
		,created_at date
		,created_by varchar(64)
		,updated_at date
		,updated_by varchar(64)
);
 -- comments for request_type
COMMENT ON TABLE tent.request_type IS 'Справочник типов заявок';
COMMENT ON COLUMN tent.request_type.id IS 'Справочник типов заявок первичный ключ';
COMMENT ON COLUMN tent.request_type.code IS 'Код';
COMMENT ON COLUMN tent.request_type.name IS 'Название';

 -- end request_type

 -- start request_info
CREATE TABLE IF NOT EXISTS  tent.request_info(
		id integer PRIMARY KEY
		,request_type integer not null references tent.request_type( id )
		,contract integer not NULL -- references tent.dog_info( id )
		,request_date date not null 
		,request_source integer not null references tent.source_system( id )
		,code_at_source varchar(40) not null
		,effective_date_start date
		,effective_date_end date
		,created_at date
		,created_by varchar(64)
		,updated_at date
		,updated_by varchar(64)
);
 -- comments for request_info
COMMENT ON TABLE tent.request_info IS 'Основная информация';
COMMENT ON COLUMN tent.request_info.id IS 'Основная информация первичный ключ';
COMMENT ON COLUMN tent.request_info.request_type IS 'Тип заявки';
COMMENT ON COLUMN tent.request_info.contract IS 'Контракт';
COMMENT ON COLUMN tent.request_info.request_date IS 'Дата заявки';
COMMENT ON COLUMN tent.request_info.request_source IS 'Источник заявки';
COMMENT ON COLUMN tent.request_info.code_at_source IS 'Код в системе-источнике';

 -- end request_info
 -- start request_content
CREATE TABLE IF NOT EXISTS  tent.request_content(
		id integer PRIMARY KEY
		,request_infoid integer not null references tent.request_info(id) on delete cascade
		,param_code varchar(64) not null
		,param_value varchar(255)
		,created_at date
		,created_by varchar(64)
		,updated_at date
		,updated_by varchar(64)
);
 -- comments for request_content
COMMENT ON TABLE tent.request_content IS 'Содержание заявки';
COMMENT ON COLUMN tent.request_content.id IS 'Содержание заявки первичный ключ';
COMMENT ON COLUMN tent.request_content.request_infoid IS ' ссылка на родительскую таблицу Основная информация';
COMMENT ON COLUMN tent.request_content.param_code IS 'Код параметра';
COMMENT ON COLUMN tent.request_content.param_value IS 'Значение параметра';

 -- end request_content


 -- start request_config
CREATE TABLE IF NOT EXISTS  tent.request_config(
		id integer PRIMARY KEY
		,request_type integer not null references tent.request_type( id )
		,version varchar(40)
		,effective_date_start date
		,effective_date_end date
		,created_at date
		,created_by varchar(64)
		,updated_at date
		,updated_by varchar(64)
);
 -- comments for request_config
COMMENT ON TABLE tent.request_config IS 'Конфигуратор заявки';
COMMENT ON COLUMN tent.request_config.id IS 'Конфигуратор заявки первичный ключ';
COMMENT ON COLUMN tent.request_config.request_type IS 'Тип заявки';
COMMENT ON COLUMN tent.request_config.version IS 'Версия  конфигурации';

 -- end request_config
 -- start request_content_config
CREATE TABLE IF NOT EXISTS  tent.request_content_config(
		id integer PRIMARY KEY
		,request_configid integer not null references tent.request_config(id) on delete cascade
		,parameter integer references tent.request_param_dict( id )
		,is_mandatory boolean
		,created_at date
		,created_by varchar(64)
		,updated_at date
		,updated_by varchar(64)
);
 -- comments for request_content_config
COMMENT ON TABLE tent.request_content_config IS 'Описание параметров заявки';
COMMENT ON COLUMN tent.request_content_config.id IS 'Описание параметров заявки первичный ключ';
COMMENT ON COLUMN tent.request_content_config.request_configid IS ' ссылка на родительскую таблицу Конфигуратор заявки';
COMMENT ON COLUMN tent.request_content_config.parameter IS 'Параметр';
COMMENT ON COLUMN tent.request_content_config.is_mandatory IS 'Обязательный';

 -- end request_content_config


 -- start request_param_dict
CREATE TABLE IF NOT EXISTS  tent.request_param_dict(
		id integer PRIMARY KEY
		,code varchar(40) not null
		,name varchar(255) not null
		,paramtype varchar(64)
		,value_array boolean
		,reference_to varchar(64)
		,created_at date
		,created_by varchar(64)
		,updated_at date
		,updated_by varchar(64)
);
 -- comments for request_param_dict
COMMENT ON TABLE tent.request_param_dict IS 'Список возможных параметров';
COMMENT ON COLUMN tent.request_param_dict.id IS 'Список возможных параметров первичный ключ';
COMMENT ON COLUMN tent.request_param_dict.code IS 'Код';
COMMENT ON COLUMN tent.request_param_dict.name IS 'Название';
COMMENT ON COLUMN tent.request_param_dict.paramtype IS 'Тип параметра';
COMMENT ON COLUMN tent.request_param_dict.value_array IS 'Допускает массив значений';
COMMENT ON COLUMN tent.request_param_dict.reference_to IS 'Описание ссылки';

 -- end request_param_dict

 -- start source_system
CREATE TABLE IF NOT EXISTS  tent.source_system(
		id integer PRIMARY KEY
		,code varchar(40) not null
		,name varchar(255)
		,created_at date
		,created_by varchar(64)
		,updated_at date
		,updated_by varchar(64)
);
 -- comments for source_system
COMMENT ON TABLE tent.source_system IS 'Система источник';
COMMENT ON COLUMN tent.source_system.id IS 'Система источник первичный ключ';
COMMENT ON COLUMN tent.source_system.code IS 'Код';
COMMENT ON COLUMN tent.source_system.name IS 'Название';

 -- end source_system



