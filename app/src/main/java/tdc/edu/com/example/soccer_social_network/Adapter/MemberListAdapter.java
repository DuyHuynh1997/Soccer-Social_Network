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
import tdc.edu.com.example.soccer_social_network.models_data.Member;

public class MemberListAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<Member> membersList;

    public MemberListAdapter(Context context, int layout, ArrayList<Member> membersList) {
        this.context = context;
        this.layout = layout;
        this.membersList = membersList;
    } 

    @Override
    public int getCount() {
        return membersList.size();
    }

    @Override
    public Object getItem(int position) {
        return membersList.get( position );
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        ImageView imageView;
        TextView txtTenMember, txtViTri;
    }

    @Override
    public View getView(int possition, View view, ViewGroup viewGroup) {
        View row = view;
        ViewHolder holder = new ViewHolder();

        if(row == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            row = inflater.inflate( layout,null );
            holder.txtTenMember = row.findViewById( R.id.txtHoTen_itemmember );
            holder.txtViTri = row.findViewById( R.id.txtViTri_itemmember );
            holder.imageView = (ImageView) row.findViewById( R.id.imgView_itemmember );
            row.setTag(holder);
        }
        else {
            holder = (ViewHolder) row.getTag();
        }

        Member member = membersList.get(possition);

        holder.txtTenMember.setText( member.getTenmember() );
        holder.txtViTri.setText( member.getVitri() );

        byte[] memberImage = member.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray( memberImage, 0,memberImage.length );
        holder.imageView.setImageBitmap( bitmap );


        return row;
    }
}
