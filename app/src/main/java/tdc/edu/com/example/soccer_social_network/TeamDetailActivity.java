package tdc.edu.com.example.soccer_social_network;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class TeamDetailActivity extends AppCompatActivity {
    TextView mTitleTv, mDetailTv;
    ImageView mImageIv;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_detail_layout);

        ActionBar actionBar = getSupportActionBar();

        actionBar.setTitle("Team Detail");

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        mTitleTv = findViewById(R.id.txtTitle);
        mDetailTv = findViewById(R.id.txtDescription);
        mImageIv = findViewById(R.id.imageView);


        byte[] bytes = getIntent().getByteArrayExtra("image");
        String title = getIntent().getStringExtra("tendoi");
        String desc = getIntent().getStringExtra("diachi");
       // Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 100, bytes.length);


        mTitleTv.setText(title);
        mDetailTv.setText(desc);
       // mImageIv.setImageBitmap(bmp);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
