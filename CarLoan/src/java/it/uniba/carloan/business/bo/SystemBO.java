package it.uniba.carloan.business.bo;


import it.uniba.carloan.business.security.DatabaseConfigObject;
import it.uniba.carloan.dao.DAOFactory;
import it.uniba.carloan.dao.entity.SystemDAO;
import it.uniba.carloan.dao.exception.PersistenceException;

public class SystemBO {

    private SystemDAO dao;

    @SuppressWarnings("LawOfDemeter")
    public SystemBO() throws PersistenceException {
        dao = DAOFactory.getDAOFactory().getSystemDAO();
    }

    public boolean checkConnection(DatabaseConfigObject object) throws PersistenceException {
        return dao.checkConnection(object);
    }
}
