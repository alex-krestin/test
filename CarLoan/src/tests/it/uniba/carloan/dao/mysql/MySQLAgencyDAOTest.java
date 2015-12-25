package it.uniba.carloan.dao.mysql;

import it.uniba.carloan.dao.exception.DuplicateEntryException;
import it.uniba.carloan.dao.exception.RecordNotFoundException;
import it.uniba.carloan.dao.mysql.tester.MySQLDAOTester;
import it.uniba.carloan.entity.Agency;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;


public class MySQLAgencyDAOTest extends MySQLDAOTester {
    private MySQLAgencyDAO dao;
    private Agency newObject;

    @Before
    public void setUp() throws Exception {
        initConnection();
        dao = new MySQLAgencyDAO();

        newObject = new Agency.Builder()
                .agencyCode("FL001")
                .city("Florenza")
                .address("Via Garibaldi, 1")
                .phoneNumber("800123000")
                .faxNumber("800123001")
                .email("fl001@carloan.it").build();
    }

    @Test
    public void testAdd() throws Exception {

        // Insert record in database
        Integer insertedRecordID = dao.add(newObject);
        assertNotNull(insertedRecordID);

        // Get inserted record
        Agency insertedRecord = dao.findByPK(insertedRecordID);
        assertEquals(newObject, insertedRecord);

        // Try to add again
        exception.expect(DuplicateEntryException.class);
        dao.add(newObject);
    }

    @Test
    public void testUpdate() throws Exception {
        // Get an object from database
        Agency originalObject = dao.findAll().iterator().next();
        assertNotNull(originalObject); // dependency

        Agency updatedObject = new Agency.Builder()
                .id(originalObject.getId())
                .agencyCode("TEST")
                .city("TEST")
                .address("TEST")
                .phoneNumber("TEST").build();

        // Update object in database
        assertTrue(dao.update(updatedObject));

        // Control if changes are applied
        Agency testObject = dao.findByPK(originalObject.getId());
        assertEquals(updatedObject, testObject);
    }

    @Test
    public void testDelete() throws Exception {
        // As we have IntegrityConstraints, let's add a new object first
        Integer insertedObjectID = dao.add(newObject);
        assertNotNull(insertedObjectID); // dependency

        // Get it back with id
        Agency insertedObject = dao.findByPK(insertedObjectID);

        // Try to delete it
        assertTrue(dao.delete(insertedObject));

        // Control if it was really removed from database
        exception.expect(RecordNotFoundException.class);
        dao.findByPK(insertedObjectID);
    }

    @Test
    public void testFindAll() throws Exception {
        List<Agency> list = dao.findAll();
        assertTrue(list.size() > 0);
    }

    @Test
    public void testUpdateState() throws Exception {
        // Get an object from database
        Agency object = dao.findAll().iterator().next();
        assertNotNull(object); // dependency

        // Change object's state and update it in database
        Agency updatedObject = object;
        updatedObject.setActive(!object.isActive());
        assertTrue(dao.updateState(object));

        // Control if it was really updated
        object = dao.findByPK(object.getId());
        assertEquals(updatedObject, object);
    }
}