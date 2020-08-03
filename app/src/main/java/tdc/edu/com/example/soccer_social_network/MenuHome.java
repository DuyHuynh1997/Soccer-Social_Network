package tdc.edu.com.example.soccer_social_network;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.io.ByteArrayOutputStream;


public class MenuHome extends Fragment {

    LinearLayoutManager mLayoutManager;
    SharedPreferences mSharedPref;
    RecyclerView mRecyclerView;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mRef;
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
        flagment = inflater.inflate( R.layout.team_listview_home,container,false);
        mRecyclerView = flagment.findViewById(R.id.recyclerview_mainlistview);
        mRecyclerView.setHasFixedSize(true);

        //set layout as linerLayot

        mRecyclerView.setLayoutManager(mLayoutManager);

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

                    @Override
                    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                        ViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);

                        viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
                            @Override
                            public void onItemClick(View view, int postion) {
                                //Views
                                TextView mTitleTv = view.findViewById(R.id.txtTitle_maincardview);
                                TextView mDescTv = view.findViewById(R.id.txtDescription_maincardview);
                                ImageView mImageView = view.findViewById(R.id.ImageView_maincarview);
                                //get dât from views
                                String mTitle = mTitleTv.getText().toString();
                                String mDesc = mDescTv.getText().toString();
                            //    Drawable mDrawable = mImageView.getDrawable();
                            //    Bitmap mBitmap = ((BitmapDrawable)mDrawable).getBitmap();

                                //pass this data to new Activity
                                Intent intent = new Intent(view.getContext(), TeamDetailActivity.class);
                                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                              //  mBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                               // byte[] bytes = stream.toByteArray();
                               // intent.putExtra("image",bytes);
                                intent.putExtra("tendoi",mTitle);
                                intent.putExtra("diachi",mDesc);
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
            startActivity(new Intent(getActivity(),AddTeam.class));
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

                    @Override
                    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                        ViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);

                        viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
                            @Override
                            public void onItemClick(View view, int postion) {
                                //Views
                                TextView mTitleTv = view.findViewById(R.id.txtTitle_maincardview);
                                TextView mDescTv = view.findViewById(R.id.txtDescription_maincardview);
                                ImageView mImageView = view.findViewById(R.id.ImageView_maincarview);
                                //get dât from views
                                String mTitle = mTitleTv.getText().toString();
                                String mDesc = mDescTv.getText().toString();
                               // Drawable mDrawable = mImageView.getDrawable();
                                //Bitmap mBitmap = (BitmapDrawable)mDrawable.getBitmap();

                                //pass this data to new Activity
                                Intent intent = new Intent(view.getContext(), TeamDetailActivity.class);
                                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                //mBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                                byte[] bytes = stream.toByteArray();
                                intent.putExtra("image",bytes);
                                intent.putExtra("tendoi",mTitle);
                                intent.putExtra("diachi",mDesc);
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
