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


class CustomAdapter extends ArrayAdapter
{
    private List<String> nameData  = new ArrayList<String>();
    private List<String> nvData  = new ArrayList<String>();
    private List<String> hdData  = new ArrayList<String>();
    private List<Integer> fidData  = new ArrayList<Integer>();
    private List<String> otData = new ArrayList<String>();
    private List<String> ctData = new ArrayList<String>();

    CustomAdapter(Context context,List<String> names, List<String> nv, List<String> hd, List<String> ot, List<String> ct, List<Integer> fid ) {
        super(context, R.layout.custom_row,  names);
        Log.w("myapp","adapter 1");
        this.nameData  = names;
        Log.w("myapp","adapter 2");
        this.nvData = nv;
        this.hdData = hd;
        Log.w("myapp","adapter 3");
        this.fidData = fid;
        this.otData = ot;
        this.ctData = ct;
        Log.w("myapp","adapter 4");
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.w("myapp","adapter 5");
        LayoutInflater namesInflater = LayoutInflater.from(getContext());
        Log.w("myapp","adapter 6");
        View customView = namesInflater.inflate(R.layout.custom_row, parent, false);
        Log.w("myapp","adapter 7");
/*        String singlename = getItem(position);
        String single= getItem(position);
        String single= getItem(position);
        String single= getItem(position);
        String single= getItem(position);
        String single= getItem(position);
*/

        TextView restaurant_name = (TextView) customView.findViewById(R.id.restaurant_name);
        Log.w("myapp","adapter 8");
        TextView serveNv = (TextView) customView.findViewById(R.id.serveNV);
        TextView hdAvailable = (TextView) customView.findViewById(R.id.hdAvailable);
        TextView opening_time = (TextView) customView.findViewById(R.id.opening_time);
        TextView closing_time = (TextView) customView.findViewById(R.id.closing_time);
        Log.w("myapp","adapter 9");
        restaurant_name.setText(nameData.get(position));
        Log.w("myapp","adapter 10");
        serveNv.setText(nvData.get(position));
        hdAvailable.setText(hdData.get(position));
        Log.w("myapp","adapter 11");
       /* TextView buckyText = (TextView) customView.findViewById(R.id.buckysText);
        buckyText.setText(singleFoodItem);
        buckyImage.setImageResource(R.drawable.ic_launcher);
        */
        return customView;


    }
}
