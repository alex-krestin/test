package business.bo;


import dao.DAOFactory;
import dao.entity.SystemDAO;
import dao.exception.UnknownDatabaseTypeException;
import entity.Response;
import utility.DBConfigObject;
import utility.SessionData;

public class SystemBO {

    private SystemDAO dao;

    @SuppressWarnings("LawOfDemeter")
    public SystemBO() throws UnknownDatabaseTypeException, InstantiationException, IllegalAccessException {
        dao = DAOFactory.getDAOFactory(SessionData.DAOFactory).getSystemDAO();
    }

    public Response checkConnection(DBConfigObject config) {
        return new Response(dao.checkConnection(config));
    }
}
