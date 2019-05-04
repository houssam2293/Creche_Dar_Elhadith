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



    //Function that returns a month Index based on the given month name
    public int getMonthIndex(String month){
        switch (month)
        {

            case "يناير":
                return 0;
            case "فبراير":
                return 1;
            case "مارس":
                return 2;
            case "أبريل":
                return 3;
            case "مايو":
                return 4;
            case "يونيو":
                return 5;
            case "يوليو":
                return 6;
            case "أغسطس":
                return 7;
            case "سبتمبر":
                return 8;
            case "أكتوبر":
                return 9;
            case "نوفمبر":
                return 10;
            case "ديسمبر":
                return 11;
        }
        return 0;
    }

}
