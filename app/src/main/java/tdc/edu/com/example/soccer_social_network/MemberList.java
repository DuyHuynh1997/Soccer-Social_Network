package tdc.edu.com.example.soccer_social_network;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import tdc.edu.com.example.soccer_social_network.Adapter.MemberListAdapter;
import tdc.edu.com.example.soccer_social_network.models_data.Member;

public class MemberList extends AppCompatActivity {

    Button btnThemThanhVien;
    ListView listView;
    ArrayList<Member> list;
    MemberListAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.member_listview );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );

        listView = findViewById( R.id.listviewMember_memberlistview );
        list = new ArrayList<>(  );
        adapter = new MemberListAdapter(this,R.layout.item_member,list );
        listView.setAdapter( adapter );

        Cursor cursor = MainActivity.sqLiteHelper.getData( "SELECT * FROM MEMBER");

        while (cursor.moveToNext()){
            int id = cursor.getInt( 0 );
            String tenmember = cursor.getString( 1 );
            String viyti = cursor.getString( 2 );
            byte[] image = cursor.getBlob( 3 );

            list.add( new Member( id, tenmember, viyti, image) );
        }
        adapter.notifyDataSetChanged();


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                CharSequence[] items = {"Update", "Delete"};
                AlertDialog.Builder dialog = new AlertDialog.Builder( MemberList.this);

                dialog.setTitle("Choose an action");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (item == 0) {
                            // update
                            Cursor c = MainActivity.sqLiteHelper.getData("SELECT id FROM MEMBER");
                            ArrayList<Integer> arrID = new ArrayList<Integer>();
                            while (c.moveToNext()){
                                arrID.add(c.getInt(0));
                            }
                            // show dialog update at here
                            showDialogUpdate( MemberList.this, arrID.get(position));

                        } else {
                            // delete
                            Cursor c = MainActivity.sqLiteHelper.getData("SELECT id FROM MEMBER");
                            ArrayList<Integer> arrID = new ArrayList<Integer>();
                            while (c.moveToNext()){
                                arrID.add(c.getInt(0));
                            }
                            showDialogDelete(arrID.get(position));
                        }
                    }
                });
                dialog.show();
                return true;
            }
        });
//        btnThemThanhVien.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent( MemberList.this, AddMember.class);
//                startActivity(intent);
//            }
//        } );
    }
    ImageView imageViewMember;
    private void showDialogUpdate(Activity activity, final int position){

        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.update_item_member_listview);
        dialog.setTitle("Update");

        imageViewMember = (ImageView) dialog.findViewById(R.id.imageViewMember_update);
        final EditText edtHoTen = (EditText) dialog.findViewById(R.id.edtHoTenMember_update);
        final EditText edtViTri = (EditText) dialog.findViewById(R.id.edtViTriChinhMember_update);
        Button btnUpdate = (Button) dialog.findViewById(R.id.btnUpdateMember_update);

        // set width for dialog
        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 0.95);
        // set height for dialog
        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels * 0.7);
        dialog.getWindow().setLayout(width, height);
        dialog.show();

        imageViewMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // request photo library
                ActivityCompat.requestPermissions(
                        MemberList.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        888
                );
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    MainActivity.sqLiteHelper.updateDataMember(
                            edtHoTen.getText().toString().trim(),
                            edtViTri.getText().toString().trim(),
                            AddMember.imageViewToByte(imageViewMember),
                            position
                    );
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Update successfully!!!",Toast.LENGTH_SHORT).show();
                }
                catch (Exception error) {
                    Log.e("Update error", error.getMessage());
                }
                updateMemberList();
            }
        });
    }
    private void showDialogDelete(final int idMember){
        final AlertDialog.Builder dialogDelete = new AlertDialog.Builder( MemberList.this);

        dialogDelete.setTitle("Cảnh Báo!!");
        dialogDelete.setMessage("Bạn có Muốn Xóa!");
        dialogDelete.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    MainActivity.sqLiteHelper.deleteDataMember(idMember);
                    Toast.makeText(getApplicationContext(), "Delete successfully!",Toast.LENGTH_SHORT).show();
                } catch (Exception e){
                    Log.e("error", e.getMessage());
                }
                updateMemberList();
            }
        });

        dialogDelete.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialogDelete.show();
    }
    private void updateMemberList(){
        // get all data from sqlite
        Cursor cursor = MainActivity.sqLiteHelper.getData("SELECT * FROM MEMBER");
        list.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String tenmember = cursor.getString(1);
            String vitri = cursor.getString(2);
            byte[] image = cursor.getBlob(3);

            list.add(new Member( id,tenmember, vitri, image));
        }
        adapter.notifyDataSetChanged();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == 888){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 888);
            }
            else {
                Toast.makeText(getApplicationContext(), "Không có thư viện Image!", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == 888 && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageViewMember.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

}
