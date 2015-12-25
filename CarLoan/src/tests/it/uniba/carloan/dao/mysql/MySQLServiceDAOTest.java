package it.uniba.carloan.dao.mysql;

import it.uniba.carloan.dao.exception.DuplicateEntryException;
import it.uniba.carloan.dao.exception.RecordNotFoundException;
import it.uniba.carloan.dao.mysql.tester.MySQLDAOTester;
import it.uniba.carloan.entity.Agency;
import it.uniba.carloan.entity.Service;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;


public class MySQLServiceDAOTest extends MySQLDAOTester {
    private MySQLServiceDAO dao;
    private Service newObject;

    @Before
    public void setUp() throws Exception {
        initConnection();
        dao = new MySQLServiceDAO();

        newObject = new Service.Builder()
                .title("Service X")
                .description("Test service").build();
    }


    @Test
    public void testAdd() throws Exception {
        // Insert record in database
        Integer insertedRecordID = dao.add(newObject);
        assertNotNull(insertedRecordID);

        // Get inserted record
        Service insertedRecord = dao.findByPK(insertedRecordID);
        assertEquals(newObject, insertedRecord);

        // Try to add again
        exception.expect(DuplicateEntryException.class);
        dao.add(newObject);
    }

    @Test
    public void testUpdate() throws Exception {
        // Get an object from database
        Service originalObject = dao.findAll().iterator().next();
        assertNotNull(originalObject); // dependency

        Service updatedObject = new Service.Builder()
                .id(originalObject.getId())
                .title("TEST")
                .description("TEST").build();

        // Update object in database
        assertTrue(dao.update(updatedObject));

        // Control if changes are applied
        Service testObject = dao.findByPK(originalObject.getId());
        assertEquals(updatedObject, testObject);
    }

    @Test
    public void testDelete() throws Exception {
        // As we have IntegrityConstraints, let's add a new object first
        Integer insertedObjectID = dao.add(newObject);
        assertNotNull(insertedObjectID); // dependency

        // Get it back with id
        Service insertedObject = dao.findByPK(insertedObjectID);

        // Try to delete it
        assertTrue(dao.delete(insertedObject));

        // Control if it was really removed from database
        exception.expect(RecordNotFoundException.class);
        dao.findByPK(insertedObjectID);
    }

    @Test
    public void testFindAll() throws Exception {
        List<Service> list = dao.findAll();
        assertTrue(list.size() > 0);
    }

    @Test
    public void testUpdateAvailability() throws Exception {
        // Get an object from database
        Service object = dao.findAll().iterator().next();
        assertNotNull(object); // dependency

        // Change object's state and update it in database
        Service updatedObject = object;
        updatedObject.setAvailable(!object.isAvailable());
        assertTrue(dao.updateAvailability(object));

        // Control if it was really updated
        object = dao.findByPK(object.getId());
        assertEquals(updatedObject, object);
    }
}