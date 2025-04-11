CREATE
EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE fraud_detection
(
    id        UUID PRIMARY KEY      NOT NULL,
    timestamp TIMESTAMP             NOT NULL,
    result    VARCHAR(10)           NOT NULL CHECK (result in ('FRAUD', 'NOT_FRAUD', 'ERROR')),
    showed    BOOLEAN DEFAULT FALSE NOT NULL
);