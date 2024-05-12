package DBAccess;

import helper.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

/** Gathers data within Users Table in SQL Database
 *
 * @author Trenton Hallman
 */
public class DBUsers {

    /** Returns User given a specific Username and Password
     * Checks for correct Username and Password for Logging In
     *
     * @param Username
     * @param Password
     */
    public static int UserCheck(String Username, String Password) {
        ResourceBundle rb = ResourceBundle.getBundle("helper/login", Locale.getDefault());
        try
        {
            String sql = "SELECT * FROM users WHERE user_name = '" + Username + "' AND password = '" + Password +"'";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            rs.next();
            if (rs.getString("User_Name").equals(Username))
            {
                if (rs.getString("Password").equals(Password))
                {
                    return rs.getInt("User_ID");
                }
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return -1;
    }

    /** Returns list of all Users
     *
     * @return AllUsers
     */
    public static ObservableList<User> getAllUsers(){
        ObservableList<User> AllUsers = FXCollections.observableArrayList();
        try{
            String sql = "SELECT * from users";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int UserID = rs.getInt("User_ID");
                String UserName = rs.getString("User_Name");
                String Password = rs.getString("Password");
                User u  = new User(UserID,UserName,Password);
                AllUsers.add(u);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return AllUsers;
    }
}
