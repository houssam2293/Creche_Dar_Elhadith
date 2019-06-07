package home.dbDir;

import home.java.Tarifs;

import java.sql.*;

public class tarifsDB {


    public int setTarifs(Tarifs tarifs) {
        StringBuilder sql = new StringBuilder("INSERT INTO `creche_dar_elhadith`.`tarifs` ( `tarifsSets`, `Matin`, `Aprem`, `MatAprem`, `Demi`, `Complet`");
        Connection connection = null;
        Statement st = null;

        try {
            connection = new ConnectionClasse().getConnection();

            if (connection == null) {
                return -1;
            }

            st = connection.createStatement();
            sql.append(")values ('");
            sql.append(tarifs.getTarifsSets()).append("','");
            sql.append(tarifs.getMatin()).append("','");
            sql.append(tarifs.getAprem()).append("','");
            sql.append(tarifs.getMatAprem()).append("','");
            sql.append(tarifs.getDemi()).append("','");
            sql.append(tarifs.getComplet()).append("');");
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


        return 1;
    }

    public int updateTarifs(Tarifs tarifs) {
        StringBuilder sql = new StringBuilder("UPDATE `tarifs` SET ");
        Connection con = null;
        Statement st = null;

        try {
            con = new ConnectionClasse().getConnection();

            if (con == null) {
                return -1;
            }


            st = con.createStatement();
            sql.append("`Matin`='").append(tarifs.getMatin());
            sql.append("', `Aprem`='").append(tarifs.getAprem());
            sql.append("', `MatAprem`='").append(tarifs.getMatAprem());
            sql.append("', `Demi`='").append(tarifs.getDemi());
            sql.append("', `Complet`='").append(tarifs.getComplet());
            sql.append("' WHERE `tarifsSets`=").append(tarifs.getTarifsSets()).append(";");

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


    public Tarifs getTarifs() {
        Connection connection = new ConnectionClasse().getConnection();
        if (connection == null) {
            return null;
        }
        String sql = "select * from tarifs";
        Tarifs tarifs = new Tarifs();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                tarifs.setTarifsSets(resultSet.getInt("tarifsSets"));
                tarifs.setMatin(resultSet.getDouble("Matin"));
                tarifs.setAprem(resultSet.getDouble("Aprem"));
                tarifs.setMatAprem(resultSet.getDouble("MatAprem"));
                tarifs.setDemi(resultSet.getDouble("Demi"));
                tarifs.setComplet(resultSet.getDouble("Complet"));
            }

        } catch (SQLException ex) {
            return null;
        }

        return tarifs;
    }

    public boolean tarifsExist() {
        Connection con = new ConnectionClasse().getConnection();
        if (con == null) // if connection failed
        {
            return true;
        }
        String sql = "SELECT * FROM `tarifs` WHERE tarifsSets=?;";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, 1);
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


