package tdc.edu.com.example.soccer_social_network;

public class ImageUplpadInfo {
    String tendoi;
    String doitruong;
    String image;
    String sodienthoai;
    String diachi;
    String ngaythanhlap;

    public ImageUplpadInfo() {
    }

    public ImageUplpadInfo(String tendoi, String doitruong, String image, String sodienthoai, String diachi, String ngaythanhlap) {
        this.tendoi = tendoi;
        this.doitruong = doitruong;
        this.image = image;
        this.sodienthoai = sodienthoai;
        this.diachi = diachi;
        this.ngaythanhlap = ngaythanhlap;
    }

    public String getTendoi() {
        return tendoi;
    }

    public void setTendoi(String tendoi) {
        this.tendoi = tendoi;
    }

    public String getDoitruong() {
        return doitruong;
    }

    public void setDoitruong(String doitruong) {
        this.doitruong = doitruong;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSodienthoai() {
        return sodienthoai;
    }

    public void setSodienthoai(String sodienthoai) {
        this.sodienthoai = sodienthoai;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getNgaythanhlap() {
        return ngaythanhlap;
    }

    public void setNgaythanhlap(String ngaythanhlap) {
        this.ngaythanhlap = ngaythanhlap;
    }
}
