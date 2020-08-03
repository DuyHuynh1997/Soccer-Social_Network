package tdc.edu.com.example.soccer_social_network;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;


public class AddTeam extends AppCompatActivity{
    EditText edtTenDoi,edtDoiTruong,edtDiaChi,edtSoDienThoai,edtNgayThanhLap;
    Button btnAddTeam;
    ImageView imageView;
   DatePickerDialog.OnDateSetListener setListener;

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

    String sTenDoi, sDiaChi, cImage;

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

        Bundle intent = getIntent().getExtras();
        if(intent != null){
            sTenDoi = intent.getString("tendoi");
            sDiaChi = intent.getString("diachi");
            cImage = intent.getString("cImage");

            edtTenDoi.setText(sTenDoi);
            edtDiaChi.setText(sDiaChi);
            Picasso.get().load(cImage).into(imageView);
            actionBar.setTitle("Update");
            btnAddTeam.setText("Update");

        }



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
                if(btnAddTeam.getText().equals("AddTeam"))
                {
                    uploadDataToFirebase();
                }
                else {
                    //begin update
                    beginUpdate();
                }


            }
        });

        mStorageReference = FirebaseStorage.getInstance().getReference();
        //assign FirebaseDatabase instance with root database name
        mDatabaseReference = FirebaseDatabase.getInstance().getReference(mDatabasePath);

        //progress dialog
        mProgressDialog = new ProgressDialog(AddTeam.this);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

       edtNgayThanhLap.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               DatePickerDialog datePickerDialog = new DatePickerDialog(
                       AddTeam.this, new DatePickerDialog.OnDateSetListener() {
                   @Override
                   public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                       month = month+1;
                       String date = day+"/"+month+"/"+year;
                       edtNgayThanhLap.setText(date);
                   }
               },year,month,day);
               datePickerDialog.show();

           }
       });



    }

    private void beginUpdate() {
        mProgressDialog.setMessage("Updating...");
        mProgressDialog.show();


        deletePreviousImage();
    }

    private void deletePreviousImage() {
        StorageReference mPictureRef = FirebaseStorage.getInstance().getReferenceFromUrl(cImage);
        mPictureRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(AddTeam.this,"Previous image deleted...",Toast.LENGTH_SHORT).show();
                uploadNewImage();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddTeam.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                mProgressDialog.dismiss();
            }
        });
    }

    private void uploadNewImage() {
        String imageName = System.currentTimeMillis()+ ".png";
        //storage reference
        StorageReference storageReference2 = mStorageReference.child(mStoragePath + imageName);
        //get bitmap form mageview
        Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = storageReference2.putBytes(data);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(AddTeam.this,"New image upload...",Toast.LENGTH_SHORT).show();
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while ((!uriTask.isSuccessful()));
                Uri downloadUri = uriTask.getResult();
                updateDatabase(downloadUri.toString());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddTeam.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                mProgressDialog.dismiss();
            }
        });
    }

    private void updateDatabase(final String s) {
        final String title = edtTenDoi.getText().toString();
        final String descr = edtDiaChi.getText().toString();
        FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mRef = mFirebaseDatabase.getReference("Data");
        Query query = mRef.orderByChild("tendoi").equalTo(sTenDoi);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren())
                {
                    ds.getRef().child("tendoi").setValue(title);
                    ds.getRef().child("diachi").setValue(descr);
                    ds.getRef().child("image").setValue(s);
                }
                mProgressDialog.dismiss();
                Toast.makeText(AddTeam.this,"Database updated...",Toast.LENGTH_SHORT).show();
                //
                startActivity(new Intent(AddTeam.this,MainActivity.class));
                finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
