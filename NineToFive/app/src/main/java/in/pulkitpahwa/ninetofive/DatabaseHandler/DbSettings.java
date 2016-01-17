package in.pulkitpahwa.ninetofive.DatabaseHandler;

import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by pulkit on 15/6/15.
 */
public class DbSettings extends SQLiteOpenHelper {



    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "9to5db";
    private static final String TABLE_settings = "settingsManager";
    private static final String screen_on = "screen_on";
    private static final String settings_id= "settings_id";
    private static final String train_me = "train_me";
    private static final String start_Time_hrs = "start_time";
    private static final String start_Time_mins = "start_time_mins";
    private static final String end_time_hrs= "end_time_hrs";
    private static final String end_time_mins = "end_time_mins";
    private static final String sunday = "sunday";
    private static final String monday = "monday";
    private static final String tuesday = "tuesday";
    private static final String wednesday = "wednesday";
    private static final String thursday = "thursday";
    private static final String friday = "friday";
    private static final String saturday = "saturday";
    private static final String durations = "duration";
    private static final String neck = "neck";
    private static final String eyes = "eyes";
    private static final String wrist = "wrist";
    private static final String last_exercise = "last_exercise";
    private static final String next_exercise = "next_exercise";

    public DbSettings(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.w("myapp", "settings dbhandler on create");
        try {

            db = SQLiteDatabase.openDatabase(TABLE_settings, null,
                    SQLiteDatabase.OPEN_READWRITE);
        }
        catch (SQLiteException e) {
            try {
                Log.w("myapp","creating db");
                String CREATE_EXERCISE_TABLE = "CREATE TABLE " + TABLE_settings +
                        "( "+settings_id + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + screen_on + " Integer," +
                        train_me + " Integer," +
                        start_Time_hrs + " Integer," +
                        start_Time_mins + " Integer," +
                        end_time_hrs + " Integer," +
                        end_time_mins + " Integer," +
                        sunday + " Integer,"+
                        monday + " INTEGER, " +
                        tuesday + " INTEGER, " +
                        wednesday + " INTEGER, " +
                        thursday + " INTEGER, " +
                        friday + " INTEGER, " +
                        durations + " INTEGER, " +
                        neck + " INTEGER, " +
                        wrist + " INTEGER, " +
                        eyes + " INTEGER, " +
                        last_exercise + " INTEGER, " +
                        next_exercise + " INTEGER, " +
                        saturday + " Integer);";
                db.execSQL(CREATE_EXERCISE_TABLE);
               // Log.w("myapp","table created");
                addSettings(db);
               // Log.w("myapp", "settings db created");
            } catch (Exception ef) {
                //Log.w("myapp","settings exception caught");
                addSettings(db);
            }
        }
       // Log.w("myapp", "settings dbhandler 1");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_settings);
        // Create tables again
        onCreate(db);
    }

    public void addSettings(SQLiteDatabase db) {
        Log.w("myapp", "settings dbhandler 2");
        Log.w("myapp", "settings dbhandler 3");
        Log.w("myapp", "settings dbhandler 4");
        ContentValues values = new ContentValues();

        values.put(sunday, 1);
        values.put(monday, 1);
        values.put(tuesday, 1);
        values.put(wednesday, 1);
        values.put(thursday, 1);
        values.put(friday, 1);
        values.put(saturday, 1);
        values.put(screen_on,1);
        values.put(train_me, 1);
        values.put(start_Time_hrs, 9);
        values.put(end_time_hrs, 17);
        values.put(start_Time_mins, 0);
        values.put(end_time_mins, 0);
        values.put(durations, 50);
        values.put(neck, 1);
        values.put(wrist, 1);
        values.put(eyes, 1);
        values.put(last_exercise,1);
        values.put(next_exercise,2);

        db.insert(TABLE_settings, null, values);
        Log.w("myapp", "settings all items added to values");

    }

/*    public Integer[] get_current_exercise_count(){
        //return the current count and  the next exercise to be done.
        //
        //first element return : current_exercise, second_element =
        // next_Exercise
        SQLiteDatabase db = this.getReadableDatabase();
       // Log.w("myapp","setings db get current exercise count");

        Cursor cursor = db.query(TABLE_settings, new String[] {last_exercise,
                        next_exercise},
                settings_id + "=?",
                new String[] { String.valueOf(1) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

       // Log.w("myapp", "settings get current exercise count 2");

        Integer[] my_Exercise_data= new Integer[]{
                cursor.getInt(0),
                cursor.getInt(1)
        };
      //  Log.w("myapp", "settings get current exercise count 3");

        return my_Exercise_data;

    }
*/
    public void update_exercise_count(int current) {
        //takes the current count as input and update the next count based on
        // some computation.
        //the computation will be based on user settings: the type of
        // exercise that he prefers to do.

       // Log.w("myapp","update exercise count settings db");
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(last_exercise, current);

        db.update(TABLE_settings, values, settings_id + " = ?",
                new String[]{String.valueOf(1)});

    }

    public Integer[]  getFirstSettings(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_settings, new String[] { train_me,
                        screen_on,},
                settings_id + "=?",
                new String[] { String.valueOf(1) }, null, null, null, null);
        if (cursor != null)
           cursor.moveToFirst();

        Log.w("myapp", "settings dbhandler 11");
        Integer[] my_exercise= new Integer[]{cursor.getInt(0),//exercise_id
                cursor.getInt(1)//exercise_NAME
        };
        Log.w("myapp", "settings dbhandler 12");
        return my_exercise;
    }

    public void updateFirstSettings(Integer settings, Boolean train,
                                    Boolean screen) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        Integer ab = 0;
        Integer bc = 0;
        if(train)
            ab = 1;
        if (screen)
            bc = 1;
        values.put(train_me, ab);
        values.put(screen_on, bc);

        Log.w("myapp", "all items added to values");

        // updating row
        db.update(TABLE_settings, values, settings_id + " = ?",
                new String[]{String.valueOf(1)});
    }

    public void update_st_hrs(Integer value) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(start_Time_hrs, value);
        db.update(TABLE_settings, values, settings_id + " = ?",
                new String[]{String.valueOf(1)});
    }

    public void update_st_min(Integer value) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(start_Time_mins, value);
        db.update(TABLE_settings, values, settings_id + " = ?",
                new String[]{String.valueOf(1)});
    }

    public void update_et_hrs(Integer value) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(end_time_hrs, value);
        db.update(TABLE_settings, values, settings_id + " = ?",
                new String[]{String.valueOf(1)});
    }

    public void update_et_mins(Integer value) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(end_time_mins, value);
        db.update(TABLE_settings, values, settings_id + " = ?",
                new String[]{String.valueOf(1)});
    }

    public void update_sunday(Integer value) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(sunday, value);
        db.update(TABLE_settings, values, settings_id + " = ?",
                new String[]{String.valueOf(1)});
    }

    public void update_monday(Integer value) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(monday, value);
        db.update(TABLE_settings, values, settings_id + " = ?",
                new String[]{String.valueOf(1)});
    }

    public void update_tuesday(Integer value) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(tuesday, value);
        db.update(TABLE_settings, values, settings_id + " = ?",
                new String[]{String.valueOf(1)});
    }

    public void update_wednesday(Integer value) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(wednesday, value);
        db.update(TABLE_settings, values, settings_id + " = ?",
                new String[]{String.valueOf(1)});
    }

    public void update_thursday(Integer value) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(thursday, value);
        db.update(TABLE_settings, values, settings_id + " = ?",
                new String[]{String.valueOf(1)});
    }

    public void update_friday(Integer value) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(friday, value);
        db.update(TABLE_settings, values, settings_id + " = ?",
                new String[]{String.valueOf(1)});
    }

    public void update_saturday(Integer value) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(saturday, value);
        db.update(TABLE_settings, values, settings_id + " = ?",
                new String[]{String.valueOf(1)});
    }

    public void update_duration(Integer value) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(durations, value);
        db.update(TABLE_settings, values, settings_id + " = ?",
                new String[]{String.valueOf(1)});
    }

    public Integer[] get_tracks()
    {
        //Log.w("myapp", "settings get_Tracks 1");
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_settings, new String[] { neck,
                        wrist,eyes},
                settings_id + "=?",
                new String[] { String.valueOf(1) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

       // Log.w("myapp", "settings get_Tracks 2");

        Integer[] my_tracks= new Integer[]{
                cursor.getInt(0),
                cursor.getInt(1),
                cursor.getInt(2)
        };
       // Log.w("myapp", "settings get_Tracks 3");
        return my_tracks;
    }

    public void update_neck(Integer value) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(neck, value);
        db.update(TABLE_settings, values, settings_id + " = ?",
                new String[]{String.valueOf(1)});
    }

    public void update_wrist(Integer value) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(wrist, value);
        db.update(TABLE_settings, values, settings_id + " = ?",
                new String[]{String.valueOf(1)});
    }


    public void update_screen(Integer value) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(screen_on, value);
        db.update(TABLE_settings, values, settings_id + " = ?",
                new String[]{String.valueOf(1)});
    }


    public void update_training(Integer value) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(train_me, value);
        db.update(TABLE_settings, values, settings_id + " = ?",
                new String[]{String.valueOf(1)});
    }


    public void updateNotificationSettings(Integer start_hrs, Integer
            start_mins, Integer end_hrs, Integer end_mins, Boolean sun,
            Boolean mon, Boolean tues, Boolean  wed, Boolean thurs,
            Boolean fri, Boolean sat, Integer period) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        Integer su = 0;
        Integer mo = 0;
        Integer tu = 0;
        Integer we = 0;
        Integer th = 0;
        Integer fr = 0;
        Integer sa = 0;
        if(sun) su = 1;
        if (mon) mo = 1;
        if(tues) tu = 1;
        if (wed)  we = 1;
        if(thurs) th = 1;
        if (fri) fr = 1;
        if(sat) sa = 1;

        values.put(start_Time_hrs, start_hrs);
        values.put(end_time_hrs, end_hrs);
        values.put(start_Time_mins, start_mins);
        values.put(end_time_mins, end_mins);
        values.put(sunday, su);
        values.put(monday, mo);
        values.put(tuesday, tu);
        values.put(wednesday, we);
        values.put(thursday, th);
        values.put(friday, fr);
        values.put(durations, period);
        values.put(saturday, sa);
        //Log.w("myapp", "all items added to values");

        // updating row
        db.update(TABLE_settings, values, settings_id + " = ?",
                new String[]{String.valueOf(1)});
    }

    public Integer getDuration()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        //Log.w("myapp", "settings dbhandler 9");
        Cursor cursor = db.query(TABLE_settings, new String[] {
                        durations},
                settings_id + "=?",
                new String[] { String.valueOf(1) }, null, null, null, null);
        if (cursor != null)
            Log.w("myapp", "settings dbhandler 10");
        cursor.moveToFirst();

        return cursor.getInt(0);
    }


    public Integer[]  getNotificationSettings(int id) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Log.w("myapp", "dbhandler 9");
            Cursor cursor = db.query(TABLE_settings, new String[]{
                            start_Time_hrs, start_Time_mins, end_time_hrs,
                            end_time_mins,
                            sunday, monday, tuesday, wednesday, thursday, friday,
                            saturday,
                            durations},
                    settings_id + "=?",
                    new String[]{String.valueOf(1)}, null, null, null, null);
            if (cursor != null)
                Log.w("myapp", "dbhandler 10");
            cursor.moveToFirst();


            Integer[] my_exercise = new Integer[]{cursor.getInt(0),
                    //exercise_id
                    cursor.getInt(1),//exercise_NAME
                    cursor.getInt(2),
                    cursor.getInt(3),
                    cursor.getInt(4),//sun
                    cursor.getInt(5),//mon
                    cursor.getInt(6),
                    cursor.getInt(7),
                    cursor.getInt(8),
                    cursor.getInt(9),//fri

                    cursor.getInt(11),
                    cursor.getInt(10),//sat

            };
            Log.w("myapp", "dbhandler 12");
            return my_exercise;
        }
        catch (Exception e)
        {
            Log.w("myapp","eror");
            return new Integer[]{0,1,2,3,4,5,6,7,8,9,10};
        }
    }

    public Integer[] getTrainingAreas(int id)
    {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Log.w("myapp", "dbhandler 9");
            Cursor cursor = db.query(TABLE_settings, new String[]{
                            neck,wrist},
                    settings_id + "=?",
                    new String[]{String.valueOf(1)}, null, null, null, null);
            if (cursor != null)
                Log.w("myapp", "dbsettings dbhandler 10");
            cursor.moveToFirst();


            Integer[] my_exercise = new Integer[]{cursor.getInt(0),//neck
                    cursor.getInt(1),//wrist
            };
            Log.w("myapp", "dbsettings dbhandler 12");
            return my_exercise;
        }
        catch (Exception e)
        {
            Log.w("myapp","eror");
            return new Integer[]{0,1};
        }
    }


    public Integer[] getHomeSettings(int id)
    {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Log.w("myapp", "dbhandler 9");
            Cursor cursor = db.query(TABLE_settings, new String[]{
                            screen_on,train_me},
                    settings_id + "=?",
                    new String[]{String.valueOf(1)}, null, null, null, null);
            if (cursor != null)
                Log.w("myapp", "dbhandler 10");
            cursor.moveToFirst();


            Integer[] my_exercise = new Integer[]{cursor.getInt(0),//neck
                    cursor.getInt(1),//eyes
            };
            Log.w("myapp", "dbhandler 12");
            return my_exercise;
        }
        catch (Exception e)
        {
            Log.w("myapp","eror");
            return new Integer[]{1,1};
        }
    }



}
