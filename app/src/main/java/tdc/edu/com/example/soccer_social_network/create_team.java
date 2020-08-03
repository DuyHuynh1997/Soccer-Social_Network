package tdc.edu.com.example.soccer_social_network;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

public class create_team extends AppCompatActivity {
    EditText edtTaoTenDoi, edtTaoGhiChu;
    TextView txtTrangThai;
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
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("ĐỘi");


        edtTaoTenDoi = (EditText) (findViewById(R.id.edtTaoTenDoi));
        edtTaoGhiChu = (EditText) (findViewById(R.id.edtTaoGhiChu));
        txtTrangThai = (TextView) (findViewById(R.id.txtTaoTrangThai));
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
                if (uploadTask != null && uploadTask.isInProgress()){
                    Toast.makeText(create_team.this,"Upload in progress",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Fileuploader();
                }
            }
        });
    }
    private String getExtension(Uri uri){
            ContentResolver cr = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }

    private void Fileuploader(){
        if(imguri != null) {
            StorageReference Ref = mStorageRef.child(System.currentTimeMillis() + "." + getExtension(imguri));
            uploadTask = Ref.putFile(imguri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                            Handler handler = new Handler();
//                            handler.postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    uploadProgress.setProgress(0);
//                                }
//                            }, 500);
                            // Get a URL to the uploaded content
                            //Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            Toast.makeText(create_team.this, "Image uploaded successfully", Toast.LENGTH_LONG).show();
                            Doi doi = new Doi(edtTaoTenDoi.getText().toString().trim(), edtTaoGhiChu.getText().toString().trim(), txtTrangThai.getText().toString().trim(), taskSnapshot.getUploadSessionUri().toString());
                            String uploadID = mDatabaseRef.push().getKey();
                            mDatabaseRef.child(uploadID).setValue(doi);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Toast.makeText(create_team.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
//                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
//                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
//                            uploadProgress.setProgress((int) progress);
//                        }
//                    });
        }
        else {
            Toast.makeText(create_team.this,"Chua chon anh !",Toast.LENGTH_SHORT).show();
        }
    }

    private void Filechooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1  && resultCode == RESULT_OK && data != null && data.getData() != null){
            imguri = data.getData();
            imgAnhDoi.setImageURI(imguri);
        }
    }
}
