package in.pulkitpahwa.ninetofive;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;

import in.pulkitpahwa.ninetofive.DatabaseHandler.DbSettings;


public class AppSettings extends ActionBarActivity {
    private DbSettings db;
    private CheckBox screen, training;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_settings);
        setRequestedOrientation(ActivityInfo
                .SCREEN_ORIENTATION_PORTRAIT);
        screen = (CheckBox) findViewById(R.id.radioButton);
        training = (CheckBox) findViewById(R.id.radioButton2);

        db = new DbSettings(this);
        Integer abc[] = db.getHomeSettings(1);

        screen.setChecked((abc[0] == 0) ? false:true);
        training.setChecked((abc[1] == 0) ? false:true);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_app_settings, menu);
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

    public void notifications(View view)
    {
        Intent notifications = new Intent(this, ExerciseNotifications.class);
        startActivity(notifications);
    }

    public void trainingTracks(View view)
    {
        Intent i = new Intent(this, TrainingAreas.class);
        startActivity(i);
    }

    public void screenOn(View view)
    {
        db.update_screen(screen.isChecked() ? 1 : 0);
    }

    public void trainMe(View view)
    {
        db.update_training(training.isChecked() ? 1 : 0);
    }

    public void done(View View)
    {
        db.update_screen(screen.isChecked() ? 1 : 0);
        db.update_training(training.isChecked() ? 1 : 0);
        Intent start_exercise = new Intent(this, Exercise.class);
        Bundle extras = new Bundle();
        extras.putInt("counter", 1);
        Log.w("myapp", "sending intent");
        start_exercise.putExtras(extras);
        startActivity(start_exercise);
    }




}
