CREATE TABLE  IF NOT EXISTS transfert(
                                         reference varchar(100) primary key,
    reason varchar(50),
    amount double precision,
    effective_date date,
    registration_date date,
    status varchar(30) check ( status='canceled' or status='pending' or status='success' ),
    account bigint  REFERENCES account(account_number)
    );

INSERT INTO transfert (reference, reason, amount, effective_date, registration_date, status, account)
VALUES ('TF123456', 'Transfer to Savings', 500.00, '2023-04-01', '2023-04-01', 'pending', 123456789)
    ON CONFLICT (reference) DO NOTHING;
