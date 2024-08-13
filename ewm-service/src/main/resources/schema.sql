DROP TABLE IF EXISTS ewm_compilation_event, ewm_compilations, ewm_requests, ewm_events,
    ewm_categories, ewm_locations, ewm_users;

CREATE TABLE IF NOT EXISTS ewm_users
(
    id    BIGINT GENERATED ALWAYS AS IDENTITY NOT NULL,
    name  VARCHAR(250)                        NOT NULL,
    email VARCHAR(254) UNIQUE                 NOT NULL,

    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS ewm_categories
(
    id   BIGINT GENERATED ALWAYS AS IDENTITY NOT NULL,
    name VARCHAR(50) UNIQUE                  NOT NULL,

    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS ewm_events
(
    id                 BIGINT GENERATED ALWAYS AS IDENTITY NOT NULL,
    title              VARCHAR(120)                        NOT NULL,
    description        VARCHAR(7000)                       NOT NULL,
    annotation         VARCHAR(2000)                       NOT NULL,
    confirmed_requests BIGINT                              NOT NULL,
    category_id        BIGINT                              NOT NULL,
    initiator_id       BIGINT                              NOT NULL,
    event_date         TIMESTAMP                           NOT NULL,
    location_id        BIGINT                              NOT NULL,
    request_moderation BOOLEAN                             NOT NULL,
    paid               BOOLEAN                             NOT NULL,
    participant_limit  INTEGER                             NOT NULL,
    state              VARCHAR                             NOT NULL,
    published_on       BOOLEAN,
    created_on         TIMESTAMP                           NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (category_id) REFERENCES ewm_categories (id),
    FOREIGN KEY (initiator_id) REFERENCES ewm_users (id)
);

CREATE TABLE IF NOT EXISTS ewm_locations
(
    id              BIGINT GENERATED ALWAYS AS IDENTITY NOT NULL,
    name            VARCHAR(120),
    expiration_date TIMESTAMP,
    permanent       BOOLEAN                             NOT NULL,
    lat             DOUBLE PRECISION                    NOT NULL,
    lon             DOUBLE PRECISION                    NOT NULL,
    radius          DOUBLE PRECISION,

    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS ewm_requests
(
    id       BIGINT GENERATED ALWAYS AS IDENTITY NOT NULL,
    event_id BIGINT                              NOT NULL,
    user_id  BIGINT                              NOT NULL,
    status   VARCHAR                             NOT NULL,
    created  TIMESTAMP                           NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (event_id) REFERENCES ewm_events (id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES ewm_users (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS ewm_compilations
(
    id     BIGINT GENERATED ALWAYS AS IDENTITY NOT NULL,
    title  VARCHAR                             NOT NULL,
    pinned BOOLEAN                             NOT NULL,

    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS ewm_compilation_event
(
    compilation_id BIGINT NOT NULL,
    event_id       BIGINT NOT NULL,

    PRIMARY KEY (compilation_id, event_id),
    FOREIGN KEY (compilation_id) REFERENCES ewm_compilations (id),
    FOREIGN KEY (event_id) REFERENCES ewm_events (id)
);

CREATE OR REPLACE FUNCTION distance(lat1 float, lon1 float, lat2 float, lon2 float)
    RETURNS float
AS
'
    declare
        dist float = 0;
        rad_lat1 float;
        rad_lat2 float;
        theta float;
        rad_theta float;
    BEGIN
        IF lat1 = lat2 AND lon1 = lon2
        THEN
            RETURN dist;
        ELSE
            -- переводим градусы широты в радианы
            rad_lat1 = pi() * lat1 / 180;
            -- переводим градусы долготы в радианы
            rad_lat2 = pi() * lat2 / 180;
            -- находим разность долгот
            theta = lon1 - lon2;
            -- переводим градусы в радианы
            rad_theta = pi() * theta / 180;
            -- находим длину ортодромии
            dist = sin(rad_lat1) * sin(rad_lat2) + cos(rad_lat1) * cos(rad_lat2) * cos(rad_theta);

            IF dist > 1
            THEN dist = 1;
            END IF;

            dist = acos(dist);
            -- переводим радианы в градусы
            dist = dist * 180 / pi();
            -- переводим градусы в километры
            dist = dist * 60 * 1.8524;

            RETURN dist;
        END IF;
    END;
'
LANGUAGE PLPGSQL;