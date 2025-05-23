-- public.dest_data определение

-- Drop table

-- DROP TABLE public.dest_data;

CREATE TABLE public.dest_data (
	"schema" text NOT NULL,
	table_name text NOT NULL,
	field_name text NOT NULL,
	field_type text NULL,
	"comment" text NULL,
	entity_key bool DEFAULT false NULL
);
CREATE INDEX dest_data_schema_idx ON public.dest_data USING btree (schema, table_name, field_name);
CREATE INDEX dest_data_table_name_idx ON public.dest_data USING btree (table_name, field_name);

-- public.src_data определение

-- Drop table

-- DROP TABLE public.src_data;

CREATE TABLE public.src_data (
	table_name text NOT NULL,
	field_name text NOT NULL,
	field_type text NULL,
	"comment" text NULL,
	api text NULL,
	api_comment text NULL,
	table_comment text NULL,
	field_order int4 NULL,
	for_output int2 DEFAULT 1 NULL,
	entity_key bool DEFAULT false NOT NULL
);
CREATE INDEX src_data_api_idx ON public.src_data USING btree (api, table_name, field_name, field_order);

-- public.mapper определение

-- public.map_data определение

-- Drop table

-- DROP TABLE public.map_data;

CREATE TABLE public.map_data (
	map_name text NULL,
	api text NULL,
	table_name text NULL,
	field_name text NULL,
	to_table text NULL,
	to_field text NULL,
	"comment" text NULL
);
CREATE UNIQUE INDEX map_data_map_name_idx ON public.map_data USING btree (map_name, to_table, to_field);


-- public.used_api определение

-- Drop table

-- DROP TABLE public.used_api;

CREATE TABLE public.used_api (
	api text NOT NULL,
	CONSTRAINT used_api_pk PRIMARY KEY (api)
);
