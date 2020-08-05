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

public class list_All_Teams extends AppCompatActivity {
    private DatabaseReference mDatabaseReference;
    private RecyclerView recyclerView;
    public ArrayList<Doi> dois;
    private doiAdapter adapter;
    private Context mcontext;
    private SearchView searchView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_teams);

        recyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        searchView = findViewById(R.id.searchTeam);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);


        // array list
        dois = new ArrayList<>();

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
            Query query = mDatabaseReference.child("Doi");
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    clearALl();
                    dois = new ArrayList<>();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    Doi doi = new Doi();
//                    doi.setUlrAnhDoi(dataSnapshot.child("ulrAnhDoi").getValue().toString());
//                    doi.setTenDoi(dataSnapshot.child("tenDoi").getValue().toString());
//                    doi.setGioiThieu(dataSnapshot.child("trangThai").getValue().toString());
//                    dois.add(doi);
                        dois.add(dataSnapshot.getValue(Doi.class));

                    }
                    adapter = new doiAdapter(getApplicationContext(), dois);
                    recyclerView.setAdapter(adapter);
//                adapter = new doiAdapter(getApplicationContext(), dois);
//                recyclerView.setAdapter(adapter);
//                adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(list_All_Teams.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

        // search teams by name
    private void search(String s) {
        if (s != null) {
            ArrayList<Doi> mylist = new ArrayList<Doi>();
            for (Doi object : dois) {
                if (object.getTenDoi().toLowerCase().contains(s.toLowerCase())) {
                    mylist.add(object);
                }
            }
            adapter = new doiAdapter(getApplicationContext(), mylist);
            recyclerView.setAdapter(adapter);
        } else {
            getDataFromFirebase();
        }
    }

    // clear arrryList
    private void clearALl() {
        if (dois != null) {
            dois.clear();
            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
        }
        dois = new ArrayList<>();
    }
}
