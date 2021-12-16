## SQL Joins

* Think of joins as venn diagrams.
* The intersection of the diagrams are where the tables are related to each other.
* INNER JOIN returns only those rows where all tables have a matching row
* LEFT OUTER JOIN additionally returns all the rows from the left table.
* RIGHT OUTER JOIN can be rewrited as a LEFT OUTER JOIN, so many DB engines don't even implement it.

### Syntax

```
SELECT l.field1 
  FROM left as l
  JOIN right as r ON l.id = r.id
  JOIN another_right as r2 ON l.id = r2.id


