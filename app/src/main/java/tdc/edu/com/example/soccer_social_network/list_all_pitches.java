package tdc.edu.com.example.soccer_social_network;

import android.content.Context;
import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class list_all_pitches extends AppCompatActivity {
    private DatabaseReference mDatabaseReference;
    private RecyclerView recyclerView;
    public ArrayList<San> sans;
    private sanAdapter adapter;
    private Context mcontext;
    private SearchView searchView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_football_pitches);

        recyclerView = (RecyclerView) findViewById(R.id.recycle_viewSan);
        searchView = findViewById(R.id.searchSan);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);


        // array list
        sans = new ArrayList<>();

        //firebase
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        // clear Arraylist
        clearALl();

        getDataFromFirebase();
        if (searchView != null) {
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    search(s);
                    return true;
                }
            });
        }

    }

    // get databse from firebase
    private void getDataFromFirebase() {
        if (mDatabaseReference != null) {
            Query query = mDatabaseReference.child("San");
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    clearALl();
                    sans = new ArrayList<>();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        sans.add(dataSnapshot.getValue(San.class));

                    }
                    adapter = new sanAdapter(getApplicationContext(), sans);
                    recyclerView.setAdapter(adapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(list_all_pitches.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    // search teams by name
    private void search(String s) {
        if (s != null) {
            ArrayList<San> mylist = new ArrayList<San>();
            for (San object : sans) {
                if (object.getsTenSan().toLowerCase().contains(s.toLowerCase())) {
                    mylist.add(object);
                }
            }
            adapter = new sanAdapter(getApplicationContext(), mylist);
            recyclerView.setAdapter(adapter);
        } else {
            getDataFromFirebase();
        }
    }

    // clear arrryList
    private void clearALl() {
        if (sans != null) {
            sans.clear();
            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
        }
        sans = new ArrayList<>();
    }
}

