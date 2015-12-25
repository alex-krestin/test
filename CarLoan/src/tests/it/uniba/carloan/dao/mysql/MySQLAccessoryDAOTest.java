package it.uniba.carloan.dao.mysql;

import it.uniba.carloan.dao.exception.DuplicateEntryException;
import it.uniba.carloan.dao.exception.RecordNotFoundException;
import it.uniba.carloan.dao.mysql.tester.MySQLDAOTester;
import it.uniba.carloan.entity.Accessory;
import it.uniba.carloan.entity.Agency;
import it.uniba.carloan.entity.Car;
import it.uniba.carloan.entity.Category;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;


public class MySQLAccessoryDAOTest extends MySQLDAOTester {
    private MySQLAccessoryDAO dao;
    private Accessory newObject;
    private Agency agency;

    @Before
    public void setUp() throws Exception {
        initConnection();
        dao = new MySQLAccessoryDAO();

        MySQLAgencyDAO agencyDAO = new MySQLAgencyDAO();
        agency = agencyDAO.findAll().iterator().next();

        MySQLAccessoryCategoryDAO accessoryCategoryDAO = new MySQLAccessoryCategoryDAO();
        Category category = accessoryCategoryDAO.findAll().iterator().next();

        newObject = new Accessory.Builder()
                .title("Accessory X")
                .description("New accessory")
                .inventoryCode("A0001A")
                .currentAgency(agency)
                .category(category)
                .build();
    }

    @Test
    public void testAdd() throws Exception {
        // Insert record in database
        Integer insertedRecordID = dao.add(newObject);
        assertNotNull(insertedRecordID);

        // Get inserted record
        Accessory insertedRecord = dao.findByPK(insertedRecordID);
        assertEquals(newObject, insertedRecord);

        // Try to add again
        exception.expect(DuplicateEntryException.class);
        dao.add(newObject);
    }

    @Test
    public void testUpdate() throws Exception {
        // Get an object from database
        Accessory originalObject = dao.findAll().iterator().next();
        assertNotNull(originalObject); // dependency

        Accessory updatedObject = new Accessory.Builder()
                .id(originalObject.getId())
                .title("TEST")
                .description("TEST")
                .inventoryCode("T0000T")
                .currentAgency(originalObject.getCurrentAgency())
                .category(originalObject.getCategory())
                .build();

        // Update object in database
        assertTrue(dao.update(updatedObject));

        // Control if changes are applied
        Accessory testObject = dao.findByPK(originalObject.getId());
        assertEquals(updatedObject, testObject);
    }

    @Test
    public void testDelete() throws Exception {
        // As we have IntegrityConstraints, let's add a new object first
        Integer insertedObjectID = dao.add(newObject);
        assertNotNull(insertedObjectID); // dependency

        // Get it back with id
        Accessory insertedObject = dao.findByPK(insertedObjectID);

        // Try to delete it
        assertTrue(dao.delete(insertedObject));

        // Control if it was really removed from database
        exception.expect(RecordNotFoundException.class);
        dao.findByPK(insertedObjectID);
    }

    @Test
    public void testFindAll() throws Exception {
        List<Accessory> list = dao.findAll();
        assertTrue(list.size() > 0);
    }

    @Test
    public void testUpdateAvailability() throws Exception {
        // Get an object from database
        Accessory object = dao.findAll().iterator().next();
        assertNotNull(object); // dependency

        // Change object's state and update it in database
        Accessory updatedObject = object;
        updatedObject.setAvailable(!object.isAvailable());
        assertTrue(dao.updateAvailability(object));

        // Control if it was really updated
        object = dao.findByPK(object.getId());
        assertEquals(updatedObject, object);
    }
}