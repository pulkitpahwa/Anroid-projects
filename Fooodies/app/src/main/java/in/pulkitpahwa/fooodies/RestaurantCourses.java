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
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class RestaurantCourses extends ActionBarActivity {
    private List<String> name  = new ArrayList<String>();
    private List<Integer> courseid  = new ArrayList<Integer>();
    private Intent i;
    private String secret_key;
    private String app_id;
    private String restaurant_id;
    private String table_id;
    private String urlString;

    private HandleRestaurantCoursesJSON obj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    
        //this page is accessed only if the user is inside the restaurant. This is based on the location apis.
        Log.w("myapp", "Restaurant Courses created");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_courses);
        Bundle extras = getIntent().getExtras();
        courseid = extras.getIntegerArrayList("course-id");
        name = extras.getStringArrayList("courses");
        secret_key = extras.getString("secret_key");
        app_id = extras.getString("app_id");
        restaurant_id = extras.getString("restaurant");
        table_id = extras.getString("table_id");

        TextView success = (TextView) findViewById(R.id.textView26);
        success.setVisibility(View.INVISIBLE);
        //Bundle next_url = getIntent().getExtras();
        //if (next_url == null)
        //{
        //    return;
        //}

        //String url = next_url.getString("next_url");//next_url is the restaurant_id.

        i = new Intent(this, RestaurantItems.class);


        ListAdapter courseAdapter = new RestaurantCustomCourseAdapter(this, name, courseid);
        ListView coursesListView = (ListView) findViewById(R.id.RestaurantCourseName);
        coursesListView.setAdapter(courseAdapter);

        Log.w("myapp","courses adapters and listview created");

        coursesListView.setOnItemClickListener(
                new  AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String food = String.valueOf(parent.getItemAtPosition(position));
                        Toast.makeText(RestaurantCourses.this, food, Toast.LENGTH_SHORT).show();
                        Log.w("myapp", "ass1");
                        String next = courseid.get(position).toString();
                        Bundle extras = new Bundle();
                        extras.putString("next_url",next);
                        extras.putString("restaurant","1");
                        extras.putString("course",name.get(position));
                        extras.putString("secret_key", secret_key);
                        extras.putString("app_id", app_id);
                        extras.putString("table_id",table_id);
                        Log.w("myapp", "ass2");
                        i.putExtras(extras);

                        startActivity(i);
                    }
                }
        );
    }

    public void call_waiter(View view)
    {
        urlString = "http://fooodies.in/bill/call-waiter/"+secret_key + "/" + restaurant_id + "/"+app_id + "/" + table_id + "/";
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(urlString);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(30000);
                    conn.setConnectTimeout(35000);
                    conn.setRequestMethod("GET");
                    Log.w("myapp",urlString + " called" );
                    conn.setDoInput(true);
                    Log.w("myapp", " ab 3");
                    conn.connect();
                    Log.w("myapp", " ab 4");
                    TextView success = (TextView) findViewById(R.id.textView26);
                    success.setVisibility(View.VISIBLE);
                    success.setText("Congrats, your order has been placed");
                    Log.w("myapp","congrats");

                }
                catch(Exception e)
                {
                    Log.w("myapp","bill 16");
                    e.printStackTrace();
                }
            }
        });
        thread.start();
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


