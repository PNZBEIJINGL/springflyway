官网 https://flywaydb.org/getstarted/how

一些基础知识

一、How Flyway works

The easiest scenario is when you point Flyway to an empty database.
最容易的方案是Flyway指向一个空的数据库。

It will try to locate its metadata table. As the database is empty. Flyway won't find it and will create it instead.
FlyWay将试图查找它的元数据表。因为数据库是空的。Flyway找不到它的元数据表，然后就会去创建它的元数据表。

You now have a database with a single empty table called SCHEME_VERSION by default:
现在呢，我们就有一个数据库，这个数据库包含一个缺省的空表SCHEME_VERSION :

This table will be used to track the state of the databse.
这张表将被用于监测数据库的状态。

Immediately afterwards Flyway will begin scanning the filesystem or the classpath of the application for migrations.They can be written in either Sql or Java.
紧接着Flyway将开始扫描文件系统或者应用的类路径进行迁移。这些迁移文件可以是SQL脚本或者是java程序。

The migrations are then sorted besed on their version number and applied in order.
这些迁移文件按照版本号进行排序并且按照这个排序去执行。

As each migration gets applied,the metadata table is updated accordingly:
当每一个迁移文件被执行后，元数据的表就会按照格式进行更新。

With the metabata and the initial state in palce,we can now talk about migrating to newer versions.
这样我们就可以根据元数据和数据库初始化的状态，进行数据库升级了。

Flyway will once agein scan the filesystem or the classpath of the application of the migrations. The migrations are checked against the metadata table. If their version number is lower or equal to the one of the version maked as current, they are ignored.
当Flyway 再次扫描迁移的时候，它就会检查元数据表中迁移版本，如果要执行的迁移脚本的版本小于或者等于当前版本，Flyway将会忽略，不再重复执行。

The remaining migrations are the pending migrations : available , but not applied.
剩下的迁移脚本将会被执行迁移：可获取的并且没有被执行过的脚本。

They are then sorted by version number and executed in order:
这些迁移文件根据版本号进行排序并且有序执行。

The metadata table is updated accordingly:
元数据的表也会做相应的更新：

And that's it! Every time the need to evolve the database arises, whether structure (DDL) or reference data (DML), simply create a new migration with a version number higher than the current one. The next time Flyway starts, it will find it and upgrade the database accordingly.
这样的话，每次要做数据库升级，无论是执行DDL语句还是执行DML语句，所有的解决的问题的就是创建一个高于现在版本的迁移文件。当下一次Flyway开始运行的时候，它将会自动发现升级脚本，执行并且更新元数据表。


二、名明规则
数据库中的schema_version为存储对比脚本版本的表
sql脚本默认放置在 classpath:db/migration

数据迁移文件名称格式为：V[version]__[name].sql。
文件以.sql结尾，命名V字开头，后面数字为版本号 例如 V1__init.sql
注意：名称中[version]和[name]之间是两个下划线！