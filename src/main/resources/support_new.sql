--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

--
-- Name: subjecttype; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE subjecttype AS ENUM (
    'Organization',
    'Personal'
);


ALTER TYPE subjecttype OWNER TO postgres;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: account_resource_maps; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE account_resource_maps (
    id bigint NOT NULL,
    account_id bigint NOT NULL,
    resource_id bigint NOT NULL
);


ALTER TABLE account_resource_maps OWNER TO postgres;

--
-- Name: accounts; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE accounts (
    id bigint NOT NULL,
    subject_type name NOT NULL,
    subject_id bigint NOT NULL,
    active integer,
    type integer DEFAULT 0 NOT NULL,
    begin timestamp(0) without time zone,
    "end" timestamp(0) without time zone,
    login_name name,
    password name,
    can_change_password integer DEFAULT 0 NOT NULL,
    password_question character varying(127),
    password_answer character varying(127),
    ip_address_created character varying(127) DEFAULT '0.0.0.0'::character varying,
    ip_address_last character varying(127) DEFAULT '0.0.0.0'::character varying,
    last_login_time timestamp(0) without time zone,
    login_count integer DEFAULT 0 NOT NULL,
    password_failed_count integer DEFAULT 0 NOT NULL,
    current_certificate character varying(8192),
    session_id character(256),
    last_opereation_time timestamp(0) without time zone
);


ALTER TABLE accounts OWNER TO postgres;

--
-- Name: certificate_create_logs; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE certificate_create_logs (
    id bigint NOT NULL,
    no bigint,
    account_id bigint NOT NULL,
    device_id bigint NOT NULL,
    user_name name,
    company_name name,
    public_key character varying(1024) NOT NULL,
    source_ip character varying(255) NOT NULL
);


ALTER TABLE certificate_create_logs OWNER TO postgres;

--
-- Name: certificate_revoke_logs; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE certificate_revoke_logs (
    id bigint NOT NULL,
    no bigint NOT NULL,
    account_id bigint NOT NULL,
    device_id bigint NOT NULL,
    notes character varying(255) NOT NULL,
    admin bigint NOT NULL,
    file_path character varying(255) NOT NULL
);


ALTER TABLE certificate_revoke_logs OWNER TO postgres;

--
-- Name: certificates; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE certificates (
    id bigint NOT NULL,
    no bigint NOT NULL,
    account_id bigint NOT NULL,
    device_id bigint NOT NULL,
    subject character varying(255) NOT NULL,
    issuer character varying(255) NOT NULL,
    not_before timestamp(6) without time zone NOT NULL,
    not_after timestamp(6) without time zone NOT NULL,
    signature_algorithm_name character varying(1024) NOT NULL,
    public_key character varying(1024) NOT NULL,
    save_path character varying(255) NOT NULL
);


ALTER TABLE certificates OWNER TO postgres;

--
-- Name: config_types; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE config_types (
    id bigint NOT NULL,
    no name,
    name name
);


ALTER TABLE config_types OWNER TO postgres;

--
-- Name: configs; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE configs (
    id bigint NOT NULL,
    name name NOT NULL,
    value character varying(255),
    type_id bigint NOT NULL
);


ALTER TABLE configs OWNER TO postgres;

--
-- Name: copyrights; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE copyrights (
    id bigint NOT NULL,
    no name NOT NULL,
    resource_id bigint,
    owner_id bigint NOT NULL,
    author_id bigint NOT NULL,
    expiration timestamp(0) without time zone,
    status integer DEFAULT 0 NOT NULL
);


ALTER TABLE copyrights OWNER TO postgres;

--
-- Name: devices; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE devices (
    id bigint NOT NULL,
    account_id bigint,
    active integer,
    type integer DEFAULT 0 NOT NULL,
    begin timestamp(0) without time zone,
    "end" timestamp(0) without time zone,
    no name,
    name name,
    description text
);


ALTER TABLE devices OWNER TO postgres;

--
-- Name: logs; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE logs (
    id bigint NOT NULL,
    kind integer DEFAULT 0 NOT NULL,
    account_id bigint NOT NULL,
    device_id bigint,
    name name NOT NULL,
    comment text NOT NULL,
    level integer NOT NULL,
    result integer DEFAULT 0 NOT NULL
);


ALTER TABLE logs OWNER TO postgres;

--
-- Name: markups; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE markups (
    id bigint NOT NULL,
    table_id bigint NOT NULL,
    row_id bigint NOT NULL,
    name name,
    value text
);


ALTER TABLE markups OWNER TO postgres;

--
-- Name: organization_types; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE organization_types (
    id bigint NOT NULL,
    no name NOT NULL,
    name name NOT NULL
);


ALTER TABLE organization_types OWNER TO postgres;

--
-- Name: organizations; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE organizations (
    id bigint NOT NULL,
    no name NOT NULL,
    name name NOT NULL,
    type_id bigint,
    parent_id bigint
);


ALTER TABLE organizations OWNER TO postgres;

--
-- Name: resource_last_transfer_logs; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE resource_last_transfer_logs (
    id bigint NOT NULL,
    resource_last_transfer_id bigint NOT NULL,
    "time" timestamp(0) without time zone
);


ALTER TABLE resource_last_transfer_logs OWNER TO postgres;

--
-- Name: resource_last_transfers; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE resource_last_transfers (
    id bigint NOT NULL,
    resource_transfer_id bigint NOT NULL,
    license_path character varying(255) NOT NULL,
    watermark_content character varying(255)
);


ALTER TABLE resource_last_transfers OWNER TO postgres;

--
-- Name: resource_subjects; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE resource_subjects (
    id bigint NOT NULL,
    no name NOT NULL,
    name name
);


ALTER TABLE resource_subjects OWNER TO postgres;

--
-- Name: resource_transfers; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE resource_transfers (
    id bigint NOT NULL,
    resource_id bigint NOT NULL,
    right_transfer_id bigint,
    "time" timestamp(0) without time zone,
    sender_id bigint NOT NULL,
    sender_device_id bigint NOT NULL,
    sender_signatrue character varying(255) NOT NULL,
    receiver_id bigint NOT NULL,
    receiver_device_id bigint NOT NULL,
    receiver_signatrue character varying(255) NOT NULL,
    key character varying(1024) NOT NULL
);


ALTER TABLE resource_transfers OWNER TO postgres;

--
-- Name: resource_types; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE resource_types (
    id bigint NOT NULL,
    no name NOT NULL,
    name name
);


ALTER TABLE resource_types OWNER TO postgres;

--
-- Name: resources; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE resources (
    id bigint NOT NULL,
    no name NOT NULL,
    name name NOT NULL,
    chief_editor name,
    associate_editor name,
    quasi_use_no name,
    grade name,
    stage name,
    type name,
    type_id bigint,
    publisher name,
    publisher_address name,
    subject name,
    subject_id bigint,
    classifier name,
    version name,
    course_type name,
    intro text,
    file_path name,
    file_ext name,
    cover name,
    price name,
    edition name,
    reversion name,
    digest character varying(256),
    owner_type name,
    owner_id bigint,
    author_id bigint,
    isbn name,
    language bigint
);


ALTER TABLE resources OWNER TO postgres;

--
-- Name: right_transfers; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE right_transfers (
    id bigint NOT NULL,
    no name NOT NULL,
    "time" timestamp(0) without time zone,
    copyright_id bigint NOT NULL,
    right_type_id bigint NOT NULL,
    from_id bigint NOT NULL,
    to_id bigint NOT NULL,
    amount integer NOT NULL,
    expiration timestamp(0) without time zone
);


ALTER TABLE right_transfers OWNER TO postgres;

--
-- Name: right_types; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE right_types (
    id bigint NOT NULL,
    no name NOT NULL,
    name name NOT NULL
);


ALTER TABLE right_types OWNER TO postgres;

--
-- Name: tree_types; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE tree_types (
    id bigint NOT NULL,
    name name,
    description text
);


ALTER TABLE tree_types OWNER TO postgres;

--
-- Name: trees; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE trees (
    id bigint NOT NULL,
    no name,
    name name,
    type_id bigint NOT NULL,
    parent_id bigint,
    description text
);


ALTER TABLE trees OWNER TO postgres;

--
-- Name: upload_logs; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE upload_logs (
    id bigint NOT NULL,
    "time" timestamp(0) without time zone,
    uploader_id bigint NOT NULL,
    file_path name NOT NULL,
    state integer NOT NULL
);


ALTER TABLE upload_logs OWNER TO postgres;

--
-- Name: users; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE users (
    id bigint NOT NULL,
    no name NOT NULL,
    name name NOT NULL,
    organization_id bigint,
    id_type character(1) NOT NULL,
    id_no name NOT NULL,
    company_name name,
    company_no name,
    "position" character varying(127),
    address character varying(255)
);


ALTER TABLE users OWNER TO postgres;

--
-- Data for Name: account_resource_maps; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: accounts; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: certificate_create_logs; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: certificate_revoke_logs; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: certificates; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: config_types; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: configs; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: copyrights; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: devices; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: logs; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: markups; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: organization_types; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: organizations; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: resource_last_transfer_logs; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: resource_last_transfers; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: resource_subjects; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: resource_transfers; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: resource_types; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: resources; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: right_transfers; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: right_types; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: tree_types; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: trees; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: upload_logs; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Name: account__login_name__ui; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY accounts
    ADD CONSTRAINT account__login_name__ui UNIQUE (login_name);


--
-- Name: account__pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY accounts
    ADD CONSTRAINT account__pk PRIMARY KEY (id);


--
-- Name: certificate__pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY certificates
    ADD CONSTRAINT certificate__pk PRIMARY KEY (id);


--
-- Name: certificate_create_log__pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY certificate_create_logs
    ADD CONSTRAINT certificate_create_log__pk PRIMARY KEY (id);


--
-- Name: certificate_revoke_log__pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY certificate_revoke_logs
    ADD CONSTRAINT certificate_revoke_log__pk PRIMARY KEY (id);


--
-- Name: config__pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY configs
    ADD CONSTRAINT config__pk PRIMARY KEY (id);


--
-- Name: config_type__pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY config_types
    ADD CONSTRAINT config_type__pk PRIMARY KEY (id);


--
-- Name: copyright__pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY copyrights
    ADD CONSTRAINT copyright__pk PRIMARY KEY (id);


--
-- Name: device__pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY devices
    ADD CONSTRAINT device__pk PRIMARY KEY (id);


--
-- Name: log__pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY logs
    ADD CONSTRAINT log__pk PRIMARY KEY (id);


--
-- Name: organization__no__ui; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY organizations
    ADD CONSTRAINT organization__no__ui UNIQUE (no);


--
-- Name: organization__pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY organizations
    ADD CONSTRAINT organization__pk PRIMARY KEY (id);


--
-- Name: organization_type__pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY organization_types
    ADD CONSTRAINT organization_type__pk PRIMARY KEY (id);


--
-- Name: resource__pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY resources
    ADD CONSTRAINT resource__pk PRIMARY KEY (id);


--
-- Name: resource_last_transfer__pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY resource_last_transfers
    ADD CONSTRAINT resource_last_transfer__pk PRIMARY KEY (id);


--
-- Name: resource_last_transfer_log__pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY resource_last_transfer_logs
    ADD CONSTRAINT resource_last_transfer_log__pk PRIMARY KEY (id);


--
-- Name: resource_transfer__pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY resource_transfers
    ADD CONSTRAINT resource_transfer__pk PRIMARY KEY (id);


--
-- Name: right_transfer__pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY right_transfers
    ADD CONSTRAINT right_transfer__pk PRIMARY KEY (id);


--
-- Name: right_type__pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY right_types
    ADD CONSTRAINT right_type__pk PRIMARY KEY (id);


--
-- Name: subject__pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY resource_subjects
    ADD CONSTRAINT subject__pk PRIMARY KEY (id);


--
-- Name: upload_log__pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY upload_logs
    ADD CONSTRAINT upload_log__pk PRIMARY KEY (id);


--
-- Name: user__no__ui; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT user__no__ui UNIQUE (no);


--
-- Name: user__pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT user__pk PRIMARY KEY (id);


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

