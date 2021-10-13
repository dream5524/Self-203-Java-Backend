-- SECURITY SCHEME -------------------------------------------------------------
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

-- Table: public.user_security_role_security
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


-- DATA SCHEME -----------------------------------------------------------------

drop table if exists public."widget";
drop table if exists public."task";
drop table if exists public."dashboard";
drop table if exists public."contact";
drop table if exists public."user";

-- Table: public.User
-- DROP TABLE public."User";
CREATE TABLE IF NOT EXISTS public."user"
(
    id serial not NULL,
    email character varying(50) NOT NULL,
    password character varying(125) NOT NULL,
    full_name character varying(50),
    CONSTRAINT id_pkey PRIMARY KEY (id),
    CONSTRAINT email_unique UNIQUE (email)
);

-- Table: public.Contact
-- DROP TABLE public."Contact";
CREATE TABLE IF NOT EXISTS public."contact"
(
    id serial NOT NULL,
    first_name character varying(50) NOT NULL,
    last_name character varying(50) NOT NULL,
    title character varying(50) NOT NULL,
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

-- Table: public.Dashboard
-- DROP TABLE public."Dashboard";
CREATE TABLE IF NOT EXISTS public."dashboard"
(
    id serial NOT NULL,
    title character varying(50) NOT NULL,
    contact_id integer NOT NULL,
    layout_type character varying(50) NOT NULL,
    CONSTRAINT dashboard_pkey PRIMARY KEY (id),
    CONSTRAINT dashboard_contact_key UNIQUE (contact_id),
    CONSTRAINT fk_dashboard_contact FOREIGN KEY (contact_id)
        REFERENCES public."contact" (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        DEFERRABLE INITIALLY DEFERRED
);

-- Table: public.Task
-- DROP TABLE public."Task";
CREATE TABLE IF NOT EXISTS public."task"
(
    id serial NOT NULL,
    description character varying(50) NOT NULL,
    contact_id integer NOT NULL,
    date_created timestamp without time zone DEFAULT (now() + '07:00:00'::interval),
    is_completed integer NOT NULL,
    CONSTRAINT task_pkey PRIMARY KEY (id),
    CONSTRAINT task_contact_id_fkey FOREIGN KEY (contact_id)
        REFERENCES public."contact" (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

-- Table: public.Widget
-- DROP TABLE public."Widget";
CREATE TABLE IF NOT EXISTS public."widget"
(
    id serial NOT NULL,
    type character varying(50) NOT NULL,
    dashboard_id integer NOT NULL,
    min_width double precision,
    min_height double precision,
    configs json,
    CONSTRAINT widget_pkey PRIMARY KEY (id),
    CONSTRAINT widget_dashboard_id_fkey FOREIGN KEY (dashboard_id)
        REFERENCES public."dashboard" (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);


-- ADD DATA -------------------------------------------------------------

insert into public."user" (id, email, password, full_name) 
values 
	(1, 	'anhquan@gmail.com',	'123456',	'Nguyễn Hoàng Anh Quân'),
	(2,		'sonct@gmail.com', 		'123456', 	'Cao Thái Sơn'),
	(3, 	'vietnq@gmail.com',		'123456',	'Nguyễn Quốc Việt'),
	(4, 	'tramdlt@gmail.com',	'123456',	'Đặng Lê Thùy Trâm'),
	(5, 	'huylg@gmail.com', 		'123456', 	'Lương Gia Huy'),
	(6, 	'tinhlv@gmail.com',		'123456', 	'Lê Văn Tính'),
	(7, 	'ngocmtb@gmail.com', 	'123456', 	'Mai Thị Bích Ngọc'),
	(8, 	'thidtd@gmail.com',		'123456',	'Đinh Thị Duyên Thi'),
	(9, 	'khangca@gmail.com',	'123456', 	'Cao An Khang'),
	(10, 	'ductm@gmail.com', 		'123456', 	'Trần Minh Đức');

--
-- Data for Name: Contact; Type: TABLE DATA; Schema: public; Owner: postgres

insert into public."contact" (id, first_name, last_name, title, user_id, project, date_created)
values
(32, 'Nguyễn Hoàng Anh',	'Quân', 	'Engineering Manager', 	1,	'Implenment API to build dashboard',	
 		'2021-09-18 15:57:54.880419'),
(33, 'Cao Thái', 			'Sơn',		'Developer',			2,	'Implement API to build dashboard',	
 		'2021-09-18 15:57:54.880419'),
(34, 'Lương Gia', 			'Huy',		'Tester',				5, 	'Implement API to build dashboard', 
 		'2021-09-18 15:57:54.880419'),
(35, 'Đinh Thị Duyên',		'Thi',		'Business Analyst',		8,	'Implement API to build dashboard',
 		'2021-09-18 15:57:54.880419'),
(36, 'Mai Thị Bích',		'Ngọc',		'Tester',				7,	'Implement API to build dashboard',
 		'2021-09-18 15:57:54.880419'),
(37, 'Đặng Lê Thùy',		'Trâm',		'Developer',			4,	'Implement API to build dashboard',	
 		'2021-09-18 15:57:54.880419'),
(38, 'Lê Văn', 				'Tính', 	'Tester', 				6, 	'Implement API to build dashboard',	
 		'2021-09-18 15:57:54.880419'),
(39, 'Cao An', 				'Khang',	'Director',				9,	'Implement API to build dashboard',	
 		'2021-09-18 15:57:54.880419'),
(40, 'Trần Minh', 			'Đức',		'Developer',			10,	'Implement API to build dashboard',	
 		'2021-09-18 15:57:54.880419'),
(41, 'Nguyễn Quốc',			'Việt',		'Developer',			3,	'Implement API to build dashboard',
 		'2021-09-18 15:57:54.880419');	
		
--
-- Data for Name: Dashboard; Type: TABLE DATA; Schema: public; Owner: postgres
--
INSERT INTO public."dashboard" (id, title, contact_id, layout_type) 
VALUES
(1,		'IT Operations',			32,	'Desktop'),
(2,		'Intership Assignment',		37,	'Desktop'),
(3,		'Health Information',		34,	'Desktop'),
(4, 	'Innovative Flexible', 		36,	'Desktop'),
(5,		'Management Infomation',	35,	'Desktop'),
(6,		'Project Manager Software',	39,	'Desktop'),
(7,		'IT Operations',			38,	'Desktop'),
(8,		'Intership Assignment',		33,	'Desktop'),
(9,		'Intership Assignment',		40,	'Desktop'),
(10,	'Intership Assignment',		41,	'Desktop');


--
-- Data for Name: Task; Type: TABLE DATA; Schema: public; Owner: postgres
--
INSERT INTO public."task" 
(id, 	description, 					contact_id, 	date_created, 					is_completed) values 
(9,		'Create Database',				33,				'2021-09-18 16:12:32.147452',		1),
(10,	'Add logger',					37,				'2021-09-18 16:12:32.147452',		0),
(11,	'Management Quality Project',	34,				'2021-09-18 16:12:32.147452',		1),
(12,	'Apply for Spring Security',	40,				'2021-09-18 16:12:32.147452',		0),
(13,	'Write Integration Test',		36,				'2021-09-18 16:12:32.147452',		1),
(14,	'Create BPMN',					35,				'2021-09-18 16:12:32.147452',		1),
(15,	'Planning',						39,				'2021-09-18 16:12:32.147452',		1),
(16,	'Manage meeting',				32,				'2021-09-18 16:12:32.147452',		0),
(17,	'Assurance testing models',		38,				'2021-09-18 16:12:32.147452',		0),
(18,	'Handle errors',				41,				'2021-09-18 16:12:32.147452',		1);



--
-- Data for Name: Widget; Type: TABLE DATA; Schema: public; Owner: postgres
--
insert into public."widget" 
(id, 	type, 	dashboard_id,	min_width, min_height, configs) values
(1, 	'Chart',	10,				70,			70,			
 		'{"type_widget":{"Text":70,"Char":70,"TodoTist":70,"DataTable":70}}'),
(2,		'TodoList',	9,				70,			70,	
 		'{"type_widget":{"Text":70,"Char":70,"TodoTist":70,"DataTable":70}}'),
(3,		'Chart',	8,				70,			70,	
 		'{"type_widget":{"Text":70,"Char":70,"TodoTist":70,"DataTable":70}}'),
(4,		'ToDoList',	7,				70,			70,	
 		'{"type_widget":{"Text":70,"Char":70,"TodoTist":70,"DataTable":70}}'),
(5,		'Text',		6,				70,			70,
 		'{"type_widget":{"Text":70,"Char":70,"TodoTist":70,"DataTable":70}}'),
(6,		'DataTable',5,				70,			70,	
 		'{"type_widget":{"Text":70,"Char":70,"TodoTist":70,"DataTable":70}}'),
(7,		'Text',		4,				70,			70,	
 		'{"type_widget":{"Text":70,"Char":70,"TodoTist":70,"DataTable":70}}'),
(8,		'Text',		3,				70,			70,	
 		'{"type_widget":{"Text":70,"Char":70,"TodoTist":70,"DataTable":70}}'),
(9,		'ToDoList',	2,				70,			70,	
 		'{"type_widget":{"Text":70,"Char":70,"TodoTist":70,"DataTable":70}}'),
(10,	'Text',		1,				70,			70,	
 		'{"type_widget":{"Text":70,"Char":70,"TodoTist":70,"DataTable":70}}'),
(11,	'Text',		10,				70,			70,	
 		'{"type_widget":{"Text":70,"Char":70,"TodoTist":70,"DataTable":70}}'),
(12,	'TodoList',	5,				70,			70,	
 		'{"type_widget":{"Text":70,"Char":70,"TodoTist":70,"DataTable":70}}'),
(15,	'TodoList',	10,				70,		70,	'{"type_widget":{"Text":70,"Char":70,"TodoTist":70,"DataTable":70}}'),
(16,	'DataTable',10,				70,		70,	'{"type_widget":{"Text":70,"Char":70,"TodoTist":70,"DataTable":70}}'),
(17,	'Text',		2,				70,		70,	'{"type_widget":{"Text":70,"Char":70,"TodoTist":70,"DataTable":70}}'),
(18,	'TodoList',	2,				70,		70,	'{"type_widget":{"Text":70,"Char":70,"TodoTist":70,"DataTable":70}}'),
(19,	'DataTable',2,				70,		70,	'{"type_widget":{"Text":70,"Char":70,"TodoTist":70,"DataTable":70}}'),
(20,	'Text',		5,				70,		70,	'{"type_widget":{"Text":70,"Char":70,"TodoTist":70,"DataTable":70}}'),
(21,	'TodoList',	5,				70,		70,	'{"type_widget":{"Text":70,"Char":70,"TodoTist":70,"DataTable":70}}'),
(22,	'DataTable',5,				70,		70,	'{"type_widget":{"Text":70,"Char":70,"TodoTist":70,"DataTable":70}}'),
(23,	'Text',		7,				70,		70,	'{"type_widget":{"Text":70,"Char":70,"TodoTist":70,"DataTable":70}}'),
(25,	'DataTable',7,				70,		70,	'{"type_widget":{"Text":70,"Char":70,"TodoTist":70,"DataTable":70}}'),
(26,	'Text',		8,				70,		70,	'{"type_widget":{"Text":70,"Char":70,"TodoTist":70,"DataTable":70}}'),
(27,	'TodoList',	8,				70,		70,	'{"type_widget":{"Text":70,"Char":70,"TodoTist":70,"DataTable":70}}'),
(28,	'DataTable',8,				70,		70,	'{"type_widget":{"Text":70,"Char":70,"TodoTist":70,"DataTable":70}}'),
(29,	'Text',		9,				70,		70,	'{"type_widget":{"Text":70,"Char":70,"TodoTist":70,"DataTable":70}}'),
(30,	'DataTable',9,				70,		70,	'{"type_widget":{"Text":70,"Char":70,"TodoTist":70,"DataTable":70}}'),
(31,	'Chart',	9,				70,		70,	'{"type_widget":{"Text":70,"Char":70,"TodoTist":70,"DataTable":70}}'),
(33,	'TodoList',	6,				70,		70,	'{"type_widget":{"Text":70,"Char":70,"TodoTist":70,"DataTable":70}}'),
(36,	'DataTable',3,				70,		70,	'{"type_widget":{"Text":70,"Char":70,"TodoTist":70,"DataTable":70}}'),
(37,	'Chart',	6,				70,		70,	'{"type_widget":{"Text":70,"Char":70,"TodoTist":70,"DataTable":70}}'),
(38,	'Chart',	5,				70,		70,	'{"type_widget":{"Text":70,"Char":70,"TodoTist":70,"DataTable":70}}'),
(39,	'TodoList',	1,				70,		70,	'{"type_widget":{"Text":70,"Char":70,"TodoTist":70,"DataTable":70}}'),
(40,	'Chart',	1,				70,		70,	'{"type_widget":{"Text":70,"Char":70,"TodoTist":70,"DataTable":70}}'),
(41,	'DataTable',1,				70,		70,	'{"type_widget":{"Text":70,"Char":70,"TodoTist":70,"DataTable":70}}'),
(42,	'Chart',	2,				70,		70,	'{"type_widget":{"Text":70,"Char":70,"TodoTist":70,"DataTable":70}}'),
(43,	'Chart',	7,				70,		70,	'{"type_widget":{"Text":70,"Char":70,"TodoTist":70,"DataTable":70}}');