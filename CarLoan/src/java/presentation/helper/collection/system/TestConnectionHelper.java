package presentation.helper.collection.system;

import business.appservice.ConnectionManager;
import dao.exception.UnknownDatabaseTypeException;
import entity.Response;
import entity.TransferObject;
import presentation.helper.Helper;
import utility.DBConfigObject;

import java.util.logging.Level;
import java.util.logging.Logger;


public class TestConnectionHelper implements Helper {
    private static final Logger log = Logger.getLogger(TestConnectionHelper.class.getName());

    @Override
    public Response execute(TransferObject to) {
        Response response = new Response();
        DBConfigObject config = (DBConfigObject) to;

        try {
            ConnectionManager connectionManager = new ConnectionManager();
            response = connectionManager.checkConnection(config);
        } catch (UnknownDatabaseTypeException | InstantiationException | IllegalAccessException e) {
            log.log(Level.SEVERE, "Error: ", e);
        }

        return response;
    }
}
