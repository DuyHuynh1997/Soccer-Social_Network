package tdc.edu.com.example.soccer_social_network.SanBong;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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

import tdc.edu.com.example.soccer_social_network.R;


public class MenuHomeSan extends Fragment {

    LinearLayoutManager mLayoutManager;
    SharedPreferences mSharedPref;
    RecyclerView mRecyclerView;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mRef;
    MenuItem itemAdd;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mSharedPref = this.getActivity().getSharedPreferences("SortSetting", Context.MODE_PRIVATE);
        String mSorting = mSharedPref.getString("Sort","newest");

        //since default value is newest so for firtt

        if(mSorting.equals("newest")){
            mLayoutManager = new LinearLayoutManager(this.getActivity());
            mLayoutManager.setReverseLayout(true);
            mLayoutManager.setStackFromEnd(true);
        }
        else if( mSorting.equals("oldest")){
            mLayoutManager = new LinearLayoutManager(this.getActivity());
            mLayoutManager.setReverseLayout(false);
            mLayoutManager.setStackFromEnd(false);
        }


        View flagment = null;
        flagment = inflater.inflate( R.layout.san_listview_home,container,false);
        mRecyclerView = flagment.findViewById(R.id.recyclerview_mainSanlistview);
        mRecyclerView.setHasFixedSize(true);

        //set layout as linerLayot

        mRecyclerView.setLayoutManager(mLayoutManager);

        //send Query to FirebaseDatabase
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getReference("DataSan");



        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        return flagment;

    }

    private void firebaseSeatch(String searchText)
    {
        Query firebaseSearchQuery = mRef.orderByChild("tensan").startAt(searchText).endAt(searchText + "\uf8ff");
        FirebaseRecyclerAdapter<ModelsSan, ViewHolderSan> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<ModelsSan, ViewHolderSan>(
                        ModelsSan.class,
                        R.layout.san_list_carview,
                        ViewHolderSan.class,
                        firebaseSearchQuery
                ) {
                    @Override
                    protected void populateViewHolder(ViewHolderSan viewHolder, ModelsSan models, int i) {
                        viewHolder.setDetails(getActivity().getApplicationContext(), models.getTensan(), models.getDiachisan(), models.getImagesan(),models.getSodienthoaisan()
                                ,models.getChusohuusan(),models.getMotasan());


                    }

                    @Override
                    public ViewHolderSan onCreateViewHolder(ViewGroup parent, int viewType) {

                        ViewHolderSan viewHolder = super.onCreateViewHolder(parent, viewType);

                        viewHolder.setOnClickListener(new ViewHolderSan.ClickListener() {
                            @Override
                            public void onItemClick(View view, int postion) {
                                final String sTenSan = getItem(postion).getTensan();
                                final String sDiaChi = getItem(postion).getDiachisan();
                                final String sSoDienThoai = getItem(postion).getSodienthoaisan();
                                final String cImage = getItem(postion).getImagesan();
                                final String sChuSoHuu = getItem(postion).getChusohuusan();
                                final String sMoTa = getItem(postion).getMotasan();


                                Intent intent = new Intent(getActivity(), SanDetailActivity.class);
                                intent.putExtra("tensan",sTenSan);
                                intent.putExtra("diachisan",sDiaChi);
                                intent.putExtra("sodienthoaisan",sSoDienThoai);
                                intent.putExtra("chusohuusan",sChuSoHuu);
                                intent.putExtra("motasan",sMoTa);
                                intent.putExtra("cImagesan",cImage);
                                startActivity(intent);

                            }

                            @Override
                            public void onItemLongClick(View view, int postion) {

                            }
                        });
                        return viewHolder;
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
            startActivity(new Intent(this.getActivity(), AddSan.class));
        }
        else if(id == R.id.action_seatch)
        {
            return true;
        }
        else if(id == R.id.action_sort)
        {
            showSortDialog();
        }

        return super.onOptionsItemSelected( item );
    }

    private void showSortDialog() {
        String[] sortOptions = {"newest", "oldest"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Sort by")
                .setIcon(R.drawable.ic_action_sort)
                .setItems(sortOptions, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        // The 'which' argument contaibs the index postion of the selected item
                        if (which==0)
                        {
                            SharedPreferences.Editor editor = mSharedPref.edit();
                            editor.putString("Sort", "newest");
                            editor.apply();
                            getActivity().recreate();
                        }
                        else if (which == 1){
                            SharedPreferences.Editor editor = mSharedPref.edit();
                            editor.putString("Sort", "oldest");
                            editor.apply();
                            getActivity().recreate();
                        }
                    }
                });
        builder.show();

    }


    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<ModelsSan, ViewHolderSan> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<ModelsSan, ViewHolderSan>(
                        ModelsSan.class,
                        R.layout.san_list_carview,
                        ViewHolderSan.class,
                        mRef
                ) {
                    @Override
                    protected void populateViewHolder(ViewHolderSan viewHolder, ModelsSan models, int i) {
                        viewHolder.setDetails(getActivity().getApplicationContext(), models.getTensan(), models.getDiachisan(), models.getImagesan(),models.getSodienthoaisan()
                        ,models.getChusohuusan(),models.getMotasan());


                    }

                    @Override
                    public ViewHolderSan onCreateViewHolder(ViewGroup parent, int viewType) {

                        ViewHolderSan viewHolder = super.onCreateViewHolder(parent, viewType);

                        viewHolder.setOnClickListener(new ViewHolderSan.ClickListener() {
                            @Override
                            public void onItemClick(View view, int postion) {
                                final String sTenSan = getItem(postion).getTensan();
                                final String sDiaChi = getItem(postion).getDiachisan();
                                final String sSoDienThoai = getItem(postion).getSodienthoaisan();
                                final String cImage = getItem(postion).getImagesan();
                                final String sChuSoHuu = getItem(postion).getChusohuusan();
                                final String sMoTa = getItem(postion).getMotasan();


                                Intent intent = new Intent(getActivity(), SanDetailActivity.class);
                                intent.putExtra("tensan",sTenSan);
                                intent.putExtra("diachisan",sDiaChi);
                                intent.putExtra("sodienthoaisan",sSoDienThoai);
                                intent.putExtra("chusohuusan",sChuSoHuu);
                                intent.putExtra("motasan",sMoTa);
                                intent.putExtra("cImagesan",cImage);
                                startActivity(intent);


                            }

                            @Override
                            public void onItemLongClick(View view, int postion) {

                            }
                        });
                        return viewHolder;
                    }
                };

        //set adapter to recyclerview
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }
}