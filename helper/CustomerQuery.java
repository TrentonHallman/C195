package helper;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/** Gathers data within Customer Table in SQL Database
 *
 * @author Trenton Hallman
 */
public class CustomerQuery {

    /** Inserts Customer Data into SQL Database
     *
     * @return RowsAffected
     * @param CustomerID
     * @param CustomerName
     * @param CustomerAddress
     * @param CustomerPostal
     * @param CustomerPhone
     * @param DivisionID
     * @throws SQLException
     */
    public static int insert(int CustomerID, String CustomerName, String CustomerAddress, String CustomerPostal, String CustomerPhone, int DivisionID) throws SQLException {
        String sql = "INSERT INTO customers (Customer_ID, Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) VALUES (?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(1,CustomerID);
        ps.setString(2,CustomerName);
        ps.setString(3,CustomerAddress);
        ps.setString(4,CustomerPostal);
        ps.setString(5,CustomerPhone);
        ps.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
        ps.setString(7, "admin");
        ps.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));
        ps.setString(9, "admin");
        ps.setInt(10,DivisionID);
        int RowsAffected = ps.executeUpdate();
        return RowsAffected;
    }

    /** Updates Customer Data in SQL Database
     *
     * @return RowsAffected
     * @param CustomerID
     * @param CustomerName
     * @param CustomerAddress
     * @param CustomerPostal
     * @param CustomerPhone
     * @param DivisionID
     * @throws SQLException
     */
    public static int update(int CustomerID, String CustomerName, String CustomerAddress, String CustomerPostal, String CustomerPhone, int DivisionID) throws SQLException{
        String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Last_Update = ?, Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setString(8, String.valueOf(CustomerID));
        ps.setString(1,CustomerName);
        ps.setString(2,CustomerAddress);
        ps.setString(3,CustomerPostal);
        ps.setString(4,CustomerPhone);
        ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
        ps.setString(6, "admin");
        ps.setInt(7, DivisionID);
        int RowsAffected = ps.executeUpdate();
        return RowsAffected;
    }

    /** Deletes Customer Data from SQL Database
     *
     * @return RowsAffected
     * @param CustomerID
     * @throws SQLException
     */
    public static int delete(int CustomerID) throws SQLException{
        String sql = "DELETE FROM customers WHERE Customer_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(1,CustomerID);
        int RowsAffected = ps.executeUpdate();
        return RowsAffected;
    }
}
