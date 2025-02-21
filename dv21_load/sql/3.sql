CREATE SCHEMA IF NOT EXISTS tent;
CREATE TYPE tent.ps_type_enum as ENUM (
'Аннуитет'
,'равными долями'
,'По графику'
);

 -- start dog_info
CREATE TABLE IF NOT EXISTS  tent.dog_info(
		id integer PRIMARY KEY
		,number varchar(40) not null
		,date_start date not null 
		,date_sign date
		,date_maturity date
		,date_maturity_shift date
		,product_type integer not null 
		,duration_days integer
		,shift_to_work integer
		,amount numeric(18,8)
		,aim_main integer references tent.aim( id )
		,currency integer references tent.currency( id )
		,effective_date_start date
		,effective_date_end date
		,created_at date
		,created_by varchar(64)
		,updated_at date
		,updated_by varchar(64)
);
 -- comments for dog_info
COMMENT ON TABLE tent.dog_info IS 'Базовые параметры';
COMMENT ON COLUMN tent.dog_info.id IS 'Базовые параметры первичный ключ';
COMMENT ON COLUMN tent.dog_info.number IS 'Номер договора';
COMMENT ON COLUMN tent.dog_info.date_start IS 'Дата начала';
COMMENT ON COLUMN tent.dog_info.date_sign IS 'Дата подписания';
COMMENT ON COLUMN tent.dog_info.date_maturity IS 'Дата погашения без переноса';
COMMENT ON COLUMN tent.dog_info.date_maturity_shift IS 'Дата погашения с переносом';
COMMENT ON COLUMN tent.dog_info.product_type IS 'Тип продукта';
COMMENT ON COLUMN tent.dog_info.duration_days IS 'Срок договора (дни)';
COMMENT ON COLUMN tent.dog_info.shift_to_work IS 'Параметр переноса даты погашения с выходного дня';
COMMENT ON COLUMN tent.dog_info.amount IS 'Сумма договора';
COMMENT ON COLUMN tent.dog_info.aim_main IS 'Цель основная';
COMMENT ON COLUMN tent.dog_info.currency IS 'Валюта основная';

 -- end dog_info
 -- start dog_rates
CREATE TABLE IF NOT EXISTS  tent.dog_rates(
		id integer PRIMARY KEY
		,dog_infoid integer not null references tent.dog_info(id) on delete cascade
);
 -- comments for dog_rates
COMMENT ON TABLE tent.dog_rates IS 'Ставки';
COMMENT ON COLUMN tent.dog_rates.id IS 'Ставки первичный ключ';
COMMENT ON COLUMN tent.dog_rates.dog_infoid IS ' ссылка на родительскую таблицу Базовые параметры';

 -- end dog_rates

 -- start dog_ps
CREATE TABLE IF NOT EXISTS  tent.dog_ps(
		id integer PRIMARY KEY
		,dog_infoid integer not null references tent.dog_info(id) on delete cascade
		,ps integer not null references tent.ps_info( id )
		,ps_type tent.ps_type_enum not null
		,pay_date integer not null 
);
 -- comments for dog_ps
COMMENT ON TABLE tent.dog_ps IS 'ГПП';
COMMENT ON COLUMN tent.dog_ps.id IS 'ГПП первичный ключ';
COMMENT ON COLUMN tent.dog_ps.dog_infoid IS ' ссылка на родительскую таблицу Базовые параметры';
COMMENT ON COLUMN tent.dog_ps.ps IS 'ГПП ссылка';
COMMENT ON COLUMN tent.dog_ps.ps_type IS 'Тип графика';
COMMENT ON COLUMN tent.dog_ps.pay_date IS 'День платежа';

 -- end dog_ps




