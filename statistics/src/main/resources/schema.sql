drop table if exists endpointhit cascade;

CREATE TABLE IF NOT EXISTS endpointhit (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    app VARCHAR(30) NOT NULL,
    uri VARCHAR(2048) NOT NULL,
    ip VARCHAR(45) NOT NULL,
    timestamp BIGINT NOT NULL,
    CONSTRAINT pk_hit PRIMARY KEY (id)
);
