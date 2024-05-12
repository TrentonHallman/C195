package model;

/** Creates the MonthType Class
 *
 * @author Trenton Hallman
 */
public class MonthType {
    private String Month;
    private String Type;
    private int TypeSize;

    public MonthType(String Month, String Type, int TypeSize) {
        this.Month = Month;
        this.Type = Type;
        this.TypeSize = TypeSize;
    }

    /**
     * @return Month
     */
    public String getMonth() {
        return Month;
    }

    /**
     * @return Type
     */
    public String getType() {
        return Type;
    }

    /**
     * @return TypeSize
     */
    public int getTypeSize() {
        return TypeSize;
    }
}
