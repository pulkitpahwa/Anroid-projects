package in.pulkitpahwa.fooodies;

/**
 * Created by pulkit on 13/4/15.
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


class CustomCourseAdapter extends ArrayAdapter
{
    private List<String> nameData  = new ArrayList<String>();
    private List<String> idData  = new ArrayList<String>();


    CustomCourseAdapter(Context context,List<String> names, List<String> id ) {
        super(context, R.layout.custom_row,  names);
        Log.w("myapp","course adapter 1");
        this.nameData  = names;
        Log.w("myapp","course adapter 2");
        this.idData = id;
        Log.w("myapp","course adapter 3");

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.w("myapp","course adapter 5");
        LayoutInflater namesInflater = LayoutInflater.from(getContext());
        Log.w("myapp","course adapter 6");
        View customView = namesInflater.inflate(R.layout.custom_course_row, parent, false);
        Log.w("myapp","course adapter 7");
        TextView course_name = (TextView) customView.findViewById(R.id.course_name);
        Log.w("myapp","course adapter 8");
        course_name.setText(nameData.get(position));
        Log.w("myapp","adapter 10");
        return customView;


    }
}
