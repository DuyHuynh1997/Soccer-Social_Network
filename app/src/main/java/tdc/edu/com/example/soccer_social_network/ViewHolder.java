package tdc.edu.com.example.soccer_social_network;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;


public class ViewHolder extends RecyclerView.ViewHolder {

    View mView;
    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        mView = itemView;
    }

    public void setDetails(Context ctx, String title, String description, String image){
        TextView mTilteTv = mView.findViewById(R.id.txtTitle_maincardview);
        TextView mDetailTv = mView.findViewById(R.id.txtDescription_maincardview);
        ImageView mImageIv = mView.findViewById(R.id.ImageView_maincarview);

        mTilteTv.setText(title);
        mDetailTv.setText(description);
        Picasso.get().load(image).into(mImageIv);
    }
}
