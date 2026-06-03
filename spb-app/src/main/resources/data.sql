-- Création de la table (optionnel si spring.jpa.hibernate.ddl-auto=update est activé)
-- Mais utile pour s'assurer que la structure est correcte avant l'insertion

CREATE TABLE IF NOT EXISTS penguin (
    id BIGSERIAL PRIMARY KEY,
    species VARCHAR(255) NOT NULL UNIQUE,
    habitat VARCHAR(255) NOT NULL
);

-- Insertion avec gestion des conflits
INSERT INTO penguin (species, habitat) VALUES ('Emperor', 'Antarctica')
ON CONFLICT (species) DO NOTHING;

INSERT INTO penguin (species, habitat) VALUES ('Adélie', 'Antarctic Peninsula')
ON CONFLICT (species) DO NOTHING;

INSERT INTO penguin (species, habitat) VALUES ('Gentoo', 'Sub-Antarctic Islands')
ON CONFLICT (species) DO NOTHING;

INSERT INTO penguin (species, habitat) VALUES ('Chinstrap', 'Antarctic Peninsula')
ON CONFLICT (species) DO NOTHING;

INSERT INTO penguin (species, habitat) VALUES ('Macaroni', 'Sub-Antarctic Islands')
ON CONFLICT (species) DO NOTHING;