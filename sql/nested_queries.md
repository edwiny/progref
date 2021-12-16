## Nested Querires


The result of any query is a table and therefor can be used as input for any other query.

Subselects can therefor be used anywhere a table can be used.



Start by building the sub select:


```
SELECT a.name AS name, b.descr as description WHERE  ... 
```


Now put parenthesis around it and as AS:


```
(
  SELECT a.name AS name, b.descr as description WHERE  ... 
) AS subquery


```

Now we can use it in FROM query as a table:

```
SELECT s.price, subquery.description FROM
(
  SELECT a.name AS name, b.descr as description WHERE  ... 
) AS subquery
JOIN sales as s
  ON subquery.name = s.name




 
Can also use the subselect in WHERE .. IN: 

```
SELECT * FROM sales WHERE city IN (SELECT city FROM cities WHERE region = 'APAC')
```

or:


```
select
  *
from
  zone_equalisers.tps_rds_meta_data
where
  dt = (
    select
      max(dt)
    from
      zone_equalisers.tps_rds_meta_data
    where
      dt > date_add(date('today'), -5)
  )
```
