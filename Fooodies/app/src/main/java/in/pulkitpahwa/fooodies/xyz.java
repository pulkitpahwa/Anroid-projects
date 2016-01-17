package in.pulkitpahwa.fooodies;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;


/*public class xyz extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xyz);


        SwipeLayout swipeLayout =  (SwipeLayout)findViewById(R.id.sample1);
        swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
        swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onClose(SwipeLayout layout) {
                //when the SurfaceView totally cover the BottomView.
            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {
                //you are swiping.
            }

            @Override
            public void onStartOpen(SwipeLayout layout) {

            }

            @Override
            public void onOpen(SwipeLayout layout) {
                //when the BottomView totally show.
            }

            @Override
            public void onStartClose(SwipeLayout layout) {

            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {
                //when user's hand released.
            }
        });
    }

    public void first_button(View view)
    {
        Log.w("myapp", "first button");

    }

    public void second_button(View view)
    {
        Log.w("myapp", "second button");

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_xyz, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
*/


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;
//import


class xyz extends ArrayAdapter
{
    private List<String> nameData  = new ArrayList<String>();
    private List<String> idData  = new ArrayList<String>();
    private List<String> priceData  = new ArrayList<String>();
    private List<String> imageData  = new ArrayList<String>();
    private String courseName= new String();
    private Context cont;



    xyz(Context context,List<String> names, List<String> id,List<String> price , String courseName,List<String> images   ) {
        super(context, R.layout.custom_row,  names);
        cont = context;
        Log.w("myapp","course adapter 1");
        this.nameData  = names;
        Log.w("myapp","course adapter 2");
        this.idData = id;
        Log.w("myapp","course adapter 3");
        this.priceData = price;
        this.courseName = courseName;
        this.imageData = images;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater namesInflater = LayoutInflater.from(getContext());
        View customView = namesInflater.inflate(R.layout.activity_xyz, parent, false);
        TextView item_name = (TextView) customView.findViewById(R.id.my_item_name);
        Log.w("myapp","item adapter 8");
        item_name.setText(nameData.get(position));
        TextView item_price = (TextView) customView.findViewById(R.id.my_item_price);
        item_price.setText(priceData.get(position));
   /*     ImageView myimage = (ImageView) customView.findViewById(R.id.image_item);
        myimage.setImageResource(R.drawable.ic_launcher);

        Picasso.with(cont)
                .load("http://fooodies.in/media/"+ imageData.get(position))
                .into(myimage);
*/        return customView;
    }

}
