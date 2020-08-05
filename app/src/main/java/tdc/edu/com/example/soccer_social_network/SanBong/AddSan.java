package tdc.edu.com.example.soccer_social_network.SanBong;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
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

import tdc.edu.com.example.soccer_social_network.MenuAcitvity;
import tdc.edu.com.example.soccer_social_network.R;


public class AddSan extends AppCompatActivity{
    EditText edtTenSan, edtChuSoHuu,edtDiaChiSan,edtSoDienThoaiSan, edtMoTaSan;
    Button btnAddTeamSan;
    ImageView imageViewSan;
   DatePickerDialog.OnDateSetListener setListener;

    //Folder path for Firebase Storage
    String mStoragePath = "All_Image_Uploads/";
    //Rôt Database name for firebase daabase
    String mDatabasePath = "DataSan";
    //Creating URI
    Uri mFilePathUri;

    //Createting StorageRence
    StorageReference mStorageReference;
    DatabaseReference mDatabaseReference;
    ProgressDialog mProgressDialog;

    int IMAGE_REQUEST_CODE = 5;

    String sTenSan, sDiaChiSan, cImageSan,sDoiTruongSan, sMoTaSan,sSoDienThoaiSan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_san_layout);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Add New San");

        edtTenSan = findViewById(R.id.edtTenSan_addSan);
        edtChuSoHuu = findViewById(R.id.edtChuSoHuu_addSan);
        edtDiaChiSan = findViewById(R.id.edtDiaChi_addSan);
        edtSoDienThoaiSan = findViewById(R.id.edtSoDienThoai_addSan);
        btnAddTeamSan = findViewById(R.id.btnAddTeam_addSan);
        imageViewSan = findViewById(R.id.imageView_addSan);
        edtMoTaSan = findViewById(R.id.edtMoTa_addSan);

        Bundle intent = getIntent().getExtras();
        if(intent != null){
            sTenSan = intent.getString("tensan");
            sDiaChiSan = intent.getString("diachisan");
            sDoiTruongSan = intent.getString("chusohuusan");
            sMoTaSan = intent.getString("motasan");
            sSoDienThoaiSan = intent.getString("sodienthoaisan");
            cImageSan = intent.getString("cImagesan");


            edtTenSan.setText(sTenSan);
            edtDiaChiSan.setText(sDiaChiSan);
            edtChuSoHuu.setText(sDoiTruongSan);
            edtMoTaSan.setText(sMoTaSan);
            edtSoDienThoaiSan.setText(sSoDienThoaiSan);
            Picasso.get().load(cImageSan).into(imageViewSan);
            actionBar.setTitle("Update");
            btnAddTeamSan.setText("Update");

        }



        imageViewSan.setOnClickListener(new View.OnClickListener() {
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

        btnAddTeamSan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //call method to upload data to firebase
                if(btnAddTeamSan.getText().equals("AddSan"))
                {
                    //Toast.makeText(AddSan.this,"Được Click",Toast.LENGTH_SHORT).show();
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
        mProgressDialog = new ProgressDialog(AddSan.this);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

       edtMoTaSan.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               DatePickerDialog datePickerDialog = new DatePickerDialog(
                       AddSan.this, new DatePickerDialog.OnDateSetListener() {
                   @Override
                   public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                       month = month+1;
                       String date = day+"/"+month+"/"+year;
                       edtMoTaSan.setText(date);
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
        StorageReference mPictureRef = FirebaseStorage.getInstance().getReferenceFromUrl(cImageSan);
        mPictureRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(AddSan.this,"Previous image deleted...",Toast.LENGTH_SHORT).show();
                uploadNewImage();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddSan.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                mProgressDialog.dismiss();
            }
        });
    }

    private void uploadNewImage() {
        String imageName = System.currentTimeMillis()+ ".png";
        //storage reference
        StorageReference storageReference3 = mStorageReference.child(mStoragePath + imageName);
        //get bitmap form mageview
        Bitmap bitmap = ((BitmapDrawable)imageViewSan.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = storageReference3.putBytes(data);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(AddSan.this,"New image upload...",Toast.LENGTH_SHORT).show();
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while ((!uriTask.isSuccessful()));
                Uri downloadUri = uriTask.getResult();
                updateDatabase(downloadUri.toString());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddSan.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                mProgressDialog.dismiss();
            }
        });
    }

    private void updateDatabase(final String s) {
        final String sTenSan = edtTenSan.getText().toString();
        final String sDiaChi = edtDiaChiSan.getText().toString();
        final String sSoDienThoai = edtSoDienThoaiSan.getText().toString();
        final String sChuSoHuu = edtChuSoHuu.getText().toString();
        final String sMoTa = edtMoTaSan.getText().toString();
        FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mRef = mFirebaseDatabase.getReference("DataSan");
        Query query = mRef.orderByChild("tensan").equalTo(this.sTenSan);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren())
                {
                    ds.getRef().child("tensan").setValue(sTenSan);
                    ds.getRef().child("diachisan").setValue(sDiaChi);
                    ds.getRef().child("chusohuusan").setValue(sChuSoHuu);
                    ds.getRef().child("sodienthoaisan").setValue(sSoDienThoai);
                    ds.getRef().child("motasan").setValue(sMoTa);
                    ds.getRef().child("imagesan").setValue(s);
                }
                mProgressDialog.dismiss();
                Toast.makeText(AddSan.this,"Database updated...",Toast.LENGTH_SHORT).show();
                //
                startActivity(new Intent(AddSan.this, MenuAcitvity.class));
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
                            String sTenSan = edtTenSan.getText().toString().trim();
                            String sChuSoHuu = edtChuSoHuu.getText().toString().trim();
                            String sodienthoai = edtSoDienThoaiSan.getText().toString().trim();
                            String diachi = edtDiaChiSan.getText().toString().trim();
                            String sMoTa = edtMoTaSan.getText().toString().trim();
                            mProgressDialog.dismiss();
                            Toast.makeText(AddSan.this,"Uploaded succcesslly",Toast.LENGTH_SHORT).show();
                            ImageUplpadInfoSan imageUplpadInfo = new ImageUplpadInfoSan(sTenSan,sChuSoHuu,downloadUrl.toString(),sodienthoai,diachi,sMoTa);

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
                            Toast.makeText(AddSan.this,e.getMessage(),Toast.LENGTH_SHORT).show();

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
                imageViewSan.setImageBitmap(bitmap);
            }
            catch (Exception e)
            {
                Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }
    }


}
