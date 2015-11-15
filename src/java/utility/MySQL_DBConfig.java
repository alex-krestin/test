package utility;

import entity.TransferObject;

import java.io.Serializable;


public class MySQL_DBConfig extends TransferObject implements DBConfigObject, Serializable  {
    private String host;
    private int port = 3306;
    private String database;
    private String user;
    private String password;
    private String tablePrefix = "carloan_";

    public MySQL_DBConfig(String host, int port, String database, String user, String password, String tablePrefix) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.user = user;
        this.password = password;
        this.tablePrefix = tablePrefix;
    }

    public MySQL_DBConfig(String host, String database, String user, String password) {
        this.host = host;
        this.database = database;
        this.user = user;
        this.password = password;
    }

    @Override
    public String getHost() {
        return this.host;
    }

    @Override
    public Integer getPort() {
        return this.port;
    }

    @Override
    public String getDatabase() {
        return this.database;
    }

    @Override
    public String getUser() {
        return this.user;
    }

    @Override
    public String getPassword() {
        return  this.password;
    }

    @Override
    public String getPath() {
        return null;
    }

    @Override
    public String toString() {
        return "Host : " + this.host + " Port : " + this.port + " Database : " + this.database + " Username : " + this.user + " Password : " + this.password;
    }

    @Override
    public String getTablePrefix() {
        return tablePrefix;
    }

    public void setTablePrefix(String tablePrefix) {
        this.tablePrefix = tablePrefix;
    }
}
