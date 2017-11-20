package com.apptime.alexw.dartmouthdrinks;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Alex W on 20/11/2017.
 */

public class OnNightAdapter extends BaseAdapter{
    private Context mContext;
    private LayoutInflater mInflater;
    private List<OnNight> mDataSource;


    //Helps build out history activity

    public OnNightAdapter(Context context, List<OnNight> onNights) {
        mContext = context;
        mDataSource = onNights;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    //1
    @Override
    public int getCount() {
        return mDataSource.size();
    }

    //2
    @Override
    public Object getItem(int position) {
        return mDataSource.get(position);
    }

    //3
    @Override
    public long getItemId(int position) {
        return position;
    }

    //4
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get view for row item
        View rowView = mInflater.inflate(R.layout.simple_row, parent, false);

        //get the appropriate TextView and add it to the layout

        TextView rowTextView = rowView.findViewById(R.id.rowTextView);

        OnNight night = (OnNight) getItem(position);

        rowTextView.setText(night.getDay().toString());

        return rowView;
    }

}
