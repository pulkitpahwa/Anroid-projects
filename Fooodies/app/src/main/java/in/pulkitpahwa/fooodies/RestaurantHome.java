package in.pulkitpahwa.fooodies;


import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class RestaurantHome extends ActionBarActivity {
    private List<String> name  = new ArrayList<String>();
    private List<String> nv  = new ArrayList<String>();
    private List<String> hd  = new ArrayList<String>();
    private List<Integer> fid  = new ArrayList<Integer>();
    private List<String> ot = new ArrayList<String>();
    private List<String> ct = new ArrayList<String>();
    private Intent i;

    private HandleMyRestaurantJSON obj;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.w("myapp","restauranthome created");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_home);
        Bundle next_url = getIntent().getExtras();
        if (next_url == null)
        {
            return;
        }

        String url = next_url.getString("next_url");
        obj = new HandleMyRestaurantJSON(url);
        obj.fetchJSON();
        while(obj.parsingComplete);
        name  = obj.getName();
        nv = obj.getNv();
        hd = obj.getHd();
        fid = obj.getFid();
        ot = obj.getOt();
        ct = obj.getCt();
        i = new Intent(this, Courses.class);

        ListAdapter restaurantAdapter = new RestaurantCustomAdapter(this, name,nv, hd, ot, ct, fid);
        ListView restaurantListView = (ListView) findViewById(R.id.RestaurantNames);
        restaurantListView.setAdapter(restaurantAdapter);

        Log.w("myapp","restaurant adapters and listview created");

        restaurantListView.setOnItemClickListener(
                new  AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Log.w("myapp","a1");
                        String food = String.valueOf(parent.getItemAtPosition(position));
                        Log.w("myapp","a2");
                        Toast.makeText(RestaurantHome.this, food, Toast.LENGTH_SHORT).show();
                        Log.w("myapp","a3");
                        String next = fid.get(position).toString();
                        Log.w("myapp","a4");
                        i.putExtra("next_url", next);
                        Log.w("myapp","a5");
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
