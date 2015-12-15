package presentation.helper.collection.contracts;

import business.appservice.ContractManager;
import dao.exception.UnknownDatabaseTypeException;
import entity.Contract;
import entity.Response;
import entity.TransferObject;
import presentation.helper.Helper;

import java.util.logging.Level;
import java.util.logging.Logger;


public class GetAllAvailableServicesHelper implements Helper {
    private static final Logger log = Logger.getLogger(GetAllAvailableServicesHelper.class.getName());

    @Override
    public Response execute(TransferObject to) {
        Contract contract = (Contract) to;
        Response response = new Response();

        try {
            ContractManager manager = new ContractManager();
            response = manager.getAllAvailableServices(contract);
        } catch (InstantiationException | UnknownDatabaseTypeException | IllegalAccessException e) {
            log.log(Level.SEVERE, "Error: ", e);
        }

        return response;
    }
}
