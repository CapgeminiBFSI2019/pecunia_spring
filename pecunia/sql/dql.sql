--Query for fetching data for passbook

SELECT trans_id, date, amount, trans_from, trans_to, transaction.type, trans_closing_balance FROM transaction JOIN account ON account.account_id= transaction.account_id WHERE transaction.account_id=? AND date BETWEEN account.last_updated and Now();

--Query for fetching data for account summary
SELECT trans_id,date, amount,trans_from,trans_to,transaction.type,trans_closing_balance FROM transaction JOIN account ON account.account_id= transaction.account_id WHERE transaction.account_id=? AND date BETWEEN ? and ?;