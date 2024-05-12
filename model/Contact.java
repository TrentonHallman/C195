package model;

/** Creates the Contact Class
 *
 * @author Trenton Hallman
 */
public class Contact {
    private int ContactID;
    private String ContactName;
    private String ContactEmail;

    public Contact(int ContactID, String ContactName, String ContactEmail){
        this.ContactID = ContactID;
        this.ContactName = ContactName;
        this.ContactEmail = ContactEmail;
    }

    /**
     * @return ContactID
     */
    public int getContactID(){
        return ContactID;
    }

    /**
     * @return ContactName
     */
    public String getContactName(){
        return ContactName;
    }

    /**
     * @return ContactEmail
     */
    public String getContactEmail(){
        return ContactEmail;
    }
}
