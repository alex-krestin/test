package presentation.helper;

import entity.TransferObject;
import presentation.helper.collection.accessories.*;
import presentation.helper.collection.cars.*;
import presentation.helper.collection.clients.*;
import presentation.helper.collection.penalties.*;
import presentation.helper.collection.services.*;
import presentation.helper.collection.agencies.*;
import presentation.helper.collection.users.*;
import presentation.helper.collection.system.TestConnectionHelper;
import presentation.helper.exception.NoSuchOperationException;

public class Request {

    private Helper helper;

    private Request(Helper command)  {
        this.helper = command;
    }

    public static Request getAllRequest(EntityCode entityCode) throws NoSuchOperationException {
        switch (entityCode) {
            case USER:
                return new Request(new GetAllUsersHelper());
            case AGENCY:
                return new Request(new GetAllAgenciesHelper());
            case SERVICE:
                return new Request(new GetAllServicesHelper());
            case ACCESSORY:
                return new Request(new GetAllAccessoriesHelper());
            case ACCESSORY_CATEGORY:
                return new Request(new GetAllAccessoryCategoriesHelper());
            case PENALTY:
                return new Request(new GetAllPenaltiesHelper());
            case PENALTY_CATEGORY:
                return new Request(new GetAllPenaltyCategoriesHelper());
            case CLIENT:
                return new Request(new GetAllClientsHelper());
            case CAR:
                return new Request(new GetAllCarsHelper());
            case CAR_CATEGORY:
                return new Request(new GetAllCarCategoriesHelper());
            default:
                throw new NoSuchOperationException();
        }
    }

    public static Request addRequest(EntityCode entityCode) throws NoSuchOperationException {
        switch (entityCode) {
            case USER:
                return new Request(new AddUserHelper());
            case AGENCY:
                return new Request(new AddAgencyHelper());
            case SERVICE:
                return new Request(new AddServiceHelper());
            case ACCESSORY:
                return new Request(new AddAccessoryHelper());
            case ACCESSORY_CATEGORY:
                return new Request(new AddAccessoryCategoryHelper());
            case PENALTY:
                return new Request(new AddPenaltyHelper());
            case PENALTY_CATEGORY:
                return new Request(new AddPenaltyCategoryHelper());
            case CLIENT:
                return new Request(new AddClientHelper());
            case CAR:
                return new Request(new AddCarHelper());
            case CAR_CATEGORY:
                return new Request(new AddCarCategoryHelper());
            default:
                throw new NoSuchOperationException();
        }
    }

    public static Request updateRequest(EntityCode entityCode) throws NoSuchOperationException {
        switch (entityCode) {
            case USER:
                return new Request(new UpdateUserHelper());
            case AGENCY:
                return new Request(new UpdateAgencyHelper());
            case SERVICE:
                return new Request(new UpdateServiceHelper());
            case ACCESSORY:
                return new Request(new UpdateAccessoryHelper());
            case ACCESSORY_CATEGORY:
                return new Request(new UpdateAccessoryCategoryHelper());
            case PENALTY:
                return new Request(new UpdatePenaltyHelper());
            case PENALTY_CATEGORY:
                return new Request(new UpdatePenaltyCategoryHelper());
            case CLIENT:
                return new Request(new UpdateClientHelper());
            case CAR:
                return new Request(new UpdateCarHelper());
            case CAR_CATEGORY:
                return new Request(new UpdateCarCategoryHelper());
            default:
                throw new NoSuchOperationException();
        }
    }

    public static Request deleteRequest(EntityCode entityCode) throws NoSuchOperationException {
        switch (entityCode) {
            case USER:
                return new Request(new DeleteUserHelper());
            case AGENCY:
                return new Request(new DeleteAgencyHelper());
            case SERVICE:
                return new Request(new DeleteServiceHelper());
            case ACCESSORY:
                return new Request(new DeleteAccessoryHelper());
            case ACCESSORY_CATEGORY:
                return new Request(new DeleteAccessoryCategoryHelper());
            case PENALTY:
                return new Request(new DeletePenaltyHelper());
            case PENALTY_CATEGORY:
                return new Request(new DeletePenaltyCategoryHelper());
            case CLIENT:
                return new Request(new DeleteClientHelper());
            case CAR:
                return new Request(new DeleteCarHelper());
            case CAR_CATEGORY:
                return new Request(new DeleteCarCategoryHelper());
            default:
                throw new NoSuchOperationException();
        }
    }

    public static Request changeStatusRequest(EntityCode entityCode) throws NoSuchOperationException {
        switch (entityCode) {
            case USER:
                return new Request(new ChangeAccessHelper());
            case AGENCY:
                return new Request(new ChangeAgencyStateHelper());
            case SERVICE:
                return new Request(new ChangeServiceStatusHelper());
            case ACCESSORY:
                return new Request(new ChangeAccessoryStatusHelper());
            case PENALTY:
                return new Request(new ChangePenaltyStateHelper());
            case CAR:
                return new Request(new ChangeCarStateHelper());
            default:
                throw new NoSuchOperationException();
        }
    }

    public static Request testRequest(EntityCode entityCode) throws NoSuchOperationException {
        switch (entityCode) {
            case CONNECTION:
                return new Request(new TestConnectionHelper());
            default:
                throw new NoSuchOperationException();
        }
    }

    public static Request getRequest(EntityCode entityCode) throws NoSuchOperationException {
        switch (entityCode) {
            case CLIENT:
                return new Request(new GetClientHelper());
            default:
                throw new NoSuchOperationException();
        }
    }

    public TransferObject execute(TransferObject to) {
        return helper.execute(to);
    }


}