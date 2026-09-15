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
    tipo ENUM('admin', 'user') DEFAULT 'user',
    senha_hash VARCHAR(255) NOT NULL,
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
    unidade_medida VARCHAR(20) GENERATED ALWAYS AS (
        CASE tipo_meta
            WHEN 'repeticoes' THEN 'reps'
            WHEN 'distancia_km' THEN 'km'
            WHEN 'tempo_minutos' THEN 'min'
            WHEN 'dias_seguidos' THEN 'dias'
        END
    ) STORED,
    data_inicio DATE NOT NULL,
    data_fim DATE NOT NULL,
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_desafios_criador
        FOREIGN KEY (criador_id) REFERENCES usuarios(id)
        ON DELETE RESTRICT,
    CONSTRAINT chk_desafios_periodo
        CHECK (data_fim > data_inicio),

    INDEX idx_desafios_status_data_fim (status, data_fim)
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
    data_registro DATE NOT NULL,
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    CONSTRAINT fk_progresso_participacao
        FOREIGN KEY (participacao_id) REFERENCES participacoes_desafio(id)
        ON DELETE CASCADE,
    CONSTRAINT chk_progresso_valor_positivo
        CHECK (valor_registrado > 0)
);

-- ===================================================
-- 1. POVOAMENTO DA TABELA: USUÁRIOS
-- ===================================================
INSERT INTO usuarios (id, nome, email, senha_hash, status, tipo, ultimo_login) VALUES
(1, 'Admin', 'admin@gmail.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'ativo', 'admin', '2026-09-13 10:00:00'), -- senha: password
(2, 'Teste', 'teste@gmail.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'ativo', 'user', '2026-09-13 10:00:00'), -- senha: password
(3, 'Leandro', 'leandro@gmail.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'ativo', 'user', '2026-09-13 11:30:00'), -- senha: password
(4, 'Ruan', 'ruan@gmail.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'ativo', 'user', '2026-09-13 14:15:00'); -- senha: password

-- ===================================================
-- 2. POVOAMENTO DA TABELA: DESAFIOS
-- ===================================================
INSERT INTO desafios (id, criador_id, nome, descricao, categoria, tipo_meta, meta_total, data_inicio, data_fim, status) VALUES
(1, 1, 'Desafio 100km em Setembro', 'Correr ou caminhar um total acumulado de 100km durante o mês de setembro.', 'corrida', 'distancia_km', 100.00, '2026-09-01', '2026-09-30', 'ativo'),
(2, 2, 'Clube das 1.000 Flexões', 'Complete mil flexões ao longo de 30 dias para fortalecer peitoral e tríceps.', 'flexoes', 'repeticoes', 1000.00, '2026-09-01', '2026-09-30', 'ativo'),
(3, 1, 'Pedal de Primavera', 'Pedalar pelo menos 250km até o fim do mês.', 'ciclismo', 'distancia_km', 250.00, '2026-09-05', '2026-09-30', 'ativo');

-- ===================================================
-- 3. POVOAMENTO DA TABELA: PARTICIPACOES_DESAFIO
-- ===================================================
INSERT INTO participacoes_desafio (id, usuario_id, desafio_id, status, concluido_em) VALUES
(1, 1, 1, 'em_andamento', NULL), -- Admin no Desafio 100km
(2, 2, 1, 'em_andamento', NULL), -- Teste no Desafio 100km
(3, 3, 1, 'em_andamento', NULL), -- Leandro no Desafio 100km
(4, 2, 2, 'em_andamento', NULL); -- Ruan no Desafio 1000 Flexões

-- ===================================================
-- 4. POVOAMENTO DA TABELA: PROGRESSO (CHECK-INS)
-- ===================================================
INSERT INTO progresso (participacao_id, valor_registrado, observacao, data_registro) VALUES
-- Check-ins do Admin (participacao_id 1) no Desafio 100km (Total: 17.8 km)
(1, 6.50, 'Treino matinal no parque', '2026-09-02'),
(1, 5.00, 'Corrida leve na esteira', '2026-09-04'),
(1, 6.30, 'Tiro curto e ritmo forte', '2026-09-07'),

-- Check-ins do Teste (participacao_id 2) no Desafio 100km (Total: 18.2 km)
(2, 8.20, 'Longão de sábado na ciclovia', '2026-09-05'),
(2, 10.00, 'Treino com a assessoria', '2026-09-07'),

-- Check-ins do Leandro (participacao_id 3) no Desafio 100km (Total: 4.5 km)
(3, 4.50, 'Primeiro treino pós-recuperação', '2026-09-03'),

-- Check-ins do Ruan (participacao_id 4) no Desafio 1000 Flexões (Total: 150 reps)
(4, 50.00, '5 séries de 10 reps', '2026-09-02'),
(4, 50.00, 'Foco em amplitude', '2026-09-04'),
(4, 50.00, 'Última série até a falha', '2026-09-06');