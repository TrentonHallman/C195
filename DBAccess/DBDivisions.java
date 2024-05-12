package DBAccess;

import helper.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Division;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** Gathers data within Divisions Table in SQL Database
 *
 * @author Trenton Hallman
 */
public class DBDivisions{

    /** Returns list of all Divisions
     *
     * @return DivisionList
     */
    public static ObservableList<Division> getAllDivisions(){
        ObservableList<Division> DivisionList = FXCollections.observableArrayList();
        try{
            String sql = "SELECT * FROM first_level_divisions";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int DivisionID = rs.getInt("Division_ID");
                String Division = rs.getString("Division");
                int CountryID = rs.getInt("Country_ID");
                Division d  = new Division(DivisionID, Division, CountryID);
                DivisionList.add(d);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return DivisionList;
    }

    /** Returns list of all US Division Names
     *
     * @return USDivisionNameList
     */
    public static ObservableList<String> getUSDivisionNames(){
        ObservableList<String> USDivisionNameList = FXCollections.observableArrayList();
        try{
            String sql = "SELECT Division FROM first_level_divisions WHERE Country_ID='1'";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                String USDivisionName = rs.getString("Division");
                USDivisionNameList.add(USDivisionName);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return USDivisionNameList;
    }

    /** Returns list of all UK Division Names
     *
     * @return UKDivisionNameList
     */
    public static ObservableList<String> getUKDivisionNames(){
        ObservableList<String> UKDivisionNameList = FXCollections.observableArrayList();
        try{
            String sql = "SELECT Division FROM first_level_divisions WHERE Country_ID='2'";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                String UKDivisionName = rs.getString("Division");
                UKDivisionNameList.add(UKDivisionName);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return UKDivisionNameList;
    }

    /** Returns list of all Canadian Division Names
     *
     * @return CanadaDivisionNameList
     */
    public static ObservableList<String> getCanadaDivisionNames(){
        ObservableList<String> CanadaDivisionNameList = FXCollections.observableArrayList();
        try{
            String sql = "SELECT Division FROM first_level_divisions WHERE Country_ID='3'";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                String CanadaDivisionName = rs.getString("Division");
                CanadaDivisionNameList.add(CanadaDivisionName);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return CanadaDivisionNameList;
    }

    /** Returns specific Division Name given a Division ID
     *
     * @return DivisionName
     * @param DivisionID
     */
    public static String getSpecificDivisionName(int DivisionID) throws SQLException{
        String DivisionName = null;
        PreparedStatement sql = DBConnection.getConnection().prepareStatement("SELECT Division, Division_ID FROM first_level_divisions WHERE Division_ID = ?");

        sql.setString(1, String.valueOf(DivisionID));

        ResultSet result = sql.executeQuery();

        while ( result.next() ) {
            DivisionName = result.getString("Division");
        }
        return DivisionName;
    }

    /** Returns list of all Divisions given a Country ID
     *
     * @return DivisionList
     * @param countryID
     */
    public static ObservableList<String> getDivisions(int countryID) throws SQLException {
        ObservableList<String> DivisionList = FXCollections.observableArrayList();
        String sql = "SELECT Division FROM first_level_divisions WHERE Country_ID = ?";

        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setInt(1, countryID);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            String Division = rs.getString("Division");
            DivisionList.add(Division);
        }
        return DivisionList;
    }
}
