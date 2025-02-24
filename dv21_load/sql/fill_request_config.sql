-- delete from tent.request_param_dict;
INSERT INTO tent.request_param_dict (id, code, "name", paramtype, value_array, reference_to) VALUES(1, 'restruct_type', 'Тип реструктуризации', 'reference', false,'restructions');
INSERT INTO tent.request_param_dict (id, code, "name", paramtype, value_array, reference_to) VALUES(2, 'request_date', 'Дата обращения', 'date', false,'');
INSERT INTO tent.request_param_dict (id, code, "name", paramtype, value_array, reference_to) VALUES(3, 'reason', 'Обстоятельство для требования', 'varchar(255)', false,'');
INSERT INTO tent.request_param_dict (id, code, "name", paramtype, value_array, reference_to) VALUES(4, 'date_start', 'Дата начала льготного периода', 'date', false,'');
INSERT INTO tent.request_param_dict (id, code, "name", paramtype, value_array, reference_to) VALUES(5, 'grace_type', 'Тип льготного периода', 'integer', false,'');
INSERT INTO tent.request_param_dict (id, code, "name", paramtype, value_array, reference_to) VALUES(6, 'date_end', 'Плановая дата окончания льготного периода', 'date', false,'');
INSERT INTO tent.request_param_dict (id, code, "name", paramtype, value_array, reference_to) VALUES(7, 'date_end_fact', 'Фактическая дата окончания льготного периода', 'date', false,'');
INSERT INTO tent.request_param_dict (id, code, "name", paramtype, value_array, reference_to) VALUES(8, 'reason_to_finish', 'Причина прекращения льготного периода', 'varchar(255)', false,'');
INSERT INTO tent.request_param_dict (id, code, "name", paramtype, value_array, reference_to) VALUES(9, 'new_duration', 'Длительлность договора', 'integer', false,'');
INSERT INTO tent.request_param_dict (id, code, "name", paramtype, value_array, reference_to) VALUES(10, 'src_tranche', 'Транш источник', 'reference', true,'dog_info');
INSERT INTO tent.request_param_dict (id, code, "name", paramtype, value_array, reference_to) VALUES(11, 'date_end_shift', 'Плановая дата окончания (с учетом переноса)', 'date', false,'');
INSERT INTO tent.request_param_dict (id, code, "name", paramtype, value_array, reference_to) VALUES(12, 'reason_doc_type', 'Тип документа', 'integer', false,'');
INSERT INTO tent.request_param_dict (id, code, "name", paramtype, value_array, reference_to) VALUES(13, 'doc_date', 'Дата документа', 'date', false,'');
INSERT INTO tent.request_param_dict (id, code, "name", paramtype, value_array, reference_to) VALUES(14, 'doc_number', 'Номер документа', 'varchar(255)', false,'');
INSERT INTO tent.request_param_dict (id, code, "name", paramtype, value_array, reference_to) VALUES(15, 'amount', 'Сумма к переносу', 'numeric(18,8)', false,'');
INSERT INTO tent.request_param_dict (id, code, "name", paramtype, value_array, reference_to) VALUES(16, 'rest_interest', 'Ставка на остаток', 'numeric(18,8)', false,'');
INSERT INTO tent.request_param_dict (id, code, "name", paramtype, value_array, reference_to) VALUES(17, 'new_ps_type', 'Тип графика', 'integer', false,'');
INSERT INTO tent.request_param_dict (id, code, "name", paramtype, value_array, reference_to) VALUES(18, 'end_date', 'Срок завершения', 'date', false,'');
INSERT INTO tent.request_param_dict (id, code, "name", paramtype, value_array, reference_to) VALUES(19, 'dest_trance', 'Транш приемник', 'reference', false,'dog_info');
INSERT INTO tent.request_param_dict (id, code, "name", paramtype, value_array, reference_to) VALUES(20, 'doc_type', 'Тип документа', 'integer', false,'');


select * from tent.request_param_dict order by code;


INSERT INTO tent.request_type(id, code, name)VALUES(1, 'credit_holidays', 'Кредитные каникулы');
INSERT INTO tent.request_type(id, code, name)VALUES(2, 'prolongation', 'Пролонгация');
INSERT INTO tent.request_type(id, code, name)VALUES(3, 'regulation', 'Урегулирование просрочки');
INSERT INTO tent.request_type(id, code, name)VALUES(4, 'tranch_merge', 'Перенос на один транш');

select * from tent.request_type order by code;


-- delete from tent.request_config;
INSERT INTO tent.request_config (id, request_type, "version")VALUES(1, 1, '1');
INSERT INTO tent.request_config (id, request_type, "version")VALUES(2, 2, '1');
INSERT INTO tent.request_config (id, request_type, "version")values(3, 3, '1');
INSERT INTO tent.request_config (id, request_type, "version")VALUES(4, 4, '1');

select * from tent.request_config;



CREATE SEQUENCE tent.seq_request_content_config
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;


INSERT INTO tent.request_content_config(id, request_configid, parameter, is_mandatory) 
select nextval( 'tent.seq_request_content_config'), c.id, p.id, false from  tent.request_param_dict p,tent.request_config c

select * from tent.request_content_config


-- config
select rt.code,rt.name,c.version, pd.code, pd.name, pd.paramtype,pd.reference_to ,cc.is_mandatory 
from tent.request_config c 
join tent.request_content_config cc on cc.request_configid = c.id
join tent.request_type rt on c.request_type = rt.id
join tent.request_param_dict pd on pd.id = cc.parameter
;