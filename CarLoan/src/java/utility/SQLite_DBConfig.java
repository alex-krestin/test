package utility;

import java.io.Serializable;


public class SQLite_DBConfig implements DBConfigObject, Serializable {
    private static final String PATH = "carloan.db";

    @Override
    public String getHost() {
        return null;
    }

    @Override
    public Integer getPort() {
        return null;
    }

    @Override
    public String getDatabase() {
        return null;
    }

    @Override
    public String getUser() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getPath() {
        return PATH;
    }

    @Override
    public String getTablePrefix() {
        return null;
    }

    @Override
    public String toString() {
        return "Path : " + PATH;
    }
}
