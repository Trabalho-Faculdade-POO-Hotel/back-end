package br.com.projeto.utils;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;

public class DBConnectionPool {
    private static BasicDataSource dataSource;

    static {
        DBConfig config = DBConfig.getDefaultConfig();

        dataSource = new BasicDataSource();
        dataSource.setUrl(config.getUrl());
        dataSource.setUsername(config.getUsername());
        dataSource.setPassword(config.getPassword());
        dataSource.setDriverClassName(config.getDriverClassName());

        // Optional settings
        dataSource.setInitialSize(5);
        dataSource.setMaxTotal(20);
    }

    public static DataSource getDataSource() {
        return dataSource;
    }
}
