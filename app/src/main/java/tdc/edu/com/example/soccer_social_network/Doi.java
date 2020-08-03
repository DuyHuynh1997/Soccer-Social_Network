package tdc.edu.com.example.soccer_social_network;

public class Doi {
    protected String tenDoi;
    protected String gioiThieu;
    protected String trangThai;
    protected String ulrAnhDoi;

    public Doi(){

    }
    public Doi(String tenDoi, String gioiThieu, String trangThai, String urlAnhDoi){
        if (tenDoi.trim().equals("")){
            tenDoi = "No name";
        }
        this.trangThai = trangThai;
        this.tenDoi = tenDoi;
        this.gioiThieu = gioiThieu;
        this.ulrAnhDoi = urlAnhDoi;
    }


    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getTenDoi() {
        return tenDoi;
    }

    public void setTenDoi(String tenDoi) {
        this.tenDoi = tenDoi;
    }

    public String getGioiThieu() {
        return gioiThieu;
    }

    public void setGioiThieu(String gioiThieu) {
        this.gioiThieu = gioiThieu;
    }

    public String getUlrAnhDoi() {
        return ulrAnhDoi;
    }

    public void setUlrAnhDoi(String ulrAnhDoi) {
        this.ulrAnhDoi = ulrAnhDoi;
    }
}
