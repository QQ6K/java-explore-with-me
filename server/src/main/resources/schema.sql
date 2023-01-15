--https://dbdiagram.io/d/62ee6852c2d9cf52fa5a030f

drop table if exists users cascade;
drop table if exists categories cascade;
drop table if exists events cascade;

CREATE TABLE IF NOT EXISTS users
(
    id    BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name  VARCHAR(30)                             NOT NULL,
    email VARCHAR(50)                             NOT NULL,
    lock  BOOLEAN DEFAULT FALSE,
    CONSTRAINT pk_user PRIMARY KEY (id),
    CONSTRAINT UQ_USER_EMAIL UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS locations
(
    id  BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    lat NUMERIC(10, 5)                          NOT NULL,
    lon NUMERIC(10, 5)                          NOT NULL,
    CONSTRAINT pk_location PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS categories
(
    id   BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name VARCHAR(128)                            NOT NULL,
    CONSTRAINT pk_category PRIMARY KEY (id),
    CONSTRAINT UQ_CATEGORY_NAME UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS events
(
    id                 BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    annotation         VARCHAR(2000)                           NOT NULL,
    category_id        BIGINT                                  NOT NULL,
    created_on         TIMESTAMP                                        DEFAULT NOW(),
    description        VARCHAR(7000)                           NOT NULL,
    event_date         TIMESTAMP                               NOT NULL,
    initiator_id       BIGINT                                  NOT NULL,
    location_id        BIGINT                                  NOT NULL,
    paid               BOOLEAN                                          DEFAULT FALSE,
    participant_limit  BIGINT                                     NOT NULL,
    published_on       TIMESTAMP,
    request_moderation BOOLEAN                                          DEFAULT FALSE,
    state              VARCHAR(50)                             ,
    title              VARCHAR(120)                            NOT NULL,
    views              BIGINT                                 NOT NULL,
    CONSTRAINT pk_event PRIMARY KEY (id),
    CONSTRAINT fk_initiator FOREIGN KEY (initiator_id)
        REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT fk_location_id FOREIGN KEY (location_id)
        REFERENCES locations (id) ON DELETE CASCADE,
    CONSTRAINT fk_category FOREIGN KEY (category_id)
        REFERENCES categories (id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS compilations
(
    id     BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    title  VARCHAR(500)                            NOT NULL,
    pinned BOOLEAN DEFAULT FALSE,
    CONSTRAINT pk_compilation PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS events_compilations
(
    id             BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    event_id       BIGINT                                  NOT NULL,
    compilation_id BIGINT                                  NOT NULL,
    CONSTRAINT pk_event_compilation PRIMARY KEY (id),
    CONSTRAINT fk_compilation_event FOREIGN KEY (event_id)
        REFERENCES events (id) ON DELETE CASCADE,
    CONSTRAINT fk_event_compilation FOREIGN KEY (compilation_id)
        REFERENCES compilations (id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS requests
(
    id           BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    event_id     BIGINT                                  NOT NULL,
    requester_id BIGINT                                  NOT NULL,
    state        VARCHAR(50)                             NOT NULL,
    created      TIMESTAMP DEFAULT NOW(),
    CONSTRAINT pk_participation PRIMARY KEY (id),
    CONSTRAINT fk_event_request FOREIGN KEY (event_id)
        REFERENCES events (id) ON DELETE CASCADE,
    CONSTRAINT fk_user_request FOREIGN KEY (requester_id)
        REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS subscriptions
(
    user_id         BIGINT NOT NULL,
    subscription_id BIGINT NOT NULL,
    blacklist       BOOLEAN DEFAULT FALSE,
    CONSTRAINT friendship_pk PRIMARY key (user_id, subscription_id),
    CONSTRAINT fk_users_sbs FOREIGN KEY (user_id)
        REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT fk_follower FOREIGN KEY (subscription_id)
        REFERENCES users (id) ON DELETE CASCADE
)