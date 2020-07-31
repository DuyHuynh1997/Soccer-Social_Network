package tdc.edu.com.example.soccer_social_network.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
        TextView txtName;
        TextView txtArea;
        ImageView img;
        Button btnDetail;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        viewHolder viewHolder = new viewHolder();

        if(convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            convertView = inflater.inflate(layoutID,null );
            viewHolder.txtName = convertView.findViewById( R.id.row_name);
            viewHolder.txtArea = convertView.findViewById( R.id.row_area);
            viewHolder.img = (ImageView) convertView.findViewById( R.id.imageSan);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (viewHolder) convertView.getTag();
        }

        SoccerField soccerField = list.get(position);

        viewHolder.txtName.setText(soccerField.getNameSan());
        viewHolder.txtArea.setText(soccerField.getArea());

        byte[] imagea = soccerField.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imagea, 0,imagea.length );
        viewHolder.img.setImageBitmap( bitmap );

        return convertView;
    }
}
