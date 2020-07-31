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

public class MemberChiTiet extends AppCompatActivity {

    Button btnThemThanhVien;
    ListView listView;
    ArrayList<Member> list;
    MemberListAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.chitiet_menu_member_add );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );


    }


}
