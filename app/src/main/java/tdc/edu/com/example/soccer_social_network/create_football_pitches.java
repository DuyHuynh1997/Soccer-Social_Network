package tdc.edu.com.example.soccer_social_network;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

public class create_football_pitches extends AppCompatActivity {
    EditText edtTaoTenSan, edtGioiThieuSan,edttrangThaiSan,edtSoienThoaiSan, edtSoLuongSan,edtLoaiSan,edtDiaChiSan;
    Button btnChonAnhSan, btnTaoSan;
    StorageReference mStorageRef;
    private StorageTask uploadTask;
    ImageView imgAnhSan;
    public Uri imguri;
    private DatabaseReference mDatabaseRef;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_football_pitches);

        mStorageRef = FirebaseStorage.getInstance().getReference("ImagePitches");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("San");


        edtTaoTenSan = (EditText) (findViewById(R.id.edtnhapTenSan));
        edtGioiThieuSan = (EditText) (findViewById(R.id.edtNhapGioiThieuSan));
        edttrangThaiSan = (EditText) (findViewById(R.id.edtTaoTrangThaiSan));
        edtSoienThoaiSan = (EditText) (findViewById(R.id.edtNhapSDTSan));
        edtSoLuongSan = (EditText) (findViewById(R.id.edtNhapSLSan));
        edtDiaChiSan = (EditText) (findViewById(R.id.edtNhapDiaChiSan));
        edtLoaiSan = (EditText) (findViewById(R.id.edtNhapLoaiSan));
        btnChonAnhSan = (Button) (findViewById(R.id.btnChonAnhSan));
        btnTaoSan = (Button) (findViewById(R.id.btnTaoSan));
        imgAnhSan = (ImageView) (findViewById(R.id.imgChonAnhSan));


        btnChonAnhSan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Filechooser();

            }
        });
        btnTaoSan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (uploadTask != null && uploadTask.isInProgress()) {
                    Toast.makeText(create_football_pitches.this, "Upload in progress", Toast.LENGTH_LONG).show();
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

                                    Toast.makeText(create_football_pitches.this, "Image uploaded successfully", Toast.LENGTH_LONG).show();
                                    String uploadID = mDatabaseRef.push().getKey();
                                    San san  = new San(edtTaoTenSan.getText().toString().trim(),edtSoLuongSan.getText().toString().trim(),edtLoaiSan.getText().toString().trim(), edtSoienThoaiSan.getText().toString().trim(), edtDiaChiSan.getText().toString().trim(), edtGioiThieuSan.getText().toString().trim(), uri.toString().trim(),uploadID);
                                    san.setsIDSan(uploadID);
                                    mDatabaseRef.child(uploadID).setValue(san);
                                }
                            });

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Toast.makeText(create_football_pitches.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        } else {
            Toast.makeText(create_football_pitches.this, "Chua chon anh !", Toast.LENGTH_SHORT).show();
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
            Glide.with(this).load(imguri).into(imgAnhSan);
        }
    }
}
