package model;

import java.time.LocalDateTime;

/** Creates the Appointment Class
 *
 * @author Trenton Hallman
 */
public class Appointment {
    private int AppointmentID;
    private String AppointmentTitle;
    private String AppointmentDescription;
    private String AppointmentLocation;
    private String AppointmentContact;
    private String AppointmentType;
    private LocalDateTime AppointmentStart;
    private LocalDateTime AppointmentEnd;
    private int CustomerID;
    private int UserID;
    private int ContactID;

    public Appointment(int AppointmentID, String AppointmentTitle, String AppointmentDescription, String AppointmentLocation, String AppointmentContact, String AppointmentType, LocalDateTime AppointmentStart, LocalDateTime AppointmentEnd, int CustomerID,  int UserID, int ContactID){
        this.AppointmentID = AppointmentID;
        this.AppointmentTitle = AppointmentTitle;
        this.AppointmentDescription = AppointmentDescription;
        this.AppointmentLocation = AppointmentLocation;
        this.AppointmentContact = AppointmentContact;
        this.AppointmentType = AppointmentType;
        this.AppointmentStart = AppointmentStart;
        this.AppointmentEnd = AppointmentEnd;
        this.CustomerID = CustomerID;
        this.UserID = UserID;
        this.ContactID = ContactID;
    }

    /**
     * @return AppointmentID
     */
    public int getAppointmentID() {
        return AppointmentID;
    }

    /**
     * @return AppointmentTitle
     */
    public String getAppointmentTitle() {
        return AppointmentTitle;
    }

    /**
     * @return AppointmentDescription
     */
    public String getAppointmentDescription() {
        return AppointmentDescription;
    }

    /**
     * @return AppointmentLocation
     */
    public String getAppointmentLocation() {
        return AppointmentLocation;
    }

    /**
     * @return AppointmentContact
     */
    public String getAppointmentContact(){return AppointmentContact;}

    /**
     * @return AppointmentType
     */
    public String getAppointmentType() {
        return AppointmentType;
    }

    /**
     * @return AppointmentStart
     */
    public LocalDateTime getAppointmentStart(){
        return AppointmentStart;
    }

    /**
     * @return AppointmentEnd
     */
    public LocalDateTime getAppointmentEnd() {
        return AppointmentEnd;
    }

    /**
     * @return CustomerID
     */
    public int getCustomerID() {
        return CustomerID;
    }

    /**
     * @return UserID
     */
    public int getUserID() {
        return UserID;
    }

    /**
     * @return ContactID
     */
    public int getContactID() {
        return ContactID;
    }
}
