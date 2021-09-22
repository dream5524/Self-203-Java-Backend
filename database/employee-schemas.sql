ALTER DATABASE employee OWNER TO postgres;
-- Table: public.Contact
-- DROP TABLE public."Contact";
CREATE TABLE IF NOT EXISTS public."Contact"
(
    id integer NOT NULL DEFAULT nextval('contact_id_seq'::regclass),
    first_name character varying(20) COLLATE pg_catalog."default" NOT NULL,
    last_name character varying(20) COLLATE pg_catalog."default" NOT NULL,
    title character varying(20) COLLATE pg_catalog."default" NOT NULL,
    user_id integer NOT NULL,
    project character varying(50) COLLATE pg_catalog."default" NOT NULL,
    date_created timestamp without time zone NOT NULL DEFAULT (now() + '07:00:00'::interval),
    CONSTRAINT contact_id_pkey PRIMARY KEY (id),
    CONSTRAINT "Contact_user_id_key" UNIQUE (user_id),
    CONSTRAINT fk_contact_user FOREIGN KEY (user_id)
    REFERENCES public."User" (id) MATCH SIMPLE
                           ON UPDATE NO ACTION
                           ON DELETE NO ACTION
    )
-- Table: public.Dashboard
-- DROP TABLE public."Dashboard";
CREATE TABLE IF NOT EXISTS public."Dashboard"
(
    id integer NOT NULL DEFAULT nextval('dashboard_id_seq'::regclass),
    title character varying(50) COLLATE pg_catalog."default" NOT NULL,
    contact_id integer NOT NULL,
    layout_type character varying(10) COLLATE pg_catalog."default" NOT NULL,
    list_widgets text[] COLLATE pg_catalog."default",
    CONSTRAINT dashboard_pkey PRIMARY KEY (id),
    CONSTRAINT dashboard_contact_key UNIQUE (contact_id),
    CONSTRAINT fk_dashboard_contact FOREIGN KEY (contact_id)
        REFERENCES public."Contact" (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        DEFERRABLE INITIALLY DEFERRED
)
-- Table: public.Task
-- DROP TABLE public."Task";
CREATE TABLE IF NOT EXISTS public."Task"
(
    id integer NOT NULL DEFAULT nextval('task_id_seq'::regclass),
    description character varying(30) COLLATE pg_catalog."default" NOT NULL,
    contact_id integer NOT NULL,
    date_created timestamp without time zone DEFAULT (now() + '07:00:00'::interval),
    is_completed integer NOT NULL,
    CONSTRAINT task_pkey PRIMARY KEY (id),
    CONSTRAINT task_contact_id_key UNIQUE (contact_id),
    CONSTRAINT task_contact_id_fkey FOREIGN KEY (contact_id)
        REFERENCES public."Contact" (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
-- Table: public.User
-- DROP TABLE public."User";
CREATE TABLE IF NOT EXISTS public."User"
(
    id integer NOT NULL DEFAULT nextval('user_id_seq'::regclass),
    email character varying(20) COLLATE pg_catalog."default" NOT NULL,
    password character varying(20) COLLATE pg_catalog."default" NOT NULL,
    full_name character varying(50) COLLATE pg_catalog."default",
    CONSTRAINT id_pkey PRIMARY KEY (id),
    CONSTRAINT user_id_key UNIQUE (id)
    )
-- Table: public.Widget
-- DROP TABLE public."Widget";
CREATE TABLE IF NOT EXISTS public."Widget"
(
    id integer NOT NULL DEFAULT nextval('widget_id_seq'::regclass),
    type character varying(30) COLLATE pg_catalog."default" NOT NULL,
    dashboard_id integer NOT NULL,
    min_width double precision,
    min_height double precision,
    configs json,
    CONSTRAINT widget_pkey PRIMARY KEY (id),
    CONSTRAINT widget_dashboard_id_fkey FOREIGN KEY (dashboard_id)
        REFERENCES public."Dashboard" (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)