package home.java;

import javafx.scene.image.Image;

public class PhotoModel {

    private String imageName;
    private String studentInImage;
    private String imageExtension;
    private String imageClasse;
    private Image image;

    public PhotoModel(String imageName, String studentInImage, String imageExtension, String imageClasse, Image image) {
        this.imageName = imageName;
        this.studentInImage = studentInImage;
        this.imageExtension = imageExtension;
        this.imageClasse = imageClasse;
        this.image = image;
    }

    public PhotoModel(String imageName, String studentInImage, String imageExtension, String imageClasse) {
        this.imageName = imageName;
        this.studentInImage = studentInImage;
        this.imageExtension = imageExtension;
        this.imageClasse = imageClasse;
    }

    public PhotoModel() {

    }

    public String getImageExtension() {
        return imageExtension;
    }

    public void setImageExtension(String imageExtension) {
        this.imageExtension = imageExtension;
    }

    public String getStudentInImage() {
        return studentInImage;
    }

    public void setStudentInImage(String studentInImage) {
        this.studentInImage = studentInImage;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageClasse() {
        return imageClasse;
    }

    public void setImageClasse(String imageClasse) {
        this.imageClasse = imageClasse;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
