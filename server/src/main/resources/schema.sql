drop table if exists users cascade;

CREATE TABLE IF NOT EXISTS USERS
(
    ID    BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    NAME  VARCHAR(255)                            NOT NULL,
    EMAIL VARCHAR(512)                            NOT NULL,
    CONSTRAINT PK_USER PRIMARY KEY (ID),
    CONSTRAINT UQ_USER_EMAIL UNIQUE (EMAIL)
);