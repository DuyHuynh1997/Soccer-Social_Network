package tdc.edu.com.example.soccer_social_network.SanBong;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import tdc.edu.com.example.soccer_social_network.R;

public class SanDetailActivity extends AppCompatActivity {
    TextView txtTenSan, txtDiaChiSan,txtSoDienThoaiSan, txtChuSoHuuSan, txtMoTaSan;
    ImageView mImageIvSan;
    String tendoiSan,diachiSan,cImageSan,sodienthoaiSan,doitruongSan,ngaythanhlapSan;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.san_detail_layout);

        ActionBar actionBar = getSupportActionBar();

        actionBar.setTitle("San Detail");

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        txtTenSan = findViewById(R.id.txtTenSan_sanDetail);
        txtDiaChiSan = findViewById(R.id.txtDescription_sanDetail);
        mImageIvSan = findViewById(R.id.imageView_sanDetail);
        txtSoDienThoaiSan = findViewById(R.id.txtSoDienThoai_sanDetail);
        txtChuSoHuuSan = findViewById(R.id.txtChuSoHuu_sanDetail);
        txtMoTaSan = findViewById(R.id.txtMoTa_sanDetail);


        Bundle intent = getIntent().getExtras();
        if(intent != null){
            tendoiSan = intent.getString("tensan");
            diachiSan = intent.getString("diachisan");
            sodienthoaiSan = intent.getString("sodienthoaisan");
            doitruongSan = intent.getString("chusohuusan");
            ngaythanhlapSan = intent.getString("motasan");

            cImageSan = intent.getString("cImagesan");


            txtTenSan.setText(tendoiSan);
            txtDiaChiSan.setText(diachiSan);
            txtSoDienThoaiSan.setText(sodienthoaiSan);
            txtChuSoHuuSan.setText(doitruongSan);
            txtMoTaSan.setText(ngaythanhlapSan);
            Picasso.get().load(cImageSan).into(mImageIvSan);
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
