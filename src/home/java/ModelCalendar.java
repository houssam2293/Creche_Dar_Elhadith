package home.java;

import java.time.LocalTime;

public class ModelCalendar {
    private final static ModelCalendar instance = new ModelCalendar();

    public static ModelCalendar getInstance() {
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

            case "janvier":
                return 0;
            case "février":
                return 1;
            case "mars":
                return 2;
            case "avril":
                return 3;
            case "mai":
                return 4;
            case "juin":
                return 5;
            case "juillet":
                return 6;
            case "août":
                return 7;
            case "septembre":
                return 8;
            case "octobre":
                return 9;
            case "novembre":
                return 10;
            case "décembre":
                return 11;
        }
        return 0;
    }

}
