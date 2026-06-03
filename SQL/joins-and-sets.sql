/*
	JOIN
		combine data from multiple tables based on some related column

		combine the tables first, then execute the query
		

		INNER JOIN (default when you use just "JOIN")
			- only rows that match in both tables
		LEFT/RIGHT JOIN
			- give ALL rows from the left/right table, and only matching rows from the other
		FULL OUTER JOIN
			- all rows from BOTH tables
		SELF JOIN
			- a table joined with itself
*/

-- select from the child (many side), join on the parent (one side)
SELECT a.title AS album_title, ar.name AS artist_name
FROM album a
JOIN artist ar ON ar.artist_id = a.artist_id;


-- three table join
SELECT t.name AS track, a.title AS album, ar.name AS artist
FROM track t 
JOIN album a ON a.album_id = t.album_id			-- joining on PK -> FK
JOIN artist ar ON ar.artist_id = a.artist_id;


-- joining on something other than keys - not traditionally useful but is possible
SELECT t.name, t.unit_price, il.unit_price AS billed_price
FROM track t
JOIN invoice_line il ON il.unit_price BETWEEN t.unit_price - 0.10 AND t.unit_price + 0.10;


/*
	SET OPERATORS
		combine the results of multiple queries into one result set

		execute separate queries first, THEN combine results at the end
		

		UNION
			- all rows from both sets, with duplicates removed
		UNION ALL
			- all rows from both sets, with duplicates kept (faster)
		INTERSECT
			- only rows that appear in BOTH sets
		EXCEPT (MINUS in other DBMS)
			- only rows from the first set that DO NOT appear in the second set
*/

-- all artists associated to a track
SELECT name FROM artist WHERE name IS NOT NULL
UNION
SELECT DISTINCT composer FROM track WHERE composer IS NOT NULL;

-- selects customers from both USA and Canada, removes customers with duplicate names
SELECT first_name, last_name, 'Canada' AS region
FROM customer
WHERE country = 'Canada'

UNION

SELECT first_name, last_name, 'USA' AS region
FROM customer
WHERE country = 'USA';




-- billing countries across invoices from before AND after a certain date
SELECT billing_country FROM invoice WHERE invoice_date < '2022-01-01'
INTERSECT
SELECT billing_country FROM invoice WHERE invoice_date >= '2022-01-01';



-- tracks that that are in multiple types of playlists
SELECT pt.track_id, t.name 
FROM playlist_track pt
JOIN track t ON pt.track_id = t.track_id
WHERE playlist_id = (SELECT playlist_id FROM playlist WHERE name = 'Heavy Metal Classic')

INTERSECT

SELECT pt.track_id, t.name 
FROM playlist_track pt
JOIN track t ON pt.track_id = t.track_id
WHERE playlist_id = (SELECT playlist_id FROM playlist WHERE name = '90’s Music');



