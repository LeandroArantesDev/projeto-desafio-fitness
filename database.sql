CREATE DATABASE IF NOT EXISTS projeto_desafios_fitness
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE projeto_desafios_fitness;

-- =========================================
-- TABELA DE USUARIOS
-- =========================================

CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    senha_hash VARCHAR(255) NOT NULL,
    avatar_url VARCHAR(255) DEFAULT NULL,
    status ENUM('ativo', 'inativo', 'suspenso') DEFAULT 'ativo',
    ultimo_login DATETIME DEFAULT NULL,
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);


-- =========================================
-- TABELA DE DESAFIOS
-- =========================================

CREATE TABLE desafios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    criador_id INT NOT NULL,
    nome VARCHAR(150) NOT NULL,
    descricao TEXT,
    categoria ENUM('corrida', 'musculacao', 'ciclismo', 'flexoes', 'habito', 'outro') DEFAULT 'outro',
    tipo_meta ENUM('repeticoes', 'distancia_km', 'tempo_minutos', 'dias_seguidos') NOT NULL,
    meta_total DECIMAL(10, 2) NOT NULL, -- Ex: 100.00 (km), 500 (repetições)
    unidade_medida VARCHAR(20) NOT NULL, -- Ex: 'km', 'reps', 'min'
    data_inicio DATE NOT NULL,
    data_fim DATE NOT NULL,
    status ENUM('rascunho', 'ativo', 'encerrado', 'cancelado') DEFAULT 'ativo',
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    CONSTRAINT fk_desafios_criador
        FOREIGN KEY (criador_id) REFERENCES usuarios(id)
        ON DELETE CASCADE
);

-- =========================================
-- TABELA DE PARTICIPAÇÕES
-- =========================================

CREATE TABLE participacoes_desafio (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario_id INT NOT NULL,
    desafio_id INT NOT NULL,
    status ENUM('em_andamento', 'concluido', 'desistiu') DEFAULT 'em_andamento',
    concluido_em DATETIME DEFAULT NULL,
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    -- Evita que o mesmo usuário se inscreva duas vezes no mesmo desafio
    UNIQUE KEY uq_usuario_desafio (usuario_id, desafio_id),
    
    CONSTRAINT fk_participacoes_usuario
        FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
        ON DELETE CASCADE,
    CONSTRAINT fk_participacoes_desafio
        FOREIGN KEY (desafio_id) REFERENCES desafios(id)
        ON DELETE CASCADE
);

-- =========================================
-- TABELA HISTÓRICO DE PROGRESSO
-- =========================================

CREATE TABLE progresso (
    id INT AUTO_INCREMENT PRIMARY KEY,
    participacao_id INT NOT NULL,
    valor_registrado DECIMAL(10, 2) NOT NULL, -- Ex: correu 5.5 (km), fez 50 (flexões)
    observacao VARCHAR(255) DEFAULT NULL,
    comprovante_url VARCHAR(255) DEFAULT NULL, -- Link de foto ou print
    data_registro DATE NOT NULL,
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    CONSTRAINT fk_progresso_participacao
        FOREIGN KEY (participacao_id) REFERENCES participacoes_desafio(id)
        ON DELETE CASCADE
);

-- ===================================================
-- 1. POVOAMENTO DA TABELA: USUÁRIOS
-- ===================================================
INSERT INTO usuarios (id, nome, email, senha_hash, avatar_url, status, ultimo_login) VALUES
(1, 'Carlos Silva', 'carlos.silva@email.com', '$2y$10$e8wD4t2E6R9hG3h6s...', 'https://images.unsplash.com/photo-1535713875002-d1d0cf377fde', 'ativo', '2026-09-07 08:30:00'),
(2, 'Mariana Souza', 'mariana.souza@email.com', '$2y$10$w09Zk2u8L5n1v4j8a...', 'https://images.unsplash.com/photo-1494790108377-be9c29b29330', 'ativo', '2026-09-08 07:15:00'),
(3, 'Rafael Mendes', 'rafael.mendes@email.com', '$2y$10$v21M9x4L8n2b3k7f9...', 'https://images.unsplash.com/photo-1570295999919-56ceb5ecca61', 'ativo', '2026-09-06 19:45:00'),
(4, 'Beatriz Lima', 'beatriz.lima@email.com', '$2y$10$q98P2m3N4b5v6c7x8...', 'https://images.unsplash.com/photo-1580489944761-15a19d654956', 'ativo', '2026-09-08 09:00:00');

-- ===================================================
-- 2. POVOAMENTO DA TABELA: DESAFIOS
-- ===================================================
INSERT INTO desafios (id, criador_id, nome, descricao, categoria, tipo_meta, meta_total, unidade_medida, data_inicio, data_fim, status) VALUES
(1, 1, 'Desafio 100km em Setembro', 'Correr ou caminhar um total acumulado de 100km durante o mês de setembro.', 'corrida', 'distancia_km', 100.00, 'km', '2026-09-01', '2026-09-30', 'ativo'),
(2, 2, 'Clube das 1.000 Flexões', 'Complete mil flexões ao longo de 30 dias para fortalecer peitoral e tríceps.', 'flexoes', 'repeticoes', 1000.00, 'reps', '2026-09-01', '2026-09-30', 'ativo'),
(3, 1, 'Pedal de Primavera', 'Pedalar pelo menos 250km até o fim do mês.', 'ciclismo', 'distancia_km', 250.00, 'km', '2026-09-05', '2026-09-30', 'ativo');

-- ===================================================
-- 3. POVOAMENTO DA TABELA: PARTICIPACOES_DESAFIO
-- ===================================================
INSERT INTO participacoes_desafio (id, usuario_id, desafio_id, status, concluido_em) VALUES
(1, 1, 1, 'em_andamento', NULL), -- Carlos no Desafio 100km
(2, 2, 1, 'em_andamento', NULL), -- Mariana no Desafio 100km
(3, 3, 1, 'em_andamento', NULL), -- Rafael no Desafio 100km
(4, 2, 2, 'em_andamento', NULL), -- Mariana no Desafio 1000 Flexões
(5, 4, 2, 'em_andamento', NULL), -- Beatriz no Desafio 1000 Flexões
(6, 1, 3, 'em_andamento', NULL); -- Carlos no Desafio Pedal

-- ===================================================
-- 4. POVOAMENTO DA TABELA: PROGRESSO (CHECK-INS)
-- ===================================================
INSERT INTO progresso (participacao_id, valor_registrado, observacao, comprovante_url, data_registro) VALUES
-- Check-ins de Carlos (participacao_id 1) no Desafio 100km (Total: 17.8 km)
(1, 6.50, 'Treino matinal no parque', 'https://storage.exemplo.com/prints/carlos_corrida_01.png', '2026-09-02'),
(1, 5.00, 'Corrida leve na esteira', NULL, '2026-09-04'),
(1, 6.30, 'Tiro curto e ritmo forte', 'https://storage.exemplo.com/prints/carlos_corrida_02.png', '2026-09-07'),

-- Check-ins de Mariana (participacao_id 2) no Desafio 100km (Total: 18.2 km)
(2, 8.20, 'Longão de sábado na ciclovia', 'https://storage.exemplo.com/prints/mariana_corrida_01.png', '2026-09-05'),
(2, 10.00, 'Treino com a assessoria', 'https://storage.exemplo.com/prints/mariana_corrida_02.png', '2026-09-07'),

-- Check-ins de Rafael (participacao_id 3) no Desafio 100km (Total: 4.5 km)
(3, 4.50, 'Primeiro treino pós-recuperação', NULL, '2026-09-03'),

-- Check-ins de Mariana (participacao_id 4) no Desafio 1000 Flexões (Total: 150 reps)
(4, 50.00, '5 séries de 10 reps', NULL, '2026-09-02'),
(4, 50.00, 'Foco em amplitude', NULL, '2026-09-04'),
(4, 50.00, 'Última série até a falha', NULL, '2026-09-06'),

-- Check-ins de Beatriz (participacao_id 5) no Desafio 1000 Flexões (Total: 100 reps)
(5, 60.00, '3 séries de 20 reps', NULL, '2026-09-03'),
(5, 40.00, 'Treino rápido no intervalo', NULL, '2026-09-05'),

-- Check-ins de Carlos (participacao_id 6) no Desafio Pedal (Total: 35.0 km)
(6, 35.00, 'Pedal de domingo estrada asfaltada', 'https://storage.exemplo.com/prints/carlos_pedal_01.png', '2026-09-06');

-- =========================================
-- CONSULTA DE EXEMPLO
-- =========================================

SELECT 
    u.nome AS atleta,
    d.nome AS desafio,
    d.meta_total,
    d.unidade_medida,
    COALESCE(SUM(p.valor_registrado), 0) AS total_acumulado,
    ROUND((COALESCE(SUM(p.valor_registrado), 0) / d.meta_total) * 100, 1) AS percentual_concluido
FROM participacoes_desafio pd
JOIN usuarios u ON pd.usuario_id = u.id
JOIN desafios d ON pd.desafio_id = d.id
LEFT JOIN progresso p ON p.participacao_id = pd.id
WHERE d.id = 1
GROUP BY pd.id, u.nome, d.nome, d.meta_total, d.unidade_medida
ORDER BY total_acumulado DESC;
