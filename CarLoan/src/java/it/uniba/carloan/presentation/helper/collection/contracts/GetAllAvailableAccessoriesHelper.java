package it.uniba.carloan.presentation.helper.collection.contracts;


import it.uniba.carloan.business.appservice.mysql.MySQLContractManager;
import it.uniba.carloan.dao.exception.PersistenceException;
import it.uniba.carloan.entity.Response;
import it.uniba.carloan.entity.SearchRequest;
import it.uniba.carloan.entity.TransferObject;
import it.uniba.carloan.presentation.helper.Helper;

import java.util.logging.Level;
import java.util.logging.Logger;

public class GetAllAvailableAccessoriesHelper implements Helper {
    private static final Logger log = Logger.getLogger(GetAllAvailableAccessoriesHelper.class.getName());

    @Override
    public Response execute(TransferObject to) {
        SearchRequest searchRequest = (SearchRequest) to;
        Response response = new Response();

        try {
            MySQLContractManager manager = new MySQLContractManager(); //TODO
            //noinspection unchecked
            response = manager.getAllAvailableAccessories(searchRequest);
        } catch (PersistenceException e) {
            e.showAlert();
            log.log(Level.SEVERE, "", e);
        }

        return response;
    }
}
