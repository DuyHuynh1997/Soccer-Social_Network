package tdc.edu.com.example.soccer_social_network;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;


public class detail_about_team extends AppCompatActivity {
    private TextView txtTenDoi,txtGioiThieu,txtSDT,txtThanhVien;
    String txtHinhDoi;
    private ImageView imgHinhDoi;
    Doi doi ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_team);
        txtGioiThieu = (TextView) (findViewById(R.id.txtDetail_GioiThieu));
        txtThanhVien = (TextView) (findViewById(R.id.txtDetail_ThanhVien));
        txtSDT = (TextView) (findViewById(R.id.txtDetail_SDT));
        txtTenDoi = (TextView) (findViewById(R.id.txtDetail_TenDoi));
        imgHinhDoi = (ImageView)(findViewById(R.id.imgDetail_anhdoi));

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
       doi =  (Doi)bundle.getSerializable("Team");
        txtGioiThieu.setText(doi.getGioiThieu().toString().trim());
        txtThanhVien.setText(doi.getThanhVien().toString().trim());
        txtTenDoi.setText(doi.getTenDoi().toString().trim());
        txtSDT.setText(doi.getSoDienThoai().toString().trim());
        txtHinhDoi = doi.getUlrAnhDoi().toString().trim();
        Glide.with(detail_about_team.this).load(txtHinhDoi).into(imgHinhDoi);
    }
}
