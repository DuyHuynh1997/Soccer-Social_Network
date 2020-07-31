package tdc.edu.com.example.soccer_social_network.Data_Models;

public class SoccerField {
    int id;
    String nameSan;
    String area;
    byte[] image;

    public SoccerField(int id,String nameSan, String area, byte[] image) {
        this.id = id;
        this.nameSan = nameSan;
        this.area = area;
        this.image = image;
    }

    public String getNameSan() {
        return nameSan;
    }

    public void setNameSan(String nameSan) {
        this.nameSan = nameSan;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
