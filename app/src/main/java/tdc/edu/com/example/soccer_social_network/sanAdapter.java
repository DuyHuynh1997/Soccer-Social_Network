package tdc.edu.com.example.soccer_social_network;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class sanAdapter extends RecyclerView.Adapter<sanAdapter.ViewHolder> {

    private ArrayList<San> Sans;
    private Context mcontext;

    public sanAdapter(ArrayList<San> sans){
        this.Sans = sans;
    }
    public sanAdapter(Context context, ArrayList<San> san) {
        this.Sans = san;
        this.mcontext= context;
    }

    @NonNull
    @Override


    public sanAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_row_recycleview_all_football_pitches, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull sanAdapter.ViewHolder holder, final int position) {
        Glide.with(mcontext).load(Sans.get(position).getsAnhSan()).into(holder.imghinhSan);
        holder.txttenSan.setText(Sans.get(position).getsTenSan());
        holder.txtDiaChiSan.setText((Sans.get(position).getsDiaChiSan()));
        holder.btnXemThongTinSan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sTenSan = Sans.get(position).getsTenSan();
                String sDiaChi = Sans.get(position).getsDiaChiSan();
                String sSDTSan = Sans.get(position).getsSDTSan();
                String sLoaiSan = Sans.get(position).getsLoaiSan();
                String sSoLuongSan = Sans.get(position).getsSLSan();
                String sIDSan = Sans.get(position).getsIDSan();
                String sGioiThieuSan = Sans.get(position).getsGioiThieuSan();
                String sHinhSan = Sans.get(position).getsAnhSan();
               San san = new San(sTenSan,sSoLuongSan,sLoaiSan,sSDTSan,sDiaChi,sGioiThieuSan,sHinhSan,sIDSan);
                Intent intent = new Intent(mcontext,detail_about_football_pitches.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Pitches",san);
                intent.putExtras(bundle);
                mcontext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return Sans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imghinhSan;
        TextView txttenSan, txtDiaChiSan;
        Button btnXemThongTinSan;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imghinhSan = (ImageView) (itemView.findViewById(R.id.imgHinhSan));
            txttenSan = (TextView) (itemView.findViewById(R.id.txttenSan));
            txtDiaChiSan = (TextView) (itemView.findViewById(R.id.txtDiaChiSan));
            btnXemThongTinSan = (Button)(itemView.findViewById(R.id.btnXemThongTinSan));
        }
    }
}