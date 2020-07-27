package tdc.edu.com.example.soccer_social_network.Adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;



import java.util.ArrayList;

import tdc.edu.com.example.soccer_social_network.Data_Models.News;
import tdc.edu.com.example.soccer_social_network.R;

public class AdapterNews extends ArrayAdapter {
    Activity context;
    int layoutID;
    ArrayList<News> list;

    public AdapterNews(Activity context, int layoutID, ArrayList<News> list) {
        super(context, layoutID, list);
        this.context = context;
        this.layoutID = layoutID;
        this.list = list;
    }

    static class viewHolder{
        TextView day;
        TextView khuyenMai;
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
        final AdapterNews.viewHolder viewHolder;
        if (convertView == null){
            //create new item for listview
            viewHolder = new AdapterNews.viewHolder();
            convertView = context.getLayoutInflater().inflate(layoutID, parent, false);
            viewHolder.day = (TextView) convertView.findViewById(R.id.txtDay);
            viewHolder.khuyenMai = (TextView) convertView.findViewById(R.id.txtTinTuc);
            //link the viewHolder with the coresonding convertView
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (AdapterNews.viewHolder) convertView.getTag();
        }
        News price = list.get(position);
        viewHolder.day.setText(price.getDate());
        viewHolder.khuyenMai.setText(price.getKhuyenMai());

        return convertView;
    }
}
