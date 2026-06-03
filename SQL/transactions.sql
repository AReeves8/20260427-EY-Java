/*
	SQL - Structured Query Language

		DDL - Data Definition Language
			modifying tables themselves
			CREATE TABLE
			ALTER TABLE
			DROP TABLE
			TRUNCATE TABLE

		DML - Data Manipulation Language
			manage the data in your tables
			SELECT
			INSERT
			UPDATE
			DELETE

		TCL - Transaction Control Language
			manage transactions at the database level	
			BEGIN - starts transaction
			COMMIT - save the entire transaction to disk
			ROLLBACK - undo any changes not saved to disk
			SAVEPOINT - create a checkpoint within the transaction


			ACID Properties:
				A - Atomicity
					- either everything succeeds, or nothing does. ALL OR NONE.
				C - Consistency
					- database moves from one valid state to another
				I - Isolation
					- concurrent transactions should be separate from each other
				D - Durable
					- once comitted, data should be persistant (survive crashes and power failures)
*/


BEGIN;		-- Transaction starts here
	INSERT INTO artist (name) VALUES ('Metal Ica');
	UPDATE artist SET name = 'WIPED' WHERE name = 'Metal Ica';
	ROLLBACK; 		-- Undoes chnages from above

-- finds nothing since it was undone
SELECT * FROM artist WHERE name = 'Metal Ica' OR name = 'WIPED';


/*
	customer buys new tracks. need to create BOTH the invoice and the invoice-lines. 
	only commit, if BOTH succeed. 

	database errors will automatically roll things back
		you only use ROLLBACK if something is wrong with the data.
*/ 
BEGIN;

	-- create a new invoice and save it as 'new_invoice' to be used throughout the transaction
	WITH new_invoice AS (
		INSERT INTO invoice (customer_id, invoice_date, billing_country, total)
		VALUES (1, CURRENT_DATE, 'Canada', 1.98)
		RETURNING invoice_id
	)

	INSERT INTO invoice_line (invoice_id, track_id, unit_price, quantity)
		SELECT new_invoice.invoice_id, t.track_id, t.unit_price, 1
		FROM new_invoice, track t
		WHERE t.track_id IN (1,2);	-- customer bought tracks 1 and 2

COMMIT;


/*
	updating an invoice, then inserting an invalid record.
	transaction enters "aborted" state when error occurs. 
	all queries after the error are ignored until transaction ends with COMMIT or ROLLBACK
*/
SELECT * FROM invoice WHERE invoice_id = 1; 
BEGIN;

	UPDATE invoice SET total = total + 0.99 WHERE invoice_id = 1;

	-- simulating a failing insert with a track_id that does not exist
	INSERT INTO invoice_line (invoice_id, track_id, unit_price, quantity)
	VALUES (1, 99999999, 0.99, 1);

	-- once an error occurs, PostgreSQL will ignore ALL other queries until the end of the transaction
	UPDATE invoice SET total = total + 0.99 WHERE invoice_id = 1;	-- would be ignored	

ROLLBACK;	-- undo the first update as well



















