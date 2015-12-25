package it.uniba.carloan.dao.mysql;


import it.uniba.carloan.dao.entity.AccessoryDAO;
import it.uniba.carloan.dao.entity.GenericDAO;
import it.uniba.carloan.dao.exception.PersistenceException;
import it.uniba.carloan.entity.Accessory;
import it.uniba.carloan.entity.Agency;
import it.uniba.carloan.entity.Category;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySQLAccessoryDAO extends MySQLAbstractDAO<Accessory, Integer> implements AccessoryDAO {
    @Override
    protected String getInsertQuery() {
        return "INSERT INTO accessories (categoryID, title, description, inventoryCode, currentAgencyID) "
                + " VALUES (?, ?, ?, ?, ?)";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE accessories SET categoryID=?, title=?, description=?, inventoryCode=? WHERE accessoryID=?";
    }

    @Override
    protected String getDeleteQuery() {
        return "DELETE FROM accessories WHERE accessoryID=?";
    }

    @Override
    protected String getFindAllQuery() {
        return "SELECT * FROM accessories, accessory_categories, agencies WHERE accessories.categoryID = " +
                "accessory_categories.categoryID AND accessories.currentAgencyID = agencies.agencyID" +
                " AND available = TRUE";
    }

    @Override
    protected String getFindByPKQuery() {
        return "SELECT * FROM accessories, accessory_categories, agencies WHERE accessories.categoryID = " +
                "accessory_categories.categoryID AND accessories.currentAgencyID = agencies.agencyID" +
                " AND accessoryID=?";
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Accessory object) throws PersistenceException {
        try {
            statement.setInt(1, object.getCategoryID());
            statement.setString(2, object.getTitle());
            statement.setString(3, object.getDescription());
            statement.setString(4, object.getInventoryCode());
            statement.setInt(5, object.getCurrentAgencyID());
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Accessory object) throws PersistenceException {
        try {
            statement.setInt(1, object.getCategoryID());
            statement.setString(2, object.getTitle());
            statement.setString(3, object.getDescription());
            statement.setString(4, object.getInventoryCode());
            statement.setInt(5, object.getId());
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    protected List<Accessory> parseResultSet(ResultSet rs) throws PersistenceException {
        List<Accessory> result = new ArrayList<>();
        if (rs != null) {
            Category category;
            Agency agency;
            Accessory accessory;

            try {
                while (rs.next()) {
                    category = new Category.Builder(rs.getString("categoryName"))
                            .description(rs.getString("categoryDescription"))
                            .id(rs.getInt("categoryID")).build();

                    agency = new Agency.Builder()
                            .id(rs.getInt("agencyID"))
                            .agencyCode(rs.getString("agencyCode"))
                            .city(rs.getString("city"))
                            .address(rs.getString("address"))
                            .phoneNumber(rs.getString("tel"))
                            .faxNumber(rs.getString("fax"))
                            .email(rs.getString("email")).build();

                    accessory = new Accessory.Builder()
                            .id(rs.getInt("accessoryID"))
                            .title(rs.getString("title"))
                            .description(rs.getString("description"))
                            .inventoryCode(rs.getString("inventoryCode"))
                            .category(category)
                            .currentAgency(agency).build();

                    result.add(accessory);
                }
            } catch (SQLException e) {
                throw new PersistenceException(e);
            }
        }

        return result;
    }

    @Override
    public boolean updateAvailability(Accessory object) throws PersistenceException {
        String query = "UPDATE accessories SET available=? WHERE accessoryID=?";
        return updateGenericField(query, object.isAvailable(), object.getId());
    }
}
