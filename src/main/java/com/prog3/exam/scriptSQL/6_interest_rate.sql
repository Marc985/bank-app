CREATE TABLE IF NOT EXISTS interest_rate(
            id_interest_rate serial primary key,
            first_7days float,
            after_7days float
);
INSERT INTO interest_rate (first_7days, after_7days)
VALUES (0.01, 0.2)
    ON CONFLICT (id_interest_rate) DO NOTHING
