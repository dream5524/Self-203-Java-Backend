CREATE DATABASE employee;
-- CREATE TABLE public."user";
CREATE TABLE IF NOT EXISTS public."user"
(
    id integer NOT NULL,
    email character varying(50) NOT NULL,
    password character varying(100) NOT NULL,
    full_name character varying(50),
    CONSTRAINT id_pkey PRIMARY KEY (id)
    );
-- CREATE TABLE public."contact";
CREATE TABLE IF NOT EXISTS public."contact"
(
    id integer NOT NULL,
    first_name character varying(20)  NOT NULL,
    last_name character varying(20) NOT NULL,
    title character varying(20) NOT NULL,
    user_id integer NOT NULL,
    project character varying(50) NOT NULL,
    date_created timestamp without time zone NOT NULL DEFAULT (now() + '07:00:00'::interval),
    CONSTRAINT contact_id_pkey PRIMARY KEY (id),
    CONSTRAINT "Contact_user_id_key" UNIQUE (user_id),
    CONSTRAINT fk_contact_user FOREIGN KEY (user_id)
    REFERENCES public."user" (id) MATCH SIMPLE
                           ON UPDATE NO ACTION
                           ON DELETE NO ACTION
    );
-- CREATE TABLE public."dashboard";
CREATE TABLE IF NOT EXISTS public."dashboard"
(
    id integer NOT NULL,
    title character varying(50) NOT NULL,
    contact_id integer NOT NULL,
    layout_type character varying(20) NOT NULL,
    list_widgets text[],
    CONSTRAINT dashboard_pkey PRIMARY KEY (id),
    CONSTRAINT dashboard_contact_key UNIQUE (contact_id),
    CONSTRAINT fk_dashboard_contact FOREIGN KEY (contact_id)
        REFERENCES public."contact" (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        DEFERRABLE INITIALLY DEFERRED
)
-- CREATE TABLE public."task";
CREATE TABLE IF NOT EXISTS public."task"
(
    id integer NOT NULL,
    description character varying(30) NOT NULL,
    contact_id integer NOT NULL,
    date_created timestamp without time zone DEFAULT (now() + '07:00:00'::interval),
    is_completed integer NOT NULL,
    CONSTRAINT task_pkey PRIMARY KEY (id),
    CONSTRAINT task_contact_id_key UNIQUE (contact_id),
    CONSTRAINT task_contact_id_fkey FOREIGN KEY (contact_id)
        REFERENCES public."contact" (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
-- CRETAE TABLE public."widget";
CREATE TABLE IF NOT EXISTS public."widget"
(
    id integer NOT NULL,
    type character varying(30) NOT NULL,
    dashboard_id integer NOT NULL,
    min_width double precision,
    min_height double precision,
    configs json,
    CONSTRAINT widget_pkey PRIMARY KEY (id),
    CONSTRAINT widget_dashboard_id_fkey FOREIGN KEY (dashboard_id)
        REFERENCES public."dashboard" (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)