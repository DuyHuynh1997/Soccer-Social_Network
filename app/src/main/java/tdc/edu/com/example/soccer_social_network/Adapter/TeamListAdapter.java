package tdc.edu.com.example.soccer_social_network.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;

import tdc.edu.com.example.soccer_social_network.R;
import tdc.edu.com.example.soccer_social_network.models_data.Team;

public class TeamListAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<Team> teamsList;

    public TeamListAdapter(Context context, int layout, ArrayList<Team> teamsList) {
        this.context = context;
        this.layout = layout;
        this.teamsList = teamsList;
    }

    @Override
    public int getCount() {
        return teamsList.size();
    }

    @Override
    public Object getItem(int position) {
        return teamsList.get( position );
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        ImageView imageView;
        TextView txtTenDoi, txtDiaChi;
    }

    @Override
    public View getView(int possition, View view, ViewGroup viewGroup) {
        View row = view;
        ViewHolder holder = new ViewHolder();

        if(row == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            row = inflater.inflate( layout,null );
            holder.txtTenDoi = row.findViewById( R.id.txtTenDoi_itemTeam );
            holder.txtDiaChi = row.findViewById( R.id.txtDiaChi_itemTeam );
            holder.imageView = (ImageView) row.findViewById( R.id.imgView_itemTeam );
            row.setTag(holder);
        }
        else {
            holder = (ViewHolder) row.getTag();
        }

        Team team = teamsList.get(possition);

        holder.txtTenDoi.setText( team.getTenDoi() );
        holder.txtDiaChi.setText( team.getDiaChi() );

        byte[] foodImage = team.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray( foodImage, 0,foodImage.length );
        holder.imageView.setImageBitmap( bitmap );


        return row;
    }
}
