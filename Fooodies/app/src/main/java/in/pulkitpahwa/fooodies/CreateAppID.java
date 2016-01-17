package in.pulkitpahwa.fooodies;

/**
 * Created by pulkit on 24/4/15.
 */


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class CreateAppID extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "app_manager";
    private static final String TABLE_APP_ID = "app_id";
    private static final String user_location = "user_location";
    private static final String App_id= "id";
    private static final String longitude= "longitude";
    private static final String latitude= "latitude";
    private static final String loc_id= "loc_id";
    private static final String number = "count";


    public CreateAppID(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.w("myapp", "dbhandler on create");
        try {
            db = SQLiteDatabase.openDatabase(TABLE_APP_ID, null,
                    SQLiteDatabase.OPEN_READWRITE);
            Log.w("myapp", "db table_app_id exist");
        }
        catch (SQLiteException e) {
            String CREATE_App_Id_TABLE = "CREATE TABLE " + TABLE_APP_ID + "("
                    + App_id + " TEXT PRIMARY KEY, " + number +" INTEGER);";
            db.execSQL(CREATE_App_Id_TABLE);
            Log.w("myapp","db created");
        }
        try{
            SQLiteDatabase checkDB;
            checkDB = SQLiteDatabase.openDatabase(user_location, null,
                    SQLiteDatabase.OPEN_READWRITE);
        }
        catch (SQLiteException e) {
            String CREATE_location_Table = "CREATE TABLE " + user_location+ "(" + longitude + " REAL," + latitude + " REAL,"+ loc_id + " INTEGER PRIMARY KEY );";
            db.execSQL(CREATE_location_Table);
            Log.w("myapp","db created");
        }

        Log.w("myapp", "dbhandler 1");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_APP_ID);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new contact
    void addAppID(String fetched_app_id) {
        String selectQuery = "SELECT  * FROM " + TABLE_APP_ID;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.getCount() == 0)
        {
            ContentValues values = new ContentValues();
            values.put(App_id, fetched_app_id );
            values.put(number, 1 );
            Log.w("myapp","adding app id = " + fetched_app_id);
            db.insert(TABLE_APP_ID, null, values);
            Log.w("myapp","row created");
            Cursor cursored = db.rawQuery(selectQuery, null);
            Log.w("myapp",Integer.toString(cursored.getCount()));
        }
        else{

        }
    }

    void addUserLocation(String longi, String lat) {
        String selectQuery = "SELECT  * FROM " + user_location;
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(user_location
                , loc_id + " = ?",
                new String[] { String.valueOf(1) }
                );

        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.getCount() == 0)
        {
            ContentValues values = new ContentValues();
            values.put(longitude, longi);
            values.put(latitude, lat);
            values.put(loc_id, "1");
            db.insert(user_location, null, values);
        }
        else{

        }
    }

    // Getting single contact
    public String getAppID() {
        SQLiteDatabase db = this.getReadableDatabase();
        Log.w("myapp", "dbhandler 19");
        Cursor cursor = db.query(TABLE_APP_ID, new String[]{App_id, number},
                number + "=?",
                new String[]{String.valueOf("1")}, null, null, null, null);
        if (cursor != null)
            Log.w("myapp", "dbhandler 20");
        cursor.moveToFirst();

        Log.w("myapp", "dbhandler 121");
        String[] columns = cursor.getColumnNames();
        return cursor.getString(0);
    }

    public List<String> getUserLocation() {
        SQLiteDatabase db = this.getReadableDatabase();
        Log.w("myapp", "dbhandler 29");
        Cursor cursor = db.query(user_location, new String[]{loc_id,
                        longitude, latitude}, loc_id + "=?",
                new String[]{String.valueOf("1")}, null, null, null, null);
        if (cursor != null)
            Log.w("myapp", "dbhandler 30");
        cursor.moveToFirst();

        Log.w("myapp", "dbhandler 31");
        Log.w("myapp","db length = "+ Integer.toString(cursor.getCount()));
        List<String> myloc = new ArrayList<String>();
        myloc.add(cursor.getString(0));
        myloc.add(cursor.getString(1));
        myloc.add(cursor.getString(2));
        Log.w("myapp", "dbhandler 32");
        return myloc;
    }


    // Getting contacts Count
    public int getIDsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_APP_ID;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        // return count
        return cursor.getCount();
    }

    public int getLocationCount(){
        String countCount = "Select * from " + user_location;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countCount, null);
        // return count
        return cursor.getCount();
    }


}
