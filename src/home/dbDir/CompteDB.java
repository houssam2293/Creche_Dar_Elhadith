package home.dbDir;

import home.java.Compte;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompteDB {
    public List<Compte> getAccounts() {
        Connection connection = new ConnectionClasse().getConnection();
        if (connection == null) {
            return null;
        }
        String sql = "select * from compte";
        List<Compte> accounts = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                accounts.add(new Compte(
                        resultSet.getString("email"),
                        resultSet.getString("login"),
                        resultSet.getString("password")));
            }
        } catch (SQLException ex) {
            return null;
        }

        return accounts;
    }

    public Compte getAcountInformation(String email) {
        Connection connection = new ConnectionClasse().getConnection();
        if (connection == null) {
            return null;
        }

        Compte compte = new Compte();
        String sql = "select * from compte where email='" + email + "'";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultset = statement.executeQuery(sql);
            while (resultset.next()) {
                compte.setEmail(resultset.getString("email"));
                compte.setLogin(resultset.getString("login"));
                compte.setPassword(resultset.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return compte;
    }


    public int addAcount(Compte compte) {
        StringBuilder sql = new StringBuilder("INSERT INTO `creche_dar_elhadith`.`compte` (`email`,`login`,`password`) values ('");
        Connection connection = null;
        Statement st = null;
        try {
            connection = new ConnectionClasse().getConnection();

            if (connection == null) {
                return -1;
            }
            if (accountExist(compte, "email")) {
                return 2;
            }
            sql.append(compte.getEmail()).append("','");
            sql.append(compte.getLogin()).append("','");
            sql.append(compte.getHashedPassword(compte.getPassword())).append("');");

            st = connection.createStatement();
            st.executeUpdate(sql.toString());

        } catch (SQLException e) {
            e.printStackTrace();
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

    public int editAccount(Compte compte, String type) {
        StringBuilder sql = new StringBuilder("UPDATE `creche_dar_elhadith`.`compte` SET ");
        Connection con = null;
        Statement st = null;

        try {
            con = new ConnectionClasse().getConnection();

            if (con == null) {
                return -1;
            }

            if (accountExist(compte, type)) {


                st = con.createStatement();

                sql.append("email='").append(compte.getEmail());
                sql.append("',login='").append(compte.getLogin());
                sql.append("',password='").append(compte.getPassword());

                if (type.equals("email"))
                    sql.append("' WHERE email='").append(compte.getEmail()).append("'");
                else {
                    sql.append("' WHERE (login='").append(compte.getLogin()).append("') AND");
                    sql.append(" (password='").append(compte.getPassword()).append("')");
                }
                int status = st.executeUpdate(sql.toString());
                //System.out.println("Status : " + status);
            } else return 2;
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

    public int deleteAccount(String email) {
        Connection con = new ConnectionClasse().getConnection();
        if (con == null) // if connection failed
        {
            return -1;
        }
        Statement st;

        try {
            st = con.createStatement();
            String sql = "DELETE FROM `employe` WHERE `email`='" + email + "';";
            st.executeUpdate(sql);
            return 1;
        } catch (SQLException e) {
            System.out.println("Error : " + e.getErrorCode());
            //e.printStackTrace();
            return 0;
        }

    }

    public int resetPassword(String email, String password) {
        Connection con = new ConnectionClasse().getConnection();
        if (con == null) // if connection failed
        {
            return -1;
        }
        Statement st;

        try {
            st = con.createStatement();
            String sql = "UPDATE `compte` SET `password` = '" + password + "' WHERE `email`='" + email + "';";
            st.executeUpdate(sql);
            return 1;
        } catch (SQLException e) {
            System.out.println("Error : " + e.getErrorCode());
            System.out.println("Error msg : " + e.getMessage());
            //e.printStackTrace();
            return 0;
        }

    }

    private boolean accountExist(Compte compte, String type) {
        Connection con = new ConnectionClasse().getConnection();
        if (con == null) // if connection failed
        {
            return true;
        }
        String sql;
        if (type.equals("email")) {
            sql = "SELECT * FROM `compte` WHERE email=?;";
        } else sql = "SELECT * FROM `compte` WHERE login=? AND password=?;";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            if (type.equals("email"))
                ps.setString(1, compte.getEmail());
            else {
                ps.setString(1, compte.getLogin());
                ps.setString(2, compte.getPassword());
            }
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true; // Username already exists
            }
            return false;
        } catch (SQLException se) {
            System.out.println("Error msg: " + se.getMessage());
            return true;
        }
    }

}
