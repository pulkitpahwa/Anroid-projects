package in.pulkitpahwa.fooodies;

/**
 * Created by pulkit on 24/4/15.
 */


import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;


public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "basketManager";

    // Contacts table name
    private static final String TABLE_BASKET = "basketdb";

    // Contacts Table Columns names
    private static final String BASKET_ID = "id";
    private static final String ITEM_NAME = "name";
    private static final String ITEM_ID = "itemid";
    private static final String TAXABLE_AMT = "taxable_amt";
    private static final String TAX = "tax";
    private static final String TOTAL = "total";
    private static final String QTY = "qty";
    private static final String PRICE = "price";



    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.w("myapp", "dbhandler on create");
        try {
            SQLiteDatabase checkDB = null;
            db = SQLiteDatabase.openDatabase(TABLE_BASKET, null,
                    SQLiteDatabase.OPEN_READWRITE);
            Log.w("myapp","db exist");
        }
        catch (SQLiteException e) {
               String CREATE_BASKET_TABLE = "CREATE TABLE " + TABLE_BASKET + "("
                    + BASKET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + ITEM_NAME + " TEXT,"
                    + ITEM_ID + " TEXT," + QTY + " INTEGER," + TAXABLE_AMT + " Double," +
                    TAX + " Double," + TOTAL + " Double, " + PRICE + " Double" +
                    ");";
            db.execSQL(CREATE_BASKET_TABLE);
            Log.w("myapp","db created");
        }
            Log.w("myapp", "dbhandler 1");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BASKET);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new contact
    void addBasket(String cartname,String cartid, int cartqty, Double cartprice,Double carttaxable, Double carttax, Double carttotal) {
        Log.w("myapp", "dbhandler 2");
        SQLiteDatabase db = this.getWritableDatabase();
        Log.w("myapp", "dbhandler 3");
   //     Cursor mCursor = db.rawQuery("Select * from " +  TABLE_BASKET, null );
    //    mCursor.moveToLast();
        Log.w("myapp", "dbhandler 4");
        ContentValues values = new ContentValues();
        //
        //Log.w("myapp", "dbhandler 5");
  //      Log.w("myapp",Integer.toString(mCursor.getColumnIndex("id") ));
//        values.put(BASKET_ID,mCursor.getColumnIndex("id") + 1);
        //Log.w("myapp","basket id fetched and added");
        values.put(ITEM_NAME, cartname);
        values.put(ITEM_ID, cartid);
        //Log.w("myapp", "dbhandler 6");
        values.put(TAXABLE_AMT, carttaxable);
        values.put(TAX, carttax);
        //Log.w("myapp", "dbhandler 7");
        values.put(TOTAL, carttotal);
        values.put(QTY, cartqty);
        values.put(PRICE, cartprice);

        // Inserting Row
        db.insert(TABLE_BASKET, null, values);
        Log.w("myapp","all items added to values");
    //  Log.w("myapp",Integer.toString(mCursor.getColumnIndex("id") ));
        List<MyCart> m = getAllBaskets();
        Log.w("myapp",m.get(0).getItemName());
        db.close(); // Closing database connection
        Log.w("myapp", "dbhandler 8");

        Log.w("myapp", getAllBaskets().toString());

    }

    // Getting single contact
    MyCart getBasket(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Log.w("myapp", "dbhandler 9");
        Cursor cursor = db.query(TABLE_BASKET, new String[] { BASKET_ID,
                        ITEM_NAME, ITEM_ID, TAXABLE_AMT, TAX, TOTAL, QTY,PRICE }, BASKET_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            Log.w("myapp", "dbhandler 10");
            cursor.moveToFirst();

//        MyCart(Integer basket_id, String name, String iid, Double item_price, Integer qty, Double taxable,
//              Double tax, Double total)
        Log.w("myapp", "dbhandler 11");
        MyCart mycart = new MyCart(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),cursor.getString(2),
                Double.parseDouble(cursor.getString(7)),
                Integer.parseInt(cursor.getString(6)),
                Double.parseDouble(cursor.getString(3)),
                Double.parseDouble(cursor.getString(4)),
                Double.parseDouble(cursor.getString(5)));
        Log.w("myapp", "dbhandler 12");
        return mycart;
    }


    public void updateBasketByItemId(int item_id, int new_qty, Double cartprice,
                                Double carttaxable, Double carttax, Double carttotal)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Log.w("myapp", "dbhandler 9");
        Cursor cursor = db.query(TABLE_BASKET, new String[] { BASKET_ID,
                        ITEM_NAME, ITEM_ID, TAXABLE_AMT, TAX, TOTAL, QTY,PRICE }, ITEM_ID + "=?",
                new String[] { String.valueOf(item_id) }, null, null, null, null);
        if (cursor != null)
            Log.w("myapp", "dbhandler 10");
        cursor.moveToFirst();
//BASKET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + ITEM_NAME + " TEXT,"
//        + ITEM_ID + " TEXT," + QTY + " INTEGER," + TAXABLE_AMT + " Double," +
//                TAX + " Double," + TOTAL + " Double, " + PRICE + " Double"
        int basket = cursor.getInt(0);
        int qty = cursor.getInt(7)+ new_qty;
        Double price = cursor.getDouble(4)+cartprice;
        Double total = cursor.getDouble(6) + carttotal;

        Log.w("myapp", "dbhandler 11");
        SQLiteDatabase dba = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(QTY, qty);
        values.put(TAXABLE_AMT, price);
        values.put(TOTAL, total);

        Log.w("myapp", "all items added to values");

        // updating row
        dba.update(TABLE_BASKET, values, BASKET_ID + " = ?",
                new String[] { String.valueOf(basket) });

        Log.w("myapp", "dbhandler 12 exit");

    }


    public void deleteBasket(int basket_id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("delete from "+TABLE_BASKET+" where id = "+basket_id+ ";");
        Log.w("myapp", "basket id = " + Integer.toString(basket_id) + " deleted");
    }
    // Getting All Basket
    public List<MyCart> getAllBaskets() {
        List<MyCart> basketList = new ArrayList<MyCart>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_BASKET;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Log.w("myapp", "dbhandler 21");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            Log.w("myapp", "dbhandler 21a");
            do {
                Log.w("myapp", "dbhandler 21b");
                MyCart mycart = new MyCart();
                Log.w("myapp", "dbhandler 21c");
                mycart.setBASKET_ID(Integer.parseInt(cursor.getString(0)));
                mycart.setITEM_ID(cursor.getString(2));
                mycart.setITEM_NAME(cursor.getString(1));
                Log.w("myapp", "dbhandler 21d");
                mycart.setTaxable(Double.parseDouble(cursor.getString(4)));
                mycart.setTax(Double.parseDouble(cursor.getString(5)));
                Log.w("myapp", "dbhandler 21e");
                mycart.setTotal(cursor.getDouble(6));
                mycart.setQty(cursor.getInt(3));
                Log.w("myapp", "dbhandler 21f");
                mycart.setPrice(Double.parseDouble(cursor.getString(7)));


                Log.w("myapp", "dbhandler 22");
                basketList.add(mycart);
            } while (cursor.moveToNext());
            Log.w("myapp", "dbhandler 23");
        }

        // return contact list
        return basketList;
    }

    // Updating single contact
    public int updateBasket(MyCart cart) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(BASKET_ID, cart.getBasketID());

        Log.w("myapp","basket id fetched and added");

        values.put(ITEM_NAME, cart.getItemName());
        values.put(ITEM_ID, cart.getItemID());
        values.put(TAXABLE_AMT, cart.getTaxableAmt());
        values.put(TAX, cart.getTAX());
        values.put(TOTAL, cart.getTotal());
        values.put(QTY, cart.getQty());
        values.put(PRICE, cart.getPrice());
        Log.w("myapp", "all items added to values");

        // updating row
        return db.update(TABLE_BASKET, values, BASKET_ID + " = ?",
                new String[] { String.valueOf(cart.getBasketID()) });
    }

    // Deleting single contact
    public void deleteCart(MyCart cart) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_BASKET
                , BASKET_ID + " = ?",
                new String[] { String.valueOf(cart.getBasketID()) });
        db.close();
    }


    public void deleteAll()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("drop table "+ TABLE_BASKET);
        String CREATE_BASKET_TABLE = "CREATE TABLE " + TABLE_BASKET + "("
                + BASKET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + ITEM_NAME + " TEXT,"
                + ITEM_ID + " TEXT," + QTY + " INTEGER," + TAXABLE_AMT + " Double," +
                TAX + " Double," + TOTAL + " Double, " + PRICE + " Double" +
                ");";
        db.execSQL(CREATE_BASKET_TABLE);
    }
    // Getting contacts Count
    public int getBasketCount() {
        String countQuery = "SELECT  * FROM " + TABLE_BASKET;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }
}