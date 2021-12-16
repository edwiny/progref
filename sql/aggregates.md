## Aggregates

Aggregate functions returns a single value over a span of multiple rows.


### GROUP BY

The group by will group rows BEFORE the aggregate is calculated.

```
SELECT product, count(*) as total
    GROUP BY total desc, product
```



### HAVING

HAVING filters the results on based on the aggregate - after the aggregate has been calculated.
The WHERE clause can filter rows before the
aggregate is calculated but not after.

Example:

```
SELECT product, count(*) as total
    GROUP BY total desc, product
    HAVING total > 10
    ORDER By total Desc, product
```


### DISTINCT

```
SELECT COUNT(DISTINCT item) FROM itemtable
```
