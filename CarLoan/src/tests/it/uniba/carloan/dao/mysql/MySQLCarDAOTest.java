package it.uniba.carloan.dao.mysql;

import it.uniba.carloan.dao.exception.DuplicateEntryException;
import it.uniba.carloan.dao.exception.RecordNotFoundException;
import it.uniba.carloan.dao.mysql.tester.MySQLDAOTester;
import it.uniba.carloan.entity.Agency;
import it.uniba.carloan.entity.Car;
import it.uniba.carloan.entity.Category;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;


public class MySQLCarDAOTest extends MySQLDAOTester {

    private MySQLCarDAO dao;
    private Car newObject;
    private Agency agency;

    @Before
    public void setUp() throws Exception {
        initConnection();
        dao = new MySQLCarDAO();

        MySQLAgencyDAO agencyDAO = new MySQLAgencyDAO();
        agency = agencyDAO.findAll().iterator().next();

        MySQLCarCategoryDAO carCategoryDAO = new MySQLCarCategoryDAO();
        Category category = carCategoryDAO.findAll().iterator().next();

        newObject = new Car.Builder()
                .brand("BMW")
                .model("x5")
                .year(2015)
                .plate("TT000TT")
                .category(category)
                .doors(5)
                .passengers(5)
                .description("New car")
                .mileage(30000)
                .currentAgency(agency).build();
    }



    @Test
    public void testAdd() throws Exception {
        // Insert record in database
        Integer insertedRecordID = dao.add(newObject);
        assertNotNull(insertedRecordID);

        // Get inserted record
        Car insertedRecord = dao.findByPK(insertedRecordID);
        assertEquals(newObject, insertedRecord);

        // Try to add again
        exception.expect(DuplicateEntryException.class);
        dao.add(newObject);
    }

    @Test
    public void testUpdate() throws Exception {
        // Get an object from database
        Car originalObject = dao.findAll().iterator().next();
        assertNotNull(originalObject); // dependency

        Car updatedObject = new Car.Builder()
                .carID(originalObject.getId())
                .brand("TEST")
                .model("TEST")
                .year(2015)
                .plate("TEST")
                .category(originalObject.getCategory())
                .doors(5)
                .passengers(5)
                .description("TEST")
                .currentAgency(originalObject.getCurrentAgency()).build();

        // Update object in database
        assertTrue(dao.update(updatedObject));

        // Control if changes are applied
        Car testObject = dao.findByPK(originalObject.getId());
        assertEquals(updatedObject, testObject);
    }

    @Test
    public void testDelete() throws Exception {
        // As we have IntegrityConstraints, let's add a new object first
        Integer insertedObjectID = dao.add(newObject);
        assertNotNull(insertedObjectID); // dependency

        // Get it back with id
        Car insertedObject = dao.findByPK(insertedObjectID);

        // Try to delete it
        assertTrue(dao.delete(insertedObject));

        // Control if it was really removed from database
        exception.expect(RecordNotFoundException.class);
        dao.findByPK(insertedObjectID);
    }

    @Test
    public void testFindAll() throws Exception {
        List<Car> list = dao.findAll();
        assertTrue(list.size() > 0);
    }

    @Test
    public void testFindByAgency() throws Exception {
        List<Car> list = dao.findByAgency(agency);
        assertTrue(list.size() > 0);
    }

    @Test
    public void testUpdateAvailability() throws Exception {
        // Get an object from database
        Car object = dao.findAll().iterator().next();
        assertNotNull(object); // dependency

        // Change object's state and update it in database
        Car updatedObject = object;
        updatedObject.setAvailable(!object.isAvailable());
        assertTrue(dao.updateAvailability(object));

        // Control if it was really updated
        object = dao.findByPK(object.getId());
        assertEquals(updatedObject, object);
    }
}