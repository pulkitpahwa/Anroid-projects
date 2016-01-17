package in.pulkitpahwa.fooodies;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import android.app.ActionBar.LayoutParams;
import android.view.View.OnClickListener;
import android.widget.Toast;


public class CartCheck extends ActionBarActivity {
    private DatabaseHandler db = new DatabaseHandler(this);
    private HandleBillJSON obj;
    private String items;
    private String qty;
    private String restaurant;
    private CreateAppID appdb = new CreateAppID(this);
    private String app_id;
    private Intent i;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_check);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        restaurant = extras.getString("restaurant");
        app_id = appdb.getAppID();
        i =new Intent(this, UserProfile.class);
        //create table. add rows
/*this commented out code is the table 1 that we can check on later.

        TableLayout tableLayout = new TableLayout(getApplicationContext());
        TableRow tableRow;
        TextView textView;

        for (int i = 0; i < 4; i++) {
            tableRow = new TableRow(getApplicationContext());
            for (int j = 0; j < 3; j++) {
                textView = new TextView(getApplicationContext());
                textView.setText("test");
                textView.setPadding(20, 20, 20, 20);
                tableRow.addView(textView);
            }
            tableLayout.addView(tableRow);
        }
        setContentView(tableLayout);
  */
        //fetching data from the database
        List<MyCart> mycartlist = db.getAllBaskets();
        items = mycartlist.get(0).getItemID();
        qty = mycartlist.get(0).getQty().toString();
        try {
            for (int i = 1; i < mycartlist.size(); i++) {
                items += "-" + mycartlist.get(i).getItemID();
                qty += "-" + mycartlist.get(i).getQty().toString();
            }
        } catch (Exception e) {
        }


       //table creation starts from here
        TableLayout tl1 = (TableLayout) findViewById(R.id.myfirsttable);
        TableRow tr1 = new TableRow(this);
        tr1.setId(100-1);
        tr1.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));

        // Create a TextView to house the name of the province
        TextView names_column1 = new TextView(this);
        names_column1.setPadding(20, 20, 20, 20);

        Log.w("myapp", "cart 7");
        names_column1.setId(200 - 1);
        names_column1.setText("Item ");
        names_column1.setTextColor(Color.BLACK);
        names_column1.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        tr1.addView(names_column1);


        // Create a TextView to house the value of the after-tax income
        TextView qty_column1= new TextView(this);
        qty_column1.setPadding(20, 20, 20, 20);
        qty_column1.setId(300 - 1);
        qty_column1.setText("QTY");
        qty_column1.setTextColor(Color.BLACK);
        qty_column1.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        tr1.addView(qty_column1);
        Log.w("myapp", "cart 5");


        TextView price_column1 = new TextView(this);
        price_column1.setPadding(20, 20, 20, 20);
        price_column1.setId(400 - 1);
        price_column1.setText("PRICE");
        price_column1.setTextColor(Color.BLACK);
        price_column1.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        tr1.addView(price_column1);

        Log.w("myapp", "cart 4");

        TextView taxable_column1 = new TextView(this);
        taxable_column1.setPadding(20, 20, 20, 20);
        taxable_column1.setId(500 - 1);
        taxable_column1.setText("TAXABLE AMT");
        taxable_column1.setTextColor(Color.BLACK);
        taxable_column1.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        tr1.addView(taxable_column1);

        Log.w("myapp","cart 3");

        TextView tax_column1 = new TextView(this);
        tax_column1.setPadding(20, 20, 20, 20);
        tax_column1.setId(600 - 1);
        tax_column1.setText("TAX");
        tax_column1.setTextColor(Color.BLACK);
        //Log.w("myapp",mycartlist.get(current).getTAX().toString());
        tax_column1.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        tr1.addView(tax_column1);



        TextView total_column1= new TextView(this);
        total_column1.setPadding(20, 20, 20, 20);
        total_column1.setId(700 - 1);
        total_column1.setText("ITEM TOTAL");
        total_column1.setTextColor(Color.BLACK);
        total_column1.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        tr1.addView(total_column1);


        Log.w("myapp", "cart 1");

        Log.w("myapp","cart 0");

        // Add the TableRow to the TableLayout
        tl1.addView(tr1, new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));


        // Go through each item in the array
        for (int current = 0; current < mycartlist.size(); current++)
        {
            // Create a TableRow and give it an ID
            TableRow tr = new TableRow(this);
            tr.setId(100+current);
            tr.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            // Create a TextView to house the name of the province
            TextView names_column = new TextView(this);
            names_column.setPadding(20, 20, 20, 20);

            Log.w("myapp","cart 7");
            names_column.setId(200+current);
            names_column.setText(mycartlist.get(current).getItemName());
            names_column.setTextColor(Color.BLACK);
            names_column.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            tr.addView(names_column);

            Log.w("myapp","qty = " + mycartlist.get(current).getQty().toString());

            // Create a TextView to house the value of the after-tax income
            TextView qty_column= new TextView(this);
            qty_column.setPadding(20, 20, 20, 20);
            qty_column.setId(300 + current);
            qty_column.setText(mycartlist.get(current).getQty().toString());
            qty_column.setTextColor(Color.BLACK);
            qty_column.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            tr.addView(qty_column);
            Log.w("myapp", "cart 5");


            TextView price_column = new TextView(this);
            price_column.setPadding(20, 20, 20, 20);
            price_column.setId(400 + current);
            price_column.setText(mycartlist.get(current).getPrice().toString());
            price_column.setTextColor(Color.BLACK);
            price_column.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            tr.addView(price_column);

            Log.w("myapp", "cart 4");

            TextView taxable_column = new TextView(this);
            taxable_column.setPadding(20, 20, 20, 20);
            taxable_column.setId(500 + current);
            taxable_column.setText(mycartlist.get(current).getTaxableAmt().toString());
            taxable_column.setTextColor(Color.BLACK);
            taxable_column.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            tr.addView(taxable_column);

            Log.w("myapp", "cart 3");

            TextView tax_column = new TextView(this);
            tax_column.setPadding(20, 20, 20, 20);
            tax_column.setId(600 + current);
            tax_column.setText(mycartlist.get(current).getTAX().toString());
            tax_column.setTextColor(Color.BLACK);
            Log.w("myapp", mycartlist.get(current).getTAX().toString());
            tax_column.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            tr.addView(tax_column);

            Log.w("myapp","cart 2");

            Log.w("myapp","total = "+mycartlist.get(current).getTotal().toString());
            TextView total_column= new TextView(this);
            total_column.setPadding(20, 20, 20, 20);
            total_column.setId(700+current);
            total_column.setText(mycartlist.get(current).getTotal().toString());
            total_column.setTextColor(Color.BLACK);
            total_column.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            tr.addView(total_column);


            Log.w("myapp", "cart 1");

            Log.w("myapp","cart 0");

            // Add the TableRow to the TableLayout
            tl1.addView(tr, new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));
        }
       Button btnTag = new Button(this);
       btnTag.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
       btnTag.setText("Request For your Tiffin");
       btnTag.setId(000000 + 0);
       tl1.addView(btnTag);
       LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        final Button btn = new Button(this);
        // Give button an ID
        btn.setId(00000 +0);
        btn.setText("Add To Cart");
        // set the layoutParams on the button
        btn.setLayoutParams(params);

        // Set click listener for buttonhttp://fooodies.in/menu/mcourse/
        btn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                Bundle extras = new Bundle();
                extras.putString("restaurant", restaurant);
                extras.putString("items",items);
                extras.putString("qty",qty);
                extras.putString("app_id",app_id);
                i.putExtras(extras);
                startActivity(i);
                /*
                String next = "http://fooodies.in/bill/" + restaurant + "/" + "hd/" + items + "/" + qty + "/" + app_id + "/";
                obj = new HandleBillJSON(next);
                obj.fetchJSON();
                Log.w("myapp", "main 1");
                while (obj.parsingComplete) ;
                item_array = obj.getItems();
                Log.w("myapp" , "main 2");
                price_array = obj.get_price();
                tax_array = obj.get_tax();
                Log.w("myapp" , "main 3");
                item_total_array = obj.get_item_total();
                Log.w("myapp" , "main 4");
                total = Double.parseDouble(obj.get_total());
                Log.w("myapp" , "main 5");
/*                bill_no = obj.get_bill_no();
                Log.w("myapp", "your order has been placed");
*/
            }
        });

        //Add button to LinearLayout
        tl1.addView(btn);
        //Add button to LinearLayout defined in XML

    }
         //use the mycartlist to populate tables.
        //at the bottom of the screen, there will be an option to submit(place order). If the button is pressed, submit() method gets called.





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cart_check, menu);
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
