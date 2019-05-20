package home.dbDir;

import home.java.Employe;
import home.java.EntreStock;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StockDB {

    public List<EntreStock> getStock() {
        Connection connection = new ConnectionClasse().getConnection();
        if (connection == null) {
            return null;
        }
        String sql = "select * from stock";
        List<EntreStock> stock = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                stock.add(new EntreStock(

                        resultSet.getInt("id"),
                        resultSet.getString("nom"),
                        resultSet.getInt("quantite"),
                        resultSet.getDouble("prix"),
                        resultSet.getDate("dateFab"),
                        resultSet.getDate("dateExp"),
                        resultSet.getString("fournisseur"),
                        resultSet.getDouble("prixTotale")));

            }

        } catch (SQLException ex) {
            return null;
        }

        return stock;
    }

    public int addStock(EntreStock stock) {
        StringBuilder sql = new StringBuilder("INSERT INTO `creche_dar_elhadith`.`stock` ( `id`, `nom`, `dateFab`, `dateExp`, `quantite`, `prix`,`fournisseur`,`prixTotale`");
        Connection connection = null;
        Statement st = null;

        try {
            connection = new ConnectionClasse().getConnection();

            if (connection == null) {
                return -1;
            }
            if (stockExist(stock.getId())) {
                return 2;
            }

            st = connection.createStatement();


            sql.append(")values ('");
            sql.append(stock.getId()).append("','");
            sql.append(stock.getNom()).append("','");
            sql.append(stock.getDateFab()).append("','");
            sql.append(stock.getDateExp()).append("','");
            sql.append(stock.getQuantite()).append("','");
            sql.append(stock.getPrix()).append("','");

            sql.append(stock.getFournisseur()).append("','");
            sql.append((stock.getPrix()*stock.getQuantite()));
            sql.append("');");
            st.executeUpdate(sql.toString());


        } catch (SQLException e) {
            //e.printStackTrace();
           System.out.println("SQL request : "+sql);
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

    public int editStock(EntreStock stock) {
        StringBuilder sql = new StringBuilder("UPDATE `stock` SET ");
        Connection con = null;
        Statement st = null;

        try {
            con = new ConnectionClasse().getConnection();

            if (con == null) {
                return -1;
            }

            if (stockExist(stock.getId())) {


                st = con.createStatement();

                sql.append("`id`='").append(stock.getId());
                sql.append("', `nom`='").append(stock.getNom());
                sql.append("', `dateFab`='").append(stock.getDateFab());
                sql.append("', `dateExp`='").append(stock.getDateExp().toString());
                sql.append("', `quantite`='").append(stock.getQuantite());
                sql.append("', `prix`='").append(stock.getPrix());
                sql.append("', `fournisseur`='").append(stock.getFournisseur());
                sql.append("', `prixTotale`='").append(stock.getPrixTotale());

                sql.append("' WHERE `id`=").append(stock.getId()).append(";");

                int status = st.executeUpdate(sql.toString());
                System.out.println("Status : " + status);
            }else return 2;
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

    public int deleteStock(int id) {
        Connection con = new ConnectionClasse().getConnection();
        if (con == null) // if connection failed
        {
            return -1;
        }
        Statement st;

        try {
            st = con.createStatement();
            String sql = "DELETE FROM `stock` WHERE `id`=" + id + ";";
            st.executeUpdate(sql);
            //TODO when the database is done add the cascade to delete the instance from all of the tables
            return 1;
        } catch (SQLException e) {
            //System.out.println("Error : " + e.getErrorCode());
            //e.printStackTrace();
            return 0;
        }

    }


    private boolean stockExist(int id) {
        Connection con = new ConnectionClasse().getConnection();
        if (con == null) // if connection failed
        {
            return true;
        }
        String sql = "SELECT * FROM `stock` WHERE id=?;";

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


}


