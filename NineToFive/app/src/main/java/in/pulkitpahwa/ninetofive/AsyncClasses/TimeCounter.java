package in.pulkitpahwa.ninetofive.AsyncClasses;


/**
 * Created by pulkit on 29/6/15.
 * Package : in.pulkitpahwa.ninetofive.AsyncClasses
 * Project : Nine To Five
 */
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import android.os.Handler;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.nio.InvalidMarkException;

import in.pulkitpahwa.ninetofive.DatabaseHandler.DbExerciseHandler;
import in.pulkitpahwa.ninetofive.DatabaseHandler.DbSettings;
import in.pulkitpahwa.ninetofive.Exercise;
import in.pulkitpahwa.ninetofive.MainActivity;
import in.pulkitpahwa.ninetofive.R;


public class TimeCounter extends AsyncTask<String, String, String> {

    public int count=0;
    //public Integer counter;

    public int next, resourceId;
    public String categ;
    public TextView large, heading;
    public ProgressBar pb;
    public Integer seconds=0;
    public TextView secondsCounter;
    private Context context;
    public Intent i;
    public ImageView image;
    public DbExerciseHandler db;
    public DbSettings settings_db;
    public TimeCounter(Context context, Integer next, ProgressBar p, TextView t, ImageView image, DbSettings settings_db, TextView large, TextView heading) {

        this.context = context;
        this.next = next;
        this.settings_db = settings_db;
        //this.current = current;

        this.pb = p;
        this.secondsCounter = t;
        this.image  = image;
        this.large = large;
        this.heading = heading;
        Log.w("myapp", "asynctask");
        i = new Intent(context, MainActivity.class);
//        Bundle extras = new Bundle();
        Log.w("myapp", "inside next");
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.w("myapp", "pre executre");
        db =  new DbExerciseHandler(this.context);
        //Log.w("myapp","exercise activity handler ");
        String exercise_details[] = db.getExercise(next, settings_db);

        resourceId = this.context.getResources().getIdentifier(exercise_details[3],
                "drawable",this.context.getPackageName());
        //large.setText(exercise_details[2]);
        //heading.setText(exercise_details[1]);
        image.setImageResource(resourceId);


    }

    @Override
    protected void onPostExecute(String arg0) {
        super.onPostExecute(arg0);
        Log.w("myapp", "done");
        //image = null;
        //secondsCounter = ;
        //pb.setVisibility(View.INVISIBLE);
        //pb = null;
        categ = null;
        nextExercise();
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        //this.execute("st");

        Log.w("myapp", "cancelled");
    }


    @Override
    protected String doInBackground(String... arg0) {
        Log.w("myapp", "background");
        image.setImageResource(resourceId);
        while(count<=20 && this.isCancelled() == false)//change the count to 100 instead of 20
        {

            synchronized (this) {
                try {
                    Log.w("myapp","count = " + Integer.toString(count));
/*                    if (count%20 == 0)
                    {
                        nextExercise();
                    }
                    else {
*/

                        wait(1000);
                        count += 1;
                        pb.setProgress(count);
                        if (count % 5 == 0) {
                            seconds += 1;
                            secondsCounter.setText(Integer.toString(seconds));
                        }
                        Log.w("myapp", "progressing");
  //                  }
                } catch (Exception e) {
                    Log.w("myapp", "error");

                }
            }
        }

        return "apple";
    }



    protected void nextExercise() {
        Log.w("myapp","nextexerciise");
        db =  new DbExerciseHandler(this.context);
        Log.w("myapp","next = " + Integer.toString(next));
        if (next == 8)
        {
            next = 1;
            Log.w("myapp", "inside if");
            context.startActivity(i);


        }



        else {
            Log.w("myapp", "inside else");
            next += 1;
            seconds = 0;
            String exercise_details[] = db.getExercise(next, settings_db);
            resourceId = this.context.getResources().getIdentifier(exercise_details[3],
                    "drawable", this.context.getPackageName());
            large.setText(exercise_details[2]);
            heading.setText(exercise_details[1]);
            image.setImageResource(resourceId);

        }


    }

}
