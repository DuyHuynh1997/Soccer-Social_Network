package tdc.edu.com.example.soccer_social_network;

public class Doi {
    protected String tenDoi;
    protected String gioiThieu;
    protected String trangThai;

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getThanhVien() {
        return thanhVien;
    }

    public void setThanhVien(String thanhVien) {
        this.thanhVien = thanhVien;
    }

    protected String soDienThoai;
    protected String thanhVien;
    protected String ulrAnhDoi;

    public String getIdDoi() {
        return idDoi;
    }

    public void setIdDoi(String idDoi) {
        this.idDoi = idDoi;
    }

    protected String idDoi;

    public Doi(){

    }
    public Doi(String tenDoi, String sdt, String thanhVien,String gioiThieu, String trangThai, String urlAnhDoi,String idDoi){
        if (tenDoi.trim().equals("")){
            tenDoi = "No name";
        }
        this.soDienThoai = sdt;
        this.thanhVien = thanhVien;
        this.trangThai = trangThai;
        this.tenDoi = tenDoi;
        this.gioiThieu = gioiThieu;
        this.ulrAnhDoi = urlAnhDoi;
        this.idDoi = idDoi;
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
