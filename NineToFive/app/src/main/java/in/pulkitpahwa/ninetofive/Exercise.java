package in.pulkitpahwa.ninetofive;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.preference.PreferenceGroup;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import in.pulkitpahwa.ninetofive.AsyncClasses.TimeCounter;
import in.pulkitpahwa.ninetofive.DatabaseHandler.DbExerciseHandler;
import in.pulkitpahwa.ninetofive.DatabaseHandler.DbSettings;


public class Exercise extends ActionBarActivity {

    private DbExerciseHandler db;
    private DbSettings settings_db;//commented on 3rd july
    private Integer counter ;
    private TimeCounter timeCounter;
    private ImageView image;
    private int resourceId;
    public TextView large, heading, secondsCounter;
    public ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        setRequestedOrientation(ActivityInfo
                .SCREEN_ORIENTATION_PORTRAIT);
        Log.w("myapp", "exercise open");
        settings_db = new DbSettings(this);//commented on 3rd july
        Intent intent = getIntent();
        counter = 1;
        intent.getExtras().getInt("counter");
        Log.w("myapp", "exercise 1");
        //current = extras.getInt("current");
        //counter = extras.getInt("counter");

        //categ = extras.getString("category", "neck");



        db =  new DbExerciseHandler(this);
        Log.w("myapp","exercise activity handler ");
        String exercise_details[] = db.getExercise(counter, settings_db);

        resourceId = this.getResources().getIdentifier(exercise_details[3],
                "drawable",this
                .getPackageName());

        image = (ImageView) findViewById(R.id.imageView);
        image.setImageResource(resourceId);

        large = (TextView) findViewById(R.id.textView2);
        large.setText(exercise_details[2]);
        Log.w("myapp", "exercise 1");
        heading= (TextView) findViewById(R.id.heading);
        heading.setText(exercise_details[1]);

        pb = (ProgressBar) findViewById(R.id.progressBar);
        Log.w("myapp", "exercise 2");
        ImageView mimage = (ImageView) findViewById(R.id.imageView);
        if (counter == 14) {
            counter = 1;
        } else {
            counter = counter + 1;
        }

        secondsCounter = (TextView) findViewById(R.id.textView27);


        timeCounter = new TimeCounter(getApplicationContext(),counter, pb, secondsCounter,mimage, settings_db, large, heading );
        timeCounter.execute("st");
        Log.w("myapp", "executed");
  }


    @Override
    protected void onStop() {
        super.onStop();
        //timeCounter.cancel(true);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        image = null;
        //timeCounter.cancel(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //timeCounter.cancel(true);
        image = null;
       // timeCounter.cancel(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.w("myapp", "destroying async");
        //timeCounter.cancel(true);
        image = null;
        Log.w("myapp", "destroyed async");
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        image = (ImageView) findViewById(R.id.imageView);
        image.setImageResource(resourceId);
        //timeCounter.execute("st");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_exercise, menu);
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
            Intent setting =  new Intent(this, ExerciseNotifications.class);
            startActivity(setting);
        }

        return super.onOptionsItemSelected(item);
    }

    public void home(View view)
    {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
