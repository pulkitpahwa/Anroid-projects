package in.pulkitpahwa.fooodies;

/**
 * Created by pulkit on 15/4/15.
 */
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


class RestaurantCustomItemAdapter extends ArrayAdapter
{
    private List<String> nameData  = new ArrayList<String>();
    private List<String> idData  = new ArrayList<String>();
    private List<String> priceData  = new ArrayList<String>();
    private String courseName= new String();



    RestaurantCustomItemAdapter(Context context,List<String> names, List<String> id,List<String> price , String courseName  ) {
        super(context, R.layout.custom_restaurant_row,  names);
        Log.w("myapp","course adapter 1");
        this.nameData  = names;
        Log.w("myapp","course adapter 2");
        this.idData = id;
        Log.w("myapp","course adapter 3");
        this.priceData = price;
        this.courseName = courseName;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.w("myapp","item adapter 5");
        LayoutInflater namesInflater = LayoutInflater.from(getContext());
        Log.w("myapp","item adapter 6");
        View customView = namesInflater.inflate(R.layout.custom_restaurant_item_row, parent, false);
        Log.w("myapp","item adapter 7");
        TextView item_name = (TextView) customView.findViewById(R.id.restaurant_item_name);
        Log.w("myapp","item adapter 8");
        item_name.setText(nameData.get(position));
        TextView item_price = (TextView) customView.findViewById(R.id.restaurant_item_price);
        Log.w("myapp","item 9");
        item_price.setText(priceData.get(position));
        Log.w("myapp","item 10");

        return customView;


    }
}

