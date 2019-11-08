package Managers;

import Utility.Database.DbConnect;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Connects to the Database and facilitates adding content to Database
 */
public class DatabaseManager {

    private static DbConnect db;

    private static final DatabaseManager DATABASE_MANAGER = new DatabaseManager();

    private DatabaseManager() {
        db = new DbConnect();
    }

    public static DatabaseManager getDatabaseManager() {
        return DATABASE_MANAGER;
    }

    /**
     * Queries the database and returns an array containing the data.
     * ONLY WORKS properly FOR QUERYING FOR A UNIQUE THING! i.e. only 1 row.
     *
     * @param query The SQL query you want to use to find data
     * @return String array containing data from SQL query
     */
    public List<String> getData(String query) {

        List<String> data = new ArrayList<>();

        try {

            db.rs = db.st.executeQuery(query);

            ResultSetMetaData rsmd = db.rs.getMetaData();

            while (db.rs.next()) {

                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    data.add(db.rs.getString(i));
                }
            }

        } catch (Exception ex) {
            System.out.println("There has been an error: " + ex);
        }

        return data;
    }

    /**
     * Returns data from SQL query that has multiple rows.
     * @param query The SQL query you want to use to find data
     * @return List of List of Strings containing query data (A List for each row; so a list of rows)
     */
    public List<List<String>> getMultiData(String query) {

        List<List<String>> data = new ArrayList<>();

        try {

            db.rs = db.st.executeQuery(query);

            ResultSetMetaData rsmd = db.rs.getMetaData();

            while (db.rs.next()) {

                List<String> component = new ArrayList<>();
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    component.add(db.rs.getString(i));
                }
                data.add(component);
            }

        } catch (Exception ex) {
            System.out.println("There has been an error: " + ex);
        }

        return data;
    }

    /**
     * Do and SQL query to add data to database. Be careful when using this.
     *
     * @param query The SQL query you want to use to find data
     */
    public void inputData(String query) {

        try {
            db.st.executeUpdate(query);

        } catch (Exception ex) {

            System.out.println("There has been an error: " + ex);
        }
    }
}
