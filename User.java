package model;

/** Creates the User Class
 *
 * @author Trenton Hallman
 */
public class User {
    private int UserID;
    private String UserName;
    private String Password;

    public User(int UserID, String UserName, String Password) {
        this.UserID = UserID;
        this.UserName = UserName;
        this.Password = Password;
    }

    /**
     * @return UserID
     */
    public int getUserId() {
        return UserID;
    }

    /**
     * @return UserName
     */
    public String getUserName() {
        return UserName;
    }

    /**
     * @return Password
     */
    public String getPassword() {
        return Password;
    }
}
