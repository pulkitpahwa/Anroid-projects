package in.pulkitpahwa.ninetofive;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;



import in.pulkitpahwa.ninetofive.Services.TimeCounterService;


public class MainActivity extends ActionBarActivity {

    //private DbSettings db;//commented on 3rd july

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo
                .SCREEN_ORIENTATION_PORTRAIT);
        //db = new DbSettings(this); //commented on 3rd july



        Log.w("myapp", "hello ");

       /// TimeCounterService timeCounterService =  TimeCounterService.getTimeCounterServiceInstance();
       // timeCounterService.startService(i);
        startService(new Intent(this, TimeCounterService.class));

        /*if(timeCounterService.count() == 1) {
            Log.w("myapp","inside if ");
            timeCounterService.stopSelf();
            Log.w("myapp",Integer.toString(timeCounterService.count()));
            Intent i = new Intent(getBaseContext(), TimeCounterService.class);
            startService(i);
        }
        else{
            Log.w("myapp","inside else ");
            timeCounterService.stopSelf();
        }
        Log.w("myapp","after else ");

        Log.w("myapp",Integer.toString(timeCounterService.count()));
*/

       /*create new service

        TimeChecker t =  new TimeChecker(getApplicationContext());

        find the number of instances of this service
        Integer count = t.getcount();

        if(count == 1) {
            //if count = 1, continue wth the service.
            Log.w("myapp","service starting");
            t.execute("st");
        }
        else{
            //decrease the service count, by setting t.count = 1
            //stopService or stopSelf method will work here.
            Log.w("myapp","service already exist");
        }*/
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            Intent i = new Intent(this, AppSettings.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void disclaimer(View view)
    {
        Intent i = new Intent(this, Disclaimer.class);
        startActivity(i);
    }

    public void Start(View view)
    {
        Intent start_exercise = new Intent(this, Exercise.class);
        Bundle extras = new Bundle();
        extras.putInt("counter", 1);
        Log.w("myapp","sending intent");
        start_exercise.putExtras(extras);
        startActivity(start_exercise);
    }

    public void app_settings(View view)
    {
        Intent setting =  new Intent(this, ExerciseNotifications.class);
        startActivity(setting);
        finish();
    }

    @Override
    protected void onDestroy() {
        Log.w("myapp","destroyed ");
        super.onDestroy();
    }
}
