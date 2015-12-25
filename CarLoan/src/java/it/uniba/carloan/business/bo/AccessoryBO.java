package it.uniba.carloan.business.bo;


import it.uniba.carloan.dao.DAOFactory;
import it.uniba.carloan.dao.entity.AccessoryDAO;
import it.uniba.carloan.dao.exception.DuplicateEntryException;
import it.uniba.carloan.dao.exception.IntegrityConstraintViolationException;
import it.uniba.carloan.dao.exception.PersistenceException;
import it.uniba.carloan.dao.exception.UnknownDatabaseTypeException;
import it.uniba.carloan.dao.mysql.MySQLAccessoryDAO;
import it.uniba.carloan.entity.Accessory;

import java.util.List;

public class AccessoryBO {
    private AccessoryDAO dao;

    /**
     * Instantiates a new Accessory Business Object. //TODO add description
     *
     * @throws UnknownDatabaseTypeException
     * @see DAOFactory
     */
    @SuppressWarnings("LawOfDemeter")
    public AccessoryBO() throws PersistenceException {
        this.dao = DAOFactory.getDAOFactory().getAccessoryDAO();
    }

    /**
     * Sends request to DAO in order to get all accessories.
     *
     * @return <code>TransferObject</code> with accessories list
     * @see MySQLAccessoryDAO#findAll()
     */
    public List<Accessory> getAllAccessories() throws PersistenceException {
        return dao.findAll();
    }

    /**
     * Sends request to DAO in order to add a new accessory.
     *
     * @param accessory an accessory to be added
     * @return <code>TransferObject</code> with operation result
     * @throws DuplicateEntryException if accessory inventory code isn't unique
     */
    public Integer addAccessory(Accessory accessory) throws DuplicateEntryException, PersistenceException {
        return dao.add(accessory);
    }

    /**
     * Sends request to DAO in order to update accessory description.
     *
     * @param accessory an accessory to be updated
     * @return <code>TransferObject</code> with operation result
     * @throws DuplicateEntryException if accessory inventory code isn't unique
     */
    public boolean updateAccessory(Accessory accessory) throws DuplicateEntryException, PersistenceException {
         return dao.update(accessory);
    }

    /**
     * Sends request to DAO in order to delete an accessory.
     *
     * @param accessory an accessory to be deleted
     * @return <code>TransferObject</code> with operation result
     * @throws IntegrityConstraintViolationException if accessory is used by another record
     */
    public boolean deleteAccessory(Accessory accessory) throws IntegrityConstraintViolationException, PersistenceException {
        return dao.delete(accessory);
    }

    public boolean updateAccessoryAvailability(Accessory accessory) throws PersistenceException {
        return dao.updateAvailability(accessory);
    }
}
