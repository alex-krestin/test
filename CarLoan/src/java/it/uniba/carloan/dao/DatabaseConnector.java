package it.uniba.carloan.dao;


import it.uniba.carloan.business.security.DatabaseConfigObject;
import org.apache.commons.dbcp2.BasicDataSource;

public abstract class DatabaseConnector {
    protected static BasicDataSource dataSource;

    public abstract void setDataSource(DatabaseConfigObject object);

}
