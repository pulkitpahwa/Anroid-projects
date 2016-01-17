package in.pulkitpahwa.fooodies;


import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import android.widget.EditText;
import android.widget.TextView;

public class RestaurantForm extends ActionBarActivity {
    private CreateAppID db = new CreateAppID(this);
    private HandleRestaurantCoursesJSON obj;
    private List<String> name  = new ArrayList<String>();
    private List<Integer> cid  = new ArrayList<Integer>();
    private Intent i;
    private View view;
    private static EditText input_restaurant_id;
    private static EditText table_text;
    private Double longi ;
    private Double lati ;
    private String secret_key;
    private Boolean wrong;
    private String app_id;
    private String table_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_form);
        setRequestedOrientation(ActivityInfo
                .SCREEN_ORIENTATION_PORTRAIT);
        TextView errorlocal = (TextView) findViewById(R.id.errorlocal);
        errorlocal.setVisibility(View.INVISIBLE);

        Log.w("myapp", "restaurant form created");
        try {
            //Log.w("myapp", db.getUserLocation().get(2).substring(10));
           // Log.w("myapp", db.getUserLocation().get(1).substring(10));
            lati = Double.parseDouble("77.73039784");
            longi = Double.parseDouble("28.95579594");
            //Log.w()
            //Log.w("myapp", db.getUserLocation().get(2) + "...." + db.getUserLocation().get(1));
            Log.w("myapp", longi.toString() + "..." + lati.toString());
            Log.w("myapp", db.getAppID());
            app_id = db.getAppID();
        }
        catch(Exception e)
        {
            setContentView(R.layout.errorlayout);
        }
/*        pb = (ProgressBar) findViewById(R.id.progressBar1);
        pb.setVisibility(View.INVISIBLE);

        editLocation = (EditText) findViewById(R.id.editTextLocation);

        btnGetLocation = (Button) findViewById(R.id.btnLocation);
        btnGetLocation.setOnClickListener(this);
*/
    }

    public  void submit_restaurant_id(View view) {
        input_restaurant_id = (EditText) findViewById(R.id.input_restaurant_id);
        Log.w("myapp", "edit text");
        String restaurant_id = input_restaurant_id.getText().toString();
        table_text = (EditText) findViewById(R.id.input_table_id);
        table_id = table_text.getText().toString();
        obj = new HandleRestaurantCoursesJSON("http://fooodies.in/bill/" + restaurant_id + "/" + longi.toString() + "/" + lati + "/" + db.getAppID() + "/" + table_id);
        obj.fetchJSON();
        while (obj.parsingComplete) ;
        wrong = obj.getwrong();
        if (wrong) {
            TextView errorlocal = (TextView) findViewById(R.id.errorlocal);
            errorlocal.setVisibility(View.VISIBLE);
        } else {
            name = obj.getName();
            cid = obj.getCourseid();
            secret_key = obj.getSecret_key();
            Log.w("myapp", "ciddddd     =  " + cid.toString());
            i = new Intent(this, RestaurantCourses.class);
            i.putIntegerArrayListExtra("course-id", (ArrayList<Integer>) cid);
            i.putStringArrayListExtra("courses", (ArrayList<String>) name);
            i.putExtra("secret_key", secret_key);
            i.putExtra("app_id", app_id);
            i.putExtra("restaurant", restaurant_id);
            i.putExtra("table_id", table_id);
            startActivity(i);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_restaurant_form, menu);
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
