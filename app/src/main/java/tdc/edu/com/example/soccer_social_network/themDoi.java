package tdc.edu.com.example.soccer_social_network;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class themDoi extends AppCompatActivity {
    EditText edtTenDoi, edtNgayTaoDoi, edtThanhVien, edtGhiChu;
    Doi doi;
    private DatabaseReference mDatabaseReference;

   public ArrayList<Doi> dois;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_doi);
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        edtTenDoi = (EditText)(findViewById(R.id.edttenDoi));
        edtNgayTaoDoi = (EditText)(findViewById(R.id.edtngayTaoDoi));
        edtThanhVien = (EditText)(findViewById(R.id.edtThanhVien));
        edtGhiChu = (EditText)(findViewById(R.id.edtGhiChu));

    }
}
