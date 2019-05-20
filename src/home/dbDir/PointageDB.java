package home.dbDir;

import home.java.Pointage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PointageDB {

   /* public List<Pointage> getEmployee() {
        Connection connection = new ConnectionClasse().getConnection();
        if (connection == null) {
            return null;
        }
        String sql = "select id,nom,prenom from Employe";
        List<Pointage> employes = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                employes.add(new Pointage(
                        resultSet.getInt("id"),
                        resultSet.getString("nom"),
                        resultSet.getString("prenom")));
            }

        } catch (SQLException ex) {
            return null;
        }

        return employes;
    }

    public int addPointage(Pointage point) {
        StringBuilder sql = new StringBuilder("INSERT INTO `creche_dar_elhadith`.`pointage` ( `id`, `nom`, `dateJour`, `timeEntre`, `timesortir`, `presence`");
        Connection connection = null;
        Statement st = null;

        try {
            connection = new ConnectionClasse().getConnection();

            if (connection == null) {
                return -1;
            }
         //   if (employerExist(point.getId())) {
            //    return 2;
            //}

            st = connection.createStatement();


            sql.append(")values ('");
            //sql.append(point.getId()).append("','");
          //  sql.append(point.getFirstName()).append("','");
         //   sql.append(point.()).append("','");
           // sql.append(point.getRmark()).append("','");

            //sql.append(point.getStatuSocial());


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


        return 1;
    }

/*
    public int editEmployee(Employe employe) {
        StringBuilder sql = new StringBuilder("UPDATE `employe` SET ");
        Connection con = null;
        Statement st = null;

        try {
            con = new ConnectionClasse().getConnection();

            if (con == null) {
                return -1;
            }

            if (employerExist(employe.getId())) {


                st = con.createStatement();

                sql.append("`id`='").append(employe.getId());
                sql.append("', `nom`='").append(employe.getPrenom());
                sql.append("', `prenom`='").append(employe.getNom());
                sql.append("', `dateNaissance`='").append(employe.getDateNaissance().toString());
                sql.append("', `adresse`='").append(employe.getAdresse());
                sql.append("', `numTelephone`='").append(employe.getNumTelephone());
                sql.append("', `socialSecurityNumber`='").append(employe.getSocialSecurityNumber());
                sql.append("', `diplome`='").append(employe.getDiplome());
                sql.append("', `experience`='").append(employe.getExperience());
                sql.append("', `itar`='").append(employe.getItar());
                sql.append("', `renouvlementDeContrat`='").append(employe.getRenouvlement_de_contrat());
                sql.append("', `dateDebut`='").append(employe.getDate_debut());
                sql.append("', `fonction`='").append(employe.getFonction());
                sql.append("', `marier`='").append(employe.getStatuSocial());

                if (employe.estmarier()) {
                    sql.append("', `celibacyTitle`='").append(employe.getCelibacyTitle());
                    sql.append("', `nombreEnfantM`='").append(employe.getMaleChild());
                    sql.append("', `nombreEnfantF`='").append(employe.getFemaleChild());
                }

                sql.append("' WHERE `id`=").append(employe.getId()).append(";");

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
*/
    public int deleteEmployee(int id) {
        Connection con = new ConnectionClasse().getConnection();
        if (con == null) // if connection failed
        {
            return -1;
        }
        Statement st;

        try {
            st = con.createStatement();
            String sql = "DELETE FROM `employe` WHERE `id`=" + id + ";";
            st.executeUpdate(sql);
            //TODO when the database is done add the cascade to delete the instance from all of the tables
            return 1;
        } catch (SQLException e) {
            //System.out.println("Error : " + e.getErrorCode());
            //e.printStackTrace();
            return 0;
        }

    }


    public boolean employerExist(int id) {
        Connection con = new ConnectionClasse().getConnection();
        if (con == null) // if connection failed
        {
            return true;
        }
        String sql = "SELECT * FROM `employe` WHERE id=?;";
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
