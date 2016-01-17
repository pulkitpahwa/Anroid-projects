package in.pulkitpahwa.fooodies;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;


public class PlaceHomeDelivery extends ActionBarActivity {
    private HandleBillJSON obj;
    private List<String> item_array  = new ArrayList<String>();
    private List<String> price_array = new ArrayList<String>();
    private List<String> tax_array = new ArrayList<String>();
    private List<String> item_total_array = new ArrayList<String>();
    private Double total;
    private String bill_no;
    private String name;
    private String address;
    private String contact;
    private String email;
    private String city;
    private Intent i;
    private String restaurant;
    private String app_id;
    private String items;
    private String qty;
    private DatabaseHandler db= new DatabaseHandler(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_home_delivery);
        setRequestedOrientation(ActivityInfo
                .SCREEN_ORIENTATION_PORTRAIT);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        restaurant = extras.getString("restaurant");
        app_id = extras.getString("app_id");
        items = extras.getString("items");
        qty = extras.getString("qty");

        restaurant = extras.getString("restaurant");

        String next = "http://fooodies.in/bill/" + restaurant + "/" + "hd/" + items + "/" + qty + "/" + app_id + "/";
        obj = new HandleBillJSON(next);
        obj.fetchJSON();
        Log.w("myapp", "main 1");
        while (obj.parsingComplete) ;
        item_array = obj.getItems();
        Log.w("myapp" , "main 2");
        price_array = obj.get_price();
        tax_array = obj.get_tax();
        Log.w("myapp" , "main 3");
        item_total_array = obj.get_item_total();
        Log.w("myapp" , "main 4");
        total = Double.parseDouble(obj.get_total());
        Log.w("myapp", "main 5");
/*                bill_no = obj.get_bill_no();
                Log.w("myapp", "your order has been placed");
*/
        Log.w("myapp","order placed. you will receive order at your place in 30 minutes. kepp your change ready");
        db.deleteAll();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_place_home_delivery, menu);
        return true;
    }

    public void goToCourses(View view)
    {
        Intent abc = new Intent(this, ChooseOption.class);
        startActivity(abc);

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
