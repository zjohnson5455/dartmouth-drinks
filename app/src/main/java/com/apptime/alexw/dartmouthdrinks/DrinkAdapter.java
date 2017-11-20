package com.apptime.alexw.dartmouthdrinks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Alex W on 20/11/2017.
 * Used to populate the listView (DrinksActivity) before the graph
 */

public class DrinkAdapter extends BaseAdapter{

    private Context mContext;
    private LayoutInflater mInflater;
    private List<Drink> mDataSource;


    //constructor
    public DrinkAdapter(Context context, List<Drink> drinks) {
        mContext = context;
        mDataSource = drinks;
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
        View rowView = mInflater.inflate(R.layout.simple_drink_row, parent, false);

        TextView nameTextView = rowView.findViewById(R.id.drinkNameTextView);
        TextView volumeTextView = rowView.findViewById(R.id.drinkVolumeTextView);
        TextView percentTextView = rowView.findViewById(R.id.percentAlcoholTextView);
        TextView timeTextView = rowView.findViewById(R.id.timeTextView);

        //set up date and time for when you had the drink

        Drink drink = (Drink) getItem(position);

        nameTextView.setText(drink.getName().toString());
        volumeTextView.setText(String.valueOf(drink.getAmount()) + "oz");
        percentTextView.setText(String.valueOf(drink.getPercent() * 100) + "%");

        DateFormat df = new SimpleDateFormat("MM/dd HH:mm");

        String time = df.format(drink.getTime());
        timeTextView.setText(time);

        return rowView;
    }

}
