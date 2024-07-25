DROP TABLE IF EXISTS stat_hits;

CREATE TABLE IF NOT EXISTS stat_hits
(
    id        BIGINT GENERATED ALWAYS AS IDENTITY NOT NULL,
    app       VARCHAR(255)                        NOT NULL,
    uri       VARCHAR(255)                        NOT NULL,
    ip        VARCHAR(255)                        NOT NULL,
    timestamp TIMESTAMP WITHOUT TIME ZONE         NOT NULL,

    PRIMARY KEY (id)
);