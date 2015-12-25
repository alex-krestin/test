package it.uniba.carloan.dao.mysql.tester;


import it.uniba.carloan.dao.entity.GenericDAO;
import it.uniba.carloan.dao.exception.DuplicateEntryException;
import it.uniba.carloan.entity.Category;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

@Ignore
public class MySQLCategoryDAOTester<T extends GenericDAO<Category, Integer>> extends MySQLDAOTester {
    protected T dao;

    /**
     * Method: add(Category object)
     * Depends on: getAll() method
     */
    @Test
    public void testAdd() throws Exception {
        Category category = new Category.Builder("testCategory")
                .description("testDescription").build();

        // Insert record in database
        Integer insertedRecordID = dao.add(category);
        assertNotNull(insertedRecordID);

        // Get all records and search for inserted object
        Category insertedRecord = dao.findByPK(insertedRecordID);
        assertEquals(category, insertedRecord);

        // Try to repeat inserting of the same object
        exception.expect(DuplicateEntryException.class);
        dao.add(category);
    }

    /**
     * Method: update(Category object)
     * Depends on: getAll() method
     */
    @Test
    public void testUpdate() throws Exception {

        // get object from database
        Category originalObject = dao.findAll().iterator().next();

        Category updatedObject = new Category.Builder("updated")
                .description("updatedItem")
                .id(originalObject.getId()).build();

        assertTrue(dao.update(updatedObject));

        // Get all records and search for updated object
        List<Category> list = dao.findAll();
        boolean inList = list.contains(updatedObject);
        assertTrue(inList);

        // Control that original object isn't in the list
        inList = list.contains(originalObject);
        assertFalse(inList);
    }

    /**
     * Method: delete(Category object)
     * Depends on: add(Category object), getAll() methods
     */
    @Test
    public void testDelete() throws Exception {
        Category object = new Category.Builder("ToBeDeleted").build();

        // Insert new record in database
        assertNotNull(dao.add(object)); // dependency

        // Get all records and find inserted one
        List<Category> list = dao.findAll();

        Category tbd = null;

        for (Category aList : list) {
            if (aList.equals(object)) {
                tbd = aList;
                break;
            }
        }

        assertNotNull(tbd); // dependency

        assertTrue(dao.delete(tbd));

        // Control that object is deleted
        list = dao.findAll();
        boolean inList = list.contains(tbd);
        assertFalse(inList);
    }

    /**
     * Method: findAll()
     */
    @Test
    public void testFindAll() throws Exception {
        List<Category> list = dao.findAll();

        assertNotNull(list);
        assertTrue(list.size() > 0);
    }


}
