package in.pulkitpahwa.fooodies;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class ChooseOption extends ActionBarActivity {
    private CreateAppID db = new CreateAppID(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_option);
        setRequestedOrientation(ActivityInfo
                .SCREEN_ORIENTATION_PORTRAIT);
        Log.w("myapp","option 1");
        Log.w("myapp", Integer.toString(db.getIDsCount()));
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_choose_option, menu);
        return true;
    }

    public void homedelivery(View view)
    {
        Log.w("myapp","home1");
        Intent i = new Intent(this, LocationFinder.class);
        startActivity(i);
    }


    public void inrestaurant(View view)
    {
        Log.w("myapp","home2");
        Intent i = new Intent(this, RestaurantForm.class);
        startActivity(i);
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
