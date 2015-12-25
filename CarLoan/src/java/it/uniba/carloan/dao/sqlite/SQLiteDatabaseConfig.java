package it.uniba.carloan.dao.sqlite;

import it.uniba.carloan.business.security.DatabaseConfigObject;
import it.uniba.carloan.entity.TransferObject;


public class SQLiteDatabaseConfig implements TransferObject, DatabaseConfigObject {
    private static final String PATH = "carloan.db";

    @Override
    public String toString() {
        return "Path : " + PATH;
    }
}
