CREATE TABLE solicitacoes_status_history (
    id UUID PRIMARY KEY,
    solicitacao_id UUID NOT NULL,
    usuario_id UUID NULL,
    actor_name VARCHAR(255) NOT NULL,
    event_type VARCHAR(30) NOT NULL,
    from_status VARCHAR(50) NULL,
    to_status VARCHAR(50) NOT NULL,
    occurred_at TIMESTAMP NOT NULL,

    CONSTRAINT fk_solicitacoes_status_history_solicitacao
        FOREIGN KEY (solicitacao_id) REFERENCES solicitacoes (id),
    CONSTRAINT fk_solicitacoes_status_history_usuario
        FOREIGN KEY (usuario_id) REFERENCES users (id),
    CONSTRAINT chk_solicitacoes_status_history_event_type
        CHECK (event_type IN ('CREATED', 'UPDATED'))
);

CREATE INDEX idx_solicitacoes_status_history_solicitacao_occurred
    ON solicitacoes_status_history (solicitacao_id, occurred_at, id);

INSERT INTO solicitacoes_status_history (
    id,
    solicitacao_id,
    usuario_id,
    actor_name,
    event_type,
    from_status,
    to_status,
    occurred_at
)
SELECT
    gen_random_uuid(),
    s.id,
    s.usuario_id,
    COALESCE(u.name, 'Anonimo'),
    'CREATED',
    NULL,
    'ABERTO',
    s.data_criacao
FROM solicitacoes s
LEFT JOIN users u ON u.id = s.usuario_id;

INSERT INTO solicitacoes_status_history (
    id,
    solicitacao_id,
    usuario_id,
    actor_name,
    event_type,
    from_status,
    to_status,
    occurred_at
)
SELECT
    gen_random_uuid(),
    s.id,
    NULL,
    'Sistema',
    'UPDATED',
    'ABERTO',
    s.status,
    s.data_atualizacao
FROM solicitacoes s
WHERE s.status <> 'ABERTO';
