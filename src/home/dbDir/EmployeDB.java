package home.dbDir;

import home.java.Employe;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeDB {

    public List<Employe> getEmployee() {
        Connection connection = new ConnectionClasse().getConnection();
        if (connection == null) {
            return null;
        }
        String sql = "select * from Employe";
        List<Employe> employes = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                employes.add(new Employe(
                        resultSet.getInt("id"),
                        resultSet.getString("nom"),
                        resultSet.getString("prenom"),
                        resultSet.getDate("dateNaissance"),
                        resultSet.getString("lieuNaissance"),
                        resultSet.getString("adresse"),
                        resultSet.getString("numTelephone"),
                        resultSet.getString("socialSecurityNumber"),
                        resultSet.getString("diplome"),
                        resultSet.getString("experience"),
                        resultSet.getString("itar"),
                        resultSet.getString("renouvlementDeContrat"),
                        resultSet.getDate("dateDebut"),
                        resultSet.getString("fonction"),
                        resultSet.getString("classe"),
                        resultSet.getString("regime"),
                        resultSet.getInt("marier"),
                        resultSet.getString("celibacyTitle"),
                        resultSet.getInt("nombreEnfantM"),
                        resultSet.getInt("nombreEnfantF"),
                        resultSet.getString("remarque")));
            }

        } catch (SQLException ex) {
            return null;
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return employes;
    }

    public int addEmploye(Employe employe) {
        StringBuilder sql = new StringBuilder("INSERT INTO `creche_dar_elhadith`.`employe` ( `id`, `nom`, `prenom`, `dateNaissance`, `lieuNaissance`, `adresse`, `numTelephone`, `socialSecurityNumber`, `diplome`, `experience`, `itar`, `renouvlementDeContrat`, `dateDebut`, `fonction`,`classe`,`regime`,`remarque`, `marier`");
        Connection connection = null;
        Statement st = null;

        try {
            connection = new ConnectionClasse().getConnection();

            if (connection == null) {
                return -1;
            }
            if (employerExist(employe.getId())) {
                return 2;
            }

            st = connection.createStatement();
            if (employe.estmarier()) {
                sql.append(",`celibacyTitle`, `nombreEnfantM`, `nombreEnfantF`");
            }

            sql.append(")values ('");
            sql.append(employe.getId()).append("','");
            sql.append(employe.getNom()).append("','");
            sql.append(employe.getPrenom()).append("','");
            sql.append(employe.getDateNaissance()).append("','");
            sql.append(employe.getLieuNaissance()).append("','");
            sql.append(employe.getAdresse()).append("','");
            sql.append(employe.getNumTelephone()).append("','");
            sql.append(employe.getSocialSecurityNumber()).append("','");
            sql.append(employe.getDiplome()).append("','");
            sql.append(employe.getExperience()).append("','");
            sql.append(employe.getItar()).append("','");
            sql.append(employe.getRenouvlement_de_contrat()).append("','");
            sql.append(employe.getDate_debut()).append("','");
            sql.append(employe.getFonction()).append("','");
            sql.append(employe.getClasse()).append("','");
            sql.append(employe.getRegimeScolaire()).append("','");
            sql.append(employe.getRemarque()).append("','");
            sql.append(employe.getStatuSocial());

            if (employe.estmarier()) {
                sql.append("','").append(employe.getCelibacyTitle()).append("','");
                sql.append(employe.getMaleChild()).append("','");
                sql.append(employe.getFemaleChild());
            }
            sql.append("');");
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

    public int editNote(Employe employe) {
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

                sql.append("`remarque`='").append(employe.getRemarque());
                sql.append("' WHERE `id`=").append(employe.getId()).append(";");
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
                sql.append("', `nom`='").append(employe.getNom());
                sql.append("', `prenom`='").append(employe.getPrenom());
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
                sql.append("', `classe`='").append(employe.getClasse());
                sql.append("', `regime`='").append(employe.getRegimeScolaire());
                sql.append("', `remarque`='").append(employe.getRemarque());
                sql.append("', `marier`='").append(employe.getStatuSocial());

                if (employe.estmarier()) {
                    sql.append("', `celibacyTitle`='").append(employe.getCelibacyTitle());
                    sql.append("', `nombreEnfantM`='").append(employe.getMaleChild());
                    sql.append("', `nombreEnfantF`='").append(employe.getFemaleChild());
                }

                sql.append("' WHERE `id`=").append(employe.getId()).append(";");

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
            return 1;
        } catch (SQLException e) {
            System.out.println("Error : " + e.getErrorCode());
            //e.printStackTrace();
            return 0;
        }

    }

    public int countEmploye(String regime) {
        Connection con = new ConnectionClasse().getConnection();
        int count = 0;
        if (con == null) // if connection failed
        {
            return -1;
        }
        Statement st;
        try {

            st = con.createStatement();
            String sql = "SELECT count(id) FROM creche_dar_elhadith.employe where regime = '" + regime + "';";
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


    private boolean employerExist(int id) {
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
