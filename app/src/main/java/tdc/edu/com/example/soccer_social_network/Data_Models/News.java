package tdc.edu.com.example.soccer_social_network.Data_Models;

public class News {
    String date;
    String khuyenMai;

    public News(String date, String khuyenMai) {
        this.date = date;
        this.khuyenMai = khuyenMai;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getKhuyenMai() {
        return khuyenMai;
    }

    public void setKhuyenMai(String khuyenMai) {
        this.khuyenMai = khuyenMai;
    }
}
