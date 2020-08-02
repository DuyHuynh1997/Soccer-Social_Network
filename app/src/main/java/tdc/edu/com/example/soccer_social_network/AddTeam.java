package tdc.edu.com.example.soccer_social_network;


import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


public class AddTeam extends AppCompatActivity {
    EditText edtTenDoi,edtDoiTruong,edtDiaChi,edtSoDienThoai,edtNgayThanhLap;
    Button btnAddTeam;
    ImageView imageView;


    //Folder path for Firebase Storage
    String mStoragePath = "All_Image_Uploads/";
    //Rôt Database name for firebase daabase
    String mDatabasePath = "Data";
    //Creating URI
    Uri mFilePathUri;

    //Createting StorageRence
    StorageReference mStorageReference;
    DatabaseReference mDatabaseReference;
    ProgressDialog mProgressDialog;

    int IMAGE_REQUEST_CODE = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_team_layout);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Add New Team");

        edtTenDoi = findViewById(R.id.edtTenDoi_addTeam);
        edtDoiTruong = findViewById(R.id.edtDoiTruong_addTeam);
        edtDiaChi = findViewById(R.id.edtDiaChi_addTeam);
        edtSoDienThoai = findViewById(R.id.edtSoDienThoai_addTeam);
        btnAddTeam = findViewById(R.id.btnAddTeam_addTeam);
        imageView = findViewById(R.id.imageView_addTeam);
        edtNgayThanhLap = findViewById(R.id.edtNgayThanhLap_addTeam);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //creating intent
                Intent intent = new Intent();
                //seting intent type as image to select image from phone storage
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Image"),IMAGE_REQUEST_CODE);
            }
        });

        btnAddTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //call method to upload data to firebase
                uploadDataToFirebase();
            }
        });

        mStorageReference = FirebaseStorage.getInstance().getReference();
        //assign FirebaseDatabase instance with root database name
        mDatabaseReference = FirebaseDatabase.getInstance().getReference(mDatabasePath);

        //progress dialog
        mProgressDialog = new ProgressDialog(AddTeam.this);
    }

    private void uploadDataToFirebase() {
            // check whether filepathuri is empty or not
        if(mFilePathUri != null)
        {
            //setting progress bar title
            mProgressDialog.setTitle("Uploading...");
            mProgressDialog.show();
            //create second storageReference
            StorageReference storageReference2nd = mStorageReference.child(mStoragePath + System.currentTimeMillis()+ "."+ getFileExtension(mFilePathUri));

            //adding addOnSuccessListener to storageReference2nd
            storageReference2nd.putFile(mFilePathUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!urlTask.isSuccessful());
                            Uri downloadUrl = urlTask.getResult();
                            //get title
                            String tendoi = edtTenDoi.getText().toString().trim();
                            String doitruong = edtDoiTruong.getText().toString().trim();
                            String sodienthoai = edtSoDienThoai.getText().toString().trim();
                            String diachi = edtDiaChi.getText().toString().trim();
                            String ngaythanhlap = edtNgayThanhLap.getText().toString().trim();
                            mProgressDialog.dismiss();
                            Toast.makeText(AddTeam.this,"Uploaded succcesslly",Toast.LENGTH_SHORT).show();
                            ImageUplpadInfo imageUplpadInfo = new ImageUplpadInfo(tendoi,doitruong,downloadUrl.toString(),sodienthoai,diachi,ngaythanhlap);

                            String imageUploadId = mDatabaseReference.push().getKey();
                            //adding image upload id's child element into databaseRefrence
                            mDatabaseReference.child(imageUploadId).setValue(imageUplpadInfo);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //hide progress dialog
                            mProgressDialog.dismiss();
                            //show error toast
                            Toast.makeText(AddTeam.this,e.getMessage(),Toast.LENGTH_SHORT).show();

                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                            mProgressDialog.setTitle("Uploading...");
                        }
                    });

        }
        else {
            Toast.makeText(this,"Please select image or add image name",Toast.LENGTH_SHORT).show();
        }
    }

    //method to get the selected image file extension from file path uri
    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == IMAGE_REQUEST_CODE
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null){
            mFilePathUri = data.getData();

            try {
                //getting image into bitmap
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), mFilePathUri);
                imageView.setImageBitmap(bitmap);
            }
            catch (Exception e)
            {
                Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }
    }
}
