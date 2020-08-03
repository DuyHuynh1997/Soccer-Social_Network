package tdc.edu.com.example.soccer_social_network;

import android.widget.ImageView;

public class Doi {
    protected String tenDoi;
    protected String ghiChu;
    protected String trangThai;
    protected String ulrAnhDoi;

    public Doi(){

    }
    public Doi(String tenDoi, String ghiChu,String trangThai,String urlAnhDoi){
        if (tenDoi.trim().equals("")){
            tenDoi = "No name";
        }
        this.trangThai = trangThai;
        this.tenDoi = tenDoi;
        this.ghiChu = ghiChu;
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

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public String getUlrAnhDoi() {
        return ulrAnhDoi;
    }

    public void setUlrAnhDoi(String ulrAnhDoi) {
        this.ulrAnhDoi = ulrAnhDoi;
    }
}
