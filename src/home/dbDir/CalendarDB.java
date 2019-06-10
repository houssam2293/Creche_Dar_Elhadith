package home.dbDir;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CalendarDB {
    private static Connection conn = null;
    private static Statement stmt = null;


    public CalendarDB() {
        conn = new ConnectionClasse().getConnection();
        if (conn == null) {
            System.out.println("Error connecting DB!");
        }
    }

    public ResultSet executeQuery(String query) {
        ResultSet result;

        try {
            stmt = conn.createStatement();
            result = stmt.executeQuery(query);
        } catch (SQLException ex) {
            System.out.println("Exception at executeQuery:dataHandler --> ERROR: " + ex.getLocalizedMessage());
            return null;
        } finally {
        }

        return result;
    }

    public boolean executeAction(String query2) {
        try {
            stmt = conn.createStatement();
            stmt.execute(query2);
            return true;
        } catch (SQLException ex) {
            System.out.println("quere error message  --> ERROR: " + ex.getMessage());
            return false;
        } finally {
        }
    }

    boolean checkIfTableIsEmpty(String tableName) {
        boolean checkingResult = false;
        try {
            stmt = conn.createStatement();

            ResultSet res = stmt.executeQuery("SELECT * FROM " + tableName);
            while (res.next()) {
                checkingResult = true;
                break;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage() + "--- checking Table failed/error");
            return false;
        } finally {
        }
        return checkingResult;
    }


    public ArrayList<String> getFilteredEvents(ArrayList<String> eventIdentifiersList, String calName) {

        ArrayList<String> filteredEventsList = new ArrayList<String>();


        if (!eventIdentifiersList.isEmpty()) {
            for (String s : eventIdentifiersList) {

                String getEventsQuery = "SELECT * FROM creche_dar_elhadith.events "
                        + "WHERE TypeEvent='" + s + "'"
                        + " AND CalendarName='" + calName + "'";

                //Variable that will hold the result of executing the previous query
                ResultSet rs = executeQuery(getEventsQuery);

                try {
                    //While there are events in the ResultSet variable, add each one of them to the ArrayList of Strings
                    while (rs.next()) {
                        //get the full row of the event info and store it in a String variable
                        String filteredEvent = rs.getString("EventDescription") + "~"
                                + rs.getString("EventDate") + "~"
                                + rs.getString("CalendarName") + "~"
                                + rs.getString("EventTime") + "~"
                                + rs.getString("TypeEvent");
                        //add event to list of filtered events
                        filteredEventsList.add(filteredEvent);
                    }
                } catch (SQLException e) {
                    System.err.println(e.getMessage() + "--- error at getListOfRules method in DBHandler class");
                }
            }
        }
        return filteredEventsList;
    }

    public String getEventColor(String description) {

        //Declare variable that will contain the color of the term to be returned
        String termColor = "x";

        //Create query that will find a matching result for the termColor based on the term's ID
        String getEventColorQuery = "SELECT color FROM creche_dar_elhadith.colortable " +
                "where nameevent = '" + description + "'";

        //Execute query to get the color of a term based a given term ID
        ResultSet res = executeQuery(getEventColorQuery);

        // store color in a String variable of the query obtained a result
        try {
            while (res.next()) {
                termColor = res.getString("color");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage() + "--- error at getTermColor method in DBHandler class");
        }

        return termColor;
    }

    public void setEventColor(String eventName, String eventColor) {
        String setEventColor = "UPDATE `creche_dar_elhadith`.`colortable`" +
                "SET `color` ='" + eventColor + "' " +
                "WHERE `nameevent` ='" + eventName + "'";
        executeAction(setEventColor);
    }
}
