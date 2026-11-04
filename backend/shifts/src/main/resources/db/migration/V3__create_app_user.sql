CREATE TABLE app_user (
    id                  BIGSERIAL PRIMARY KEY,
    keycloak_id         UUID NOT NULL UNIQUE,
    name                VARCHAR(150) NOT NULL,
    email               VARCHAR(150) NOT NULL,
    phone               VARCHAR(20),
    registration_number VARCHAR(30),
    access_profile_id   BIGINT NOT NULL REFERENCES access_profile(id),
    profession_id       BIGINT NOT NULL REFERENCES profession(id),
    shift_template_id   BIGINT REFERENCES shift_template(id),
    status              VARCHAR(20) NOT NULL DEFAULT 'PENDING_ACTIVATION',
    created_at          TIMESTAMPTZ NOT NULL DEFAULT now(),
    created_by          BIGINT REFERENCES app_user(id),
    updated_at          TIMESTAMPTZ,
    updated_by          BIGINT REFERENCES app_user(id)        
);

CREATE UNIQUE INDEX uq_app_user_email_ci ON app_user (lower(email));

-- Administradores e coordenadores também são usuários e precisam de profissão
INSERT INTO profession (name, default_shift_template_id)
VALUES ('Administrativo', (SELECT id FROM shift_template WHERE name = 'Recepção 8h'));