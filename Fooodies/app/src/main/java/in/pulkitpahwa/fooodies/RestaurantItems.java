package in.pulkitpahwa.fooodies;


import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class RestaurantItems extends ActionBarActivity {
    private List<String> name  = new ArrayList<String>();
    private List<String> itemid  = new ArrayList<String>();
    private List<String> price = new ArrayList<String>();
    private List<String> tax = new ArrayList<String>();
    private String courseName;
    private Intent i;
    private String restaurant;
    private HandleRestaurantItemsJSON obj;
    private String secret_key;
    private String app_id;
    private String restaurant_id;
    private String table_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.w("myapp","Items created");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_items);
        Log.w("myapp", "ass4");
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String url = extras.getString("next_url");
        courseName = extras.getString("course");
        secret_key = extras.getString("secret_key");
        app_id = extras.getString("app_id");
        restaurant_id = extras.getString("restaurant");
        table_id = extras.getString("table_id");


        Log.w("myapp", "ass5");
        obj = new HandleRestaurantItemsJSON(url);
        obj.fetchJSON();
        while(obj.parsingComplete);
        name  = obj.getName();
        itemid = obj.getItemid();
        price = obj.getPrice();
        tax = obj.getTax();
        i = new Intent(this, RestaurantBill.class);
        Log.w("myapp","vrinda8");

        ListAdapter itemAdapter = new RestaurantCustomItemAdapter(this, name, itemid, price, courseName);
        ListView itemsListView = (ListView) findViewById(R.id.RestaurantItemName);
        TextView course_name= (TextView) findViewById(R.id.restaurant_course_name);
        course_name.setText(courseName);
        Log.w("myapp", "vrinda7");
        itemsListView.setAdapter(itemAdapter);
        Log.w("myapp","items adapters and listview created");

        itemsListView.setOnItemClickListener(
                new  AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Log.w("myapp","vrinda1");
                        String food = String.valueOf(parent.getItemAtPosition(position));
                        Toast.makeText(RestaurantItems.this, food, Toast.LENGTH_SHORT).show();
                        String next = itemid.get(position);
                        Log.w("myapp","vrinda2");
                        Bundle extras = new Bundle();
                        extras.putString("next_url",next);
                        extras.putString("item",name.get(position));
                        extras.putString("price",price.get(position));
                        extras.putString("id",itemid.get(position));
                        Log.w("myapp", "vrinda4");
                        extras.putString("tax", tax.get(position));
                        extras.putString("restaurant", restaurant);
                        extras.putString("secret_key", secret_key);
                        extras.putString("app_id",app_id);
                        extras.putString("restaurant", restaurant_id);
                        Log.w("myapp", "vrinda5");
                        extras.putString("table_id", table_id);
                        i.putExtras(extras);
                        Log.w("myapp", "vrinda6");
                        startActivity(i);
                    }
                }
        );
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_restaurant_home, menu);
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


