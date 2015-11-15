package presentation.helper.collection.system;

import business.ConnectionManager;
import dao.exception.UnknownDatabaseTypeException;
import entity.TransferObject;
import presentation.helper.Helper;
import utility.DBConfigObject;


public class TestConnectionHelper implements Helper {
    @Override
    public TransferObject execute(TransferObject to) {
        TransferObject responce = new TransferObject();
        DBConfigObject config = (DBConfigObject) to;

        try {
            ConnectionManager connectionManager = new ConnectionManager();
            responce = connectionManager.checkConnection(config);
        } catch (UnknownDatabaseTypeException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return responce;
    }
}
