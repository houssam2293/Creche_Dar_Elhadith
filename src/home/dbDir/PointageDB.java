package home.dbDir;

import com.jfoenix.controls.JFXDatePicker;
import home.java.Pointage;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PointageDB {

    public List<Pointage> getPointage(LocalDate date) {
        Connection connection = new ConnectionClasse().getConnection();
        if (connection == null) {
            return null;
        }
        String sql = "select idEmp,name,fonction,remark from creche_dar_elhadith.employe,creche_dar_elhadith.pointage " +
                "where dateJour=? and presence = 0 and id = idEmp;";

        List<Pointage> pointages = new ArrayList<>();


        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, String.valueOf(date));
            // Statement statement = connection.createStatement();
            ResultSet resultSet = ps.executeQuery();
            // ResultSet resultSet = statement.executeQuery(sql);
            if (!resultSet.next()) {
                System.out.println("tableau khawi ");
                return null;
            }
            while (resultSet.next()) {
                pointages.add(new Pointage(
                        resultSet.getInt("idEmp"),
                        resultSet.getString("name"),
                        resultSet.getString("fonction"),
                        resultSet.getString("remark")));
                System.out.println("BDD ");

            }

        } catch (SQLException ex) {
            return null;
        }

        return pointages;
    }

    public int addPointage(Pointage point) {
        JFXDatePicker a = new JFXDatePicker();
        a.setValue(LocalDate.now());
        if (pointExist(point.getId(), a.getValue())) {


            StringBuilder sql = new StringBuilder("INSERT INTO `creche_dar_elhadith`.`pointage` ( `idEmp`, `name`, `dateJour`, `timeEntre`, `remark`, `presence`");
            Connection connection = null;
            Statement st = null;
            try {
                connection = new ConnectionClasse().getConnection();

                if (connection == null) {
                    return -1;
                }

                st = connection.createStatement();
                sql.append(")values ('");
                sql.append(point.getId()).append("','");
                sql.append(point.getFirstName()).append("',");
                sql.append("CURDATE(),'");
                sql.append(point.getTempEnt().getValue().toString()).append("','");
                sql.append(point.getRemarkk().getText()).append("','");

                if (point.getRemark().isSelected())
                    sql.append("1");
                else
                    sql.append("0");

                sql.append("');");
                st.executeUpdate(sql.toString());


            } catch (SQLException e) {
                //e.printStackTrace();
                //System.out.println("SQL request : "+sql);
                System.out.println("SQLException msg: " + e.getMessage());
                return 0;
            } finally {
                try {
                    if (st != null) {
                        st.close();
                    }
                    if (connection != null) {
                        connection.close();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            }
        } else {
            // la je vais mets a jour
            System.out.println("raw deja kaye");


            StringBuilder sql = new StringBuilder("INSERT INTO `creche_dar_elhadith`.`pointage` ( `idEmp`, `name`, `dateJour`, `timeEntre`, `remark`, `presence`");
            Connection connection = null;
            Statement st = null;
            try {
                connection = new ConnectionClasse().getConnection();

                if (connection == null) {
                    return -1;
                }

                st = connection.createStatement();
                sql.append(")values ('");
                sql.append(point.getId()).append("','");
                sql.append(point.getFirstName()).append("',");
                sql.append("CURDATE(),'");
                sql.append(point.getTempEnt().getValue().toString()).append("','");
                sql.append(point.getRemarkk().getText()).append("','");

                if (point.getRemark().isSelected())
                    sql.append("1");
                else
                    sql.append("0");

                sql.append("');");
                st.executeUpdate(sql.toString());


            } catch (SQLException e) {
                //e.printStackTrace();
                //System.out.println("SQL request : "+sql);
                System.out.println("SQLException msg: " + e.getMessage());
                return 0;
            } finally {
                try {
                    if (st != null) {
                        st.close();
                    }
                    if (connection != null) {
                        connection.close();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            }


        }


        return 1;
    }


    public boolean pointExist(int id, LocalDate date) {
        Connection con = new ConnectionClasse().getConnection();
        if (con == null) // if connection failed
        {
            return true;
        }
        String sql = "SELECT * FROM `pointage` WHERE idEmp=? and dateJour=?;";
        try { // a verifier
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, String.valueOf(id));
            ps.setString(2, String.valueOf(date));

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true; // Pointage already exists
            }
            return false;
        } catch (SQLException se) {
            System.out.println("Methode pointExist!! Error msg: " + se.getMessage());
            return true;
        }
    }


}
