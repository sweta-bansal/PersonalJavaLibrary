set hive.cli.print.header=true;
set hive.resultset.use.unique.column.names=false;
set hive.execution.engine=tez;
set hive.vectorized.execution.enabled = true;

drop table objectUnion;
create table objectUnion as select column1,column2,column3 from awsS3Object1 o1 union all select column1,column2,column3 from awsS3Object2 o2;

drop table differences;
create table differences as select o.column1,o.column2,o.column3, count(*) as cnt from objectUnion o group by o.column1,o.column2,o.column3 having cnt <> 2;

