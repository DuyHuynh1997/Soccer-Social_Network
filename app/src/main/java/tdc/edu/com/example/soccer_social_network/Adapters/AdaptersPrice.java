package tdc.edu.com.example.soccer_social_network.Adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;



import java.util.ArrayList;

import tdc.edu.com.example.soccer_social_network.Data_Models.Price;
import tdc.edu.com.example.soccer_social_network.R;

public class AdaptersPrice extends ArrayAdapter {
    Activity context;
    int layoutID;
    ArrayList<Price> list;

    public AdaptersPrice(Activity context, int layoutID, ArrayList<Price> list) {
        super(context, layoutID, list);
        this.context = context;
        this.layoutID = layoutID;
        this.list = list;
    }

    static class viewHolder{
        TextView time;
        TextView gia;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final AdaptersPrice.viewHolder viewHolder;
        if (convertView == null){
            //create new item for listview
            viewHolder = new AdaptersPrice.viewHolder();
            convertView = context.getLayoutInflater().inflate(layoutID, parent, false);
            viewHolder.time = (TextView) convertView.findViewById(R.id.txttime);
            viewHolder.gia = (TextView) convertView.findViewById(R.id.txtgia);
            //link the viewHolder with the coresonding convertView
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (AdaptersPrice.viewHolder) convertView.getTag();
        }
        Price price = list.get(position);
        viewHolder.time.setText(price.getTime());
        viewHolder.gia.setText(price.getGia());

        return convertView;
    }
}
