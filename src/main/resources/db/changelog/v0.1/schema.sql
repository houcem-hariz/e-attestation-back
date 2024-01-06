CREATE SEQUENCE IF NOT EXISTS enterprise_enterprise_id_seq;
CREATE TABLE IF NOT EXISTS "enterprise"
(
    enterprise_id    bigint                 NOT NULL DEFAULT nextval('enterprise_enterprise_id_seq') PRIMARY KEY,
    enterprise_name  character varying(100) NOT NULL,
    enterprise_siret character varying(14),
    certified_iso_27001    BOOLEAN                NOT NULL,
    certified_iso_9001     BOOLEAN                NOT NULL,
    certified_iso_45001    BOOLEAN                NOT NULL
);

ALTER SEQUENCE enterprise_enterprise_id_seq
    OWNED BY enterprise.enterprise_id;