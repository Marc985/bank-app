
CREATE TABLE IF NOT EXISTS account (
    account_number bigint PRIMARY KEY,
    account_name VARCHAR,
    is_eligible boolean DEFAULT false
);


CREATE  TABLE client(
    id_client varchar(100) primary key ,
    first_name varchar(80),
    last_name varchar(80),
    birthdate date,
    monthly_net_salary double precision
);
ALTER TABLE account DROP COLUMN  client_name;
ALTER TABLE account DROP COLUMN  client_last_name;
ALTER TABLE account DROP COLUMN  birthdate;
ALTER TABLE account DROP COLUMN  monthly_net_income;

ALTER TABLE  account ADD COLUMN id_client varchar(100) references client(id_client);

INSERT INTO client values ('550e8400-e29b-41d4-a716-446655440000','John','Doe','2000-04-08',3000)
    ON CONFLICT (id_client) DO NOTHING;
INSERT INTO account  (account_number, is_eligible,id_client,account_name)
VALUES (1234567829, false,'550e8400-e29b-41d4-a716-446655440000','name')
    ON CONFLICT (account_number) DO NOTHING;
