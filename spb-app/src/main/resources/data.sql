-- Création de la table (optionnel si spring.jpa.hibernate.ddl-auto=update est activé)
-- Mais utile pour s'assurer que la structure est correcte avant l'insertion
CREATE TABLE IF NOT EXISTS penguin (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    species VARCHAR(255) NOT NULL,
    habitat VARCHAR(255) NOT NULL
);

-- Insertion des données de test
INSERT INTO penguin (species, habitat) VALUES ('Emperor', 'Antarctica');
INSERT INTO penguin (species, habitat) VALUES ('Adélie', 'Antarctic Peninsula');
INSERT INTO penguin (species, habitat) VALUES ('Gentoo', 'Sub-Antarctic Islands');
INSERT INTO penguin (species, habitat) VALUES ('Chinstrap', 'Antarctic Peninsula');
INSERT INTO penguin (species, habitat) VALUES ('Macaroni', 'Sub-Antarctic Islands');