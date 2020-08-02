package tdc.edu.com.example.soccer_social_network;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


public class MenuHome extends Fragment {

    RecyclerView mRecyclerView;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mRef;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View flagment = null;
        flagment = inflater.inflate( R.layout.team_listview_home,container,false);
        mRecyclerView = flagment.findViewById(R.id.recyclerview_mainlistview);
        mRecyclerView.setHasFixedSize(true);

        //set layout as linerLayot

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //send Query to FirebaseDatabase
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getReference("Data");



        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        return flagment;

    }

    private void firebaseSeatch(String searchText)
    {
        Query firebaseSearchQuery = mRef.orderByChild("tendoi").startAt(searchText).endAt(searchText + "\uf8ff");
        FirebaseRecyclerAdapter<Models, ViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Models, ViewHolder>(
                        Models.class,
                        R.layout.team_list_carview,
                        ViewHolder.class,
                        firebaseSearchQuery
                ) {
                    @Override
                    protected void populateViewHolder(ViewHolder viewHolder, Models models, int i) {
                        viewHolder.setDetails(getActivity().getApplicationContext(), models.getTendoi(), models.getDiachi(), models.getImage());
                    }
                };
        //set adapter to recyclerview
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.main,menu);
        MenuItem item = menu.findItem(R.id.action_seatch);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                firebaseSeatch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                firebaseSeatch(newText);
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {
            startActivity(new Intent(getActivity(),AddTeam.class));
        }
        else if(id == R.id.action_seatch)
        {
            return true;
        }

        return super.onOptionsItemSelected( item );
    }


    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Models, ViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Models, ViewHolder>(
                        Models.class,
                        R.layout.team_list_carview,
                        ViewHolder.class,
                        mRef
                ) {
                    @Override
                    protected void populateViewHolder(ViewHolder viewHolder, Models models, int i) {
                        viewHolder.setDetails(getActivity().getApplicationContext(), models.getTendoi(), models.getDiachi(), models.getImage());
                    }


                };

        //set adapter to recyclerview
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }
}
