--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = ON;
SET check_function_bodies = FALSE;
SET client_min_messages = WARNING;

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
-- Name: getSequenceNameByTableName(character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION "getSequenceNameByTableName"(tablename CHARACTER VARYING)
  RETURNS CHARACTER VARYING
LANGUAGE plpgsql
AS $$
DECLARE
  result VARCHAR;
  len    INTEGER;
BEGIN
  len = length(tableName);
  result = tableName || '__sync_id__seq';
  RETURN result;

END;
$$;


ALTER FUNCTION public."getSequenceNameByTableName"(tablename CHARACTER VARYING )
OWNER TO postgres;

--
-- Name: fillSyncFields2(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION "fillSyncFields2"()
  RETURNS TRIGGER
LANGUAGE plpgsql
AS $$
DECLARE
  seqName VARCHAR;
BEGIN
  seqName = getSequenceNameByTableName(CAST(TG_TABLE_NAME AS VARCHAR));
  NEW.createVersion = nextval(seqName);
  NEW.updateVersion = NEW.createVersion;
  RETURN NEW;
END;
$$;


ALTER FUNCTION public."fillSyncFields2"()
OWNER TO postgres;

--
-- Name: fillSyncFields4(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION "fillSyncFields4"()
  RETURNS TRIGGER
LANGUAGE plpgsql
AS $$
DECLARE
  seqName VARCHAR;
BEGIN
  seqName = getSequenceNameByTableName(CAST(TG_TABLE_NAME AS VARCHAR));
  NEW.createTime = CURRENT_TIMESTAMP;
  NEW.updateTime = NEW.createTime;
  NEW.createVersion = nextval(seqName);
  NEW.updateVersion = NEW.createVersion;
  RETURN NEW;
END;
$$;


ALTER FUNCTION public."fillSyncFields4"()
OWNER TO postgres;

--
-- Name: updateSyncFields2(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION "updateSyncFields2"()
  RETURNS TRIGGER
LANGUAGE plpgsql
AS $$
DECLARE
  seqName VARCHAR;
BEGIN
  seqName = getSequenceNameByTableName(CAST(TG_TABLE_NAME AS VARCHAR));
  NEW.updateVersion = nextval(seqName);
  RETURN NEW;
END;
$$;


ALTER FUNCTION public."updateSyncFields2"()
OWNER TO postgres;

--
-- Name: updateSyncFields4(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION "updateSyncFields4"()
  RETURNS TRIGGER
LANGUAGE plpgsql
AS $$
DECLARE
  seqName VARCHAR;
BEGIN
  seqName = getSequenceNameByTableName(CAST(TG_TABLE_NAME AS VARCHAR));
  NEW.updateTime = CURRENT_TIMESTAMP;
  NEW.updateVersion = nextval(seqName);
  RETURN NEW;
END;
$$;


ALTER FUNCTION public."updateSyncFields4"()
OWNER TO postgres;

SET default_tablespace = '';

SET default_with_oids = FALSE;

--
-- Name: accountCertificateMaps; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE "accountCertificateMaps" (
  id              BIGINT                         NOT NULL,
  "accountId"     BIGINT                         NOT NULL,
  certificate     CHARACTER VARYING(8192)        NOT NULL,
  "startTime"     TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL,
  "stopTime"      TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL,
  "instanceId"    BIGINT DEFAULT 0               NOT NULL,
  "creatorId"     BIGINT                         NOT NULL,
  "createTime"    TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL,
  "createVersion" BIGINT                         NOT NULL,
  "updaterId"     BIGINT                         NOT NULL,
  "updateTime"    TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL,
  "updateVersion" BIGINT                         NOT NULL,
  "rowState"      INTEGER                        NOT NULL,
  "rowVersion"    INTEGER                        NOT NULL
);


ALTER TABLE "accountCertificateMaps" OWNER TO postgres;

--
-- Name: accountCertificateMaps__sync_id__seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE "accountCertificateMaps__sync_id__seq"
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;


ALTER TABLE "accountCertificateMaps__sync_id__seq" OWNER TO postgres;

--
-- Name: accountRoles; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE "accountRoleMaps" (
  id              BIGINT                         NOT NULL,
  "accountId"     BIGINT                         NOT NULL,
  "roleId"        BIGINT                         NOT NULL,
  "instanceId"    BIGINT DEFAULT 0               NOT NULL,
  "creatorId"     BIGINT                         NOT NULL,
  "createTime"    TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL,
  "createVersion" BIGINT                         NOT NULL,
  "updaterId"     BIGINT                         NOT NULL,
  "updateTime"    TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL,
  "updateVersion" BIGINT                         NOT NULL,
  "rowState"      INTEGER                        NOT NULL,
  "rowVersion"    INTEGER                        NOT NULL
);


ALTER TABLE "accountRoleMaps" OWNER TO postgres;

--
-- Name: accountRoleMaps__sync_id__seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE "accountRoleMaps__sync_id__seq"
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;


ALTER TABLE "accountRoleMaps__sync_id__seq" OWNER TO postgres;

--
-- Name: accounts; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE accounts (
  id                    BIGINT                                                        NOT NULL,
  "loginName"           CHARACTER VARYING(127)                                        NOT NULL,
  password              CHARACTER VARYING(127)                                        NOT NULL,
  -- "subjectId" bigint NOT NULL, -- user.id or organization.id
  "canChangePassword"   INTEGER DEFAULT 0                                             NOT NULL,
  "passwordQuestion"    CHARACTER VARYING(127)                                        NOT NULL,
  "passwordAnswer"      CHARACTER VARYING(127)                                        NOT NULL,
  "ipAddressCreated"    CHARACTER VARYING(127) DEFAULT '0.0.0.0' :: CHARACTER VARYING,
  "ipAddressLast"       CHARACTER VARYING(127) DEFAULT '0.0.0.0' :: CHARACTER VARYING NOT NULL,
  "lastLoginTime"       TIMESTAMP(0) WITHOUT TIME ZONE                                NOT NULL,
  "loginCount"          INTEGER DEFAULT 0                                             NOT NULL,
  "passwordFailedCount" INTEGER DEFAULT 0                                             NOT NULL,
  currentCertificate    CHARACTER VARYING(8192),
  "sessionId"           CHARACTER VARYING(64),
  "instanceId"          BIGINT DEFAULT 0                                              NOT NULL,
  "creatorId"           BIGINT                                                        NOT NULL,
  "createTime"          TIMESTAMP(0) WITHOUT TIME ZONE                                NOT NULL,
  "createVersion"       BIGINT                                                        NOT NULL,
  "updaterId"           BIGINT                                                        NOT NULL,
  "updateTime"          TIMESTAMP(0) WITHOUT TIME ZONE                                NOT NULL,
  "updateVersion"       BIGINT                                                        NOT NULL,
  "rowState"            INTEGER                                                       NOT NULL,
  "rowVersion"          INTEGER                                                       NOT NULL
);


ALTER TABLE accounts OWNER TO postgres;

--
-- Name: accounts__sync_id__seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE accounts__sync_id__seq
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;


ALTER TABLE accounts__sync_id__seq OWNER TO postgres;

--
-- Name: resources; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE resources (
  id              BIGINT                         NOT NULL,
  no              CHARACTER(24)                  NOT NULL,
  name            CHARACTER VARYING(255)         NOT NULL,
  version         INTEGER,
  type            BIGINT                         NOT NULL,
  digest          CHARACTER VARYING(255),
  "subjectId"     BIGINT,
  "ownerId"       BIGINT                         NOT NULL,
  "authorId"      BIGINT                         NOT NULL,
  isbn            CHARACTER VARYING(100),
  "filePath"      CHARACTER VARYING(255),
  cover           CHARACTER VARYING(255),
  language        BIGINT,
  intro           CHARACTER VARYING(1000),
  author          CHARACTER VARYING(50),
  "instanceId"    BIGINT DEFAULT 0               NOT NULL,
  "creatorId"     BIGINT                         NOT NULL,
  "createTime"    TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL,
  "createVersion" BIGINT                         NOT NULL,
  "updaterId"     BIGINT                         NOT NULL,
  "updateTime"    TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL,
  "updateVersion" BIGINT                         NOT NULL,
  "rowState"      INTEGER                        NOT NULL,
  "rowVersion"    INTEGER                        NOT NULL
);


ALTER TABLE resources OWNER TO postgres;

--
-- Name: resources__sync_id__seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE resources__sync_id__seq
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;


ALTER TABLE resources__sync_id__seq OWNER TO postgres;

--
-- Name: certificateCreateLogs; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE "certificateCreateLogs" (
  id              BIGINT                         NOT NULL,
  no              BIGINT                         NOT NULL,
  "accountId"     BIGINT                         NOT NULL,
  "deviceId"      BIGINT                         NOT NULL,
  "userName"      CHARACTER VARYING(255)         NOT NULL,
  "companyName"   CHARACTER VARYING(255)         NOT NULL,
  "publicKey"     CHARACTER VARYING(1024)        NOT NULL,
  "sourceIp"      CHARACTER VARYING(255)         NOT NULL,
  "instanceId"    BIGINT DEFAULT 0               NOT NULL,
  "creatorId"     BIGINT                         NOT NULL,
  "createTime"    TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL,
  "createVersion" BIGINT                         NOT NULL,
  "updaterId"     BIGINT                         NOT NULL,
  "updateTime"    TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL,
  "updateVersion" BIGINT                         NOT NULL,
  "rowState"      INTEGER                        NOT NULL,
  "rowVersion"    INTEGER                        NOT NULL
);


ALTER TABLE "certificateCreateLogs" OWNER TO postgres;

--
-- Name: certificateCreateLogs__sync_id__seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE "certificateCreateLogs__sync_id__seq"
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;


ALTER TABLE "certificateCreateLogs__sync_id__seq" OWNER TO postgres;

--
-- Name: certificateRevokes; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE "certificateRevokeLogs" (
  id              BIGINT                         NOT NULL,
  no              BIGINT                         NOT NULL,
  "accountId"     BIGINT                         NOT NULL,
  "deviceId"      BIGINT                         NOT NULL,
  notes           CHARACTER VARYING(255)         NOT NULL,
  admin           BIGINT                         NOT NULL,
  "filePath"      CHARACTER VARYING(255)         NOT NULL,
  "instanceId"    BIGINT DEFAULT 0               NOT NULL,
  "creatorId"     BIGINT                         NOT NULL,
  "createTime"    TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL,
  "createVersion" BIGINT                         NOT NULL,
  "updaterId"     BIGINT                         NOT NULL,
  "updateTime"    TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL,
  "updateVersion" BIGINT                         NOT NULL,
  "rowState"      INTEGER                        NOT NULL,
  "rowVersion"    INTEGER                        NOT NULL
);


ALTER TABLE "certificateRevokeLogs" OWNER TO postgres;

--
-- Name: certificateRevokeLogs__sync_id__seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE "certificateRevokeLogs__sync_id__seq"
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;


ALTER TABLE "certificateRevokeLogs__sync_id__seq" OWNER TO postgres;

--
-- Name: certificates; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE certificates (
  id                       BIGINT                         NOT NULL,
  no                       BIGINT                         NOT NULL,
  "accountId"              BIGINT                         NOT NULL,
  "deviceId"               BIGINT                         NOT NULL,
  subject                  CHARACTER VARYING(255)         NOT NULL,
  issuer                   CHARACTER VARYING(255)         NOT NULL,
  "notBefore"              TIMESTAMP(6) WITHOUT TIME ZONE NOT NULL,
  "notAfter"               TIMESTAMP(6) WITHOUT TIME ZONE NOT NULL,
  "signatureAlgorithmName" CHARACTER VARYING(1024)        NOT NULL,
  "publicKey"              CHARACTER VARYING(1024)        NOT NULL,
  "savePath"               CHARACTER VARYING(255)         NOT NULL,
  "instanceId"             BIGINT DEFAULT 0               NOT NULL,
  "creatorId"              BIGINT                         NOT NULL,
  "createTime"             TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL,
  "createVersion"          BIGINT                         NOT NULL,
  "updaterId"              BIGINT                         NOT NULL,
  "updateTime"             TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL,
  "updateVersion"          BIGINT                         NOT NULL,
  "rowState"               INTEGER                        NOT NULL,
  "rowVersion"             INTEGER                        NOT NULL
);


ALTER TABLE certificates OWNER TO postgres;

--
-- Name: certificates__sync_id__seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE certificates__sync_id__seq
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;


ALTER TABLE certificates__sync_id__seq OWNER TO postgres;

--
-- Name: configTypes; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE "configTypes" (
  id              BIGINT                         NOT NULL,
  no              CHARACTER(24)                  NOT NULL,
  name            CHARACTER VARYING(255),
  "instanceId"    BIGINT DEFAULT 0               NOT NULL,
  "creatorId"     BIGINT                         NOT NULL,
  "createTime"    TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL,
  "createVersion" BIGINT                         NOT NULL,
  "updaterId"     BIGINT                         NOT NULL,
  "updateTime"    TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL,
  "updateVersion" BIGINT                         NOT NULL,
  "rowState"      INTEGER                        NOT NULL,
  "rowVersion"    INTEGER                        NOT NULL
);


ALTER TABLE "configTypes" OWNER TO postgres;

--
-- Name: configTypes__sync_id__seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE "configTypes__sync_id__seq"
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;


ALTER TABLE "configTypes__sync_id__seq" OWNER TO postgres;

--
-- Name: configs; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE configs (
  id              BIGINT                         NOT NULL,
  no              NAME                           NOT NULL, --same
  name            CHARACTER VARYING(255)         NOT NULL, --same
  key             CHARACTER VARYING(50), --same
  value           CHARACTER VARYING(255),
  "typeId"        BIGINT                         NOT NULL,
  "ownerId"       BIGINT,
  "instanceId"    BIGINT DEFAULT 0               NOT NULL,
  "creatorId"     BIGINT                         NOT NULL,
  "createTime"    TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL,
  "createVersion" BIGINT                         NOT NULL,
  "updaterId"     BIGINT                         NOT NULL,
  "updateTime"    TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL,
  "updateVersion" BIGINT                         NOT NULL,
  "rowState"      INTEGER                        NOT NULL,
  "rowVersion"    INTEGER                        NOT NULL
);


ALTER TABLE configs OWNER TO postgres;

--
-- Name: configs__sync_id__seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE configs__sync_id__seq
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;


ALTER TABLE configs__sync_id__seq OWNER TO postgres;

--
-- Name: copyrights; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE copyrights (
  id              BIGINT                         NOT NULL,
  no              CHARACTER(24)                  NOT NULL,
  "resourceId"    BIGINT,
  version         INTEGER,
  "ownerId"       BIGINT                         NOT NULL,
  "authorId"      BIGINT                         NOT NULL,
  expiration      TIMESTAMP(0) WITHOUT TIME ZONE,
  status          INTEGER DEFAULT 0              NOT NULL,
  "instanceId"    BIGINT DEFAULT 0               NOT NULL,
  "creatorId"     BIGINT                         NOT NULL,
  "createTime"    TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL,
  "createVersion" BIGINT                         NOT NULL,
  "updaterId"     BIGINT                         NOT NULL,
  "updateTime"    TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL,
  "updateVersion" BIGINT                         NOT NULL,
  "rowState"      INTEGER                        NOT NULL,
  "rowVersion"    INTEGER                        NOT NULL
);


ALTER TABLE copyrights OWNER TO postgres;

--
-- Name: copyrights__sync_id__seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE copyrights__sync_id__seq
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;


ALTER TABLE copyrights__sync_id__seq OWNER TO postgres;

CREATE TABLE devices (
  id              BIGINT                         NOT NULL,
  no              NAME,
  name            NAME,
  description     TEXT,
  "instanceId"    BIGINT DEFAULT 0               NOT NULL,
  "creatorId"     BIGINT                         NOT NULL,
  "createTime"    TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL,
  "createVersion" BIGINT                         NOT NULL,
  "updaterId"     BIGINT                         NOT NULL,
  "updateTime"    TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL,
  "updateVersion" BIGINT                         NOT NULL,
  "rowState"      INTEGER                        NOT NULL,
  "rowVersion"    INTEGER                        NOT NULL
);

ALTER TABLE "devices" OWNER TO postgres;

CREATE SEQUENCE "devices__sync_id__seq"
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;


ALTER TABLE "devices__sync_id__seq" OWNER TO postgres;


--
-- Name: resourceLastTransferLogs; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE "resourceLastTransferLogs" (
  id                       BIGINT                         NOT NULL,
  "resourceLastTransferId" BIGINT                         NOT NULL,
  "receiverId"             BIGINT                         NOT NULL,
  "deviceId"               BIGINT                         NOT NULL,
  "receiverSignatrue"      CHARACTER VARYING(255)         NOT NULL,
  "instanceId"             BIGINT DEFAULT 0               NOT NULL,
  "creatorId"              BIGINT                         NOT NULL,
  "createTime"             TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL,
  "createVersion"          BIGINT                         NOT NULL,
  "updaterId"              BIGINT                         NOT NULL,
  "updateTime"             TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL,
  "updateVersion"          BIGINT                         NOT NULL,
  "rowState"               INTEGER                        NOT NULL,
  "rowVersion"             INTEGER                        NOT NULL
);


ALTER TABLE "resourceLastTransferLogs" OWNER TO postgres;

--
-- Name: resourceLastTransferLogs__sync_id__seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE "resourceLastTransferLogs__sync_id__seq"
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;


ALTER TABLE "resourceLastTransferLogs__sync_id__seq" OWNER TO postgres;

--
-- Name: logs; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE logs (
  id              BIGINT                         NOT NULL,
  kind            INTEGER DEFAULT 0              NOT NULL,
  "accountId"     BIGINT                         NOT NULL,
  name            CHARACTER VARYING(255)         NOT NULL,
  comment         TEXT                           NOT NULL,
  level           INTEGER                        NOT NULL,
  result          INTEGER DEFAULT 0              NOT NULL,
  "instanceId"    BIGINT DEFAULT 0               NOT NULL,
  "creatorId"     BIGINT                         NOT NULL,
  "createTime"    TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL,
  "createVersion" BIGINT                         NOT NULL,
  "updaterId"     BIGINT                         NOT NULL,
  "updateTime"    TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL,
  "updateVersion" BIGINT                         NOT NULL,
  "rowState"      INTEGER                        NOT NULL,
  "rowVersion"    INTEGER                        NOT NULL
);


ALTER TABLE logs OWNER TO postgres;

--
-- Name: logs__sync_id__seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE logs__sync_id__seq
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;


ALTER TABLE logs__sync_id__seq OWNER TO postgres;

--
-- Name: operationLogs; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE "operationLogs" (
  id              BIGINT                         NOT NULL,
  "tableType"     INTEGER                        NOT NULL,
  type            BIGINT                         NOT NULL,
  notes           CHARACTER VARYING(255),
  status          INTEGER,
  "instanceId"    BIGINT DEFAULT 0               NOT NULL,
  "creatorId"     BIGINT                         NOT NULL,
  "createTime"    TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL,
  "createVersion" BIGINT                         NOT NULL,
  "updaterId"     BIGINT                         NOT NULL,
  "updateTime"    TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL,
  "updateVersion" BIGINT                         NOT NULL,
  "rowState"      INTEGER                        NOT NULL,
  "rowVersion"    INTEGER                        NOT NULL
);


ALTER TABLE "operationLogs" OWNER TO postgres;

--
-- Name: operationLogs__sync_id__seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE "operationLogs__sync_id__seq"
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;


ALTER TABLE "operationLogs__sync_id__seq" OWNER TO postgres;

--
-- Name: organizationAccountMaps; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE "organizationAccountMaps" (
  id               BIGINT                         NOT NULL,
  "accountId"      BIGINT                         NOT NULL,
  "organizationId" BIGINT                         NOT NULL,
  active           INTEGER,
  type             INTEGER DEFAULT 0              NOT NULL,
  begin            TIMESTAMP(0) WITHOUT TIME ZONE,
  "end"            TIMESTAMP(0) WITHOUT TIME ZONE,
  "instanceId"     BIGINT DEFAULT 0               NOT NULL,
  "creatorId"      BIGINT                         NOT NULL,
  "createTime"     TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL,
  "createVersion"  BIGINT                         NOT NULL,
  "updaterId"      BIGINT                         NOT NULL,
  "updateTime"     TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL,
  "updateVersion"  BIGINT                         NOT NULL,
  "rowState"       INTEGER                        NOT NULL,
  "rowVersion"     INTEGER                        NOT NULL
);


ALTER TABLE "organizationAccountMaps" OWNER TO postgres;

--
-- Name: organizationAccountMaps__sync_id__seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE "organizationAccountMaps__sync_id__seq"
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;


ALTER TABLE "organizationAccountMaps__sync_id__seq" OWNER TO postgres;

--
-- Name: organizationDeviceMaps; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE "organizationDeviceMaps" (
  id               BIGINT                         NOT NULL,
  "accountId"      BIGINT                         NOT NULL,
  "organizationId" BIGINT                         NOT NULL,
  active           INTEGER,
  type             INTEGER DEFAULT 0              NOT NULL,
  begin            TIMESTAMP(0) WITHOUT TIME ZONE,
  "end"            TIMESTAMP(0) WITHOUT TIME ZONE,
  "instanceId"     BIGINT DEFAULT 0               NOT NULL,
  "creatorId"      BIGINT                         NOT NULL,
  "createTime"     TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL,
  "createVersion"  BIGINT                         NOT NULL,
  "updaterId"      BIGINT                         NOT NULL,
  "updateTime"     TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL,
  "updateVersion"  BIGINT                         NOT NULL,
  "rowState"       INTEGER                        NOT NULL,
  "rowVersion"     INTEGER                        NOT NULL
);


ALTER TABLE "organizationDeviceMaps" OWNER TO postgres;

--
-- Name: organizationDeviceMaps__sync_id__seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE "organizationDeviceMaps__sync_id__seq"
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;


ALTER TABLE "organizationDeviceMaps__sync_id__seq" OWNER TO postgres;

--
-- Name: organizationResourceMaps; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE "organizationResourceMaps" (
  id               BIGINT                         NOT NULL,
  "organizationId" BIGINT                         NOT NULL,
  "resourceId"     BIGINT                         NOT NULL,
  "instanceId"     BIGINT DEFAULT 0               NOT NULL,
  "creatorId"      BIGINT                         NOT NULL,
  "createTime"     TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL,
  "createVersion"  BIGINT                         NOT NULL,
  "updaterId"      BIGINT                         NOT NULL,
  "updateTime"     TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL,
  "updateVersion"  BIGINT                         NOT NULL,
  "rowState"       INTEGER                        NOT NULL,
  "rowVersion"     INTEGER                        NOT NULL
);


ALTER TABLE "organizationResourceMaps" OWNER TO postgres;

--
-- Name: organizationResourceMaps__sync_id__seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE "organizationResourceMaps__sync_id__seq"
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;


ALTER TABLE "organizationResourceMaps__sync_id__seq" OWNER TO postgres;

--
-- Name: organizationTypes; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE "organizationTypes" (
  id              BIGINT                         NOT NULL,
  no              CHARACTER(24)                  NOT NULL,
  name            CHARACTER VARYING(127)         NOT NULL,
  "instanceId"    BIGINT DEFAULT 0               NOT NULL,
  "creatorId"     BIGINT                         NOT NULL,
  "createTime"    TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL,
  "createVersion" BIGINT                         NOT NULL,
  "updaterId"     BIGINT                         NOT NULL,
  "updateTime"    TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL,
  "updateVersion" BIGINT                         NOT NULL,
  "rowState"      INTEGER                        NOT NULL,
  "rowVersion"    INTEGER                        NOT NULL
);


ALTER TABLE "organizationTypes" OWNER TO postgres;

--
-- Name: organizationTypes__sync_id__seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE "organizationTypes__sync_id__seq"
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;


ALTER TABLE "organizationTypes__sync_id__seq" OWNER TO postgres;

CREATE TABLE trees (
  id              BIGINT                         NOT NULL,
  no              NAME,
  name            NAME,
  "typeId"        BIGINT                         NOT NULL,
  "parentId"      BIGINT,
  "description"   TEXT,
  "instanceId"    BIGINT DEFAULT 0               NOT NULL,
  "creatorId"     BIGINT                         NOT NULL,
  "createTime"    TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL,
  "createVersion" BIGINT                         NOT NULL,
  "updaterId"     BIGINT                         NOT NULL,
  "updateTime"    TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL,
  "updateVersion" BIGINT                         NOT NULL,
  "rowState"      INTEGER                        NOT NULL,
  "rowVersion"    INTEGER                        NOT NULL
);

--
-- tree type include：
-- organization-tree
-- site/module/function-tree
-- scheme/stage/grade-tree
-- dist/school/grade/class-tree
-- region-tree
-- resourceClassifier-tree
-- role-tree
--

CREATE TABLE treeTypes (
  id              BIGINT                         NOT NULL,
  name            NAME,
  description     TEXT,
  "instanceId"    BIGINT DEFAULT 0               NOT NULL,
  "creatorId"     BIGINT                         NOT NULL,
  "createTime"    TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL,
  "createVersion" BIGINT                         NOT NULL,
  "updaterId"     BIGINT                         NOT NULL,
  "updateTime"    TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL,
  "updateVersion" BIGINT                         NOT NULL,
  "rowState"      INTEGER                        NOT NULL,
  "rowVersion"    INTEGER                        NOT NULL
);

CREATE TABLE markups (
  id              BIGINT                         NOT NULL,
  tableId         BIGINT                         NOT NULL,
  rowId           BIGINT                         NOT NULL,
  name            NAME,
  value           TEXT,
  "instanceId"    BIGINT DEFAULT 0               NOT NULL,
  "creatorId"     BIGINT                         NOT NULL,
  "createTime"    TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL,
  "createVersion" BIGINT                         NOT NULL,
  "updaterId"     BIGINT                         NOT NULL,
  "updateTime"    TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL,
  "updateVersion" BIGINT                         NOT NULL,
  "rowState"      INTEGER                        NOT NULL,
  "rowVersion"    INTEGER                        NOT NULL
);

--
-- Name: resourceLastTransfers; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE "resourceLastTransfers" (
  id                   BIGINT                         NOT NULL,
  "resourceTransferId" BIGINT                         NOT NULL,
  "resourcePath"       CHARACTER VARYING(255)         NOT NULL,
  "watermarkContent"   CHARACTER VARYING(255),
  "instanceId"         BIGINT DEFAULT 0               NOT NULL,
  "creatorId"          BIGINT                         NOT NULL,
  "createTime"         TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL,
  "createVersion"      BIGINT                         NOT NULL,
  "updaterId"          BIGINT                         NOT NULL,
  "updateTime"         TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL,
  "updateVersion"      BIGINT                         NOT NULL,
  "rowState"           INTEGER                        NOT NULL,
  "rowVersion"         INTEGER                        NOT NULL
);


ALTER TABLE "resourceLastTransfers" OWNER TO postgres;

--
-- Name: resourceLastTransfers__sync_id__seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE "resourceLastTransfers__sync_id__seq"
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;


ALTER TABLE "resourceLastTransfers__sync_id__seq" OWNER TO postgres;

--
-- Name: resourceTransfers; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE "resourceTransfers" (
  id                  BIGINT                         NOT NULL,
  no                  CHARACTER(24)                  NOT NULL,
  "senderId"          BIGINT                         NOT NULL,
  "receiverId"        BIGINT                         NOT NULL,
  "senderSignatrue"   CHARACTER VARYING(255)         NOT NULL,
  "receiverSignatrue" CHARACTER VARYING(255)         NOT NULL,
  "copyrightId"       BIGINT                         NOT NULL,
  "rightTransferId"   BIGINT,
  key                 CHARACTER VARYING(1024)        NOT NULL,
  "instanceId"        BIGINT DEFAULT 0               NOT NULL,
  "creatorId"         BIGINT                         NOT NULL,
  "createTime"        TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL,
  "createVersion"     BIGINT                         NOT NULL,
  "updaterId"         BIGINT                         NOT NULL,
  "updateTime"        TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL,
  "updateVersion"     BIGINT                         NOT NULL,
  "rowState"          INTEGER                        NOT NULL,
  "rowVersion"        INTEGER                        NOT NULL
);


ALTER TABLE "resourceTransfers" OWNER TO postgres;

--
-- Name: resourceTransfers__sync_id__seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE "resourceTransfers__sync_id__seq"
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;


ALTER TABLE "resourceTransfers__sync_id__seq" OWNER TO postgres;

--
-- Name: rightTransferItems; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE "rightTransferItems" (
  id              BIGINT                         NOT NULL,
  "transferId"    BIGINT                         NOT NULL,
  "rightId"       BIGINT                         NOT NULL,
  amount          INTEGER                        NOT NULL,
  expiration      TIMESTAMP(0) WITHOUT TIME ZONE,
  "instanceId"    BIGINT DEFAULT 0               NOT NULL,
  "creatorId"     BIGINT                         NOT NULL,
  "createTime"    TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL,
  "createVersion" BIGINT                         NOT NULL,
  "updaterId"     BIGINT                         NOT NULL,
  "updateTime"    TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL,
  "updateVersion" BIGINT                         NOT NULL,
  "rowState"      INTEGER                        NOT NULL,
  "rowVersion"    INTEGER                        NOT NULL
);


ALTER TABLE "rightTransferItems" OWNER TO postgres;

--
-- Name: rightTransferItems__sync_id__seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE "rightTransferItems__sync_id__seq"
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;


ALTER TABLE "rightTransferItems__sync_id__seq" OWNER TO postgres;

--
-- Name: rightTransfers; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE "rightTransfers" (
  id              BIGINT                         NOT NULL,
  no              CHARACTER(24)                  NOT NULL,
  "copyrightId"   BIGINT                         NOT NULL,
  "fromId"        BIGINT                         NOT NULL,
  "toId"          BIGINT                         NOT NULL,
  "instanceId"    BIGINT DEFAULT 0               NOT NULL,
  "creatorId"     BIGINT                         NOT NULL,
  "createTime"    TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL,
  "createVersion" BIGINT                         NOT NULL,
  "updaterId"     BIGINT                         NOT NULL,
  "updateTime"    TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL,
  "updateVersion" BIGINT                         NOT NULL,
  "rowState"      INTEGER                        NOT NULL,
  "rowVersion"    INTEGER                        NOT NULL
);


ALTER TABLE "rightTransfers" OWNER TO postgres;

--
-- Name: rightTransfers__sync_id__seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE "rightTransfers__sync_id__seq"
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;


ALTER TABLE "rightTransfers__sync_id__seq" OWNER TO postgres;

--
-- Name: rightTypes; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE "rightTypes" (
  id              BIGINT                         NOT NULL,
  no              CHARACTER(24)                  NOT NULL,
  name            CHARACTER VARYING(255)         NOT NULL,
  "instanceId"    BIGINT DEFAULT 0               NOT NULL,
  "creatorId"     BIGINT                         NOT NULL,
  "createTime"    TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL,
  "createVersion" BIGINT                         NOT NULL,
  "updaterId"     BIGINT                         NOT NULL,
  "updateTime"    TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL,
  "updateVersion" BIGINT                         NOT NULL,
  "rowState"      INTEGER                        NOT NULL,
  "rowVersion"    INTEGER                        NOT NULL
);


ALTER TABLE "rightTypes" OWNER TO postgres;

--
-- Name: rightTypes__sync_id__seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE "rightTypes__sync_id__seq"
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;


ALTER TABLE "rightTypes__sync_id__seq" OWNER TO postgres;

--
-- Name: rights; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE rights (
  id              BIGINT                         NOT NULL,
  no              CHARACTER(24)                  NOT NULL,
  "copyrightId"   BIGINT                         NOT NULL,
  "typeId"        BIGINT                         NOT NULL,
  expiration      TIMESTAMP(0) WITHOUT TIME ZONE,
  amount          INTEGER                        NOT NULL,
  "rightOwnerId"  BIGINT                         NOT NULL,
  "expenseAmount" INTEGER                        NOT NULL,
  status          INTEGER DEFAULT 0              NOT NULL,
  "instanceId"    BIGINT DEFAULT 0               NOT NULL,
  "creatorId"     BIGINT                         NOT NULL,
  "createTime"    TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL,
  "createVersion" BIGINT                         NOT NULL,
  "updaterId"     BIGINT                         NOT NULL,
  "updateTime"    TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL,
  "updateVersion" BIGINT                         NOT NULL,
  "rowState"      INTEGER                        NOT NULL,
  "rowVersion"    INTEGER                        NOT NULL
);


ALTER TABLE rights OWNER TO postgres;

--
-- Name: rights__sync_id__seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE rights__sync_id__seq
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;


ALTER TABLE rights__sync_id__seq OWNER TO postgres;

--
-- Name: rolePermissionMaps; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE "rolePermissionMaps" (
  id              BIGINT                         NOT NULL,
  "roleId"        BIGINT                         NOT NULL,
  "moduleId"      BIGINT                         NOT NULL,
  "functionId"    BIGINT,
  "instanceId"    BIGINT DEFAULT 0               NOT NULL,
  "creatorId"     BIGINT                         NOT NULL,
  "createTime"    TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL,
  "createVersion" BIGINT                         NOT NULL,
  "updaterId"     BIGINT                         NOT NULL,
  "updateTime"    TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL,
  "updateVersion" BIGINT                         NOT NULL,
  "rowState"      INTEGER                        NOT NULL,
  "rowVersion"    INTEGER                        NOT NULL
);


ALTER TABLE "rolePermissionMaps" OWNER TO postgres;

--
-- Name: rolePermissionMaps__sync_id__seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE "rolePermissionMaps__sync_id__seq"
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;


ALTER TABLE "rolePermissionMaps__sync_id__seq" OWNER TO postgres;

--
-- Name: roles; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE roles (
  id              BIGINT                         NOT NULL,
  name            CHARACTER VARYING(127)         NOT NULL,
  description     CHARACTER VARYING(50),
  "instanceId"    BIGINT DEFAULT 0               NOT NULL,
  "creatorId"     BIGINT                         NOT NULL,
  "createTime"    TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL,
  "createVersion" BIGINT                         NOT NULL,
  "updaterId"     BIGINT                         NOT NULL,
  "updateTime"    TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL,
  "updateVersion" BIGINT                         NOT NULL,
  "rowState"      INTEGER                        NOT NULL,
  "rowVersion"    INTEGER                        NOT NULL
);


ALTER TABLE roles OWNER TO postgres;

--
-- Name: roles__sync_id__seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE roles__sync_id__seq
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;


ALTER TABLE roles__sync_id__seq OWNER TO postgres;

--
-- Name: subjects; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE subjects (
  id              BIGINT                         NOT NULL,
  code            CHARACTER VARYING(50)          NOT NULL,
  name            CHARACTER VARYING(127),
  "instanceId"    BIGINT DEFAULT 0               NOT NULL,
  "creatorId"     BIGINT                         NOT NULL,
  "createTime"    TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL,
  "createVersion" BIGINT                         NOT NULL,
  "updaterId"     BIGINT                         NOT NULL,
  "updateTime"    TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL,
  "updateVersion" BIGINT                         NOT NULL,
  "rowState"      INTEGER                        NOT NULL,
  "rowVersion"    INTEGER                        NOT NULL
);


ALTER TABLE subjects OWNER TO postgres;

--
-- Name: subjects__sync_id__seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE subjects__sync_id__seq
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;


ALTER TABLE subjects__sync_id__seq OWNER TO postgres;

--
-- Name: userAccountMaps; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE "userAccountMaps" (
  id              BIGINT                         NOT NULL,
  "userId"        BIGINT                         NOT NULL,
  "resourceId"    BIGINT                         NOT NULL,
  "instanceId"    BIGINT DEFAULT 0               NOT NULL,
  "creatorId"     BIGINT                         NOT NULL,
  "createTime"    TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL,
  "createVersion" BIGINT                         NOT NULL,
  "updaterId"     BIGINT                         NOT NULL,
  "updateTime"    TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL,
  "updateVersion" BIGINT                         NOT NULL,
  "rowState"      INTEGER                        NOT NULL,
  "rowVersion"    INTEGER                        NOT NULL
);


ALTER TABLE "userAccountMaps" OWNER TO postgres;

--
-- Name: userAccountMaps__sync_id__seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE "userAccountMaps__sync_id__seq"
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;


ALTER TABLE "userAccountMaps__sync_id__seq" OWNER TO postgres;

--
-- Name: userDeviceMaps; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE "userDeviceMaps" (
  id              BIGINT                         NOT NULL,
  "userId"        BIGINT                         NOT NULL,
  "resourceId"    BIGINT                         NOT NULL,
  "instanceId"    BIGINT DEFAULT 0               NOT NULL,
  "creatorId"     BIGINT                         NOT NULL,
  "createTime"    TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL,
  "createVersion" BIGINT                         NOT NULL,
  "updaterId"     BIGINT                         NOT NULL,
  "updateTime"    TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL,
  "updateVersion" BIGINT                         NOT NULL,
  "rowState"      INTEGER                        NOT NULL,
  "rowVersion"    INTEGER                        NOT NULL
);


ALTER TABLE "userDeviceMaps" OWNER TO postgres;

--
-- Name: userDeviceMaps__sync_id__seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE "userDeviceMaps__sync_id__seq"
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;


ALTER TABLE "userDeviceMaps__sync_id__seq" OWNER TO postgres;

--
-- Name: userResourceMaps; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE "userResourceMaps" (
  id              BIGINT                         NOT NULL,
  "userId"        BIGINT                         NOT NULL,
  "resourceId"    BIGINT                         NOT NULL,
  "instanceId"    BIGINT DEFAULT 0               NOT NULL,
  "creatorId"     BIGINT                         NOT NULL,
  "createTime"    TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL,
  "createVersion" BIGINT                         NOT NULL,
  "updaterId"     BIGINT                         NOT NULL,
  "updateTime"    TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL,
  "updateVersion" BIGINT                         NOT NULL,
  "rowState"      INTEGER                        NOT NULL,
  "rowVersion"    INTEGER                        NOT NULL
);


ALTER TABLE "userResourceMaps" OWNER TO postgres;

--
-- Name: userResourceMaps__sync_id__seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE "userResourceMaps__sync_id__seq"
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;


ALTER TABLE "userResourceMaps__sync_id__seq" OWNER TO postgres;

--
-- Name: userMessages; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE "userMessages" (
  id              BIGINT                         NOT NULL,
  type            INTEGER                        NOT NULL,
  "senderId"      BIGINT                         NOT NULL,
  "receiverId"    BIGINT                         NOT NULL,
  "parentId"      BIGINT,
  title           CHARACTER VARYING(255)         NOT NULL,
  content         TEXT                           NOT NULL,
  shield          INTEGER DEFAULT 0              NOT NULL,
  "rootId"        BIGINT,
  "readTime"      TIMESTAMP(0) WITHOUT TIME ZONE,
  "instanceId"    BIGINT DEFAULT 0               NOT NULL,
  "creatorId"     BIGINT                         NOT NULL,
  "createTime"    TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL,
  "createVersion" BIGINT                         NOT NULL,
  "updaterId"     BIGINT                         NOT NULL,
  "updateTime"    TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL,
  "updateVersion" BIGINT                         NOT NULL,
  "rowState"      INTEGER                        NOT NULL,
  "rowVersion"    INTEGER                        NOT NULL
);


ALTER TABLE "userMessages" OWNER TO postgres;

--
-- Name: userMessages__sync_id__seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE "userMessages__sync_id__seq"
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;


ALTER TABLE "userMessages__sync_id__seq" OWNER TO postgres;

--
-- Name: users; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE users (
  id               BIGINT                         NOT NULL,
  no               CHARACTER VARYING(31)          NOT NULL,
  name             CHARACTER VARYING(127)         NOT NULL,
  "organizationId" BIGINT                         NOT NULL,
  "idType"         CHARACTER(1)                   NOT NULL,
  "idNo"           CHARACTER VARYING(127)         NOT NULL,
  "companyName"    CHARACTER VARYING(127),
  "companyNo"      CHARACTER VARYING(127),
  "position"       CHARACTER VARYING(127),
  address          CHARACTER VARYING(255),
  "instanceId"     BIGINT DEFAULT 0               NOT NULL,
  "creatorId"      BIGINT                         NOT NULL,
  "createTime"     TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL,
  "createVersion"  BIGINT                         NOT NULL,
  "updaterId"      BIGINT                         NOT NULL,
  "updateTime"     TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL,
  "updateVersion"  BIGINT                         NOT NULL,
  "rowState"       INTEGER                        NOT NULL,
  "rowVersion"     INTEGER                        NOT NULL
);


ALTER TABLE users OWNER TO postgres;

--
-- Name: users__sync_id__seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE users__sync_id__seq
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;


ALTER TABLE users__sync_id__seq OWNER TO postgres;

--
-- Data for Name: accountRoleMaps; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Name: accountRoleMaps__sync_id__seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('"accountRoleMaps__sync_id__seq"', 1, FALSE);


--
-- Data for Name: accounts; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Name: accounts__sync_id__seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('accounts__sync_id__seq', 1, FALSE);


--
-- Data for Name: resources; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Name: resources__sync_id__seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('"resources__sync_id__seq"', 1, FALSE);


--
-- Data for Name: certificateCreateLogs; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Name: certificateCreateLogs__sync_id__seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('"certificateCreateLogs__sync_id__seq"', 1, FALSE);


--
-- Data for Name: certificateRevokeLogs; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Name: certificateRevokeLogs__sync_id__seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('"certificateRevokeLogs__sync_id__seq"', 1, FALSE);


--
-- Data for Name: certificates; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Name: certificates__sync_id__seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('certificates__sync_id__seq', 1, FALSE);


--
-- Data for Name: configTypes; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Name: configTypes__sync_id__seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('"configTypes__sync_id__seq"', 1, FALSE);


--
-- Data for Name: configs; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Name: configs__sync_id__seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('configs__sync_id__seq', 1, FALSE);


--
-- Data for Name: copyrights; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Name: copyrights__sync_id__seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('copyrights__sync_id__seq', 1, FALSE);


--
-- Data for Name: lastProtectedResourceTransferLogs; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Name: resourceLastTransferLogs__sync_id__seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('"resourceLastTransferLogs__sync_id__seq"', 1, FALSE);


--
-- Name: devices__sync_id__seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('devices__sync_id__seq', 1, FALSE);


--
-- Data for Name: logs; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Name: logs__sync_id__seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('logs__sync_id__seq', 1, FALSE);


--
-- Data for Name: operationLogs; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Name: operationLogs__sync_id__seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('"operationLogs__sync_id__seq"', 1, FALSE);


--
-- Data for Name: organizationAccountMaps; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Name: organizationAccountMaps__sync_id__seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('"organizationAccountMaps__sync_id__seq"', 1, FALSE);


--
-- Data for Name: organizationDeviceMaps; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Name: organizationDeviceMaps__sync_id__seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('"organizationDeviceMaps__sync_id__seq"', 1, FALSE);


--
-- Data for Name: organizationResourceMaps; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Name: organizationResourceMaps__sync_id__seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('"organizationResourceMaps__sync_id__seq"', 1, FALSE);


--
-- Data for Name: organizationTypes; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Name: organizationTypes__sync_id__seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('"organizationTypes__sync_id__seq"', 1, FALSE);


--
-- Data for Name: resourceLastTransfers; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Name: resourceLastTransfers__sync_id__seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('"resourceLastTransfers__sync_id__seq"', 1, FALSE);


--
-- Data for Name: resourceTransfers; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Name: resourceTransfers__sync_id__seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('"resourceTransfers__sync_id__seq"', 1, FALSE);


--
-- Data for Name: resources; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Name: resources__sync_id__seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('resources__sync_id__seq', 1, FALSE);


--
-- Data for Name: rightTransferItems; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Name: rightTransferItems__sync_id__seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('"rightTransferItems__sync_id__seq"', 1, FALSE);


--
-- Data for Name: rightTransfers; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Name: rightTransfers__sync_id__seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('"rightTransfers__sync_id__seq"', 1, FALSE);


--
-- Data for Name: rightTypes; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Name: rightTypes__sync_id__seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('"rightTypes__sync_id__seq"', 1, FALSE);


--
-- Data for Name: rights; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Name: rights__sync_id__seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('rights__sync_id__seq', 1, FALSE);


--
-- Data for Name: rolePermissionMaps; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Name: rolePermissionMaps__sync_id__seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('"rolePermissionMaps__sync_id__seq"', 1, FALSE);


--
-- Data for Name: roles; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Name: roles__sync_id__seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('roles__sync_id__seq', 1, FALSE);


--
-- Data for Name: subjects; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Name: subjects__sync_id__seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('subjects__sync_id__seq', 1, FALSE);


--
-- Data for Name: userAccountMaps; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Name: userAccountMaps__sync_id__seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('"userAccountMaps__sync_id__seq"', 1, FALSE);


--
-- Data for Name: userDeviceMaps; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Name: userDeviceMaps__sync_id__seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('"userDeviceMaps__sync_id__seq"', 1, FALSE);


--
-- Data for Name: userResourceMaps; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Name: userResourceMaps__sync_id__seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('"userResourceMaps__sync_id__seq"', 1, FALSE);


--
-- Data for Name: userMessages; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Name: userMessages__sync_id__seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('"userMessages__sync_id__seq"', 1, FALSE);


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Name: users__sync_id__seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('users__sync_id__seq', 1, FALSE);


--
-- Name: account__pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY accounts
ADD CONSTRAINT account__pk PRIMARY KEY (id);


--
-- Name: account__username__ui; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY accounts
ADD CONSTRAINT account__username__ui UNIQUE ("loginName");


--
-- Name: accountrole__pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY "accountRoleMaps"
ADD CONSTRAINT accountrole__pk PRIMARY KEY (id);


--
-- Name: resources__pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY resources
ADD CONSTRAINT resource__pk PRIMARY KEY (id);


--
-- Name: certificate__pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY certificates
ADD CONSTRAINT certificate__pk PRIMARY KEY (id);


--
-- Name: certificatecreatelog__pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY "certificateCreateLogs"
ADD CONSTRAINT certificatecreatelog__pk PRIMARY KEY (id);


--
-- Name: certificaterevokelog__pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY "certificateRevokeLogs"
ADD CONSTRAINT certificaterevokelog__pk PRIMARY KEY (id);


--
-- Name: config__pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY configs
ADD CONSTRAINT config__pk PRIMARY KEY (id);


--
-- Name: copyright__pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY copyrights
ADD CONSTRAINT copyright__pk PRIMARY KEY (id);


--
-- Name: lastProtectedResourceTransferLog__pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY "resourceLastTransferLogs"
ADD CONSTRAINT resourceLastTransferLogs__pk PRIMARY KEY (id);


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
-- Name: operationlog__pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY "operationLogs"
ADD CONSTRAINT operationlog__pk PRIMARY KEY (id);


--
-- Name: organizationaccountmap__pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY "organizationAccountMaps"
ADD CONSTRAINT organizationaccountmap__pk PRIMARY KEY (id);


--
-- Name: organizationcatalog__pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY "organizationResourceMaps"
ADD CONSTRAINT organizationresourcemap__pk PRIMARY KEY (id);


--
-- Name: organizationtype___pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY "organizationTypes"
ADD CONSTRAINT organizationtype___pk PRIMARY KEY (id);


--
-- Name: resourceLastTransfers__pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY "resourceLastTransfers"
ADD CONSTRAINT resourceLastTransfers__pk PRIMARY KEY (id);


--
-- Name: resourceTransfers__pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY "resourceTransfers"
ADD CONSTRAINT resourceTransfers__pk PRIMARY KEY (id);


--
-- Name: right__pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY rights
ADD CONSTRAINT right__pk PRIMARY KEY (id);


--
-- Name: righttransfer__pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY "rightTransfers"
ADD CONSTRAINT righttransfer__pk PRIMARY KEY (id);


--
-- Name: righttransferitem__pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY "rightTransferItems"
ADD CONSTRAINT righttransferitem__pk PRIMARY KEY (id);


--
-- Name: righttype__pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY "rightTypes"
ADD CONSTRAINT righttype__pk PRIMARY KEY (id);


--
-- Name: role__pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY roles
ADD CONSTRAINT role__pk PRIMARY KEY (id);


--
-- Name: rolepermissionmap__pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY "rolePermissionMaps"
ADD CONSTRAINT rolepermissionmap__pk PRIMARY KEY (id);


--
-- Name: subject__pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY subjects
ADD CONSTRAINT subject__pk PRIMARY KEY (id);


--
-- Name: type__pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY "configTypes"
ADD CONSTRAINT type__pk PRIMARY KEY (id);


--
-- Name: user__pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY users
ADD CONSTRAINT user__pk PRIMARY KEY (id);


--
-- Name: user__serialno__ui; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY users
ADD CONSTRAINT user__serialno__ui UNIQUE ("no");


--
-- Name: userresourcemap__pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE ONLY "userResourceMaps"
ADD CONSTRAINT userresourcemap__pk PRIMARY KEY (id);


--
-- Name: usermessage__pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY "userMessages"
ADD CONSTRAINT usermessage__pk PRIMARY KEY (id);


--
-- Name: accountKeysInsertTrigger; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER "accountKeysInsertTrigger" BEFORE INSERT ON "accountCertificateMaps" FOR EACH ROW EXECUTE PROCEDURE "fillSyncFields4"();


--
-- Name: accountKeysUpdateTrigger; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER "accountKeysUpdateTrigger" BEFORE UPDATE ON "accountCertificateMaps" FOR EACH ROW EXECUTE PROCEDURE "updateSyncFields4"();


--
-- Name: accountRoleInsertTrigger; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER "accountRoleInsertTrigger" BEFORE INSERT ON "accountRoleMaps" FOR EACH ROW EXECUTE PROCEDURE "fillSyncFields4"();


--
-- Name: accountRoleUpdateTrigger; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER "accountRoleUpdateTrigger" BEFORE UPDATE ON "accountRoleMaps" FOR EACH ROW EXECUTE PROCEDURE "updateSyncFields4"();


--
-- Name: accountUpdateTrigger; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER "accountUpdateTrigger" BEFORE UPDATE ON accounts FOR EACH ROW EXECUTE PROCEDURE "updateSyncFields4"();


--
-- Name: accountsInsertTrigger; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER "accountsInsertTrigger" BEFORE INSERT ON accounts FOR EACH ROW EXECUTE PROCEDURE "fillSyncFields4"();


--
-- Name: resourcesInsertTrigger; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER "resourcesInsertTrigger" BEFORE INSERT ON resources FOR EACH ROW EXECUTE PROCEDURE "fillSyncFields4"();


--
-- Name: resourcesUpdateTrigger; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER "resourcesUpdateTrigger" BEFORE UPDATE ON resources FOR EACH ROW EXECUTE PROCEDURE "updateSyncFields4"();


--
-- Name: certificateCreateLogsInsertTrigger; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER "certificateCreateLogsInsertTrigger" BEFORE INSERT ON "certificateCreateLogs" FOR EACH ROW EXECUTE PROCEDURE "fillSyncFields4"();


--
-- Name: certificateCreateLogsUpdateTrigger; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER "certificateCreateLogsUpdateTrigger" BEFORE UPDATE ON "certificateCreateLogs" FOR EACH ROW EXECUTE PROCEDURE "updateSyncFields4"();


--
-- Name: certificateRevokeLogsInsertTrigger; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER "certificateRevokeLogsInsertTrigger" BEFORE INSERT ON "certificateRevokeLogs" FOR EACH ROW EXECUTE PROCEDURE "fillSyncFields4"();


--
-- Name: certificateRevokeLogsUpdateTrigger; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER "certificateRevokeLogsUpdateTrigger" BEFORE UPDATE ON "certificateRevokeLogs" FOR EACH ROW EXECUTE PROCEDURE "updateSyncFields4"();


--
-- Name: certificatesInsertTrigger; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER "certificatesInsertTrigger" BEFORE INSERT ON certificates FOR EACH ROW EXECUTE PROCEDURE "fillSyncFields4"();


--
-- Name: certificatesUpdateTrigger; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER "certificatesUpdateTrigger" BEFORE UPDATE ON certificates FOR EACH ROW EXECUTE PROCEDURE "updateSyncFields4"();


--
-- Name: configTypesInsertTrigger; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER "configTypesInsertTrigger" BEFORE INSERT ON "configTypes" FOR EACH ROW EXECUTE PROCEDURE "fillSyncFields4"();


--
-- Name: configTypesUpdateTrigger; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER "configTypesUpdateTrigger" BEFORE UPDATE ON "configTypes" FOR EACH ROW EXECUTE PROCEDURE "updateSyncFields4"();


--
-- Name: configsInsertTrigger; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER "configsInsertTrigger" BEFORE INSERT ON configs FOR EACH ROW EXECUTE PROCEDURE "fillSyncFields4"();


--
-- Name: configsUpdateTrigger; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER "configsUpdateTrigger" BEFORE UPDATE ON configs FOR EACH ROW EXECUTE PROCEDURE "updateSyncFields4"();


--
-- Name: copyrightsInsertTrigger; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER "copyrightsInsertTrigger" BEFORE INSERT ON copyrights FOR EACH ROW EXECUTE PROCEDURE "fillSyncFields4"();


--
-- Name: copyrightsUpdatetTrigger; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER "copyrightsUpdatetTrigger" BEFORE UPDATE ON copyrights FOR EACH ROW EXECUTE PROCEDURE "updateSyncFields4"();


--
-- Name: resourceLastTransferLogsInsertTrigger; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER "resourceLastTransferLogsInsertTrigger" BEFORE INSERT ON "resourceLastTransferLogs" FOR EACH ROW EXECUTE PROCEDURE "fillSyncFields4"();


--
-- Name: resourceLastTransferLogsUpdateTrigger; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER "resourceLastTransferLogsUpdateTrigger" BEFORE UPDATE ON "resourceLastTransferLogs" FOR EACH ROW EXECUTE PROCEDURE "updateSyncFields4"();


--
-- Name: logsInsertTrigger; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER "logsInsertTrigger" BEFORE INSERT ON logs FOR EACH ROW EXECUTE PROCEDURE "fillSyncFields2"();


--
-- Name: logsUpdateTrigger; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER "logsUpdateTrigger" BEFORE UPDATE ON logs FOR EACH ROW EXECUTE PROCEDURE "updateSyncFields2"();


--
-- Name: operationLogsInsertTrigger; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER "operationLogsInsertTrigger" BEFORE INSERT ON "operationLogs" FOR EACH ROW EXECUTE PROCEDURE "fillSyncFields4"();


--
-- Name: operationLogsUpdateTrigger; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER "operationLogsUpdateTrigger" BEFORE UPDATE ON "operationLogs" FOR EACH ROW EXECUTE PROCEDURE "updateSyncFields4"();


--
-- Name: organizationAccountsInsertTrigger; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER "organizationAccountsInsertTrigger" BEFORE INSERT ON "organizationAccountMaps" FOR EACH ROW EXECUTE PROCEDURE "fillSyncFields4"();


--
-- Name: organizationAccountsUpdateTrigger; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER "organizationAccountsUpdateTrigger" BEFORE UPDATE ON "organizationAccountMaps" FOR EACH ROW EXECUTE PROCEDURE "updateSyncFields4"();


--
-- Name: organizationCatalogsInsertTrigger; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER "organizationCatalogsInsertTrigger" BEFORE INSERT ON "organizationResourceMaps" FOR EACH ROW EXECUTE PROCEDURE "fillSyncFields4"();


--
-- Name: organizationCatalogsUpdateTrigger; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER "organizationCatalogsUpdateTrigger" BEFORE UPDATE ON "organizationResourceMaps" FOR EACH ROW EXECUTE PROCEDURE "updateSyncFields4"();


--
-- Name: organizationTypesInsertTrigger; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER "organizationTypesInsertTrigger" BEFORE INSERT ON "organizationTypes" FOR EACH ROW EXECUTE PROCEDURE "fillSyncFields2"();


--
-- Name: organizationTypesUpdateTrigger; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER "organizationTypesUpdateTrigger" BEFORE UPDATE ON "organizationTypes" FOR EACH ROW EXECUTE PROCEDURE "updateSyncFields2"();


--
-- Name: resourceLastTransfersInsertTrigger; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER "resourceLastTransfersInsertTrigger" BEFORE INSERT ON "resourceLastTransfers" FOR EACH ROW EXECUTE PROCEDURE "fillSyncFields4"();


--
-- Name: resourceLastTransfersUpdateTrigger; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER "resourceLastTransfersUpdateTrigger" BEFORE UPDATE ON "resourceLastTransfers" FOR EACH ROW EXECUTE PROCEDURE "updateSyncFields4"();


--
-- Name: resourceTransfersInsertTrigger; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER "resourceTransfersInsertTrigger" BEFORE INSERT ON "resourceTransfers" FOR EACH ROW EXECUTE PROCEDURE "fillSyncFields4"();


--
-- Name: resourceTransfersUpdateTrigger; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER "resourceTransfersUpdateTrigger" BEFORE UPDATE ON "resourceTransfers" FOR EACH ROW EXECUTE PROCEDURE "updateSyncFields4"();


--
-- Name: rightTransferItemsInsertTrigger; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER "rightTransferItemsInsertTrigger" BEFORE INSERT ON "rightTransferItems" FOR EACH ROW EXECUTE PROCEDURE "fillSyncFields4"();


--
-- Name: rightTransferItemsUpdateTrigger; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER "rightTransferItemsUpdateTrigger" BEFORE UPDATE ON "rightTransferItems" FOR EACH ROW EXECUTE PROCEDURE "updateSyncFields4"();


--
-- Name: rightTransfersInsertTrigger; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER "rightTransfersInsertTrigger" BEFORE INSERT ON "rightTransfers" FOR EACH ROW EXECUTE PROCEDURE "fillSyncFields4"();


--
-- Name: rightTransfersUpdateTrigger; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER "rightTransfersUpdateTrigger" BEFORE UPDATE ON "rightTransfers" FOR EACH ROW EXECUTE PROCEDURE "updateSyncFields4"();


--
-- Name: rightTypesInsertTrigger; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER "rightTypesInsertTrigger" BEFORE INSERT ON "rightTypes" FOR EACH ROW EXECUTE PROCEDURE "fillSyncFields4"();


--
-- Name: rightTypesUpdateTrigger; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER "rightTypesUpdateTrigger" BEFORE UPDATE ON "rightTypes" FOR EACH ROW EXECUTE PROCEDURE "updateSyncFields4"();


--
-- Name: rightsInsertTrigger; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER "rightsInsertTrigger" BEFORE INSERT ON rights FOR EACH ROW EXECUTE PROCEDURE "fillSyncFields4"();


--
-- Name: rightsUpdateTrigger; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER "rightsUpdateTrigger" BEFORE UPDATE ON rights FOR EACH ROW EXECUTE PROCEDURE "updateSyncFields4"();


--
-- Name: rolePermissionMapsInsertTrigger; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER "rolePermissionMapsInsertTrigger" BEFORE INSERT ON "rolePermissionMaps" FOR EACH ROW EXECUTE PROCEDURE "fillSyncFields4"();


--
-- Name: rolePermissionMapsUpdateTrigger; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER "rolePermissionMapsUpdateTrigger" BEFORE UPDATE ON "rolePermissionMaps" FOR EACH ROW EXECUTE PROCEDURE "updateSyncFields4"();


--
-- Name: rolesInsertTrigger; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER "rolesInsertTrigger" BEFORE INSERT ON roles FOR EACH ROW EXECUTE PROCEDURE "fillSyncFields4"();


--
-- Name: rolesUpdateTrigger; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER "rolesUpdateTrigger" BEFORE UPDATE ON roles FOR EACH ROW EXECUTE PROCEDURE "updateSyncFields4"();


--
-- Name: subjectsInsertTrigger; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER "subjectsInsertTrigger" BEFORE INSERT ON subjects FOR EACH ROW EXECUTE PROCEDURE "fillSyncFields4"();


--
-- Name: subjectsUpdateTrigger; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER "subjectsUpdateTrigger" BEFORE UPDATE ON subjects FOR EACH ROW EXECUTE PROCEDURE "updateSyncFields4"();


--
-- Name: userCatalogsInsertTrigger; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER "userCatalogsInsertTrigger" BEFORE INSERT ON "userResourceMaps" FOR EACH ROW EXECUTE PROCEDURE "fillSyncFields4"();


--
-- Name: userCatalogsUpdateTrigger; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER "userCatalogsUpdateTrigger" BEFORE UPDATE ON "userResourceMaps" FOR EACH ROW EXECUTE PROCEDURE "updateSyncFields4"();


--
-- Name: userMessagesInsertTrigger; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER "userMessagesInsertTrigger" BEFORE INSERT ON "userMessages" FOR EACH ROW EXECUTE PROCEDURE "fillSyncFields4"();


--
-- Name: userMessagesUpdateTrigger; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER "userMessagesUpdateTrigger" BEFORE UPDATE ON "userMessages" FOR EACH ROW EXECUTE PROCEDURE "updateSyncFields4"();


--
-- Name: usersInsertTrigger; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER "usersInsertTrigger" BEFORE INSERT ON users FOR EACH ROW EXECUTE PROCEDURE "fillSyncFields4"();


--
-- Name: usersUpdateTrigger; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER "usersUpdateTrigger" BEFORE UPDATE ON users FOR EACH ROW EXECUTE PROCEDURE "updateSyncFields4"();


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

