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

public class MemberListView extends AppCompatActivity {

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


        listView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent( MemberListView.this, MemberChiTiet.class);
                startActivity(intent);
            }
        } );

    }

}
