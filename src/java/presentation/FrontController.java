package presentation;

import presentation.helper.exception.NoSuchOperationException;
import entity.TransferObject;
import presentation.helper.OperationCode;
import presentation.helper.Request;
import presentation.helper.EntityCode;

public class FrontController {

    // Shows view in the same window
    public static void handleRequest(String view) {
        ViewDispatcher dispatcher = new ViewDispatcher();
        dispatcher.showView(view);
    }

    // Closes previous window and open a new one
    public static void handleRequest(String view, boolean resizable) {
        ViewDispatcher dispatcher = new ViewDispatcher();
        dispatcher.showView(view, resizable);
    }

    // Opens popup
    public  static void handleRequest(String view, String title) {
        ViewDispatcher dispatcher = new ViewDispatcher();
        dispatcher.openPopupWindow(view, title);
    }

    public static TransferObject handleRequest(OperationCode operationCode, EntityCode entityCode, TransferObject to) {
        Request request = null;

        // Get specific Request instance (Request class will transfer
        switch (operationCode) {
            case GET:
                try {
                    request = Request.getRequest(entityCode);
                } catch (NoSuchOperationException e) {
                    AlertHandler.showException("Operazione fallita", e);
                }
                break;
            case GET_ALL:
                try {
                    request = Request.getAllRequest(entityCode);
                } catch (NoSuchOperationException e) {
                    //TODO add logging
                }
                break;
            case ADD:
                try {
                    request = Request.addRequest(entityCode);
                } catch (NoSuchOperationException e) {
                    AlertHandler.showException("Operazione fallita", e);
                }
                break;
            case UPDATE:
                try {
                    request = Request.updateRequest(entityCode);
                } catch (NoSuchOperationException e) {
                    AlertHandler.showException("Operazione fallita", e);
                }
                break;
            case DELETE:
                try {
                    request = Request.deleteRequest(entityCode);
                } catch (NoSuchOperationException e) {
                    AlertHandler.showException("Operazione fallita", e);
                }
                break;
            case CHANGE_STATUS:
                try {
                    request = Request.changeStatusRequest(entityCode);
                } catch (NoSuchOperationException e) {
                    AlertHandler.showException("Operazione fallita", e);
                }
                break;
            case TEST:
                try {
                    request = Request.testRequest(entityCode);
                } catch (NoSuchOperationException e) {
                    AlertHandler.showException("Operazione fallita", e);
                }
                break;
        }


        return request != null ? request.execute(to) : new TransferObject(false);
    }

}
