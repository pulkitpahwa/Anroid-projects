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


public class Items extends ActionBarActivity {
    private List<String> name  = new ArrayList<String>();
    private List<String> itemid  = new ArrayList<String>();
    private List<String> price = new ArrayList<String>();
    private List<String> tax = new ArrayList<String>();
    private String courseName;
    private Intent i;
    private List<String> images = new ArrayList<String>();
    private HandleItemsJSON obj;
    private String restaurant_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.w("myapp","Items created");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String url = extras.getString("next_url");
        restaurant_id = extras.getString("url");
        courseName = extras.getString("course");
        obj = new HandleItemsJSON(url);
        obj.fetchJSON();
        while(obj.parsingComplete);
        name  = obj.getName();
        itemid = obj.getItemid();
        price = obj.getPrice();
        tax = obj.getTax();
        images = obj.getimages();

        i = new Intent(this, Bill.class);


        ListAdapter itemAdapter = new CustomItemAdapter(this, name, itemid, price, courseName, images);
        ListView itemsListView = (ListView) findViewById(R.id.ItemName);
        TextView course_name= (TextView) findViewById(R.id.course_name);
        course_name.setText(courseName);

        itemsListView.setAdapter(itemAdapter);

        Log.w("myapp","items adapters and listview created");

        itemsListView.setOnItemClickListener(
                new  AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String food = String.valueOf(parent.getItemAtPosition(position));
                        Toast.makeText(Items.this, food, Toast.LENGTH_SHORT).show();
                        String next = itemid.get(position);
                        Bundle extras = new Bundle();
                        extras.putString("next_url",next);
                        extras.putString("restaurant", restaurant_id);
                        extras.putString("item",name.get(position));
                        extras.putString("price",price.get(position));
                        extras.putString("id",itemid.get(position));
                        extras.putString("tax",tax.get(position));

                        i.putExtras(extras);
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

