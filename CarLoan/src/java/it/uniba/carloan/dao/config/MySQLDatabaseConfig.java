package it.uniba.carloan.dao.config;


import it.uniba.carloan.business.security.DatabaseConfigObject;
import it.uniba.carloan.entity.TransferObject;

public class MySQLDatabaseConfig implements DatabaseConfigObject, TransferObject {

    private final String host;
    private int port = 3306;
    private final String database;
    private final String user;
    private final String password;
    private String tablePrefix = "carloan_";

    public MySQLDatabaseConfig(String host, int port, String database, String user, String password, String tablePrefix) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.user = user;
        this.password = password;
        this.tablePrefix = tablePrefix;
    }

    public MySQLDatabaseConfig(String host, String database, String user, String password) {
        this.host = host;
        this.database = database;
        this.user = user;
        this.password = password;
    }

    public String getHost() {
        return this.host;
    }

    public Integer getPort() {
        return this.port;
    }

    public String getDatabase() {
        return this.database;
    }

    public String getUser() {
        return this.user;
    }

    public String getPassword() {
        return  this.password;
    }

    public String getPath() {
        return null;
    }

    public String getTablePrefix() {
        return tablePrefix;
    }

    public void setTablePrefix(String tablePrefix) {
        this.tablePrefix = tablePrefix;
    }

}
