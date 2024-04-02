CREATE OR REPLACE FUNCTION account_statement(account_number bigint, begin_date DATE, end_date DATE,transaction_reference varchar(100))
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
    "transaction" t
WHERE
    t."date" BETWEEN begin_date AND end_date
  AND t.account_number = account_statement.account_number
OR
    t.reference=account_statement.transaction_reference

ORDER BY
    t."date" DESC;
END; $$
LANGUAGE plpgsql;






