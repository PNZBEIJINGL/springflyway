package com.star;

import org.flywaydb.core.Flyway;

import javax.sql.DataSource;

public class FlywayMigration {
    /**
     * 定义私有数据源
     */
    private DataSource dataSource;

    /**
     * # 该方法用于读取配置文件的数据源
     *
     * @param: dataSource spring中配置的数据源property
     * @return:
     **/
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * # 该方法通过调用flyway的Java API实现指定数据库的版本控制。由于在spring中配置了实例化父类时会执行该方法，所以在每次项目更新重启之后都会根据定义好的sql脚本自动将数据库更新到匹配的版本
     *
     * @param:
     * @return:
     **/
    public void migrate() {

        Flyway flyway = new Flyway();
        System.out.println(flyway.getClass().getClassLoader().getResource("").getPath());//输出当前目录，用于核对脚本路径是否能正确读取
        flyway.setDataSource(dataSource);//设置数据源
        //flyway.setSchemas("flywaydemo"); // 设置接受flyway进行版本管理的多个数据库
        //flyway.setTable("schema_version");// 设置存放flyway metadata数据的表名.默认小写的schema_version
        flyway.setBaselineOnMigrate(true);//设置基线，已经开发过一段时间的数据库需要设置为true
        //flyway.setLocations("db.migrations", "db.migration"); //设置flyway扫描sql升级脚本、java升级脚本的目录路径或包路径。脚本命名规约详见flyway官网
        flyway.setEncoding("UTF-8"); // 设置sql脚本文件的编码
        flyway.migrate();

    }

}
