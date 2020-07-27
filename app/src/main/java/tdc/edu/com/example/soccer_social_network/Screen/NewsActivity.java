package tdc.edu.com.example.soccer_social_network.Screen;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;


import java.util.ArrayList;

import tdc.edu.com.example.soccer_social_network.Adapters.AdapterNews;
import tdc.edu.com.example.soccer_social_network.Data_Models.News;
import tdc.edu.com.example.soccer_social_network.R;

public class NewsActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<News> listTinTuc;
    AdapterNews adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_news_layout);

        anhxa();

        adapter = new AdapterNews(this,R.layout.row_news_layout,listTinTuc);
        listView.setAdapter(adapter);
    }

    private void anhxa() {
        listView = (ListView) findViewById(R.id.listTinTuc);
        listTinTuc = new ArrayList<>();
        listTinTuc.add(new News("2/9","20%"));
        listTinTuc.add(new News("20/10","10%"));
        listTinTuc.add(new News("20/11","10%"));
        listTinTuc.add(new News("22/12","5%"));
    }
}