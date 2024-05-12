package helper;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

/** Alters data within Appointments Table in SQL Database
 *
 * @author Trenton Hallman
 */
public class AppointmentQuery {

    /** Inserts Appointment Data into SQL Database
     *
     * @return RowsAffected
     * @param AppointmentID
     * @param AppointmentTitle
     * @param AppointmentDescription
     * @param AppointmentLocation
     * @param AppointmentType
     * @param AppointmentStart
     * @param AppointmentEnd
     * @param CustomerID
     * @param ContactID
     * @param UserID
     * @throws SQLException
     */
    public static int insert(int AppointmentID, String AppointmentTitle, String AppointmentDescription, String AppointmentLocation, String AppointmentType, LocalDateTime AppointmentStart, LocalDateTime AppointmentEnd, int CustomerID, int UserID, int ContactID) throws SQLException {
        String sql = "INSERT INTO appointments (Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(1,AppointmentID);
        ps.setString(2,AppointmentTitle);
        ps.setString(3,AppointmentDescription);
        ps.setString(4,AppointmentLocation);
        ps.setString(5,AppointmentType);
        ps.setTimestamp(6, Timestamp.valueOf(AppointmentStart));
        ps.setTimestamp(7, Timestamp.valueOf(AppointmentEnd));
        ps.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));
        ps.setString(9, "admin");
        ps.setTimestamp(10, Timestamp.valueOf(LocalDateTime.now()));
        ps.setString(11, "admin");
        ps.setInt(12,CustomerID);
        ps.setInt(13,UserID);
        ps.setInt(14,ContactID);
        int RowsAffected = ps.executeUpdate();
        return RowsAffected;
    }

    /** Updates Appointment Data in SQL Database
     *
     * @return RowsAffected
     * @param AppointmentID
     * @param AppointmentTitle
     * @param AppointmentDescription
     * @param AppointmentLocation
     * @param AppointmentType
     * @param AppointmentStart
     * @param AppointmentEnd
     * @param CustomerID
     * @param ContactID
     * @param UserID
     * @throws SQLException
     */
    public static int update(int AppointmentID, String AppointmentTitle, String AppointmentDescription, String AppointmentLocation, String AppointmentType, LocalDateTime AppointmentStart, LocalDateTime AppointmentEnd, int CustomerID, int UserID, int ContactID) throws SQLException{
        String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(12,AppointmentID);
        ps.setString(1,AppointmentTitle);
        ps.setString(2,AppointmentDescription);
        ps.setString(3,AppointmentLocation);
        ps.setString(4,AppointmentType);
        ps.setTimestamp(5, Timestamp.valueOf(AppointmentStart));
        ps.setTimestamp(6, Timestamp.valueOf(AppointmentEnd));
        ps.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
        ps.setString(8, "admin");
        ps.setInt(9,CustomerID);
        ps.setInt(10,UserID);
        ps.setInt(11,ContactID);
        int RowsAffected = ps.executeUpdate();
        return RowsAffected;
    }

    /** Deletes Appointment Data from SQL Database
     *
     * @return RowsAffected
     * @param AppointmentID
     * @throws SQLException
     */
    public static int delete(int AppointmentID) throws SQLException{
        String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(1,AppointmentID);
        int RowsAffected = ps.executeUpdate();
        return RowsAffected;
    }
}
