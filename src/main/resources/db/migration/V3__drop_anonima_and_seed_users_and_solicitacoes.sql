ALTER TABLE solicitacoes
DROP COLUMN anonima;

INSERT INTO users (id, name, email, password, role)
VALUES
    (gen_random_uuid(), 'Eric', 'eric@observa.com', '$2a$10$nwxf0wu.bst9DTdNMoexweGiEYDI1OgPD6MGrxxv92RvMdOX6nRsm', 'ADMIN'),
    (gen_random_uuid(), 'Nicholas', 'nicholas@observa.com', '$2a$10$nwxf0wu.bst9DTdNMoexweGiEYDI1OgPD6MGrxxv92RvMdOX6nRsm', 'ADMIN'),
    (gen_random_uuid(), 'Bruno', 'bruno@observa.com', '$2a$10$nwxf0wu.bst9DTdNMoexweGiEYDI1OgPD6MGrxxv92RvMdOX6nRsm', 'ADMIN'),
    (gen_random_uuid(), 'Joao', 'joao@gmail.com', '$2a$10$nwxf0wu.bst9DTdNMoexweGiEYDI1OgPD6MGrxxv92RvMdOX6nRsm', 'USER'),
    (gen_random_uuid(), 'Maria', 'maria@gmail.com', '$2a$10$nwxf0wu.bst9DTdNMoexweGiEYDI1OgPD6MGrxxv92RvMdOX6nRsm', 'USER'),
    (gen_random_uuid(), 'Pedro', 'pedro@gmail.com', '$2a$10$nwxf0wu.bst9DTdNMoexweGiEYDI1OgPD6MGrxxv92RvMdOX6nRsm', 'USER');

INSERT INTO solicitacoes (
    id,
    protocolo,
    categoria,
    descricao,
    localizacao,
    prioridade,
    status,
    data_criacao,
    data_atualizacao,
    usuario_id
)
VALUES
    (
        gen_random_uuid(),
        'JOAO0001',
        'ILUMINACAO',
        'Poste apagado em frente a escola durante a noite.',
        'Rua das Palmeiras, 120',
        'ALTA',
        'ABERTO',
        '2026-06-10 08:30:00',
        '2026-06-10 08:30:00',
        (SELECT id FROM users WHERE email = 'joao@gmail.com')
    ),
    (
        gen_random_uuid(),
        'JOAO0002',
        'BURACO',
        'Buraco grande ocupando metade da faixa da avenida.',
        'Avenida Central, 850',
        'URGENTE',
        'EM_EXECUCAO',
        '2026-06-09 14:15:00',
        '2026-06-11 09:45:00',
        (SELECT id FROM users WHERE email = 'joao@gmail.com')
    ),
    (
        gen_random_uuid(),
        'MARIA001',
        'LIMPEZA',
        'Acumulo de lixo e entulho na calcada ha varios dias.',
        'Rua do Comercio, 42',
        'MEDIA',
        'TRIAGEM',
        '2026-06-08 10:00:00',
        '2026-06-10 16:20:00',
        (SELECT id FROM users WHERE email = 'maria@gmail.com')
    ),
    (
        gen_random_uuid(),
        'MARIA002',
        'SAUDE',
        'Unidade basica sem atendimento odontologico no periodo da tarde.',
        'UBS Jardim Esperanca',
        'ALTA',
        'RESOLVIDO',
        '2026-06-03 09:10:00',
        '2026-06-07 11:30:00',
        (SELECT id FROM users WHERE email = 'maria@gmail.com')
    ),
    (
        gen_random_uuid(),
        'PEDRO001',
        'SEGURANCA_ESCOLAR',
        'Falta de agente de transito na saida dos alunos.',
        'Escola Municipal Horizonte, portao principal',
        'URGENTE',
        'ENCERRADO',
        '2026-05-28 07:20:00',
        '2026-06-02 17:40:00',
        (SELECT id FROM users WHERE email = 'pedro@gmail.com')
    ),
    (
        gen_random_uuid(),
        'PEDRO002',
        'ILUMINACAO',
        'Luminarias piscando e com risco de apagar completamente.',
        'Praca do Bairro Novo',
        'BAIXA',
        'ABERTO',
        '2026-06-11 19:05:00',
        '2026-06-11 19:05:00',
        (SELECT id FROM users WHERE email = 'pedro@gmail.com')
    );
