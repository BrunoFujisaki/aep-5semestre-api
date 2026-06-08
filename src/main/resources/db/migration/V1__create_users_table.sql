CREATE TABLE users (
    id UUID NOT NULL,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,

    CONSTRAINT pk_users PRIMARY KEY (id)
);

CREATE UNIQUE INDEX idx_users_email
    ON users (email);

CREATE INDEX idx_users_id
    ON users (id);