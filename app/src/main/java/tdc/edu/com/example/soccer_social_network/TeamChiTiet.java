package tdc.edu.com.example.soccer_social_network;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class TeamChiTiet extends AppCompatActivity {
    TextView edtTenDoi, edtDiaChi;
    Button btnThanhVien;
    ImageView imageView;

    //public static SQLiteHelper sqLiteHelper;
    final int REQUEST_CODE_GALLERY = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.chitiet_menu_add_team );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );

        init();

        btnThanhVien.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getIntent();
                Intent intent = new Intent( TeamChiTiet.this, MemberListView.class);
                startActivity(intent);
            }
        } );
    }



    private void init()
    {
        btnThanhVien = findViewById( R.id.btnThanhVien_addTeamChiTiet );
        edtTenDoi = findViewById( R.id.edtTenDoi_addTeamChiTiet );
        edtDiaChi = findViewById( R.id.edtDiaChi_addTeamChiTiet );
        imageView = findViewById( R.id.imageView_addTeamChiTiet );
    }
}
