CREATE TABLE access_profile (
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(50) NOT NULL UNIQUE,
    status      VARCHAR(10) NOT NULL DEFAULT 'ACTIVE',
    created_at  TIMESTAMPTZ NOT NULL DEFAULT now(),
    created_by  BIGINT,
    updated_at  TIMESTAMPTZ,
    updated_by  BIGINT
);

CREATE TABLE shift_template (
    id               BIGSERIAL PRIMARY KEY,
    name             VARCHAR(80) NOT NULL,
    start_time       TIME NOT NULL,
    end_time         TIME NOT NULL,
    duration_minutes INT NOT NULL CHECK (duration_minutes BETWEEN 1 AND 1440),
    crosses_midnight BOOLEAN NOT NULL DEFAULT FALSE,
    status           VARCHAR(10) NOT NULL DEFAULT 'ACTIVE',
    created_at       TIMESTAMPTZ NOT NULL DEFAULT now(),
    created_by       BIGINT,
    updated_at       TIMESTAMPTZ,
    updated_by       BIGINT
);          

CREATE TABLE profession (
    id                        BIGSERIAL PRIMARY KEY,
    name                      VARCHAR(80) NOT NULL UNIQUE,
    default_shift_template_id BIGINT REFERENCES shift_template(id),
    status                    VARCHAR(10) NOT NULL DEFAULT 'ACTIVE',
    created_at                TIMESTAMPTZ NOT NULL DEFAULT now(),
    created_by                BIGINT,
    updated_at                TIMESTAMPTZ,
    updated_by                BIGINT
);

-- Dados iniciais (seed)
INSERT INTO access_profile (name) VALUES ('ADMIN'), ('COORDINATOR'), ('PROFESSIONAL');

INSERT INTO shift_template (name, start_time, end_time, duration_minutes, crosses_midnight) VALUES
  ('Diurno 12h',   '07:00', '19:00', 720, FALSE),
  ('Noturno 12h',  '19:00', '07:00', 720, TRUE),
  ('Recepção 8h',  '08:00', '16:00', 480, FALSE);

  INSERT INTO profession (name, default_shift_template_id) VALUES
  ('Médico',        (SELECT id FROM shift_template WHERE name = 'Diurno 12h')),
  ('Enfermeiro',    (SELECT id FROM shift_template WHERE name = 'Diurno 12h')),
  ('Recepcionista', (SELECT id FROM shift_template WHERE name = 'Recepção 8h'));