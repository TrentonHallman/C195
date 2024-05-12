package DBAccess;

import helper.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** Gathers data within Countries Table in SQL Database
 *
 * @author Trenton Hallman
 */
public class DBCountries {

    /** Returns list of all Countries
     *
     * @return CountryList
     */

    public static ObservableList<Country> getAllCountries(){
        ObservableList<Country> CountryList = FXCollections.observableArrayList();
        try{
            String sql = "SELECT * FROM countries";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int CountryID = rs.getInt("Country_ID");
                String CountryName = rs.getString("Country");
                Country c  = new Country(CountryID, CountryName);
                CountryList.add(c);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return CountryList;
    }

    /** Returns list of all Country Names
     *
     * @return CountryNameList
     */
    public static ObservableList<String> getCountryNames(){
        ObservableList<String> CountryNameList = FXCollections.observableArrayList();
        try{
            String sql = "SELECT Country FROM countries";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                String CountryName = rs.getString("Country");
                CountryNameList.add(CountryName);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return CountryNameList;
    }

    /** Returns a specific Country Name given a Country ID
     *
     * @return CountryName
     * @param CountryID
     * @throws SQLException
     */
    public static String getCountryName(int CountryID) throws SQLException {
        String CountryName = null;
        PreparedStatement sql = DBConnection.getConnection().prepareStatement("SELECT Country FROM countries WHERE Country_ID = ?");

        sql.setString(1, String.valueOf(CountryID));

        ResultSet result = sql.executeQuery();

        while ( result.next() ) {
            CountryName = result.getString("Country");
        }
        sql.close();
        return CountryName;
    }

    /** Returns a specific Country ID given a Country Name
     *
     * @return CountryID
     * @param Country
     * @throws SQLException
     */
    public static int getSpecificCountryID(String Country) throws SQLException {
        int CountryID = 0;
        PreparedStatement sql = DBConnection.getConnection().prepareStatement("SELECT Country_ID FROM countries WHERE Country = ?");

        sql.setString(1, Country);

        ResultSet result = sql.executeQuery();

        while ( result.next() ) {
            CountryID = result.getInt("Country_ID");
        }
        sql.close();
        return CountryID;
    }
}
