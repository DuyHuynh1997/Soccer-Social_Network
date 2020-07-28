package tdc.edu.com.example.soccer_social_network;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;


import java.util.ArrayList;

import tdc.edu.com.example.soccer_social_network.Adapter.TeamListAdapter;
import tdc.edu.com.example.soccer_social_network.models_data.Team;


public class Home extends Fragment {
    ListView listView;
    ArrayList<Team> list;
    TeamListAdapter adapter = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View flagment = null;
        flagment = inflater.inflate( R.layout.fragment_home,container,false);

        listView = flagment.findViewById( R.id.listviewHome );
        list = new ArrayList<>(  );
        adapter = new TeamListAdapter( getContext(),R.layout.item_team,list );
        listView.setAdapter( adapter );

        Cursor cursor = AddTeam.sqLiteHelper.getData( "SELECT * FROM TEAM");

        while (cursor.moveToNext()){
            int id = cursor.getInt( 0 );
            String tendoi = cursor.getString( 1 );
            String diachi = cursor.getString( 2 );
            byte[] image = cursor.getBlob( 3 );

            list.add( new Team( id, tendoi, diachi, image) );
        }
        adapter.notifyDataSetChanged();

//        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
//
//                CharSequence[] items = {"Update", "Delete"};
//                AlertDialog.Builder dialog = new AlertDialog.Builder( getContext());
//
//                dialog.setTitle("Choose an action");
//                dialog.setItems(items, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int item) {
//                        if (item == 0) {
//                            // update
//                            Cursor c = AddTeam.sqLiteHelper.getData("SELECT id FROM TEAM");
//                            ArrayList<Integer> arrID = new ArrayList<Integer>();
//                            while (c.moveToNext()){
//                                arrID.add(c.getInt(0));
//                            }
//                            // show dialog update at here
//                            //  showDialogUpdate(FoodList.this, arrID.get(position));
//
//                        } else {
//                            // delete
//                            Cursor c = AddTeam.sqLiteHelper.getData("SELECT id FROM TEAM");
//                            ArrayList<Integer> arrID = new ArrayList<Integer>();
//                            while (c.moveToNext()){
//                                arrID.add(c.getInt(0));
//                            }
//                            // showDialogDelete(arrID.get(position));
//                        }
//                    }
//                });
//                dialog.show();
//                return true;
//            }
//        });

        // Inflate the layout for this fragment
        return flagment;
    }

}
