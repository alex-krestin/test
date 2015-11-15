package presentation.helper.collection.agencies;

import business.AgencyManager;
import dao.exception.UnknownDatabaseTypeException;
import entity.TransferObject;
import presentation.helper.Helper;

public class GetAllAgenciesHelper implements Helper {
    @Override
    public TransferObject execute(TransferObject to) {
        // to is null
        TransferObject responce = new TransferObject();

        try {
            AgencyManager manager = new AgencyManager();
            responce = manager.getAllAgencies();
        } catch (InstantiationException | UnknownDatabaseTypeException | IllegalAccessException e) {
            e.printStackTrace();
        }
        
        return responce;
    }
}
