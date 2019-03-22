package home.dbDir;

import com.mysql.cj.xdevapi.SqlDataResult;
import home.java.Employe;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeDB {

    public List<Employe> getEmploye() {
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
                employes.add(new Employe(resultSet.getInt("num"),
                        resultSet.getString("id"),
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
                        resultSet.getBoolean("marier"),
                        resultSet.getString("celibacyTitle"),
                        resultSet.getInt("nombreEnfantM"),
                        resultSet.getInt("nombreEnfantF")));
            }

        } catch (SQLException ex) {
            return null;
        }

        return employes;
    }

    public int addEmploye(Employe employe) {
        StringBuilder sql = new StringBuilder("INSERT INTO 'employe' ('num','id','nom','prenom','dateNaissance'," +
                "'lieuNaissance','adresse','numTelephone','socialSecurityNumber'," +
                "'diplome','experience','itar','renouvlementDeContrat','dateDebut'," +
                "'fonction','marier'");
        Connection connection = null;
        Statement st = null;

        try {
            connection = new ConnectionClasse().getConnection();

            if (connection == null) {
                return -1;
            }
            if (employerExist(employe.getNom())) {
                return 2;
            }

            st = connection.createStatement();
            if (!employe.isStatuSocial()) {
                sql.append(",'celibacyTitle','nombreEnfantM','nombreEnfantF'");
            }

            sql.append(") values ('").append(employe.getNum()).append("','");
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
            sql.append(employe.isStatuSocial() ? 1 : 0);

            if (!employe.isStatuSocial()) {
                sql.append("','").append(employe.getCelibacyTitle()).append("','");
                sql.append(employe.getMaleChild()).append("','");
                sql.append(employe.getFemaleChild());
            }
            sql.append("');");
            st.executeUpdate(sql.toString());


        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }finally {
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


    public boolean employerExist(String username) {
        Connection con = new ConnectionClasse().getConnection();
        if (con == null) // if connection failed
        {
            return true;
        }
        String sql = "SELECT * FROM `users` WHERE username=?;";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);
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
