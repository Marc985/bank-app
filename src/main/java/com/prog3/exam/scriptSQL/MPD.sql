CREATE  DATABASE digital_bank;

-- \c digital_bank

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- db for the user authentication

CREATE TABLE IF NOT EXISTS "user" (
    user_id VARCHAR(255) PRIMARY KEY DEFAULT uuid_generate_v4(),
    username VARCHAR(255),
    email VARCHAR(255) UNIQUE,
    password VARCHAR(255) NOT NULL,
    email_verified TIMESTAMP,
    image VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS "user_log" (
    id                VARCHAR(255) PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id           VARCHAR(255) REFERENCES "user"(user_id),
    "type"              VARCHAR(255),
    provider          VARCHAR(255),
    provider_account_id VARCHAR(255),
    refresh_token     TEXT,
    access_token      TEXT,
    expires_at        INTEGER,
    token_type        VARCHAR(255),
    "scope"             VARCHAR(255),
    id_token          TEXT,
    session_state     VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS "session" (
    id           VARCHAR(255) PRIMARY KEY DEFAULT uuid_generate_v4(),
    session_token VARCHAR(255) UNIQUE,
    user_id       VARCHAR(255) REFERENCES "user"(user_id),
    expires      TIMESTAMP
);

CREATE TABLE IF NOT EXISTS "verification_token" (
    identifier VARCHAR(255),
    token      VARCHAR(255) UNIQUE,
    expires    TIMESTAMP,
    UNIQUE (identifier, token)
);

-- db for the bank account

CREATE  TABLE IF NOT EXISTS  "bank_account"(
    account_number bigint primary key,
    client_name varchar(80),
    client_last_name varchar(80),
    birthdate date,
    monthly_net_income double precision,
    is_eligible boolean default false,
    user_id  varchar(50) REFERENCES "user"(user_id)
);
wq
CREATE TABLE IF NOT EXISTS "sold"(
    id_sold serial primary key,
    balance double precision,
    "date" date,
    account_id bigint  REFERENCES bank_account(account_number)
);

CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE IF NOT EXISTS "category"(
    id_category serial primary key,
    category_name varchar(100),
    category_type varchar(30) CHECK (category_type='debit' OR category_type='credit')
);

CREATE TABLE IF NOT EXISTS "transaction"(
    reference varchar(100) PRIMARY KEY,
    "type" varchar(20) CHECK (type='debit' OR type='credit'),
    amount double precision,
    "date" date,
    reason varchar(20),
    account_number bigint REFERENCES bank_account(account_number),
    id_category bigint REFERENCES category(id_category)
);

CREATE TABLE  IF NOT EXISTS "transfert"(
    reference varchar(100) primary key,
    reason varchar(50),
    amount double precision,
    effective_date date,
    registration_date date,
    status varchar(30) check ( status='canceled' or status='pending' or status='success' ),
    account bigint  REFERENCES bank_account(account_number)
);

CREATE TABLE IF NOT EXISTS "interest_rate"(
    id_interest_rate serial primary key,
    first_7days float,
    after_7days float
);

-- functions

CREATE OR REPLACE FUNCTION account_statement(account_number bigint, begin_date DATE, end_date DATE)
RETURNS TABLE (
    reference varchar(100),
    type varchar(20),
    amount double precision,
    transaction_date DATE,
    reason varchar(20),
    balance double precision
) AS $$
BEGIN
RETURN QUERY
SELECT
    t.reference,
    t."type",
    t.amount,
    t."date",
    t.reason,
    SUM(CASE WHEN t."type" = 'credit' THEN t.amount ELSE -t.amount END) OVER
    (
    PARTITION BY t.account_number
    ORDER BY t."date"
    ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW
    ) AS balance
FROM
    "transaction"
WHERE
    t."date" BETWEEN begin_date AND end_date
  AND t.account_number = account_statement.account_number
ORDER BY
    t."date" DESC;
END; $$
LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION category_amount_sum(
    IN account_id bigint,
    IN start_date date,
    IN end_date date
)
RETURNS TABLE(category_type VARCHAR(30), category_amount_sum double precision) AS $$
BEGIN
RETURN QUERY
SELECT
    category.category_name,
    COALESCE(SUM(CASE WHEN transaction.amount IS NULL THEN 0 ELSE transaction.amount END), 0) AS category_amount_sum
FROM category
         LEFT JOIN transaction ON transaction.id_category = category.id_category
    AND transaction.account_number = account_id
    AND transaction.date BETWEEN start_date AND end_date
GROUP BY category.category_name;
END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION entry_expense_sum(
in account_number bigint,
in start_date date,
in end_date date)
RETURNS TABLE (entry double precision,expense double precision) as $$
BEGIN
RETURN QUERY
SELECT SUM(CASE WHEN transaction.type='credit' THEN transaction.amount ELSE 0 END) as entry,
       SUM (CASE WHEN transaction.type='debit' THEN transaction.amount ELSE 0 END) as expense FROM
    transaction where transaction.account_number=entry_expense_sum.account_number and transaction.date
    between start_date and end_date;
END;
$$ LANGUAGE plpgsql;

__________ DUMMY DATA_____________


INSERT INTO account (account_number, client_name, client_last_name, birthdate, monthly_net_income, is_eligible)
VALUES
(123456789, 'John', 'Doe', '1990-01-01', 5000.0, true),
(987654321, 'Jane', 'Smith', '1995-05-15', 6000.0, false),
(555555555, 'Alice', 'Johnson', '1988-11-30', 7000.0, true),
(888888888, 'Bob', 'Williams', '1992-09-10', 5500.0, true);


INSERT INTO sold (balance, "date", account_id)
VALUES
(1500.0, '2024-03-15', 123456789),
(2500.0, '2024-03-16', 987654321),
(1800.0, '2024-03-17', 555555555),
(3000.0, '2024-03-18', 888888888);


INSERT INTO "transaction" (reference, "type", amount, "date", reason, account_number)
VALUES
('REF123', 'debit', 500.0, '2024-03-15', 'Purchase', 123456789),
('REF456', 'credit', 1000.0, '2024-03-16', 'Salary', 987654321),
('REF789', 'debit', 200.0, '2024-03-17', 'Utility Bill', 555555555),
('REFABC', 'credit', 1500.0, '2024-03-18', 'Refund', 888888888);



INSERT INTO transfert (reason, amount, effective_date, registration_date, status, sender_account, recipient_account)
VALUES
('Payment', 200.0, '2024-03-20', '2024-03-20', 'success', 123456789, 987654321),
('Transfer', 500.0, '2024-03-19', '2024-03-19', 'pending', 987654321, 123456789),
('Refund', 100.0, '2024-03-18', '2024-03-18', 'success', 555555555, 888888888),
('Payment', 300.0, '2024-03-17', '2024-03-17', 'canceled', 888888888, 555555555);



INSERT INTO interest_rate (first_7days, after_7days)
VALUES
(0.05, 0.07);


