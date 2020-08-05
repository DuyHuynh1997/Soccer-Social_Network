package tdc.edu.com.example.soccer_social_network;

import java.io.Serializable;

public class San implements Serializable {
    private String sTenSan;
    private String sLoaiSan;
    private String sSLSan;
    private String sSDTSan;
    private String sDiaChiSan;
    private String sAnhSan;
    private String sGioiThieuSan;
    private String sIDSan;

    public String getsIDSan() {
        return sIDSan;
    }

    public void setsIDSan(String sIDSan) {
        this.sIDSan = sIDSan;
    }


    public San() {

    }

    public San(String sTenSan, String sSLSan, String sLoaiSan, String sSDTSan, String sDiaChiSan, String sGioiThieuSan, String sAnhSan, String sIDSan) {
        if (sTenSan.trim().equals("")) {
            sTenSan = "No name";
        }
        this.sAnhSan = sAnhSan;
        this.sDiaChiSan = sDiaChiSan;
        this.sLoaiSan = sLoaiSan;
        this.sSDTSan = sSDTSan;
        this.sSLSan = sSLSan;
        this.sTenSan = sTenSan;
        this.sGioiThieuSan = sGioiThieuSan;
        this.sIDSan = sIDSan;
    }

    public String getsTenSan() {
        return sTenSan;
    }

    public void setsTenSan(String sTenSan) {
        this.sTenSan = sTenSan;
    }

    public String getsLoaiSan() {
        return sLoaiSan;
    }

    public void setsLoaiSan(String sLoaiSan) {
        this.sLoaiSan = sLoaiSan;
    }

    public String getsSLSan() {
        return sSLSan;
    }

    public void setsSLSan(String sSLSan) {
        this.sSLSan = sSLSan;
    }

    public String getsSDTSan() {
        return sSDTSan;
    }

    public void setsSDTSan(String sSDTSan) {
        this.sSDTSan = sSDTSan;
    }

    public String getsDiaChiSan() {
        return sDiaChiSan;
    }

    public void setsDiaChiSan(String sDiaChiSan) {
        this.sDiaChiSan = sDiaChiSan;
    }

    public String getsAnhSan() {
        return sAnhSan;
    }

    public void setsAnhSan(String sAnhSan) {
        this.sAnhSan = sAnhSan;
    }

    public String getsGioiThieuSan() {
        return sGioiThieuSan;
    }

    public void setsGioiThieuSan(String sGioiThieuSan) {
        this.sGioiThieuSan = sGioiThieuSan;
    }
}
