package tdc.edu.com.example.soccer_social_network.Data_Models;

public class Price {
    String time;
    String gia;

    public Price(String time, String gia) {
        this.time = time;
        this.gia = gia;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }
}
