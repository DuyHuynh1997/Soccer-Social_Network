package tdc.edu.com.example.soccer_social_network;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class detail_about_football_pitches extends AppCompatActivity {
    private TextView txtTenSan,txtGioiThieuSan,txtDiaChiSan,txtSoLuongSan,txtLoaiSan,txtSoDienThoaiSan;
    String txtHinhSan;
    private ImageView imgHinhSan;
    San san ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_football_pitches);

        txtTenSan = (TextView) (findViewById(R.id.txtDetail_TenSan));
        txtGioiThieuSan = (TextView) (findViewById(R.id.txtDetail_txtGioiThieuSan));
        txtDiaChiSan = (TextView) (findViewById(R.id.txtDetail_DiaChiSan));
        txtSoLuongSan = (TextView) (findViewById(R.id.txtDetail_SLSan));
        txtLoaiSan = (TextView) (findViewById(R.id.txtDetail_LoaiSan));
        txtSoDienThoaiSan = (TextView) (findViewById(R.id.txtDetail_txtSDTSan));
        imgHinhSan = (ImageView)(findViewById(R.id.imgDetail_anhSan));

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        san =  (San) bundle.getSerializable("Pitches");
        txtTenSan.setText(san.getsTenSan().toString().trim());
        txtGioiThieuSan.setText(san.getsGioiThieuSan().toString().trim());
        txtDiaChiSan.setText(san.getsDiaChiSan().toString().trim());
        txtSoLuongSan.setText(san.getsSLSan().toString().trim());
        txtDiaChiSan.setText(san.getsDiaChiSan().toString().trim());
        txtLoaiSan.setText(san.getsLoaiSan().toString().trim());
        txtSoDienThoaiSan.setText(san.getsSDTSan().toString().trim());
        txtHinhSan = san.getsAnhSan().toString().trim();
        Glide.with(detail_about_football_pitches.this).load(txtHinhSan).into(imgHinhSan);
    }
}
