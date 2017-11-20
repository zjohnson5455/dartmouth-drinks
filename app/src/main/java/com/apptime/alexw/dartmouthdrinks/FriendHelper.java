package com.apptime.alexw.dartmouthdrinks;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by zacharyjohnson on 11/20/17.
 */

public class FriendHelper {

    String name;
    String number;
    Context context;

    public FriendHelper(Context context, String name, String number) {
        this.name = name;
        this.number = number;
        this.context = context;
    }

    public TextView createTextView() {
        TextView text = new TextView(context);
        text.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        text.setText(name + ": " + number);
        text.setTextSize(12.0f);
        return text;

    }
}
