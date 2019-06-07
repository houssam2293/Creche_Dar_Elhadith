package home.dbDir;

import home.java.Eleve;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EleveDB {

    public List<Eleve> getEleve() {
        Connection connection = new ConnectionClasse().getConnection();
        if (connection == null) {
            return null;
        }
        String sql = "select * from Eleve";
        List<Eleve> eleves = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                eleves.add(new Eleve(
                        resultSet.getInt("id"),
                        resultSet.getInt("gender"),
                        resultSet.getString("nom"),
                        resultSet.getString("prenom"),
                        resultSet.getDate("dateNaissance"),
                        resultSet.getString("lieuNaissance"),
                        resultSet.getString("classe"),
                        resultSet.getInt("anneeScolaire"),
                        resultSet.getString("regime"),
                        resultSet.getString("adresse"),
                        resultSet.getString("phone"),
                        resultSet.getString("prenomPere"),
                        resultSet.getString("prenomMere"),
                        resultSet.getString("nomMere"),
                        resultSet.getString("travailPere"),
                        resultSet.getString("travailMere"),
                        resultSet.getString("maladie"),
                        resultSet.getString("wakil"),
                        resultSet.getString("remarque"),
                        resultSet.getInt("tranches"),
                        resultSet.getDouble("montantRestant")));
            }

        } catch (SQLException ex) {
            return null;
        }

        return eleves;
    }

    public List<Eleve> getEleveDeClasse(String classe) {
        Connection connection = new ConnectionClasse().getConnection();
        if (connection == null) {
            return null;
        }
        String sql = "select * from Eleve where classe='" + classe + "'";
        List<Eleve> eleves = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                eleves.add(new Eleve(
                        resultSet.getInt("id"),
                        resultSet.getInt("gender"),
                        resultSet.getString("nom"),
                        resultSet.getString("prenom"),
                        resultSet.getDate("dateNaissance"),
                        resultSet.getString("lieuNaissance"),
                        resultSet.getString("classe"),
                        resultSet.getInt("anneeScolaire"),
                        resultSet.getString("regime"),
                        resultSet.getString("adresse"),
                        resultSet.getString("phone"),
                        resultSet.getString("prenomPere"),
                        resultSet.getString("prenomMere"),
                        resultSet.getString("nomMere"),
                        resultSet.getString("travailPere"),
                        resultSet.getString("travailMere"),
                        resultSet.getString("maladie"),
                        resultSet.getString("wakil"),
                        resultSet.getString("remarque"),
                        resultSet.getInt("tranches"),
                        resultSet.getDouble("montantRestant")));
            }

        } catch (SQLException ex) {
            return null;
        }

        return eleves;
    }

    public int addEleve(Eleve eleve) {
        StringBuilder sql = new StringBuilder("INSERT INTO `creche_dar_elhadith`.`eleve` ( `id`,`gender`, `nom`, `prenom`, `dateNaissance`, `lieuNaissance`,`classe`,`anneeScolaire`,`regime`, `adresse`, `phone`, `prenomPere`, `prenomMere`, `nomMere`, `travailPere`, `travailMere`, `maladie`,`wakil`, `remarque`, `tranches`, `montantRestant`");
        Connection connection = null;
        Statement st = null;

        try {
            connection = new ConnectionClasse().getConnection();

            if (connection == null) {
                return -1;
            }
            if (eleveExist(eleve.getId())) {
                return 2;
            }

            st = connection.createStatement();
            sql.append(")values ('");
            sql.append(eleve.getId()).append("','");
            sql.append(eleve.getGender()).append("','");
            sql.append(eleve.getNom()).append("','");
            sql.append(eleve.getPrenom()).append("','");
            sql.append(eleve.getDateNaissance()).append("','");
            sql.append(eleve.getLieuNaissance()).append("','");
            sql.append(eleve.getClasse()).append("','");
            sql.append(eleve.getAnneeScolaire()).append("','");
            sql.append(eleve.getRegime()).append("','");
            sql.append(eleve.getAdresse()).append("','");
            sql.append(eleve.getPhone()).append("','");
            sql.append(eleve.getPrenomPere()).append("','");
            sql.append(eleve.getPrenomMere()).append("','");
            sql.append(eleve.getNomMere()).append("','");
            sql.append(eleve.getTravailPere()).append("','");
            sql.append(eleve.getTravailMere()).append("','");
            sql.append(eleve.getMaladie()).append("','");
            sql.append(eleve.getWakil()).append("','");
            sql.append(eleve.getRemarque()).append("','");
            sql.append(eleve.getTranches()).append("','");
            sql.append(eleve.getMontantRestant()).append("');");
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

    public int editEleve(Eleve eleve) {
        StringBuilder sql = new StringBuilder("UPDATE `eleve` SET ");
        Connection con = null;
        Statement st = null;

        try {
            con = new ConnectionClasse().getConnection();

            if (con == null) {
                return -1;
            }

            if (eleveExist(eleve.getId())) {


                st = con.createStatement();

                sql.append("`id`='").append(eleve.getId());
                sql.append("', `gender`='").append(eleve.getGender());
                sql.append("', `nom`='").append(eleve.getNom());
                sql.append("', `prenom`='").append(eleve.getPrenom());
                sql.append("', `dateNaissance`='").append(eleve.getDateNaissance().toString());
                sql.append("', `lieuNaissance`='").append(eleve.getLieuNaissance());
                sql.append("', `classe`='").append(eleve.getClasse());
                sql.append("', `anneeScolaire`='").append(eleve.getAnneeScolaire());
                sql.append("', `regime`='").append(eleve.getRegime());
                sql.append("', `adresse`='").append(eleve.getAdresse());
                sql.append("', `phone`='").append(eleve.getPhone());
                sql.append("', `prenomPere`='").append(eleve.getPrenomPere());
                sql.append("', `prenomMere`='").append(eleve.getPrenomMere());
                sql.append("', `nomMere`='").append(eleve.getNomMere());
                sql.append("', `travailPere`='").append(eleve.getTravailPere());
                sql.append("', `travailMere`='").append(eleve.getTravailMere());
                sql.append("', `maladie`='").append(eleve.getMaladie());
                sql.append("', `wakil`='").append(eleve.getWakil());
                sql.append("', `remarque`='").append(eleve.getRemarque());

                sql.append("' WHERE `id`=").append(eleve.getId()).append(";");

                int status = st.executeUpdate(sql.toString());
                System.out.println("Status : " + status);
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


    public int notes(int id, Eleve eleve) {
        StringBuilder sql = new StringBuilder("UPDATE `eleve` SET ");
        Connection con = null;
        Statement st = null;

        try {
            con = new ConnectionClasse().getConnection();

            if (con == null) {
                return -1;
            }

            if (eleveExist(eleve.getId())) {
                st = con.createStatement();

                sql.append("`remarque`='").append(eleve.getRemarque());

                sql.append("' WHERE `id`=").append(eleve.getId()).append(";");

                int status = st.executeUpdate(sql.toString());
                System.out.println("Status : " + status);
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

    public int countRegimeEleve(String regime) {
        Connection con = new ConnectionClasse().getConnection();
        int count = 0;
        if (con == null) // if connection failed
        {
            return -1;
        }
        Statement st;
        try {

            st = con.createStatement();
            String sql = "SELECT count(id) FROM creche_dar_elhadith.eleve where regime = '" + regime + "';";
            ResultSet resultSet = st.executeQuery(sql);
            while (resultSet.next()) {
                count = resultSet.getInt("count(id)");
            }
            return count;
        } catch (SQLException e) {
            System.out.println("Error : " + e.getErrorCode());
            //e.printStackTrace();
            return -1;
        }
    }

    public int countGenderEleve(int gender) {
        Connection con = new ConnectionClasse().getConnection();
        int count = 0;
        if (con == null) // if connection failed
        {
            return -1;
        }
        Statement st;
        try {

            st = con.createStatement();
            String sql = "SELECT count(id) FROM creche_dar_elhadith.eleve where gender = '" + gender + "';";
            ResultSet resultSet = st.executeQuery(sql);
            while (resultSet.next()) {
                count = resultSet.getInt("count(id)");
            }
            return count;
        } catch (SQLException e) {
            System.out.println("Error : " + e.getErrorCode());
            //e.printStackTrace();
            return -1;
        }
    }

    public int countSchoolYear(int anneeScolaire) {
        Connection con = new ConnectionClasse().getConnection();
        int count = 0;
        if (con == null) // if connection failed
        {
            return -1;
        }
        Statement st;
        try {

            st = con.createStatement();
            String sql = "SELECT count(id) FROM creche_dar_elhadith.eleve where anneeScolaire = '" + anneeScolaire + "';";
            ResultSet resultSet = st.executeQuery(sql);
            while (resultSet.next()) {
                count = resultSet.getInt("count(id)");
            }
            return count;
        } catch (SQLException e) {
            System.out.println("Error : " + e.getErrorCode());
            //e.printStackTrace();
            return -1;
        }
    }

    public int removeEleve(int id) {
        Connection con = new ConnectionClasse().getConnection();
        if (con == null) // if connection failed
        {
            return -1;
        }
        Statement st;

        try {
            st = con.createStatement();
            String sql = "DELETE FROM `eleve` WHERE `id`=" + id + ";";
            st.executeUpdate(sql);
            //TODO when the database is done add the cascade to delete the instance from all of the tables
            return 1;
        } catch (SQLException e) {
            //System.out.println("Error : " + e.getErrorCode());
            //e.printStackTrace();
            return 0;
        }

    }

    public int deductTranche(int id, int tranche, double montantreduit) {
        StringBuilder sql = new StringBuilder("UPDATE `eleve` SET ");
        Connection con = null;
        Statement st = null;

        try {
            con = new ConnectionClasse().getConnection();

            if (con == null) {
                return -1;
            }


            st = con.createStatement();

            sql.append("`tranches`=tranches-'").append(tranche);
            sql.append("', `montantRestant`=montantRestant-'").append(montantreduit);

            sql.append("' WHERE `id`=").append(id).append(";");

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


    public boolean eleveExist(int id) {
        Connection con = new ConnectionClasse().getConnection();
        if (con == null) // if connection failed
        {
            return true;
        }
        String sql = "SELECT * FROM `eleve` WHERE id=?;";
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
