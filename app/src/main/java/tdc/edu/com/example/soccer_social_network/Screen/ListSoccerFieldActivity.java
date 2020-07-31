package tdc.edu.com.example.soccer_social_network.Screen;

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

import tdc.edu.com.example.soccer_social_network.Adapters.AdaptersSoccerField;
import tdc.edu.com.example.soccer_social_network.Data_Models.SoccerField;
import tdc.edu.com.example.soccer_social_network.R;

public class ListSoccerFieldActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<SoccerField> list;
    AdaptersSoccerField adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_san_layout);
        listView = findViewById( R.id.listSan);
        list = new ArrayList<>(  );
        adapter = new AdaptersSoccerField(this,R.layout.row_listview_layout,list);
        listView.setAdapter( adapter );

        Cursor cursor = InsertActivity.sqLiteHelper.getData( "SELECT * FROM SOCCERFIELD");

        while (cursor.moveToNext()){
            int id = cursor.getInt( 0 );
            String name = cursor.getString( 1 );
            String area = cursor.getString( 2 );
            byte[] image = cursor.getBlob( 3 );

            list.add( new SoccerField( id, name, area, image) );
        }
        adapter.notifyDataSetChanged();


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                CharSequence[] items = {"Update", "Delete"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(ListSoccerFieldActivity.this);

                dialog.setTitle("Choose an action");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (item == 0) {
                            // update
                            Cursor c = InsertActivity.sqLiteHelper.getData("SELECT id FROM SOCCERFIELD");
                            ArrayList<Integer> arrID = new ArrayList<Integer>();
                            while (c.moveToNext()){
                                arrID.add(c.getInt(0));
                            }
                            // show dialog update at here
                            showDialogUpdate(ListSoccerFieldActivity.this, arrID.get(position));

                        } else {
                            // delete
                            Cursor c = InsertActivity.sqLiteHelper.getData("SELECT id FROM SOCCERFIELD");
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
    }
    ImageView imageViewTeam;
    private void showDialogUpdate(Activity activity, final int position){

        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.update_row_soccerfild_listview);
        dialog.setTitle("Update");

        imageViewTeam = (ImageView) dialog.findViewById(R.id.image_update);
        final EditText edtName = (EditText) dialog.findViewById(R.id.edtNameSoccerFile_update);
        final EditText edtArea = (EditText) dialog.findViewById(R.id.edtArea_update);
        Button btnUpdate = (Button) dialog.findViewById(R.id.btnUpdate);

        // set width for dialog
        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 0.95);
        // set height for dialog
        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels * 0.7);
        dialog.getWindow().setLayout(width, height);
        dialog.show();

        imageViewTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // request photo library
                ActivityCompat.requestPermissions(
                        ListSoccerFieldActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        888
                );
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    InsertActivity.sqLiteHelper.updateData(
                            edtName.getText().toString().trim(),
                            edtArea.getText().toString().trim(),
                            InsertActivity.imageViewToByte(imageViewTeam),
                            position
                    );
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Update successfully!!!",Toast.LENGTH_SHORT).show();
                }
                catch (Exception error) {
                    Log.e("Update error", error.getMessage());
                }
                updateTeamList();
            }
        });
    }
    private void showDialogDelete(final int idFood){
        final AlertDialog.Builder dialogDelete = new AlertDialog.Builder(ListSoccerFieldActivity.this);

        dialogDelete.setTitle("Cảnh Báo!!");
        dialogDelete.setMessage("Bạn có Muốn Xóa!");
        dialogDelete.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    InsertActivity.sqLiteHelper.deleteData(idFood);
                    Toast.makeText(getApplicationContext(), "Delete successfully!",Toast.LENGTH_SHORT).show();
                } catch (Exception e){
                    Log.e("error", e.getMessage());
                }
                updateTeamList();
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
    private void updateTeamList(){
        // get all data from sqlite
        Cursor cursor = InsertActivity.sqLiteHelper.getData("SELECT * FROM SOCCERFIELD");
        list.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String area = cursor.getString(2);
            byte[] image = cursor.getBlob(3);

            list.add(new SoccerField( id,name, area, image));
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
                imageViewTeam.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}