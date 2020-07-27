package tdc.edu.com.example.soccer_social_network.Screen;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;


import java.util.ArrayList;

import tdc.edu.com.example.soccer_social_network.Adapters.AdaptersPrice;
import tdc.edu.com.example.soccer_social_network.Data_Models.Price;
import tdc.edu.com.example.soccer_social_network.R;

public class PriceActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<Price> listGia;
    AdaptersPrice adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_price_san_layout);

        anhxa();

        adapter = new AdaptersPrice(this, R.layout.row_price_layout,listGia);
        listView.setAdapter(adapter);
    }

    private void anhxa() {
        listView = (ListView) findViewById(R.id.listPrice);
        listGia = new ArrayList<>();
        listGia.add(new Price("17h-18h","180000"));
        listGia.add(new Price("18h-19h","200000"));
        listGia.add(new Price("19h-20h","250000"));
        listGia.add(new Price("20h-21h","300000"));
        listGia.add(new Price("21h-22h","320000"));
    }
}