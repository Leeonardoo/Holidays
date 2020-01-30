package com.leonardo.holidaysapp.Holidays;

public class Holidays {
    private String localName, date, name, launchYear;
    private Boolean fixed, global;
    private String[] counties;

    public String getLocalName() {
        return localName;
    }

    public String getDate() {
        String yyyy = date.substring(0, 4);
        String mm = date.substring(5, 7);
        String dd = date.substring(8, 10);
        return (dd+"/"+mm+"/"+yyyy); }

    public String getName() { return name; }

    public String getLaunchYear() { return launchYear; }

    public Boolean getFixed() { return fixed; }

    public Boolean getGlobal() { return global; }

    public String[] getCounties() { return counties; }

}
