package presentation.helper.collection.contracts;

import business.appservice.ContractManager;
import dao.exception.UnknownDatabaseTypeException;
import entity.Response;
import entity.SearchRequest;
import entity.TransferObject;
import presentation.helper.Helper;

import java.util.logging.Level;
import java.util.logging.Logger;


public class GetAllAvailableCarsHelper implements Helper {
    private static final Logger log = Logger.getLogger(GetAllAvailableCarsHelper.class.getName());

    @Override
    public Response execute(TransferObject to) {

        SearchRequest searchRequest = (SearchRequest) to;
        Response response = new Response();

        try {
            ContractManager manager = new ContractManager();
            //noinspection unchecked
            response = manager.getAvailableCars(searchRequest);
        } catch (InstantiationException | UnknownDatabaseTypeException | IllegalAccessException e) {
            log.log(Level.SEVERE, "Error: ", e);
        }

        return response;
    }
}
