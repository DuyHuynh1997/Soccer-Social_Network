package tdc.edu.com.example.soccer_social_network;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class TeamDetailActivity extends AppCompatActivity {
    TextView edtTenDoi, edtDiaChi,txtSoDienThoai;
    ImageView mImageIv;
    String tendoi,diachi,cImage,sodienthoai;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_detail_layout);

        ActionBar actionBar = getSupportActionBar();

        actionBar.setTitle("Team Detail");

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        edtTenDoi = findViewById(R.id.txtTitle_teamDetail);
        edtDiaChi = findViewById(R.id.txtDescription_teamDetail);
        mImageIv = findViewById(R.id.imageView_teamDetail);
        txtSoDienThoai = findViewById(R.id.txtSoDienThoai_teamDetail);

        Bundle intent = getIntent().getExtras();
        if(intent != null){
            tendoi = intent.getString("tendoi");
            diachi = intent.getString("diachi");
            sodienthoai = intent.getString("sodienthoai");
            cImage = intent.getString("cImage");


            edtTenDoi.setText(tendoi);
            edtDiaChi.setText(diachi);
            txtSoDienThoai.setText(sodienthoai);
            Picasso.get().load(cImage).into(mImageIv);
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
