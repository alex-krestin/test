package presentation.helper.collection.agencies;

import business.AgencyManager;
import dao.exception.UnknownDatabaseTypeException;
import entity.Agency;
import entity.TransferObject;
import presentation.helper.Helper;


public class ChangeAgencyStateHelper implements Helper {
    @Override
    public TransferObject execute(TransferObject to) {
        // Create Default Transfer Object
        TransferObject responce = new TransferObject();
        // cast TO to needed object
        Agency agency = (Agency) to;

        try {
            AgencyManager manager = new AgencyManager();
            responce = manager.changeAgencyState(agency);
        } catch (InstantiationException | UnknownDatabaseTypeException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return responce;
    }
}
