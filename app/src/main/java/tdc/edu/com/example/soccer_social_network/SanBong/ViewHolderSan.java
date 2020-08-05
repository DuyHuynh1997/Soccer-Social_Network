package tdc.edu.com.example.soccer_social_network.SanBong;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import tdc.edu.com.example.soccer_social_network.R;


public class ViewHolderSan extends RecyclerView.ViewHolder {

    View mView;
    public ViewHolderSan(@NonNull View itemView) {
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

    public void setDetails(Context ctx, String sTenSan, String sDiaChi, String image,String sSoDienThoai,String sChuSoHuu,String sMoTaSan){
        TextView txtTenSan = mView.findViewById(R.id.txtTenSan_mainSancardview);
        TextView txtDiaChi = mView.findViewById(R.id.txtDescription_mainSancardview);
        ImageView mImageIv = mView.findViewById(R.id.ImageView_mainSancardview);

        txtTenSan.setText(sTenSan);
        txtDiaChi.setText(sDiaChi);
        Picasso.get().load(image).into(mImageIv);
    }


    private ViewHolderSan.ClickListener mClickListener;

    public interface ClickListener{
        void onItemClick(View view, int postion);
        void onItemLongClick(View view, int postion);

    }

    public void setOnClickListener(ViewHolderSan.ClickListener clickListener)
    {
            mClickListener = clickListener;
    }
}
