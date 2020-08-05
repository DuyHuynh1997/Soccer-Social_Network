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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Locale;

public class doiAdapter extends RecyclerView.Adapter<doiAdapter.ViewHolder> {
    private ArrayList<Doi> dois;
    private Context mcontext;

    public doiAdapter(ArrayList<Doi> dois){
        this.dois = dois;
    }
    public doiAdapter(Context context, ArrayList<Doi> doi) {
        this.dois = doi;
        this.mcontext= context;
    }

    @NonNull
    @Override
    public doiAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_reycleview_all_team, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Glide.with(mcontext).load(dois.get(position).getUlrAnhDoi()).into(holder.imghinhDoi);
        holder.txttenDoi.setText(dois.get(position).getTenDoi());
        holder.txtGioithieu.setText((dois.get(position).getGioiThieu()));
        holder.btnXemThongTin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sTenDoi = dois.get(position).getTenDoi();
                String sGioiThieu = dois.get(position).getGioiThieu();
                String sSDT = dois.get(position).getSoDienThoai();
                String sThanhVien = dois.get(position).getThanhVien();
                String sHinhDoi = dois.get(position).getUlrAnhDoi();
                String sID = dois.get(position).getIdDoi();
                String sTrangThai = dois.get(position).getIdDoi();
                Doi doi = new Doi(sTenDoi,sSDT,sThanhVien,sGioiThieu,sTrangThai,sHinhDoi,sID);
                Intent intent = new Intent(mcontext,detail_about_team.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Team",doi);
                intent.putExtras(bundle);
                mcontext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dois.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imghinhDoi;
        TextView txttenDoi, txtGioithieu;
        Button btnXemThongTin;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imghinhDoi = (ImageView) (itemView.findViewById(R.id.imghinhDoi));
            txttenDoi = (TextView) (itemView.findViewById(R.id.txttenDOi));
            txtGioithieu = (TextView) (itemView.findViewById(R.id.txtgioiThieu));
            btnXemThongTin = (Button)(itemView.findViewById(R.id.btnXemThongTinDoi));
        }
    }
}
