CREATE TABLE "user"
(
    id       BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    token    VARCHAR      NOT NULL,
    created  TIMESTAMP    NOT NULL DEFAULT NOW()
);