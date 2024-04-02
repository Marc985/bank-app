CREATE TABLE IF NOT EXISTS "transaction"(
    reference varchar(100) PRIMARY KEY,
    "type" varchar(20) CHECK (type='debit' OR type='credit'),
    amount double precision,
    "date" date,
    reason varchar(20),
    account_number bigint REFERENCES account(account_number),
    id_category bigint REFERENCES category(id_category)
    );
INSERT INTO "transaction" (reference, "type", amount, "date", reason, account_number, id_category)
VALUES ('TR123456', 'debit', 200.00, '2023-04-01', 'Rent', 123456789, 1),
       ('TR123457', 'credit', 5000.00, '2023-04-01', 'Salary', 123456789, 2)
    ON CONFLICT (reference) DO NOTHING;
