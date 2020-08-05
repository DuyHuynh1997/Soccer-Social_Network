package tdc.edu.com.example.soccer_social_network.SanBong;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import tdc.edu.com.example.soccer_social_network.R;

public class SanDetailActivity extends AppCompatActivity {
    TextView txtTenDoiSan, txtDiaChiSan,txtSoDienThoaiSan,txtDoiTruongSan,txtNgayThanhLapSan;
    ImageView mImageIvSan;
    String tendoiSan,diachiSan,cImageSan,sodienthoaiSan,doitruongSan,ngaythanhlapSan;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.san_detail_layout);

        ActionBar actionBar = getSupportActionBar();

        actionBar.setTitle("San Detail");

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        txtTenDoiSan = findViewById(R.id.txtTitle_sanDetail);
        txtDiaChiSan = findViewById(R.id.txtDescription_sanDetail);
        mImageIvSan = findViewById(R.id.imageView_sanDetail);
        txtSoDienThoaiSan = findViewById(R.id.txtSoDienThoai_sanDetail);
        txtDoiTruongSan = findViewById(R.id.txtDoiTruong_sanDetail);
        txtNgayThanhLapSan = findViewById(R.id.txtNgayThanhLap_sanDetail);


        Bundle intent = getIntent().getExtras();
        if(intent != null){
            tendoiSan = intent.getString("tendoisan");
            diachiSan = intent.getString("diachisan");
            sodienthoaiSan = intent.getString("sodienthoaisan");
            doitruongSan = intent.getString("doitruongsan");
            ngaythanhlapSan = intent.getString("ngaythanhlapsan");

            cImageSan = intent.getString("cImagesan");


            txtTenDoiSan.setText(tendoiSan);
            txtDiaChiSan.setText(diachiSan);
            txtSoDienThoaiSan.setText(sodienthoaiSan);
            txtDoiTruongSan.setText(doitruongSan);
            txtNgayThanhLapSan.setText(ngaythanhlapSan);
            Picasso.get().load(cImageSan).into(mImageIvSan);
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
