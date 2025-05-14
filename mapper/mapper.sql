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

-- Drop table

-- DROP TABLE public.mapper;

CREATE TABLE public.mapper (
	dest_id int4 NOT NULL,
	src_id int4 NOT NULL,
	"comment" text NULL,
	map_name text NULL,
	id int4 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 START 1 CACHE 1 NO CYCLE) NOT NULL,
	CONSTRAINT mapper_pk PRIMARY KEY (id),
	CONSTRAINT mapper_dest_data_fk FOREIGN KEY (dest_id) REFERENCES public.dest_data(id),
	CONSTRAINT mapper_src_data_fk FOREIGN KEY (src_id) REFERENCES public.src_data(id)
);