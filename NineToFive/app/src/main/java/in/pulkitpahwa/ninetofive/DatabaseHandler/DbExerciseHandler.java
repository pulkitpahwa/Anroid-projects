package in.pulkitpahwa.ninetofive.DatabaseHandler;

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
public class DbExerciseHandler extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "9to5Exercisedb";
    private static final String TABLE_EXERCISES = "exerciseManager";

    private static final String exercise_Id = "exercise_id";
    private static final String exercise_NAME = "name";
    private static final String Category = "category";
    private static final String exercise_text = "exercise_text";
    private static final String duration = "duration";
    private static final String done = "done";
    private static final String image = "image_name";


    public DbExerciseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
     //   Log.w("myapp","inside dbexercisehadnler constructor");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.w("myapp", "dbexercise handler on create ");
        try {

            db = SQLiteDatabase.openDatabase(TABLE_EXERCISES, null,
                    SQLiteDatabase.OPEN_READWRITE);
            Log.w("myapp","db exist");
        }
        catch (SQLiteException e) {
            try {
                Log.w("myapp", "inside exercisedb  dbhandler");

                String CREATE_EXERCISE_TABLE = "CREATE TABLE " + TABLE_EXERCISES +
                        " ( "
                        + exercise_Id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        exercise_NAME + " TEXT, " +
                        Category + " TEXT, " +
                        exercise_text + " TEXT, " +
                        image + " TEXT, "+
                        duration + " INTEGER, " +
                        done + " INTEGER);";
                db.execSQL(CREATE_EXERCISE_TABLE);
                Log.w("myapp","exercisedb query executed");

                populate(db);
                Log.w("myapp", "exercise db created");
            } catch (Exception ef) {
                Log.w("myapp","exercisedb exception caught");
            }
        }
        Log.w("myapp", "exercisedb dbhandler 1");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXERCISES);
        // Create tables again
        onCreate(db);
    }


    public void addExercise(SQLiteDatabase db,String name,String category,
                            String
            image_name, String  e_text, Integer req_time, Integer checked) {
        Log.w("myapp", "exercisedb dbhandler 2");

        Log.w("myapp", "exercisedb dbhandler 3");

        ContentValues values = new ContentValues();
        Integer abc = 0;
        values.put(Category, category);
        values.put(image, image_name);
        values.put(exercise_NAME, name);
        values.put(exercise_text, e_text);
        values.put(duration, req_time);
        Log.w("myapp", "exercisedb dbhandler 4");
        values.put(done, checked);

        db.insert(TABLE_EXERCISES, null, values);
        Log.w("myapp", "all items added to values");

    }



    public String[]  getExercise(int id, DbSettings settings_db) {
        SQLiteDatabase db = this.getReadableDatabase();

     //   Log.w("myapp", "exercisedb dbhandler 9");
        Cursor cursor = db.query(TABLE_EXERCISES, new String[] { exercise_Id,
                        exercise_NAME, exercise_text, image, duration, done,
                        Category},
                exercise_Id + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
    //    Log.w("myapp", Integer.toString(cursor.getCount()));

        if (cursor != null)
            Log.w("myapp", "exercisedb dbhandler 10");
        cursor.moveToFirst();
     //   Log.w("myapp","ckdjk");
        String[] my_exercise= new String[]{
                cursor.getString(0),
                //exercise_id
                cursor.getString(1), //exercise_NAME
                cursor.getString(2), //exercise_text
                cursor.getString(3), //image
                cursor.getString(4), //duration
                cursor.getString(5),//done
                cursor.getString(6) //category
        };

        settings_db.update_exercise_count(id);

        // Log.w("myapp", "exercise dbhandler 12");
        return my_exercise;
    }
/*
    public Integer[] get_next_Exercise(int current, int count, DbSettings
            settings_db)
    {
        //Integer tracks[] = settings_db.get_tracks();
        Integer tracks[] = settings_db.getTrainingAreas(1);
        Log.w("myapp","tracks [0] = " + Integer.toString(tracks[0]) + " tracks[1] = " + tracks[1]);
        Log.w("myapp","get new exercise");
        if(tracks[0] == 0 && current == 9)
        {
            return new Integer[]{1,9};
        }
        else if (current == 14){

            Log.w("myapp","inside current");
            if (tracks[1] == 1)
            {
                return new Integer[]{1,13};
            }
            else{
                return new Integer[]{10,13};
            }
        }
        String category = this.getExercise(current)[6];
        Log.w("myapp","the category = "+category);
        Log.w("myapp",Integer.toString(count));
        if (count == 4)
        {
            if(category.equals("neck"))
            {
                if (tracks[1] == 1)
                {
                    category = "wrist";
                }
                else
                {
                    category  = "neck";
                }
            }


            else{
                if(tracks[0] == 1)
                {
                    category = "neck";
                }
                else{
                    category = "wrist";
                }
            }

/*            if (tracks[0] == 1 )
            {
                Log.w("myapp", "category = "+category);

                if (category.equals("neck")) {
                    if (tracks[1]==1)
                    category = "wrist";
                    Log.w("myapp","change1");
                }
                else{
                    if(tracks[1]==1)
                    category = "neck";
                    Log.w("myapp","change2");
                }
                Log.w("myapp", "category = "+category);


                //else if (category!= "wrist") category = "wrist";
            }
            else if (tracks[1] == 1)
            {
                Log.w("myapp", "category 1= "+category);

                if (category.equals("wrist"))   {
                    if(tracks[0] == 1)
                    category = "neck";}
                else{
                    category = "wrist";
                }
                //category = "wrist";
                //Log.w("myapp", "category 1= "+category);

                //else if (category!= "eyes")  category = "eyes";

            }
            else if (tracks[2] == 1)
            {
                if (category != "neck") category = "neck";
                else if (category!= "wrist") category = "wrist";
            }

            if(category.equals("wrist"))
            {
                Log.w("myapp", "category = "+category);

                category = "neck";
                Log.w("myapp", "category = "+category);

            }
            else if(category.equals("neck"))
            {
                Log.w("myapp", "category = "+category);

                category = "wrist";
                Log.w("myapp", "category = "+category);

            }

            SQLiteDatabase db = this.getReadableDatabase();

            Cursor cursor = db.rawQuery("Select exercise_id from " +
                    TABLE_EXERCISES + " where category = '" + category + "';", null) ;
            Log.w("myapp","query executed");
            cursor.moveToFirst();
            Integer id = cursor.getInt(0);

            if (cursor != null)
                Log.w("myapp", "exercisedb dbhandler 10");
            settings_db.update_exercise_count(current, id);

            return new Integer[]{id,current};

            //return new Integer[]{1,current};
        }
        else{
            SQLiteDatabase db = this.getReadableDatabase();
            String query = "Select exercise_id from " +
                    TABLE_EXERCISES + "" +
                    " where category='" + category + "' and exercise_id > " +
                    Integer.toString(current) + " ;";

            Log.w("myapp",query);

            Cursor cursor = db.rawQuery(query, null) ;

            cursor.moveToFirst();

            Integer id = cursor.getInt(0);
            settings_db.update_exercise_count(current, id);

            return new Integer[]{id,current};
        }
    }*/

    public void updateExercise(Integer exercise_id, Boolean exercise_status) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        Integer abc = 0;
        if(exercise_status)
        {
            abc = 1;
        }

        values.put(done, abc);
        Log.w("myapp", "exercisedb all items added to values");

        // updating row
        db.update(TABLE_EXERCISES, values, exercise_Id + " = ?",
                new String[]{String.valueOf(exercise_id)});
    }

    public void populate(SQLiteDatabase db)
    {
       // Log.w("myapp", "populating");



        addExercise(db, "Head lift neck side exercise", "neck",
                "head_lift_neck_side_bend", "Lie down on your side and move your neck vertically upwards slowly.",20,0);



        addExercise(db, "Isometric Neck Extension exercise", "neck",
                "isometric_neck_extension", "Hold your neck in your hand and move your neck up and down. Resist the movemement with hands",20,0);




        addExercise(db, "Isometric Neck Side Bend exercise", "neck",
                "isometric_neck_side_bend", "Push your neck to the right and apply resistance with right hand. Do the same with left hand.",20,0);


        addExercise(db, "Neck Rotation Stretch exercise", "neck",
                "neck_rotation_stretch", "Rotate neck to your left and right to the maximum right and maximum left.",20,0);


        addExercise(db, "scalene stretch exercise", "neck",
                "scalene_stretch", "Push your neck slowly to the left to touch left shoulders. Do the same towards right side ",20,0);




        addExercise(db, "Active Range Of Motion", "wrist",
                "active_range_of_motion_a", "join all the fingers in the " +
                        "shape as prescribed. And rotate the wrist left to right smoothly.", 20, 0);//1

        addExercise(db,"Tendon glides", "wrist", "tendon_glides", "open " +
                "and close the fingers alternatively as shown.",20,0);//2

        addExercise(db,"Wrist Extension","Wrist","wrist_extension_exercise",
                "hold a can in your own hand and twist the rotate up-down, left-right alernatively.",30,0);

        addExercise(db, "Active Range Of Motion", "wrist",
                "active_range_of_motion_b", "open the palm as prescribed. And" +
                        " rotate the wrist in circular motions smoothly.",20,0);
//5
        addExercise(db, "Active Range Of Motion", "wrist",
                "active_range__of_motion_c", "Make a fist as prescribed. And " +
                        "rotate the wrist in circular motions, up-down, and right-left smoothly.",35,0);

        addExercise(db, "wrist extension exercise", "wrist",
                "wrist_extension_exercise", "Hold a can in your hand  and rotate the wrist in circular motions, up-down, and right-left smoothly.",35,0);

        addExercise(db, "wrist flexion exercise", "wrist",
                "wrist_flexion_exercise", "Hold a can in your hand  and rotate the wrist up-down, and right-left smoothly.",35,0);

        addExercise(db, "wrist stretch exercise", "wrist",
                "wrist_stretch", "Hold your right hands' fingers in left hand and push them backwards. Repeat same for right hand fingers",20,0);

//9

        addExercise(db, "wrist stretch exercise", "wrist",
                "wrist_stretch", "Hold your right hands' fingers in left hand and push them backwards. Repeat same for right hand fingers",20,0);

//10








//        Log.w("myapp", "populated");


    }


}
