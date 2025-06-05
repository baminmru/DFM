CREATE TABLE IF NOT EXISTS public.dest_data_new (
	table_name text NOT NULL,
	field_name text NOT NULL,
	field_type text NULL,
	"comment" text NULL,
	"schema" text NULL,
	entity_key bool DEFAULT false NULL,
	entity_root bool DEFAULT false NULL
);
CREATE INDEX IF NOT EXISTS dest_data_new_schema_idx ON public.dest_data_new USING btree (schema, table_name, field_name);
CREATE INDEX IF NOT EXISTS dest_data_new_table_name_idx ON public.dest_data_new USING btree (table_name, field_name);



-- inserting to dest_data_new
-- excel formula:
-- ="INSERT INTO public.dest_data_new (table_name, field_name, field_type, comment, schema) VALUES('"&B2&"', '"&C2&"', '"&D2&"', '"&H2&"', '"&A2&"');"



-- append and update dest_data
-- select schema, table_name, field_name, field_type, comment from dest_data where table_name || field_name not in ( select table_name || field_name from dest_data_new)

insert into dest_data( schema, table_name, field_name, field_type, comment) select schema, table_name, field_name, field_type, comment 
	from dest_data_new where table_name || field_name not in ( select table_name || field_name from dest_data);
	
delete from dest_data where table_name || field_name not in ( select table_name || field_name from dest_data_new);



update dest_data set schema = s.schema, comment = s.comment, field_type = s.field_type
from (
select 
n.*
from
dest_data d
join dest_data_new n on d.table_name = n.table_name and  d.field_name =n.field_name
) s where dest_data.table_name = s.table_name  and  dest_data.field_name =s.field_name;


-- update comments 
update dest_data set comment =  replace(comment,'Import data type: ', '');
update dest_data set comment =  replace(comment,'text ', '');
update dest_data set comment =  replace(comment,'int2 ', '');
update dest_data set comment =  replace(comment,'int4 ', '');
update dest_data set comment =  replace(comment,'int8 ', '');
update dest_data set comment =  replace(comment,'bool ', '');
update dest_data set comment =  replace(comment,'date ', '');
update dest_data set comment =  replace(comment,'numeric ', '');
update dest_data set comment =  replace(comment,'timestamptz ', '');
update dest_data set comment =  replace(comment,'bpchar (3) ', '');
update dest_data set comment =  trim(comment);

-- drop table dest_data_new;
-- delete from dest_data_new;

select * from dest_data order by schema, table_name, field_name

-- copy mapping
/*
INSERT INTO public.map_data
(map_name, api, table_name, field_name, to_table, to_field, "comment", to_schema, "condition")
select 'LoanLine', api, table_name, field_name, to_table, to_field, "comment", to_schema, "condition" from map_data where map_name ='SimpleCredit'
*/


