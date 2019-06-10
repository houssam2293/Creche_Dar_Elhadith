package home.dbDir;

import home.java.Frais;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class fraisDB {

    public int initFrais(Frais frais) {
        StringBuilder sql = new StringBuilder("INSERT INTO `creche_dar_elhadith`.`frais` ( `fraisEmploye`");
        Connection connection = null;
        Statement st = null;

        try {
            connection = new ConnectionClasse().getConnection();

            if (connection == null) {
                return -1;
            }
            st = connection.createStatement();


            sql.append(")values ('");
            sql.append(frais.getFraisEmploye()).append("');");
            st.executeUpdate(sql.toString());


        } catch (SQLException e) {
            //e.printStackTrace();
            System.out.println("SQL request : " + sql);
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
        return 1;
    }


    public int addFraisEleve(Frais frais) {
        StringBuilder sql = new StringBuilder("UPDATE `frais` SET ");
        Connection con = null;
        Statement st = null;

        try {
            con = new ConnectionClasse().getConnection();

            if (con == null) {
                return -1;
            }


            st = con.createStatement();

            sql.append("`fraisEleve`=`fraisEleve`+'").append(frais.getFraisEleve()).append("';");

            int status = st.executeUpdate(sql.toString());
            System.out.println("Status : " + status);

        } catch (SQLException ex) {
            System.out.println("SQL Exception code: " + ex.getErrorCode());
            System.out.println("SQLException msg: " + ex.getMessage());
            //ex.printStackTrace();
            return 0;
        } finally {
            try {
                if (st != null) {
                    st.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return 1;
    }

    public int addFraisEmploye(Frais frais) {
        StringBuilder sql = new StringBuilder("UPDATE `frais`  SET");
        Connection con = null;
        Statement st = null;

        try {
            con = new ConnectionClasse().getConnection();

            if (con == null) {
                return -1;
            }


            st = con.createStatement();

            sql.append("`fraisEmploye`=`fraisEmploye`+'").append(frais.getFraisEmploye()).append("';");

            int status = st.executeUpdate(sql.toString());
            System.out.println("Status : " + status);

        } catch (SQLException ex) {
            System.out.println("SQL Exception code: " + ex.getErrorCode());
            System.out.println("SQLException msg: " + ex.getMessage());
            //ex.printStackTrace();
            return 0;
        } finally {
            try {
                if (st != null) {
                    st.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return 1;
    }

    public int addFraisStock(Frais frais) {
        StringBuilder sql = new StringBuilder("UPDATE `frais` SET ");
        Connection con = null;
        Statement st = null;

        try {
            con = new ConnectionClasse().getConnection();

            if (con == null) {
                return -1;
            }


            st = con.createStatement();

            sql.append("`fraisStock`=`fraisStock`+'").append(frais.getFraisStock()).append("';");

            int status = st.executeUpdate(sql.toString());
            System.out.println("Status : " + status);

        } catch (SQLException ex) {
            System.out.println("SQL Exception code: " + ex.getErrorCode());
            System.out.println("SQLException msg: " + ex.getMessage());
            //ex.printStackTrace();
            return 0;
        } finally {
            try {
                if (st != null) {
                    st.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return 1;
    }

    public int addFraisCharity(Frais frais) {
        StringBuilder sql = new StringBuilder("UPDATE `frais` SET ");
        Connection con = null;
        Statement st = null;

        try {
            con = new ConnectionClasse().getConnection();

            if (con == null) {
                return -1;
            }


            st = con.createStatement();

            sql.append("`fraisCharity`=`fraisCharity`+'").append(frais.getFraisCharity()).append("';");

            int status = st.executeUpdate(sql.toString());
            System.out.println("Status : " + status);

        } catch (SQLException ex) {
            System.out.println("SQL Exception code: " + ex.getErrorCode());
            System.out.println("SQLException msg: " + ex.getMessage());
            //ex.printStackTrace();
            return 0;
        } finally {
            try {
                if (st != null) {
                    st.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return 1;
    }

    public double countFraisEmploye() {
        Connection con = new ConnectionClasse().getConnection();
        double count = 0;
        if (con == null) // if connection failed
        {
            return -1;
        }
        Statement st;
        try {

            st = con.createStatement();
            String sql = "SELECT round(100*fraisEmploye/(fraisStock+fraisEmploye),2) AS a FROM frais";
            ResultSet resultSet = st.executeQuery(sql);
            while (resultSet.next()) {
                count = resultSet.getDouble("a");
            }
            return count;
        } catch (SQLException e) {
            System.out.println("Error : " + e.getErrorCode());
            //e.printStackTrace();
            return -1;
        }
    }

    public double countFraisEleve() {
        Connection con = new ConnectionClasse().getConnection();
        double count = 0;
        if (con == null) // if connection failed
        {
            return -1;
        }
        Statement st;
        try {

            st = con.createStatement();
            String sql = "SELECT round(100*fraisEleve/(fraisCharity+fraisEleve),2) AS a FROM frais";
            ResultSet resultSet = st.executeQuery(sql);
            while (resultSet.next()) {
                count = resultSet.getDouble("a");
            }
            return count;
        } catch (SQLException e) {
            System.out.println("Error : " + e.getErrorCode());
            //e.printStackTrace();
            return -1;
        }
    }

    public double countFraisStock() {
        Connection con = new ConnectionClasse().getConnection();
        double count = 0;
        if (con == null) // if connection failed
        {
            return -1;
        }
        Statement st;
        try {

            st = con.createStatement();
            String sql = "SELECT round(100*fraisStock/ (fraisStock+fraisEmploye),2) AS a FROM frais";
            ResultSet resultSet = st.executeQuery(sql);
            while (resultSet.next()) {
                count = resultSet.getDouble("a");
            }
            return count;
        } catch (SQLException e) {
            System.out.println("Error : " + e.getErrorCode());
            //e.printStackTrace();
            return -1;
        }
    }

    public double countFraisCharity() {
        Connection con = new ConnectionClasse().getConnection();
        double count = 0;
        if (con == null) // if connection failed
        {
            return -1;
        }
        Statement st;
        try {

            st = con.createStatement();
            String sql = "SELECT round(100*fraisCharity/(fraisCharity+fraisEleve),2) AS a FROM frais";
            ResultSet resultSet = st.executeQuery(sql);
            while (resultSet.next()) {
                count = resultSet.getDouble("a");
            }
            return count;
        } catch (SQLException e) {
            System.out.println("Error : " + e.getErrorCode());
            //e.printStackTrace();
            return -1;
        }
    }


    public double getFraisSortant() {
        Connection connection = new ConnectionClasse().getConnection();
        if (connection == null) {
            return 0;
        }
        String sql = "SELECT fraisEmploye+fraisStock FROM frais";
        double lesFraisSortant = 0;

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
                lesFraisSortant = resultSet.getDouble("fraisEmploye+fraisStock");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lesFraisSortant;
    }

    public double getFraisEntrant() {
        Connection connection = new ConnectionClasse().getConnection();
        if (connection == null) {
            return 0;
        }
        String sql = "SELECT fraisCharity+fraisEleve FROM frais";
        double lesFraisEntrant = 0;

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
                lesFraisEntrant = resultSet.getDouble("fraisCharity+fraisEleve");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lesFraisEntrant;
    }

    public double getFraisEmploye() {
        Connection connection = new ConnectionClasse().getConnection();
        if (connection == null) {
            return 0;
        }
        String sql = "SELECT fraisEmploye FROM frais";
        double fraisEmploye = 0;

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
                fraisEmploye = resultSet.getDouble("fraisEmploye");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fraisEmploye;
    }

    public double getFraisEleve() {
        Connection connection = new ConnectionClasse().getConnection();
        if (connection == null) {
            return 0;
        }
        String sql = "SELECT fraisEleve FROM frais";
        double fraisEleve = 0;

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
                fraisEleve = resultSet.getDouble("fraisEleve");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fraisEleve;
    }

    public double getFraisCharity() {
        Connection connection = new ConnectionClasse().getConnection();
        if (connection == null) {
            return 0;
        }
        String sql = "SELECT fraisCharity FROM frais";
        double fraisCharity = 0;

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
                fraisCharity = resultSet.getDouble("fraisCharity");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fraisCharity;
    }


    public double getFraisStock() {
        Connection connection = new ConnectionClasse().getConnection();
        if (connection == null) {
            return 0;
        }
        String sql = "SELECT fraisStock FROM frais";
        double fraisStock = 0;

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
                fraisStock = resultSet.getDouble("fraisStock");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fraisStock;
    }


}
