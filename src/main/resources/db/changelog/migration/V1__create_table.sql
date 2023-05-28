CREATE TABLE "currency"
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR NOT NULL UNIQUE
);

CREATE TABLE "user"
(
    id       BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(64)  NOT NULL,
    token    VARCHAR(64)  NOT NULL UNIQUE,
    created  TIMESTAMP    NOT NULL DEFAULT NOW()
);

CREATE TABLE "expense"
(
    id           BIGSERIAL PRIMARY KEY,
    payment      DOUBLE PRECISION NOT NULL,
    currency_id  BIGINT           NOT NULL REFERENCES currency (id),
    payment_date DATE             NOT NULL,
    user_id      BIGINT           NOT NULL
);

CREATE TABLE "exchange"
(
    id            BIGSERIAL PRIMARY KEY,
    currency_id   BIGINT           NOT NULL,
    value         DOUBLE PRECISION NOT NULL,
    exchange_date DATE             NOT NULL,
    UNIQUE (currency_id, value, exchange_date)
);