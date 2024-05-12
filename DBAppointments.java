package DBAccess;

import helper.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.MonthType;

import java.sql.*;
import java.time.*;
import java.util.TimeZone;

/** Gathers data within Appointments Table in SQL Database
 *
 * @author Trenton Hallman
 */
public class DBAppointments {

    /** Returns list of all Appointments
     *
     * @return AppointmentList
     */
    public static ObservableList<Appointment> getAllAppointments(){
        ObservableList<Appointment> AppointmentList = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * FROM appointments";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int AppointmentID = rs.getInt("Appointment_ID");
                String AppointmentTitle = rs.getString("Title");
                String AppointmentDescription = rs.getString("Description");
                String AppointmentLocation = rs.getString("Location");
                String AppointmentContact = getContactName(Integer.parseInt(rs.getString("Contact_ID")));
                String AppointmentType = rs.getString("Type");
                LocalDateTime AppointmentStart = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime AppointmentEnd = rs.getTimestamp("End").toLocalDateTime();
                int CustomerID = rs.getInt("Customer_ID");
                int UserID = rs.getInt("User_ID");
                int ContactID = rs.getInt("Contact_ID");

                Appointment a  = new Appointment(AppointmentID, AppointmentTitle,AppointmentDescription,AppointmentLocation, AppointmentContact, AppointmentType, AppointmentStart, AppointmentEnd, CustomerID, UserID,ContactID);
                AppointmentList.add(a);
            }
        } catch (
                SQLException throwables) {
            throwables.printStackTrace();
        }
        return AppointmentList;
    }

    /** Returns specific Contact Name given a Contact ID
     *
     * @return ContactName
     * @param ContactID
     * @throws SQLException
     */
    public static String getContactName(int ContactID) throws SQLException {
        String ContactName = null;
        PreparedStatement sql = DBConnection.getConnection().prepareStatement("SELECT Contact_Name FROM contacts WHERE Contact_ID = ?");

        sql.setString(1, String.valueOf(ContactID));

        ResultSet result = sql.executeQuery();

        while ( result.next() ) {
            ContactName = result.getString("Contact_Name");
        }
        sql.close();
        return ContactName;
    }

    /** Returns specific Contact ID given a Contact Name
     *
     * @return ContactID
     * @param ContactName
     * @throws SQLException
     */
    public static int getContactID(String ContactName) throws SQLException {
        int ContactID = 0;
        PreparedStatement sql = DBConnection.getConnection().prepareStatement("SELECT Contact_ID FROM contacts WHERE Contact_Name = ?");

        sql.setString(1, String.valueOf(ContactName));

        ResultSet result = sql.executeQuery();

        while ( result.next() ) {
            ContactID = result.getInt("Contact_ID");
        }
        sql.close();
        return ContactID;
    }

    /** Returns list of all Appointments within the next week of the user
     *
     * @return AppointmentsWithinWeek
     */
    public static ObservableList<Appointment> getAppointmentsWithinWeek(){
        ObservableList<Appointment> AppointmentsWithinWeek = FXCollections.observableArrayList();
        try{
            String sql = "SELECT * FROM appointments";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int AppointmentID = rs.getInt("Appointment_ID");
                String AppointmentTitle = rs.getString("Title");
                String AppointmentDescription = rs.getString("Description");
                String AppointmentLocation = rs.getString("Location");
                String AppointmentContact = getContactName(Integer.parseInt(rs.getString("Contact_ID")));
                String AppointmentType = rs.getString("Type");
                int CustomerID = rs.getInt("Customer_ID");
                int UserID = rs.getInt("User_ID");
                int ContactID = rs.getInt("Contact_ID");

                LocalDateTime AppointmentStart = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime AppointmentEnd = rs.getTimestamp("End").toLocalDateTime();

                ZoneId LocalZoneID = ZoneId.of(TimeZone.getDefault().getID());

                ZonedDateTime AppointmentStartZoned = ZonedDateTime.of(AppointmentStart,LocalZoneID);
                ZonedDateTime AppointmentEndZoned = ZonedDateTime.of(AppointmentEnd,LocalZoneID);

                ZonedDateTime WeekStart = LocalDateTime.now().minusWeeks(1).atZone(LocalZoneID);
                ZonedDateTime WeekEnd = LocalDateTime.now().plusWeeks(1).atZone(LocalZoneID);

                if(AppointmentStartZoned.isAfter(WeekStart) && AppointmentEndZoned.isBefore(WeekEnd)){
                    Appointment a  = new Appointment(AppointmentID, AppointmentTitle,AppointmentDescription,AppointmentLocation, AppointmentContact, AppointmentType, AppointmentStart, AppointmentEnd, CustomerID, UserID,ContactID);
                    AppointmentsWithinWeek.add(a);
                }
            }
        } catch (
                SQLException throwables) {
            throwables.printStackTrace();
        }
        return AppointmentsWithinWeek;
    }

    /** Returns list of all Appointments within the next month of the user
     *
     * @return AppointmentsWithinMonth
     */
    public static ObservableList<Appointment> getAppointmentsWithinMonth() {
        ObservableList<Appointment> AppointmentsWithinMonth = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM appointments";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int AppointmentID = rs.getInt("Appointment_ID");
                String AppointmentTitle = rs.getString("Title");
                String AppointmentDescription = rs.getString("Description");
                String AppointmentLocation = rs.getString("Location");
                String AppointmentContact = getContactName(Integer.parseInt(rs.getString("Contact_ID")));
                String AppointmentType = rs.getString("Type");
                int CustomerID = rs.getInt("Customer_ID");
                int UserID = rs.getInt("User_ID");
                int ContactID = rs.getInt("Contact_ID");

                LocalDateTime AppointmentStart = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime AppointmentEnd = rs.getTimestamp("End").toLocalDateTime();

                ZoneId LocalZoneID = ZoneId.of(TimeZone.getDefault().getID());

                ZonedDateTime MonthStart = LocalDateTime.now().minusMonths(1).atZone(LocalZoneID);
                ZonedDateTime MonthEnd = LocalDateTime.now().plusMonths(1).atZone(LocalZoneID);

                ZonedDateTime AppointmentStartZoned = ZonedDateTime.of(AppointmentStart, LocalZoneID);
                ZonedDateTime AppointmentEndZoned = ZonedDateTime.of(AppointmentEnd, LocalZoneID);

                if (AppointmentStartZoned.isAfter(MonthStart) && AppointmentEndZoned.isBefore(MonthEnd)) {
                    Appointment a = new Appointment(AppointmentID, AppointmentTitle, AppointmentDescription, AppointmentLocation, AppointmentContact, AppointmentType, AppointmentStart, AppointmentEnd, CustomerID, UserID, ContactID);
                    AppointmentsWithinMonth.add(a);
                }
            }
        } catch (
                SQLException throwables) {
            throwables.printStackTrace();
        }
        return AppointmentsWithinMonth;
    }

    /** Returns list of all Appointments given a specific Customer ID
     *
     * @return CustomerAppointments
     * @param SelectedCustomerID
     */
    public static ObservableList<Appointment> getCustomerAppointments(int SelectedCustomerID) {
        ObservableList<Appointment> CustomerAppointments = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM appointments WHERE Customer_ID = ?";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1,SelectedCustomerID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int AppointmentID = rs.getInt("Appointment_ID");
                String AppointmentTitle = rs.getString("Title");
                String AppointmentDescription = rs.getString("Description");
                String AppointmentLocation = rs.getString("Location");
                String AppointmentContact = getContactName(Integer.parseInt(rs.getString("Contact_ID")));
                String AppointmentType = rs.getString("Type");
                int CustomerID = rs.getInt("Customer_ID");
                int UserID = rs.getInt("User_ID");
                int ContactID = rs.getInt("Contact_ID");

                LocalDateTime AppointmentStart = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime AppointmentEnd = rs.getTimestamp("End").toLocalDateTime();

                Appointment a = new Appointment(AppointmentID, AppointmentTitle, AppointmentDescription, AppointmentLocation, AppointmentContact, AppointmentType, AppointmentStart, AppointmentEnd, CustomerID, UserID, ContactID);
                CustomerAppointments.add(a);
            }
        } catch (
                SQLException throwables) {
            throwables.printStackTrace();
        }
        return CustomerAppointments;
    }

    /** Returns list of all Appointments given a specific Contact ID
     *
     * @return ContactAppointments
     * @param SelectedContactID
     */
    public static ObservableList<Appointment> getAppointmentsByContactID(int SelectedContactID) {
        ObservableList<Appointment> ContactAppointments = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM appointments WHERE Contact_ID = ?";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1,SelectedContactID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int AppointmentID = rs.getInt("Appointment_ID");
                String AppointmentTitle = rs.getString("Title");
                String AppointmentDescription = rs.getString("Description");
                String AppointmentLocation = rs.getString("Location");
                String AppointmentContact = getContactName(Integer.parseInt(rs.getString("Contact_ID")));
                String AppointmentType = rs.getString("Type");
                int CustomerID = rs.getInt("Customer_ID");
                int UserID = rs.getInt("User_ID");
                int ContactID = rs.getInt("Contact_ID");

                LocalDateTime AppointmentStart = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime AppointmentEnd = rs.getTimestamp("End").toLocalDateTime();

                Appointment a = new Appointment(AppointmentID, AppointmentTitle, AppointmentDescription, AppointmentLocation, AppointmentContact, AppointmentType, AppointmentStart, AppointmentEnd, CustomerID, UserID, ContactID);
                ContactAppointments.add(a);
            }
        } catch (
                SQLException throwables) {
            throwables.printStackTrace();
        }
        return ContactAppointments;
    }

    /** Returns list of all Appointments given a specific Customer ID
     *
     * @return CustomerAppointments
     * @param SelectedCustomerID
     */
    public static ObservableList<Appointment> getAppointmentsByCustomerID(int SelectedCustomerID) {
        ObservableList<Appointment> CustomerAppointments = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM appointments WHERE Customer_ID = ?";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1,SelectedCustomerID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int AppointmentID = rs.getInt("Appointment_ID");
                String AppointmentTitle = rs.getString("Title");
                String AppointmentDescription = rs.getString("Description");
                String AppointmentLocation = rs.getString("Location");
                String AppointmentContact = getContactName(Integer.parseInt(rs.getString("Contact_ID")));
                String AppointmentType = rs.getString("Type");
                int CustomerID = rs.getInt("Customer_ID");
                int UserID = rs.getInt("User_ID");
                int ContactID = rs.getInt("Contact_ID");

                LocalDateTime AppointmentStart = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime AppointmentEnd = rs.getTimestamp("End").toLocalDateTime();

                Appointment a = new Appointment(AppointmentID, AppointmentTitle, AppointmentDescription, AppointmentLocation, AppointmentContact, AppointmentType, AppointmentStart, AppointmentEnd, CustomerID, UserID, ContactID);
                CustomerAppointments.add(a);
            }
        } catch (
                SQLException throwables) {
            throwables.printStackTrace();
        }
        return CustomerAppointments;
    }

    /** Returns list of all Appointments except the specified Appointment
     *
     * @return AppointmentList
     */
    public static ObservableList<Appointment> getAllAppointmentsExcept(int SelectedAppointmentID){
        ObservableList<Appointment> AppointmentList = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * FROM appointments WHERE Appointment_ID != ?";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1,SelectedAppointmentID);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int AppointmentID = rs.getInt("Appointment_ID");
                String AppointmentTitle = rs.getString("Title");
                String AppointmentDescription = rs.getString("Description");
                String AppointmentLocation = rs.getString("Location");
                String AppointmentContact = getContactName(Integer.parseInt(rs.getString("Contact_ID")));
                String AppointmentType = rs.getString("Type");
                LocalDateTime AppointmentStart = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime AppointmentEnd = rs.getTimestamp("End").toLocalDateTime();
                int CustomerID = rs.getInt("Customer_ID");
                int UserID = rs.getInt("User_ID");
                int ContactID = rs.getInt("Contact_ID");

                Appointment a  = new Appointment(AppointmentID, AppointmentTitle,AppointmentDescription,AppointmentLocation, AppointmentContact, AppointmentType, AppointmentStart, AppointmentEnd, CustomerID, UserID,ContactID);
                AppointmentList.add(a);
            }
        } catch (
                SQLException throwables) {
            throwables.printStackTrace();
        }
        return AppointmentList;
    }

    /** Returns list of all Appointments sorted by Month and Type
     *
     * @return MonthTypeList
     */
    public static ObservableList<MonthType> getAppointmentsMonthType(){
        ObservableList<MonthType> MonthTypeList = FXCollections.observableArrayList();
        try{
            String sql = "SELECT MONTHNAME(Start) AS Month, Type, COUNT(*) as TypeCount FROM appointments GROUP BY Month, Type";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                String AppointmentMonth = rs.getString("Month");
                String AppointmentType = rs.getString("Type");
                int TypeCount = rs.getInt("TypeCount");

                MonthType a  = new MonthType(AppointmentMonth,AppointmentType, TypeCount);
                MonthTypeList.add(a);
            }
        } catch (
                SQLException throwables) {
            throwables.printStackTrace();
        }
        return MonthTypeList;
    }
}
