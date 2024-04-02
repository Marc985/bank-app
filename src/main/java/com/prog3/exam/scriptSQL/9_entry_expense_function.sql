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
