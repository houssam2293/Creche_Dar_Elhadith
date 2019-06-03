package home.dbDir;

import home.java.PhotoModel;

import java.sql.*;

public class ImageDB {

    public PhotoModel getImageInfo(String classe) {
        PhotoModel image = new PhotoModel();
        Connection connection = new ConnectionClasse().getConnection();
        if (connection == null) {
            return null;
        }
        String sql = "select * from images where imageClasse='" + classe + "'";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {

                image.setImageName(resultSet.getString("imageName"));
                image.setStudentInImage(resultSet.getString("studentInimage"));
                image.setImageExtension(resultSet.getString("imagesExtension"));
                image.setImageClasse(resultSet.getString("imageClasse"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            ex.getMessage();
        }

        return image;
    }

    public int addImage(PhotoModel image) {
        StringBuilder sql = new StringBuilder("INSERT INTO `creche_dar_elhadith`.`images` ( `imageName`,`studentInimage`,`imagesExtension`,`imageClasse`");
        Connection connection = null;
        Statement st = null;

        try {
            connection = new ConnectionClasse().getConnection();

            if (connection == null) {
                return -1;
            }
            if ((imageExist(image.getImageName(), image.getStudentInImage(), image.getImageExtension(), image.getImageClasse()))) {
                return 2;
            }
            st = connection.createStatement();
            sql.append(")values ('");
            sql.append(image.getImageName()).append("','");
            sql.append(image.getStudentInImage()).append("','");
            sql.append(image.getImageExtension()).append("','");
            sql.append(image.getImageClasse()).append("');");
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

    public int editImage(PhotoModel image, PhotoModel updatedImage) {
        StringBuilder sql = new StringBuilder("UPDATE `images` SET ");
        Connection con = null;
        Statement st = null;

        try {
            con = new ConnectionClasse().getConnection();

            if (con == null) {
                return -1;
            }

            if (imageExist(image.getImageName(), image.getStudentInImage(), image.getImageExtension(), image.getImageClasse())) {

                st = con.createStatement();

                sql.append("`imageName`").append(image.getImageName());
                sql.append("', `studentInimage`").append(image.getStudentInImage());
                sql.append("', `imagesExtension`").append(image.getImageExtension());
                sql.append("', `imageClasse`").append(image.getImageClasse());

                sql.append("' where (`imageName`='").append(updatedImage.getImageName()).append("') and(");
                sql.append("`studentInimage`='").append(updatedImage.getStudentInImage()).append("') and(");
                sql.append("`imagesExtension`='").append(updatedImage.getImageExtension()).append("');");
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

    private boolean imageExist(String imageName, String studentInImage, String imageExtension, String imageClasse) {
        Connection con = new ConnectionClasse().getConnection();
        if (con == null) // if connection failed
        {
            return true;
        }
        String sql = "SELECT * FROM `images` WHERE (imageName=?) AND (studentInimage=?) AND (imageExtension=?) AND(imageClasse=?);";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, String.valueOf(imageName));
            ps.setString(2, String.valueOf(studentInImage));
            ps.setString(3, imageExtension);
            ps.setString(4, imageClasse);
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