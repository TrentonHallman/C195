package model;

/** Creates the Division Class
 *
 * @author Trenton Hallman
 */
public class Division {
    private static int DivisionID;
    private static String Division;
    protected int CountryID;
    public Division(int DivisionID, String Division, int CountryID){
        this.DivisionID = DivisionID;
        this.Division = Division;
        this.CountryID = CountryID;
    }

    /**
     * @return DivisionID
     */
    public static int getDivisionID(){
        return DivisionID;
    }

    /**
     * @return Division
     */
    public static String getDivisionName(){
        return Division;
    }
}
