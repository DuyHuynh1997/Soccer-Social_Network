package tdc.edu.com.example.soccer_social_network;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class main_menu extends AppCompatActivity {
    Button btnTimDoi, btnTimSan, btnQuanLiDoi, btnQuanLiSan,btndanhSachDoi,btnDanhSachSan,btnTaoDoi;
    Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        btnTimDoi = (Button) (findViewById(R.id.btntimDoi));
        btnTimSan = (Button) (findViewById(R.id.btntimDoi));
        btnQuanLiDoi = (Button) (findViewById(R.id.btnquanLiDoi));
        btnQuanLiSan = (Button) (findViewById(R.id.btnquanLiSan));
        btnDanhSachSan = (Button) (findViewById(R.id.btndanhSachSan));
        btndanhSachDoi = (Button) (findViewById(R.id.btndanhSachDoi));
        btnTaoDoi = (Button) (findViewById(R.id.btnTaoDoi));

        btnTimDoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(main_menu.this, find_teams.class);
                startActivity(intent);
            }
        });

        btndanhSachDoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(main_menu.this, list_All_Teams.class);
                startActivity(intent);
            }
        });

        btnTaoDoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(main_menu.this, create_team.class);
                startActivity(intent);
            }
        });
    }
}