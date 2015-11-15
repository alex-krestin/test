package presentation.helper.collection.penalties;

import business.PenaltyManager;
import dao.exception.UnknownDatabaseTypeException;
import entity.TransferObject;
import presentation.helper.Helper;


public class GetAllPenaltiesHelper implements Helper {
    @Override
    public TransferObject execute(TransferObject to) {
        // to is null
        TransferObject responce = new TransferObject();

        try {
            PenaltyManager manager = new PenaltyManager();
            responce = manager.getAllPenalties();
        } catch (InstantiationException | UnknownDatabaseTypeException | IllegalAccessException e) {
            e.printStackTrace(); //TODO exception Handler
        }

        return responce;
    }
}
