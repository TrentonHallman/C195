package DBAccess;

import helper.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** Gathers data within Contacts Table in SQL Database
 *
 * @author Trenton Hallman
 */
public class DBContacts {

    /** Returns list of all Contacts
     *
     * @return ContactList
     */
    public static ObservableList<Contact> getAllContacts(){
        ObservableList<Contact> ContactList = FXCollections.observableArrayList();
        try{
            String sql = "SELECT * FROM contacts";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int CountryID = rs.getInt("Contact_ID");
                String ContactName = rs.getString("Contact_Name");
                String ContactEmail = rs.getString("Email");
                Contact c  = new Contact(CountryID, ContactName,ContactEmail);
                ContactList.add(c);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ContactList;
    }

    /** Returns list of all Contact Names
     *
     * @return ContactNameList
     */
    public static ObservableList<String> getAllContactNames(){
        ObservableList<String> ContactNameList = FXCollections.observableArrayList();
        try{
            String sql = "SELECT Contact_Name FROM contacts";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                String ContactName = rs.getString("Contact_Name");
                ContactNameList.add(ContactName);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ContactNameList;
    }
}
