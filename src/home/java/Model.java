package home.java;

import java.sql.Time;
import java.time.LocalTime;

public class Model {
    private final static Model instance = new Model();

    public static Model getInstance() {
        return instance;
    }

    // for adding/editing events
    public int event_day;
    public int event_month;
    public int event_year;
    public String eventType;
    public String eventDescreption;
    public LocalTime eventTime;

    // for the year and month the user has open, is "viewing"
    public int viewing_month;
    public int viewing_year;

    // for the current calendar being worked on
    public int calendar_start;
    public int calendar_end;
    public String calendar_start_date;
    public String calendar_name;

    // for editing rules
    public int rule_days;
    public String rule_term;
    public String rule_descript;

    // for editing terms
    public String term_name;
    public String term_date;

    //Function that returns a month Index based on the given month name
    public int getMonthIndex(String month){
        switch (month)
        {

            case "January":
                return 0;
            case "February":
                return 1;
            case "March":
                return 2;
            case "April":
                return 3;
            case "May":
                return 4;
            case "June":
                return 5;
            case "July":
                return 6;
            case "August":
                return 7;
            case "September":
                return 8;
            case "October":
                return 9;
            case "November":
                return 10;
            case "December":
                return 11;
        }
        return 0;
    }

}
