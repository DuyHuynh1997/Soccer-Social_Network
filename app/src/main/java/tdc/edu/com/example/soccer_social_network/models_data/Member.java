package tdc.edu.com.example.soccer_social_network.models_data;

public class Member {
    private int id;
    private String tenmember;
    private String vitri;
    private byte[] image;

    public Member(int id, String tenmember, String vitri, byte[] image) {
        this.id = id;
        this.tenmember = tenmember;
        this.vitri = vitri;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenmember() {
        return tenmember;
    }

    public void setTenmember(String tenmember) {
        this.tenmember = tenmember;
    }

    public String getVitri() {
        return vitri;
    }

    public void setVitri(String vitri) {
        this.vitri = vitri;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
