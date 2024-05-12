package DBAccess;

import helper.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** Gathers data within Customers Table in SQL Database
 *
 * @author Trenton Hallman
 */
public class DBCustomers {

    /** Returns list of all Customers
     *
     * @return CustomerList
     */
    public static ObservableList<Customer> getAllCustomers(){
        ObservableList<Customer> CustomerList = FXCollections.observableArrayList();
        try{
            String sql = "SELECT customers.Customer_ID, customers.Customer_Name, customers.Address, customers.Postal_Code, customers.Phone, customers.Division_ID, first_level_divisions.Division from customers INNER JOIN  first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int CustomerID = rs.getInt("Customer_ID");
                String CustomerName = rs.getString("Customer_Name");
                String CustomerAddress = rs.getString("Address");
                String CustomerPostal = rs.getString("Postal_Code");
                String CustomerPhone = rs.getString("Phone");
                int DivisionID = rs.getInt("Division_ID");
                String DivisionName = rs.getString("Division");
                Customer c  = new Customer(CustomerID, CustomerName,CustomerAddress,CustomerPostal, CustomerPhone, DivisionID, DivisionName);
                CustomerList.add(c);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return CustomerList;
    }

    /** Returns specific Division ID given a Division Name
     *
     * @return DivisionID
     * @param Division
     */
    public static int getCustomerDivisionID(String Division) throws SQLException {
        int DivisionID = 0;
        PreparedStatement sql = DBConnection.getConnection().prepareStatement("SELECT Division, Division_ID FROM first_level_divisions WHERE Division = ?");

        sql.setString(1, Division);

        ResultSet result = sql.executeQuery();

        while ( result.next() ) {
            DivisionID = result.getInt("Division_ID");
        }
        sql.close();
        return DivisionID;
    }

    /** Returns specific Country ID given a Division ID
     *
     * @return CountryID
     * @param DivisionID
     */
    public static int getCustomerCountryID(int DivisionID){
        int CountryID = 0;
        try {
            PreparedStatement sql = DBConnection.getConnection().prepareStatement("SELECT Country_ID FROM first_level_divisions WHERE Division_ID = ?");

            sql.setString(1, String.valueOf(DivisionID));

            ResultSet result = sql.executeQuery();

            while (result.next()) {
                CountryID = result.getInt("Country_ID");
            }
            sql.close();

        } catch(SQLException throwables){
            throwables.printStackTrace();
        }
        return CountryID;
    }

    /** Returns list of all Customer Names
     *
     * @return CustomerNameList
     */
    public static ObservableList<String> getAllCustomerNames(){
        ObservableList<String> CustomerNameList = FXCollections.observableArrayList();
        try{
            String sql = "SELECT Customer_Name FROM customers";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                String CustomerName = rs.getString("Customer_Name");

                CustomerNameList.add(CustomerName);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return CustomerNameList;
    }

    /** Returns specific Customer ID given a Customer Name
     *
     * @return CustomerID
     * @param CustomerName
     */
    public static int getCustomerID(String CustomerName){
        int CustomerID = 0;
        try {
            PreparedStatement sql = DBConnection.getConnection().prepareStatement("SELECT Customer_ID FROM customers WHERE Customer_Name = ?");

            sql.setString(1, String.valueOf(CustomerName));

            ResultSet result = sql.executeQuery();

            while (result.next()) {
                CustomerID = result.getInt("Customer_ID");
            }
            sql.close();

        } catch(SQLException throwables){
            throwables.printStackTrace();
        }
        return CustomerID;
    }
}
