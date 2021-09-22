-- This postgresql script is used to generate the UserSecurity and RoleSecurity table. They are necessary
-- to customize the Spring Security, including the authentication and authorization filter.

drop table if exists public.user_security_role_security;
drop table if exists public.user_security;
drop table if exists public.role_security;

-- Table: public.role_security

CREATE TABLE IF NOT EXISTS public.role_security
(
    id integer NOT NULL,
    name varchar(50) unique,
    PRIMARY KEY (id)
);

-- Table: public.user_security

CREATE TABLE IF NOT EXISTS public.user_security
(
    id integer NOT NULL,
    name varchar(50),
    password varchar(125) not NULL,
    username character varying(255) unique,
    PRIMARY KEY (id)
);

-- Table: public.user_security_role_securities

-- DROP TABLE public.user_security_role_securities;

CREATE TABLE IF NOT EXISTS public.user_security_role_security
(
    user_security_id integer NOT NULL,
    role_security_id integer NOT NULL,
    FOREIGN KEY (role_security_id)
        REFERENCES public.role_security (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
	FOREIGN KEY (user_security_id)
        REFERENCES public.user_security (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,

	PRIMARY KEY (user_security_id, role_security_id)
);
