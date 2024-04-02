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