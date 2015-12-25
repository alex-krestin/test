package it.uniba.carloan.business.security;


import it.uniba.carloan.business.constants.DatabaseType;

import java.io.Serializable;

public class DatabaseConfig<T extends DatabaseConfigObject> implements Serializable {
    private static final long serialVersionUID = 1;

    private DatabaseType databaseType;
    private T databaseConfig;


    public DatabaseConfig(DatabaseType databaseType, T databaseConfig) {
        this.databaseType = databaseType;
        this.databaseConfig = databaseConfig;
    }


    public DatabaseType getDatabaseType() {
        return databaseType;
    }

    public T getDatabaseConfig() {
        return databaseConfig;
    }

}
