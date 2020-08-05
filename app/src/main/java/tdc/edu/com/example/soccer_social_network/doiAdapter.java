package tdc.edu.com.example.soccer_social_network;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(mcontext).load(dois.get(position).getUlrAnhDoi()).into(holder.imghinhDoi);
//      Picasso.get().load(dois.get(position).getUlrAnhDoi()).into(holder.imghinhDoi);
        holder.txttenDoi.setText(dois.get(position).getTenDoi());
        holder.txtGioithieu.setText((dois.get(position).getGioiThieu()));
    }

    @Override
    public int getItemCount() {
        return dois.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imghinhDoi;
        TextView txttenDoi, txtGioithieu;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imghinhDoi = (ImageView) (itemView.findViewById(R.id.imghinhDoi));
            txttenDoi = (TextView) (itemView.findViewById(R.id.txttenDOi));
            txtGioithieu = (TextView) (itemView.findViewById(R.id.txtgioiThieu));
        }
    }
}
