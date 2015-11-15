package presentation.helper.collection.penalties;

import business.PenaltyManager;
import dao.exception.UnknownDatabaseTypeException;
import entity.Penalty;
import entity.TransferObject;
import presentation.helper.Helper;


public class ChangePenaltyStateHelper implements Helper {
    @Override
    public TransferObject execute(TransferObject to) {
        // Create Default Transfer Object
        TransferObject responce = new TransferObject();
        // cast TO to needed object
        Penalty penalty = (Penalty) to;

        try {
            PenaltyManager manager = new PenaltyManager();
            responce = manager.changePenaltyState(penalty);
        } catch (InstantiationException | UnknownDatabaseTypeException | IllegalAccessException e) {
            e.printStackTrace(); //TODO exception Handler
        }

        return responce;
    }
}
