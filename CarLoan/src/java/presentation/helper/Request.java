package presentation.helper;

import entity.Response;
import entity.TransferObject;
import presentation.helper.collection.accessories.*;
import presentation.helper.collection.agencies.*;
import presentation.helper.collection.cars.*;
import presentation.helper.collection.clients.*;
import presentation.helper.collection.contracts.GetAllAvailableAccessoriesHelper;
import presentation.helper.collection.contracts.GetAllAvailableCarsHelper;
import presentation.helper.collection.contracts.GetAllAvailableServicesHelper;
import presentation.helper.collection.penalties.*;
import presentation.helper.collection.services.*;
import presentation.helper.collection.system.TestConnectionHelper;
import presentation.helper.collection.users.*;

import java.util.HashMap;
import java.util.Map;

import static presentation.helper.EntityCode.*;

public class Request {

    private final Helper helper;
    private static final int MAX_SIZE = 25;

    private static final Map<EntityCode, Request> getAllRequestMap = new HashMap<>(MAX_SIZE);
    static
    {
        getAllRequestMap.put(USER,                  new Request(new GetAllUsersHelper()));
        getAllRequestMap.put(AGENCY,                new Request(new GetAllAgenciesHelper()));
        getAllRequestMap.put(SERVICE,               new Request(new GetAllServicesHelper()));
        getAllRequestMap.put(ACCESSORY,             new Request(new GetAllAccessoriesHelper()));
        getAllRequestMap.put(ACCESSORY_CATEGORY,    new Request(new GetAllAccessoryCategoriesHelper()));
        getAllRequestMap.put(PENALTY,               new Request(new GetAllPenaltiesHelper()));
        getAllRequestMap.put(PENALTY_CATEGORY,      new Request(new GetAllPenaltyCategoriesHelper()));
        getAllRequestMap.put(CLIENT,                new Request(new GetAllClientsHelper()));
        getAllRequestMap.put(CAR,                   new Request(new GetAllCarsHelper()));
        getAllRequestMap.put(CAR_CATEGORY,          new Request(new GetAllCarCategoriesHelper()));
        getAllRequestMap.put(CAR_TARIFF,            new Request(new GetAllCarTariffsHelper()));
        getAllRequestMap.put(ACCESSORY_TARIFF,      new Request(new GetAllAccessoryTariffsHelper()));
        getAllRequestMap.put(PENALTY_TARIFF,        new Request(new GetAllPenaltyTariffsHelper()));
        getAllRequestMap.put(SERVICE_TARIFF,        new Request(new GetAllServiceTariffsHelper()));
        getAllRequestMap.put(AVAILABLE_CAR,         new Request(new GetAllAvailableCarsHelper()));
        getAllRequestMap.put(AVAILABLE_SERVICE,     new Request(new GetAllAvailableServicesHelper()));
        getAllRequestMap.put(AVAILABLE_ACCESSORY,   new Request(new GetAllAvailableAccessoriesHelper()));
    }

    private static final Map<EntityCode, Request> addRequestMap = new HashMap<>(MAX_SIZE);
    static
    {
        addRequestMap.put(USER,                 new Request(new AddUserHelper()));
        addRequestMap.put(AGENCY,               new Request(new AddAgencyHelper()));
        addRequestMap.put(SERVICE,              new Request(new AddServiceHelper()));
        addRequestMap.put(ACCESSORY,            new Request(new AddAccessoryHelper()));
        addRequestMap.put(ACCESSORY_CATEGORY,   new Request(new AddAccessoryCategoryHelper()));
        addRequestMap.put(PENALTY,              new Request(new AddPenaltyHelper()));
        addRequestMap.put(PENALTY_CATEGORY,     new Request(new AddPenaltyCategoryHelper()));
        addRequestMap.put(CLIENT,               new Request(new AddClientHelper()));
        addRequestMap.put(CAR,                  new Request(new AddCarHelper()));
        addRequestMap.put(CAR_CATEGORY,         new Request(new AddCarCategoryHelper()));
        addRequestMap.put(CAR_TARIFF,           new Request(new AddCarTariffHelper()));
        addRequestMap.put(ACCESSORY_TARIFF,     new Request(new AddAccessoryTariffHelper()));
        addRequestMap.put(PENALTY_TARIFF,       new Request(new AddPenaltyTariffHelper()));
        addRequestMap.put(SERVICE_TARIFF,       new Request(new AddServiceTariffHelper()));
    }

    private static final Map<EntityCode, Request> updateRequestMap = new HashMap<>(MAX_SIZE);
    static
    {
        updateRequestMap.put(USER,                 new Request(new UpdateUserHelper()));
        updateRequestMap.put(AGENCY,               new Request(new UpdateAgencyHelper()));
        updateRequestMap.put(SERVICE,              new Request(new UpdateServiceHelper()));
        updateRequestMap.put(ACCESSORY,            new Request(new UpdateAccessoryHelper()));
        updateRequestMap.put(ACCESSORY_CATEGORY,   new Request(new UpdateAccessoryCategoryHelper()));
        updateRequestMap.put(PENALTY,              new Request(new UpdatePenaltyHelper()));
        updateRequestMap.put(PENALTY_CATEGORY,     new Request(new UpdatePenaltyCategoryHelper()));
        updateRequestMap.put(CLIENT,               new Request(new UpdateClientHelper()));
        updateRequestMap.put(CAR,                  new Request(new UpdateCarHelper()));
        updateRequestMap.put(CAR_CATEGORY,         new Request(new UpdateCarCategoryHelper()));
        updateRequestMap.put(CAR_TARIFF,           new Request(new UpdateCarTariffHelper()));
        updateRequestMap.put(ACCESSORY_TARIFF,     new Request(new UpdateAccessoryTariffHelper()));
        updateRequestMap.put(PENALTY_TARIFF,       new Request(new UpdatePenaltyTariffHelper()));
        updateRequestMap.put(SERVICE_TARIFF,       new Request(new UpdateServiceTariffHelper()));
    }

    private static final Map<EntityCode, Request> deleteRequestMap = new HashMap<>(MAX_SIZE);
    static
    {
        deleteRequestMap.put(USER,                 new Request(new DeleteUserHelper()));
        deleteRequestMap.put(AGENCY,               new Request(new DeleteAgencyHelper()));
        deleteRequestMap.put(SERVICE,              new Request(new DeleteServiceHelper()));
        deleteRequestMap.put(ACCESSORY,            new Request(new DeleteAccessoryHelper()));
        deleteRequestMap.put(ACCESSORY_CATEGORY,   new Request(new DeleteAccessoryCategoryHelper()));
        deleteRequestMap.put(PENALTY,              new Request(new DeletePenaltyHelper()));
        deleteRequestMap.put(PENALTY_CATEGORY,     new Request(new DeletePenaltyCategoryHelper()));
        deleteRequestMap.put(CLIENT,               new Request(new DeleteClientHelper()));
        deleteRequestMap.put(CAR,                  new Request(new DeleteCarHelper()));
        deleteRequestMap.put(CAR_CATEGORY,         new Request(new DeleteCarCategoryHelper()));
        deleteRequestMap.put(CAR_TARIFF,           new Request(new DeleteCarTariffHelper()));
        deleteRequestMap.put(ACCESSORY_TARIFF,     new Request(new DeleteAccessoryTariffHelper()));
        deleteRequestMap.put(PENALTY_TARIFF,       new Request(new DeletePenaltyTariffHelper()));
        deleteRequestMap.put(SERVICE_TARIFF,       new Request(new DeleteServiceTariffHelper()));
    }

    private static final Map<EntityCode, Request> changeStatusRequestMap = new HashMap<>(MAX_SIZE);
    static
    {
        changeStatusRequestMap.put(USER,                 new Request(new ChangeAccessHelper()));
        changeStatusRequestMap.put(AGENCY,               new Request(new ChangeAgencyStateHelper()));
        changeStatusRequestMap.put(SERVICE,              new Request(new ChangeServiceStatusHelper()));
        changeStatusRequestMap.put(ACCESSORY,            new Request(new ChangeAccessoryStatusHelper()));
        changeStatusRequestMap.put(PENALTY,              new Request(new ChangePenaltyStateHelper()));
        changeStatusRequestMap.put(CAR,                  new Request(new ChangeCarStateHelper()));
    }

    private static final Map<EntityCode, Request> testRequestMap = new HashMap<>(MAX_SIZE);
    static
    {
        testRequestMap.put(CONNECTION,          new Request(new TestConnectionHelper()));
    }

    private static final Map<EntityCode, Request> getRequestMap = new HashMap<>(MAX_SIZE);
    static
    {
        getRequestMap.put(CLIENT,               new Request(new GetClientHelper()));
    }

    private Request(Helper command)  {
        this.helper = command;
    }

    public static Request getAllRequest(EntityCode entityCode) {
        return getAllRequestMap.get(entityCode);
    }

    public static Request addRequest(EntityCode entityCode) {
        return addRequestMap.get(entityCode);
    }

    public static Request updateRequest(EntityCode entityCode) {
        return updateRequestMap.get(entityCode);
    }

    public static Request deleteRequest(EntityCode entityCode) {
        return deleteRequestMap.get(entityCode);
    }

    public static Request changeStatusRequest(EntityCode entityCode) {
        return changeStatusRequestMap.get(entityCode);
    }

    public static Request testRequest(EntityCode entityCode) {
        return testRequestMap.get(entityCode);
    }

    public static Request getRequest(EntityCode entityCode) {
        return getRequestMap.get(entityCode);
    }

    public Response execute(TransferObject to) {
        return helper.execute(to);
    }
}