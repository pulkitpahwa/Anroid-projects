package in.pulkitpahwa.ninetofive;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import in.pulkitpahwa.ninetofive.DatabaseHandler.DbSettings;


public class TrainingAreas extends ActionBarActivity {

    private DbSettings db;
    private CheckBox neck, wrist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);
        setRequestedOrientation(ActivityInfo
                .SCREEN_ORIENTATION_PORTRAIT);
        db = new DbSettings(this);
        Integer abc[] = db.getTrainingAreas(1);

        neck = (CheckBox) findViewById(R.id.CheckBox2);
        wrist = (CheckBox) findViewById(R.id.CheckBox4);

        neck.setChecked((abc[0] == 0) ? false:true);
        wrist.setChecked((abc[1] == 0) ? false:true);


    }

    public void back(View view)
    {

        Log.w("myapp",neck.isChecked() ? "neck = on" : "neck = off");
        Log.w("myapp",wrist.isChecked() ? "wrist = on" : "wrist = off");

        db.update_neck(neck.isChecked() ? 1 : 0);
        db.update_wrist(wrist.isChecked() ? 1 : 0);

        Intent i = new Intent(this, AppSettings.class);
        startActivity(i);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_training_areas, menu);
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

