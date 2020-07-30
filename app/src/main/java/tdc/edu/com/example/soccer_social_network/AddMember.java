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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class AddMember extends AppCompatActivity {
    EditText edtHoTen, edtViTri;
    Button btnMemberList, btnAddMember;
    ImageView imageView;

    //public static SQLiteHelper sqLiteHelper;
    final int REQUEST_CODE_GALLERY = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.member_add_layout );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );

        init();
        //sqLiteHelper.queryData( "CREATE TABLE IF NOT EXISTS TEAM (Id INTEGER PRIMARY KEY AUTOINCREMENT, tendoi VARCHAR, diachi VARCHAR, image BLOB)" );

        imageView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(
                        AddMember.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY
                );
            }
        } );
        btnAddMember.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    MainActivity.sqLiteHelper.insertDataMember(
                            edtHoTen.getText().toString().trim(),
                            edtViTri.getText().toString().trim(),
                            imageViewToByte(imageView)
                    );
                    Toast.makeText(getApplicationContext(),"Added successfully",Toast.LENGTH_SHORT ).show();
                    edtHoTen.setText( "" );
                    edtViTri.setText( "" );
                    imageView.setImageResource( R.mipmap.ic_launcher );
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
        } );
        btnMemberList.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( AddMember.this, MemberList.class);
                startActivity(intent);
            }
        } );
    }

    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CODE_GALLERY)
        {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent( Intent.ACTION_PICK );
                intent.setType( "image/*" );
                startActivityForResult( intent,REQUEST_CODE_GALLERY );
            }
            else {
                Toast.makeText(getApplicationContext(), "You don't have permission to access file location!",Toast.LENGTH_SHORT ).show();
            }
            return;
        }

        super.onRequestPermissionsResult( requestCode, permissions, grantResults );
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE_GALLERY && resultCode == Activity.RESULT_OK && data != null)
        {
            Uri uri = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream( uri );
                Bitmap bitmap = BitmapFactory.decodeStream( inputStream );
                imageView.setImageBitmap( bitmap );
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult( requestCode, resultCode, data );
    }



    private void init()
    {
        edtHoTen = findViewById( R.id.edtTenMember_addmember );
        edtViTri = findViewById( R.id.edtViTriChinh_addmember );
        btnAddMember = findViewById( R.id.btnAddMember_addmember );
        imageView = findViewById( R.id.imageView_addMember );
        btnMemberList = findViewById( R.id.btnMemberList_addmember );
    }
}
