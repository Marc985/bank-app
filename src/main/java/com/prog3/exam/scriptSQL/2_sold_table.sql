CREATE TABLE IF NOT EXISTS sold(
        id_sold serial primary key,
        balance double precision,
        "date" date,
        account_id bigint  REFERENCES account(account_number)

    );



INSERT INTO sold (balance, "date", account_id)
VALUES (1000.00, '2023-04-01', 1234567829)
ON CONFLICT (id_sold) DO NOTHING;
