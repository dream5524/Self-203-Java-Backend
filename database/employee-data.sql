
ALTER DATABASE employee OWNER TO postgres;

--
-- Data for Name: User; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."User" (id, email, password, full_name) FROM stdin;
10	ductm@gmail.com	123456	Trần Minh  Đức
1	quannha@gmail.com	123456	Nguyễn Hoàng Anh Quân
2	sonct@gmail.com	123456	Cao Thái Sơn
3	vietnq@gmail.com	123456	Nguyễn Quốc Việt
4	tramdlt@gmail.com	123456	Đặng Lê Thùy Trâm
5	huylg@gmail.com	123456	Lương Gia Huy
6	tinhlv@gmail.com	123456	Lê Văn Tính
7	ngocmtb@gmail.com	123456	Mai Thị Bích Ngọc
8	thidtd@gmail.com	123456	Đinh Thị Duyên Thi
9	khangca@gmail.com	123456	Cao An Khang
\.


--
-- Data for Name: Contact; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."Contact" (id, first_name, last_name, title, user_id, project, date_created) FROM stdin;
32	Nguyễn Hoàng Anh	Quân	Engineering Manager	1	Implenment API to build dashboard	2021-09-18 15:57:54.880419
33	Cao Thái	Sơn	Developer	2	Implement API to build dashboard	2021-09-18 15:57:54.880419
34	Lương Gia	Huy	Tester	5	Implement API to build dashboard	2021-09-18 15:57:54.880419
35	Đinh Thị Duyên	Thi	Business Analyst	8	Implement API to build dashboard	2021-09-18 15:57:54.880419
36	Mai Thị Bích	Ngọc	Tester	7	Implement API to build dashboard	2021-09-18 15:57:54.880419
37	Đặng Lê Thùy	Trâm	Developer	4	Implement API to build dashboard	2021-09-18 15:57:54.880419
38	Lê Văn	Tính	Tester	6	Implement API to build dashboard	2021-09-18 15:57:54.880419
39	Cao An	Khang	Director	9	Implement API to build dashboard	2021-09-18 15:57:54.880419
40	Trần Minh 	Đức	Developer	10	Implement API to build dashboard	2021-09-18 15:57:54.880419
41	Nguyễn Quốc	Việt	Developer	3	Implement API to build dashboard	2021-09-18 15:57:54.880419
\.


--
-- Data for Name: Dashboard; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."Dashboard" (id, title, contact_id, layout_type, list_widgets) FROM stdin;
3	Health Information	34	Desktop	{DataTable,Text}
4	Innovative Flexible	36	Desktop	{Text}
5	Management Infomation	35	Desktop	{DataTable,Text,TodoList,Chart}
6	Project Manager Software	39	Desktop	{Text,TodoList,Chart}
1	IT Operations	32	Desktop	{DataTable,Text,TodoList,''Chart}
2	Intership Assignment	37	Desktop	{DataTable,Text,TodoList,''Chart}
7	IT Operations	38	Desktop	{DataTable,Text,TodoList,''Chart}
8	Intership Assignment	33	Desktop	{DataTable,Text,TodoList,''Chart}
9	Intership Assignment	40	Desktop	{DataTable,Text,TodoList,''Chart}
10	Intership Assignment	41	Desktop	{DataTable,Text,TodoList,''Chart}
\.


--
-- Data for Name: Task; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."Task" (id, description, contact_id, date_created, is_completed) FROM stdin;
9	Create Database	33	2021-09-18 16:12:32.147452	1
10	Add logger	37	2021-09-18 16:12:32.147452	0
11	Management Quality Project	34	2021-09-18 16:12:32.147452	1
12	Apply fot Spring Security	40	2021-09-18 16:12:32.147452	0
13	Write Integration Test	36	2021-09-18 16:12:32.147452	1
14	Create BPMN	35	2021-09-18 16:12:32.147452	1
15	Planning	39	2021-09-18 16:12:32.147452	1
16	Manage meeting	32	2021-09-18 16:12:32.147452	0
17	Assurance testing models	38	2021-09-18 16:12:32.147452	0
18	Handle errors	41	2021-09-18 16:12:32.147452	1
\.


--
-- Data for Name: Widget; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."Widget" (id, type, dashboard_id, min_width, min_height, configs) FROM stdin;
4	ToDoList	7	70	70	{"type_widget":{"Text":70,"Char":70,"TodoTist":70,"DataTable":70}}
5	Text	6	70	70	{"type_widget":{"Text":70,"Char":70,"TodoTist":70,"DataTable":70}}
6	DataTable	5	70	70	{"type_widget":{"Text":70,"Char":70,"TodoTist":70,"DataTable":70}}
9	ToDoList	2	70	70	{"type_widget":{"Text":70,"Char":70,"TodoTist":70,"DataTable":70}}
10	Text	1	70	70	{"type_widget":{"Text":70,"Char":70,"TodoTist":70,"DataTable":70}}
11	Text	10	70	70	{"type_widget":{"Text":70,"Char":70,"TodoTist":70,"DataTable":70}}
12	TodoList	5	70	70	{"type_widget":{"Text":70,"Char":70,"TodoTist":70,"DataTable":70}}
15	TodoList	10	70	70	{"type_widget":{"Text":70,"Char":70,"TodoTist":70,"DataTable":70}}
16	DataTable	10	70	70	{"type_widget":{"Text":70,"Char":70,"TodoTist":70,"DataTable":70}}
17	Text	2	70	70	{"type_widget":{"Text":70,"Char":70,"TodoTist":70,"DataTable":70}}
18	TodoList	2	70	70	{"type_widget":{"Text":70,"Char":70,"TodoTist":70,"DataTable":70}}
19	DataTable	2	70	70	{"type_widget":{"Text":70,"Char":70,"TodoTist":70,"DataTable":70}}
20	Text	5	70	70	{"type_widget":{"Text":70,"Char":70,"TodoTist":70,"DataTable":70}}
21	TodoList	5	70	70	{"type_widget":{"Text":70,"Char":70,"TodoTist":70,"DataTable":70}}
22	DataTable	5	70	70	{"type_widget":{"Text":70,"Char":70,"TodoTist":70,"DataTable":70}}
23	Text	7	70	70	{"type_widget":{"Text":70,"Char":70,"TodoTist":70,"DataTable":70}}
25	DataTable	7	70	70	{"type_widget":{"Text":70,"Char":70,"TodoTist":70,"DataTable":70}}
26	Text	8	70	70	{"type_widget":{"Text":70,"Char":70,"TodoTist":70,"DataTable":70}}
27	TodoList	8	70	70	{"type_widget":{"Text":70,"Char":70,"TodoTist":70,"DataTable":70}}
28	DataTable	8	70	70	{"type_widget":{"Text":70,"Char":70,"TodoTist":70,"DataTable":70}}
33	TodoList	6	70	70	{"type_widget":{"Text":70,"Char":70,"TodoTist":70,"DataTable":70}}
36	DataTable	3	70	70	{"type_widget":{"Text":70,"Char":70,"TodoTist":70,"DataTable":70}}
37	Chart	6	70	70	{"type_widget":{"Text":70,"Char":70,"TodoTist":70,"DataTable":70}}
38	Chart	5	70	70	{"type_widget":{"Text":70,"Char":70,"TodoTist":70,"DataTable":70}}
39	TodoList	1	70	70	{"type_widget":{"Text":70,"Char":70,"TodoTist":70,"DataTable":70}}
40	Chart	1	70	70	{"type_widget":{"Text":70,"Char":70,"TodoTist":70,"DataTable":70}}
41	DataTable	1	70	70	{"type_widget":{"Text":70,"Char":70,"TodoTist":70,"DataTable":70}}
42	Chart	2	70	70	{"type_widget":{"Text":70,"Char":70,"TodoTist":70,"DataTable":70}}
43	Chart	7	70	70	{"type_widget":{"Text":70,"Char":70,"TodoTist":70,"DataTable":70}}
3	Chart	8	70	70	{"type_widget":{"Text":70,"Char":70,"TodoTist":70,"DataTable":70}}
31	Chart	9	70	70	{"type_widget":{"Text":70,"Char":70,"TodoTist":70,"DataTable":70}}
2	TodoList	9	70	70	{"type_widget":{"Text":70,"Char":70,"TodoTist":70,"DataTable":70}}
29	Text	9	70	70	{"type_widget":{"Text":70,"Char":70,"TodoTist":70,"DataTable":70}}
30	DataTable	9	70	70	{"type_widget":{"Text":70,"Char":70,"TodoTist":70,"DataTable":70}}
1	Chart	10	70	70	{"type_widget":{"Text":70,"Char":70,"TodoTist":70,"DataTable":70}}
7	Text	4	70	70	{"type_widget":{"Text":70,"Char":70,"TodoTist":70,"DataTable":70}}
8	Text	3	70	70	{"type_widget":{"Text":70,"Char":70,"TodoTist":70,"DataTable":70}}
\.

