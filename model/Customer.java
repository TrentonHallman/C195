package model;

/** Creates the Customer Class
 *
 * @author Trenton Hallman
 */
public class Customer {
    private int CustomerID;
    private String CustomerName;
    private String CustomerAddress;
    private String CustomerPostal;
    private String CustomerPhone;
    private int DivisionID;
    private String DivisionName;

    public Customer(int CustomerID, String CustomerName, String CustomerAddress, String CustomerPostal, String CustomerPhone, int DivisionID, String DivisionName) {
        this.CustomerID = CustomerID;
        this.CustomerName = CustomerName;
        this.CustomerAddress = CustomerAddress;
        this.CustomerPostal = CustomerPostal;
        this.CustomerPhone = CustomerPhone;
        this.DivisionID = DivisionID;
        this.DivisionName = DivisionName;
    }

    /**
     * @return CustomerID
     */
    public int getCustomerID() {
        return CustomerID;
    }

    /**
     * @return CustomerName
     */
    public String getCustomerName() {
        return CustomerName;
    }

    /**
     * @return CustomerAddress
     */
    public String getCustomerAddress() {
        return CustomerAddress;
    }

    /**
     * @return CustomerPostal
     */
    public String getCustomerPostal() {
        return CustomerPostal;
    }

    /**
     * @return CustomerPhone
     */
    public String getCustomerPhone() {
        return CustomerPhone;
    }

    /**
     * @return DivisionID
     */
    public int getDivisionID(){return DivisionID;}

    /**
     * @return DivisionName
     */
    public String getDivisionName(){return DivisionName;}
}
