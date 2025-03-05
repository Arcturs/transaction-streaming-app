CREATE
EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE transactions
(
    system_id               UUID PRIMARY KEY NOT NULL DEFAULT uuid_generate_v4(),
    id                      UUID             NOT NULL UNIQUE,
    timestamp               TIMESTAMP        NOT NULL,
    type                    VARCHAR(20)      NOT NULL,
    amount                  DECIMAL(15, 2)   NOT NULL,
    currency                VARCHAR(10),
    origin_id               UUID             NOT NULL,
    destination_id          UUID             NOT NULL,
    origin_old_balance      DECIMAL(15, 2)   NOT NULL,
    origin_new_balance      DECIMAL(15, 2)   NOT NULL,
    destination_old_balance DECIMAL(15, 2)   NOT NULL,
    destination_new_balance DECIMAL(15, 2)   NOT NULL,
    card_type               VARCHAR(10)      NOT NULL,
    client_category         VARCHAR(10),
    sex                     VARCHAR(10),
    payment_category        VARCHAR(20),
    client_type             VARCHAR(10)
);