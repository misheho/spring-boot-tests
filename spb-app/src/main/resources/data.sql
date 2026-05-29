-- Création de la table (optionnel si spring.jpa.hibernate.ddl-auto=update est activé)
-- Mais utile pour s'assurer que la structure est correcte avant l'insertion
CREATE TABLE IF NOT EXISTS penguin (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    species VARCHAR(255) NOT NULL UNIQUE,
    habitat VARCHAR(255) NOT NULL
);

INSERT IGNORE INTO penguin (species, habitat) VALUES ('Emperor', 'Antarctica');
INSERT IGNORE INTO penguin (species, habitat) VALUES ('Adélie', 'Antarctic Peninsula');