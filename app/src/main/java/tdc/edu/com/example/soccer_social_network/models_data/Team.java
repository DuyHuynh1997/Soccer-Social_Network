package tdc.edu.com.example.soccer_social_network.models_data;

public class Team {
    private int id;
    private String tenDoi;
    private String diaChi;
    private byte[] image;

    public Team(int id, String tenDoi, String diaChi, byte[] image) {
        this.id = id;
        this.tenDoi = tenDoi;
        this.diaChi = diaChi;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenDoi() {
        return tenDoi;
    }

    public void setTenDoi(String tenDoi) {
        this.tenDoi = tenDoi;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
