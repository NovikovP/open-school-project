CREATE TABLE IF NOT EXISTS tasks
(
    id          BIGINT PRIMARY KEY,
    title       VARCHAR(4000),
    description VARCHAR(255),
    user_id     BIGINT                      NOT NULL DEFAULT 0,
    status      VARCHAR(255)                NOT NULL DEFAULT 'NEW'
);