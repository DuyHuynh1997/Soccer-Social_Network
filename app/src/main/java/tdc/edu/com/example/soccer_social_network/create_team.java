package tdc.edu.com.example.soccer_social_network;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.PicassoProvider;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

public class create_team extends AppCompatActivity {
    EditText edtTaoTenDoi, edtTaoGhiChu,edttrangThai,edtSoienThoai, edtThanhVien;
    Button btnChonAnh, btnTaoDoi;
    StorageReference mStorageRef;
    private StorageTask uploadTask;
    ImageView imgAnhDoi;
    public Uri imguri;
    private ProgressBar uploadProgress;
    private DatabaseReference mDatabaseRef;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ativity_create_team);
        mStorageRef = FirebaseStorage.getInstance().getReference("ImageTeams");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Doi");


        edtTaoTenDoi = (EditText) (findViewById(R.id.edtTaoTenDoi));
        edtTaoGhiChu = (EditText) (findViewById(R.id.edtTaoGhiChu));
        edttrangThai = (EditText) (findViewById(R.id.edtTaoTrangThai));
        edtSoienThoai = (EditText) (findViewById(R.id.edtSDT));
        edtThanhVien = (EditText) (findViewById(R.id.edtThanhVienDoi));
        btnChonAnh = (Button) (findViewById(R.id.btnChonAnh));
        btnTaoDoi = (Button) (findViewById(R.id.btnTaoDoi));
        imgAnhDoi = (ImageView) (findViewById(R.id.imgChonAnhDoi));


        btnChonAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Filechooser();

            }
        });
        btnTaoDoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (uploadTask != null && uploadTask.isInProgress()) {
                    Toast.makeText(create_team.this, "Upload in progress", Toast.LENGTH_LONG).show();
                } else {
                    Fileuploader();
                }
            }
        });
    }

    public String getExtension(Uri uri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }

    public void Fileuploader() {
        if (imguri != null) {
            final StorageReference Ref = mStorageRef.child(System.currentTimeMillis()+"."+getExtension(imguri));
            Ref.putFile(imguri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    Toast.makeText(create_team.this, "Image uploaded successfully", Toast.LENGTH_LONG).show();
                                    String uploadID = mDatabaseRef.push().getKey();
                                    Doi doi = new Doi(edtTaoTenDoi.getText().toString().trim(),edtSoienThoai.getText().toString().trim(),edtThanhVien.getText().toString().trim(), edtTaoGhiChu.getText().toString().trim(), edttrangThai.getText().toString().trim(), uri.toString().trim(),uploadID);
                                    doi.setIdDoi(uploadID);
                                    mDatabaseRef.child(uploadID).setValue(doi);
                                }
                            });

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Toast.makeText(create_team.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        } else {
            Toast.makeText(create_team.this, "Chua chon anh !", Toast.LENGTH_SHORT).show();
        }
    }

    private void Filechooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imguri = data.getData();
            Glide.with(this).load(imguri).into(imgAnhDoi);
        }
    }
}
