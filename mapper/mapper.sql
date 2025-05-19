-- public.dest_data определение

-- Drop table

-- DROP TABLE public.dest_data;

CREATE TABLE public.dest_data (
	table_name text NOT NULL,
	field_name text NOT NULL,
	field_type text NULL,
	"comment" text NULL,
	id int4 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 START 1 CACHE 1 NO CYCLE) NOT NULL,
	CONSTRAINT dest_data_pk PRIMARY KEY (id)
);


-- public.src_data определение

-- Drop table

-- DROP TABLE public.src_data;

CREATE TABLE public.src_data (
	api text NULL,
	api_comment text NULL,
	table_name text NOT NULL,
	table_comment text NULL,
	field_name text NOT NULL,
	field_type text NULL,
	"comment" text NULL,
	id int4 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 START 1 CACHE 1 NO CYCLE) NOT NULL,
	
	CONSTRAINT src_data_pk PRIMARY KEY (id)
);


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
