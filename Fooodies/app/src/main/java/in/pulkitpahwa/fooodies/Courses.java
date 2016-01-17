package in.pulkitpahwa.fooodies;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
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
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;


public class Courses extends ActionBarActivity {
    private List<String> name = new ArrayList<String>();
    private List<String> courseid = new ArrayList<String>();
    private Intent i;
    private String url;
    private HandleCoursesJSON obj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.w("myapp", "Courses created");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);
        Bundle next_url = getIntent().getExtras();
        if (next_url == null) {
            return;
        }

        url = next_url.getString("next_url");
        obj = new HandleCoursesJSON(url);
        obj.fetchJSON();
        while (obj.parsingComplete) ;
        name = obj.getName();
        courseid = obj.getCourseid();
        i = new Intent(this, Items.class);

        ImageView img = (ImageView) findViewById(R.id.image_restaurant);
        img.setImageResource(R.drawable.loader);
        new DownloadImageTask(img).execute("http://fooodies.in/"+obj.getimage());



        ListAdapter courseAdapter = new CustomCourseAdapter(this, name, courseid);
        ListView coursesListView = (ListView) findViewById(R.id.CourseName);
        coursesListView.setAdapter(courseAdapter);

        Log.w("myapp", "courses adapters and listview created");

        coursesListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String food = String.valueOf(parent.getItemAtPosition(position));
                        Toast.makeText(Courses.this, food, Toast.LENGTH_SHORT).show();
                        String next = courseid.get(position);
                        Bundle extras = new Bundle();
                        extras.putString("next_url", next);
                        extras.putString("course", name.get(position));
                        extras.putString("url", url);
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

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }
        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon = BitmapFactory.decodeStream(in);
                Log.w("myapp","get image");
            } catch (Exception e) {
                Log.w("myapp", e.getMessage());
                e.printStackTrace();
            }
            return mIcon;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

}



