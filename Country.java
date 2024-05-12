package model;

/** Creates the Country Class
 *
 * @author Trenton Hallman
 */
public class Country {
    private int CountryID;
    private String CountryName;
    public Country(int CountryID, String CountryName){
        this.CountryID = CountryID;
        this.CountryName = CountryName;
    }

    /**
     * @return CountryID
     */
    public int getCountryID(){
        return CountryID;
    }

    /**
     * @return CountryName
     */
    public String getCountryName(){
        return CountryName;
    }
}
