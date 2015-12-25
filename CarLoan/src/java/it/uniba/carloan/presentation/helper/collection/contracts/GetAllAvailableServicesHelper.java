package it.uniba.carloan.presentation.helper.collection.contracts;

import it.uniba.carloan.business.appservice.mysql.MySQLContractManager;
import it.uniba.carloan.dao.exception.PersistenceException;
import it.uniba.carloan.entity.Contract;
import it.uniba.carloan.entity.Response;
import it.uniba.carloan.entity.TransferObject;
import it.uniba.carloan.presentation.helper.Helper;

import java.util.logging.Level;
import java.util.logging.Logger;


public class GetAllAvailableServicesHelper implements Helper {
    private static final Logger log = Logger.getLogger(GetAllAvailableServicesHelper.class.getName());

    @Override
    public Response execute(TransferObject to) {
        Contract contract = (Contract) to;
        Response response = new Response();

        try {
            MySQLContractManager manager = new MySQLContractManager();  //TODO
            response = manager.getAllAvailableServices(contract);
        } catch (PersistenceException e) {
            e.showAlert();
            log.log(Level.SEVERE, "", e);
        }

        return response;
    }
}
