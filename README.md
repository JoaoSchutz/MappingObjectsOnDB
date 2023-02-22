## MappingObjectsOnDBTool

This program has the purpose of mapping objects inside the database, from keywords (configured by you in the function present in the Function class).

--- Example
```sql
insert into mapping_tool.KEYWORD (table, column , select, fks)
select distinct
               t.table_name as table
              ,string_agg(c.column_name, Chr(13)) as column
              ,max(concat('SELECT * FROM '||SCHEMA_NAME||'.'||t.table_name||' ORDER BY 1 DESC')) as select
              ,string_agg(concat('LEFT JOIN '||k2.column_name|| ' ON ' || k2.column_name|| ' = ' ||'id'), Chr(13) ) as fks
from information_schema.tables t
         inner join information_schema.columns c on c.table_name = t.table_name and c.table_schema = t.table_schema
         left join information_schema.KEY_COLUMN_USAGE k2 on t.table_name  = k2.table_name and k2.constraint_name not like '%pk%'
where c.column_name ilike '%KEYWORD%'
and t.table_schema in (SCHEMA_NAME)
group by 1;
```

It also generates a DDL script to generate your primary keys for each table in the database, to handle it.

--- Example

```sql
insert into mapping_tool.ddl(ddl)
select string_agg(ddl,Chr(13)) as ddl
from (
         select distinct
                        t.table_name as table
                       ,k2.constraint_name
                       ,CASE WHEN k2.constraint_name IS NOT NULL THEN concat('ALTER TABLE ',$1,'.',t.table_name,' ADD COLUMN IF NOT EXISTS YOUR_PRIMARY_KEY_NAME UUID ',Chr(13),'GENERATED ALWAYS AS (',upper(k2.column_name),'::TEXT,','''',upper(k2.constraint_name),'''',')) STORED;
CREATE INDEX CONCURRENTLY IF NOT EXISTS IDX_',upper(t.table_name),' ON ',$1,'.',t.table_name,' (YOUR_PRIMARY_KEY_NAME);',Chr(13))::text ELSE NULL END AS ddl
         from information_schema.tables t
                  inner join information_schema.columns c on c.table_name = t.table_name and c.table_schema = t.table_schema
                  left join information_schema.KEY_COLUMN_USAGE k2 on t.table_name  = k2.table_name and k2.constraint_name like '%pk%'
                  and t.table_schema in (SCHEMA_NAME)
         group by 1,2,3
     )a;
```
