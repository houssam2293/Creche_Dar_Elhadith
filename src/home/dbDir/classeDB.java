package home.dbDir;

import home.java.Classe;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class classeDB {

    public List<Classe> getClasse() {
        Connection connection = new ConnectionClasse().getConnection();
        if (connection == null) {
            return null;
        }
        String sql = "select * from Classe";
        List<Classe> classes = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                classes.add(new Classe(
                        resultSet.getInt("id"),
                        resultSet.getString("ClassNam"),
                        resultSet.getString("ClassRom"),
                        resultSet.getInt("maxNbrElev"),
                        resultSet.getString("remarque")));

            }} catch (SQLException e) {
            e.printStackTrace();
        }
    return classes ;
    }

    public int removClasse(String ClassNam) {
        Connection con = new ConnectionClasse().getConnection();
        if (con == null) // if connection failed
        {
            return -1;
        }
        Statement st;

        try {
            st = con.createStatement();
            String sql = "DELETE FROM `Classe` WHERE `ClassNam`='" + ClassNam + "';";
           // String sql = "DELETE FROM Classe WHERE `ClassNam`=" + ClassNam + ";";
            st.executeUpdate(sql);
            //TODO when the database is done add the cascade to delete the instance from all of the tables
            return 1;
        } catch (SQLException e) {
            System.out.println("Error : " + e.getErrorCode());
            e.printStackTrace();
            return 0;
        }

    }

    public int addClasse(Classe cls) {
        StringBuilder sql = new StringBuilder("INSERT INTO `creche_dar_elhadith`.`Classe` ( `id`, `ClassNam`, `ClassRom`, `maxNbrElev`, `remarque`");
        Connection connection = null;
        Statement st = null;

        try {
            connection = new ConnectionClasse().getConnection();

            if (connection == null) {
                return -1;
            }
            if (classeExist(cls.getId())) {
                return 2;
            }

            st = connection.createStatement();
            sql.append(")values ('");
            sql.append(cls.getId()).append("','");
            sql.append(cls.getClassNam()).append("','");
            sql.append(cls.getClassRom()).append("','");
            sql.append(cls.getmaxNbrElev()).append("','");
            sql.append(cls.getremarque()).append("');");
            st.executeUpdate(sql.toString());


        } catch (SQLException e) {
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


    private boolean classeExist(int id) {
        Connection con = new ConnectionClasse().getConnection();
        if (con == null) // if connection failed
        {
            return true;
        }
        String sql = "SELECT * FROM `Classe` WHERE id=?;";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, String.valueOf(id));
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
    public int editEmployee(Classe clss) {
        StringBuilder sql = new StringBuilder("UPDATE `Classe` SET ");
        Connection con = null;
        Statement st = null;

        try {
            con = new ConnectionClasse().getConnection();

            if (con == null) {
                return -1;
            }

            if (classeExist(clss.getId())) {


                st = con.createStatement();

                sql.append("`id`='").append(clss.getId());
                sql.append("', `ClassNam`='").append(clss.getClassNam());
                sql.append("', `ClassRom`='").append(clss.getClassRom());
                sql.append("', `maxNbrElev`='").append(clss.getmaxNbrElev());
                sql.append("', `remarque`='").append(clss.getremarque());

            }

            sql.append("' WHERE `id`=").append(clss.getId()).append(";");

            int status = st.executeUpdate(sql.toString());
            System.out.println("Status : " + status);

    } catch (SQLException ex) {
        System.out.println(" SQL Exception code: " + ex.getErrorCode());
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


}
