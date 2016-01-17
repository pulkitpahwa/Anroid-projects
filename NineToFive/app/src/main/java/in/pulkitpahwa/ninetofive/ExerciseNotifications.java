package in.pulkitpahwa.ninetofive;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import in.pulkitpahwa.ninetofive.DatabaseHandler.DbSettings;


public class ExerciseNotifications extends ActionBarActivity {
    private DbSettings db;
    public  EditText st_hrs, st_min, et_hrs, et_min,duration;
    public CheckBox sun, mon, tues, wed, thurs,fri, sat;
    public Boolean su,mo, tu,we, th, fr,sa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_notifications);
        setRequestedOrientation(ActivityInfo
                .SCREEN_ORIENTATION_PORTRAIT);
        db = new DbSettings(this);
        Integer abc[] = db.getNotificationSettings(1);

        st_hrs = (EditText) findViewById(R.id.st_hrs);
        st_hrs.setFilters(new InputFilter[]{new InputFilterMinMax("0", "24")});
        st_hrs.setText(Integer.toString(abc[0]));

        st_min = (EditText) findViewById(R.id.editText4);//end_time
        st_min.setFilters(new InputFilter[]{new InputFilterMinMax("0", "60")});
        st_min.setText(Integer.toString(abc[1]));

        et_hrs = (EditText) findViewById(R.id.et_hrs);//duration
        et_hrs.setFilters(new InputFilter[]{new InputFilterMinMax("0", "24")});
        et_hrs.setText(Integer.toString(abc[2]));

        et_min = (EditText) findViewById(R.id.editText5);
        et_min.setFilters(new InputFilter[]{new InputFilterMinMax("0", "60")});
        et_min.setText(Integer.toString(abc[3]));

        duration = (EditText) findViewById(R.id.editText3);
        duration.setFilters(new InputFilter[]{new InputFilterMinMax("0", "150")});
        duration.setText(Integer.toString(abc[10]));


        st_hrs.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Integer value = Integer.parseInt(st_hrs.getText().toString());
                db.update_st_hrs(value);


            }
        });

        st_min.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Integer value = Integer.parseInt(st_min.getText().toString());
                db.update_st_min(value);
            }
        });

        et_hrs.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Integer value = Integer.parseInt(et_hrs.getText().toString());
                db.update_et_hrs(value);
            }
        });

        et_min.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Integer value = Integer.parseInt(et_min.getText().toString());
                db.update_et_mins(value);
            }
        });

        duration.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Integer value = Integer.parseInt(duration.getText().toString());
                db.update_duration(value);


            }
        });

        sun = (CheckBox)  findViewById(R.id.checkBox);
        mon = (CheckBox)  findViewById(R.id.checkBox2);
        tues = (CheckBox)  findViewById(R.id.checkBox3);
        wed = (CheckBox)  findViewById(R.id.checkBox4);
        thurs = (CheckBox)  findViewById(R.id.checkBox5);
        fri = (CheckBox)  findViewById(R.id.checkBox6);
        sat = (CheckBox)  findViewById(R.id.checkBox7);

        su = (abc[4] == 0) ? false:true;
        mo = (abc[5] == 0) ? false:true;
        tu = (abc[6] == 0) ? false:true;
        we = (abc[7] == 0) ? false:true;
        th = (abc[8] == 0) ? false:true;
        fr = (abc[9] == 0) ? false:true;
        sa = (abc[11] == 0) ? false:true;

        sun.setChecked(su);
        mon.setChecked(mo);
        tues.setChecked(tu);
        wed.setChecked(we);
        thurs.setChecked(th);
        fri.setChecked(fr);
        sat.setChecked(sa);


    }


    public void done(View view)
    {
        db.update_sunday(sun.isChecked() ? 1 : 0);
        db.update_monday(mon.isChecked() ? 1 : 0);
        db.update_tuesday(tues.isChecked() ? 1 : 0);
        db.update_wednesday(wed.isChecked() ? 1 : 0);
        db.update_thursday(thurs.isChecked() ? 1 : 0);
        db.update_friday(fri.isChecked() ? 1 : 0);
        db.update_saturday(sat.isChecked() ? 1: 0 );

        Intent i = new Intent(this, AppSettings.class);
        startActivity(i);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_exercise_notifications, menu);
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
