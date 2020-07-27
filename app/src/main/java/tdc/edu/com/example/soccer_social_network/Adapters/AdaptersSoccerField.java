package tdc.edu.com.example.soccer_social_network.Adapters;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import java.util.ArrayList;

import tdc.edu.com.example.soccer_social_network.Data_Models.SoccerField;
import tdc.edu.com.example.soccer_social_network.R;

public class AdaptersSoccerField extends ArrayAdapter {
    Activity context;
    int layoutID;
    ArrayList<SoccerField> list;

    public AdaptersSoccerField(Activity context, int layoutID, ArrayList<SoccerField> list) {
        super(context, layoutID, list);
        this.context = context;
        this.layoutID = layoutID;
        this.list = list;
    }

    static class viewHolder {
        TextView lblName;
        TextView lblArea;
        ImageView img;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final viewHolder viewHolder;
        if (convertView == null){
            //create new item for listview
            viewHolder = new viewHolder();
            convertView = context.getLayoutInflater().inflate(layoutID, parent, false);
            viewHolder.lblName = (TextView) convertView.findViewById(R.id.nameSan);
            viewHolder.lblArea = (TextView) convertView.findViewById(R.id.diaChi);
            viewHolder.img =(ImageView) convertView.findViewById(R.id.img);
            //link the viewHolder with the coresonding convertView
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (viewHolder) convertView.getTag();
        }
        SoccerField soccerField = list.get(position);
        byte[] hinhAnh = soccerField.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(hinhAnh,0,hinhAnh.length);
        viewHolder.img.setImageBitmap(bitmap);
        viewHolder.lblName.setText(soccerField.getNameSan());
        viewHolder.lblArea.setText(soccerField.getArea());

        return convertView;
    }
}
