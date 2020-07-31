package tdc.edu.com.example.soccer_social_network;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

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


import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import tdc.edu.com.example.soccer_social_network.Adapter.TeamListAdapter;
import tdc.edu.com.example.soccer_social_network.models_data.Team;

public class TeamList extends AppCompatActivity {

    ListView listView;
    ArrayList<Team> list;
    TeamListAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.team_listview );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );

        listView = findViewById( R.id.listviewUpdate );
        list = new ArrayList<>(  );
        adapter = new TeamListAdapter(this,R.layout.item_team,list );
        listView.setAdapter( adapter );

        Cursor cursor = MainActivity.sqLiteHelper.getData( "SELECT * FROM TEAM");

        while (cursor.moveToNext()){
            int id = cursor.getInt( 0 );
            String tendoi = cursor.getString( 1 );
            String diachi = cursor.getString( 2 );
            byte[] image = cursor.getBlob( 3 );

            list.add( new Team( id, tendoi, diachi, image) );
        }
        adapter.notifyDataSetChanged();


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                CharSequence[] items = {"Update", "Delete","Thanh Vien Team"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(TeamList.this);

                dialog.setTitle("Choose an action");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (item == 0) {
                            // update
                            Cursor c = MainActivity.sqLiteHelper.getData("SELECT id FROM TEAM");
                            ArrayList<Integer> arrID = new ArrayList<Integer>();
                            while (c.moveToNext()){
                                arrID.add(c.getInt(0));
                            }
                            // show dialog update at here
                            showDialogUpdate(TeamList.this, arrID.get(position));

                        } else if (item == 1) {
                            // delete
                            Cursor c = MainActivity.sqLiteHelper.getData("SELECT id FROM TEAM");
                            ArrayList<Integer> arrID = new ArrayList<Integer>();
                            while (c.moveToNext()){
                                arrID.add(c.getInt(0));
                            }
                            showDialogDelete(arrID.get(position));
                        }
                        else {
                            Intent intent = new Intent( TeamList.this, MemberList.class);
                                     startActivity(intent);
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
        dialog.setContentView(R.layout.update_item_team_listview);
        dialog.setTitle("Update");

        imageViewTeam = (ImageView) dialog.findViewById(R.id.imageViewTeam_update);
        final EditText edtTenDoi = (EditText) dialog.findViewById(R.id.edtTenDoi_update);
        final EditText edtDiaChi = (EditText) dialog.findViewById(R.id.edtDiaChi_update);
        Button btnUpdate = (Button) dialog.findViewById(R.id.btnUpdate_update);

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
                        TeamList.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        888
                );
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    MainActivity.sqLiteHelper.updateData(
                            edtTenDoi.getText().toString().trim(),
                            edtDiaChi.getText().toString().trim(),
                            AddTeam.imageViewToByte(imageViewTeam),
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
    private void showDialogDelete(final int idTeam){
        final AlertDialog.Builder dialogDelete = new AlertDialog.Builder(TeamList.this);

        dialogDelete.setTitle("Cảnh Báo!!");
        dialogDelete.setMessage("Bạn có Muốn Xóa!");
        dialogDelete.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    MainActivity.sqLiteHelper.deleteData(idTeam);
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
        Cursor cursor = MainActivity.sqLiteHelper.getData("SELECT * FROM TEAM");
        list.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String tenDoi = cursor.getString(1);
            String diaChi = cursor.getString(2);
            byte[] image = cursor.getBlob(3);

            list.add(new Team( id,tenDoi, diaChi, image));
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
