CREATE TABLE IF NOT EXISTS "category"(
    id_category serial primary key,
    category_name varchar(100),
    category_type varchar(30) CHECK (category_type='debit' OR category_type='credit')
    );

INSERT INTO category (id_category, category_name, category_type)
VALUES
    (1, 'nourriture et boissons', 'debit'),
    (2, 'Achats et boutiques en ligne', 'debit'),
    (3, 'Logement', 'debit'),
    (4, 'Transports', 'debit'),
    (5, 'Véhicule', 'debit'),
    (6, 'Loisirs', 'debit'),
    (7, 'Multimédia, Informatique', 'debit'),
    (8, 'Frais financiers', 'debit'),
    (9, 'Investissements', 'debit'),
    (10, 'Revenu', 'debit'),
    (11, 'Autres', 'debit');
