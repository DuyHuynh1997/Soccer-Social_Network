package tdc.edu.com.example.soccer_social_network;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;



import java.util.ArrayList;

import tdc.edu.com.example.soccer_social_network.Adapter.TeamListAdapter;
import tdc.edu.com.example.soccer_social_network.models_data.Team;


public class MenuChinh extends Fragment {
    ListView listView;
    ArrayList<Team> list;
    TeamListAdapter adapter = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View flagment = null;
        flagment = inflater.inflate( R.layout.quanlydoi,container,false);



        // Inflate the layout for this fragment
        return flagment;

    }

}
