package it.uniba.carloan.presentation;

import it.uniba.carloan.entity.Response;
import it.uniba.carloan.entity.TransferObject;
import it.uniba.carloan.presentation.helper.EntityCode;
import it.uniba.carloan.presentation.helper.OperationCode;
import it.uniba.carloan.presentation.helper.Request;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import static it.uniba.carloan.presentation.helper.OperationCode.*;

public class FrontController {
    private static final Logger log = Logger.getLogger(FrontController.class.getName());

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

    public static Response handleRequest(OperationCode operationCode, EntityCode entityCode, TransferObject to) {
        Request request = sendRequest(operationCode, entityCode);

        //noinspection LawOfDemeter
        return request != null ? request.execute(to) : new Response(false);
    }

    private static Request sendRequest(OperationCode operationCode, EntityCode entityCode) {
        Map<OperationCode, Request> requestMap = getRequestMap(entityCode);
        return requestMap.get(operationCode);
    }

    private static Map<OperationCode, Request> getRequestMap(EntityCode entityCode) {
        Map<OperationCode, Request> requestMap = new HashMap<>();
        requestMap.put(GET, Request.getRequest(entityCode));
        requestMap.put(GET_ALL, Request.getAllRequest(entityCode));
        requestMap.put(ADD, Request.addRequest(entityCode));
        requestMap.put(UPDATE, Request.updateRequest(entityCode));
        requestMap.put(DELETE, Request.deleteRequest(entityCode));
        requestMap.put(CHANGE_STATUS, Request.changeStatusRequest(entityCode));
        requestMap.put(TEST, Request.testRequest(entityCode));

        return requestMap;
    }

}
