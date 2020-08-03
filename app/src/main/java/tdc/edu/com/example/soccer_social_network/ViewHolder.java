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

        //item click
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onItemClick(view, getAdapterPosition());
            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mClickListener.onItemLongClick(view, getAdapterPosition());
                return true;
            }
        });
    }

    public void setDetails(Context ctx, String title, String description, String image){
        TextView txtTenDoi = mView.findViewById(R.id.txtTitle_maincardview);
        TextView txtDiaChi = mView.findViewById(R.id.txtDescription_maincardview);
        ImageView mImageIv = mView.findViewById(R.id.ImageView_maincarview);

        txtTenDoi.setText(title);
        txtDiaChi.setText(description);
        Picasso.get().load(image).into(mImageIv);
    }

    private ViewHolder.ClickListener mClickListener;

    public interface ClickListener{
        void onItemClick(View view, int postion);
        void onItemLongClick(View view, int postion);

    }

    public void setOnClickListener(ViewHolder.ClickListener clickListener)
    {
            mClickListener = clickListener;
    }
}
