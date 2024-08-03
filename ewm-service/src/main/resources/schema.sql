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
    id   BIGINT GENERATED ALWAYS AS IDENTITY NOT NULL,
    name VARCHAR(100),
    lat  DOUBLE PRECISION                    NOT NULL,
    lon  DOUBLE PRECISION                    NOT NULL,

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