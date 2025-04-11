CREATE
EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE fraud_detection
(
    id        UUID PRIMARY KEY      NOT NULL,
    timestamp TIMESTAMP             NOT NULL,
    result    VARCHAR(10)           NOT NULL CHECK (result in ('FRAUD', 'NOT_FRAUD', 'ERROR')),
    showed    BOOLEAN DEFAULT FALSE NOT NULL
);

CREATE TABLE category_stats
(
    id         UUID PRIMARY KEY NOT NULL DEFAULT uuid_generate_v4(),
    timestamp  TIMESTAMP        NOT NULL,
    category   VARCHAR(100)     NOT NULL,
    count      BIGINT           NOT NULL,
    max_amount NUMERIC(10, 2)   NOT NULL,
    min_amount NUMERIC(10, 2)   NOT NULL,
    sum        NUMERIC(15, 2)   NOT NULL,
    showed     BOOLEAN                   DEFAULT FALSE NOT NULL,
    UNIQUE (category, timestamp, count, max_amount, min_amount, sum)
);