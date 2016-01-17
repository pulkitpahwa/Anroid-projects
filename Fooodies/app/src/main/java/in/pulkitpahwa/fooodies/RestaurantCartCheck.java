package in.pulkitpahwa.fooodies;

import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class RestaurantCartCheck extends ActionBarActivity {
    private List<String> item_array  = new ArrayList<String>();
    private List<String> price_array = new ArrayList<String>();
    private List<String> tax_array = new ArrayList<String>();
    private List<String> item_total_array = new ArrayList<String>();
    private Double total;
    private String bill_no;
    private RestaurantDatabaseHandler db = new RestaurantDatabaseHandler(this);
    private HandleRestaurantBillJSON obj;
    private String items;
    private String qty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_cart_check);
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
        List<MyRestaurantCart> mycartlist = db.getAllBaskets();
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
        TableLayout tl = (TableLayout) findViewById(R.id.restaurant_myfirsttable);

        // Go through each item in the array
        for (int current = 0; current < mycartlist.size(); current++)
        {
            // Create a TableRow and give it an ID
            TableRow tr = new TableRow(this);
            tr.setId(1000+current);
            tr.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            // Create a TextView to house the name of the province
            TextView names_column = new TextView(this);
            names_column.setPadding(20, 20, 20, 20);

            Log.w("myapp","cart 7");
            names_column.setId(2000+current);
            names_column.setText(mycartlist.get(current).getItemName());
            names_column.setTextColor(Color.BLACK);
            names_column.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            tr.addView(names_column);

            Log.w("myapp","qty = " + mycartlist.get(current).getQty().toString());

            // Create a TextView to house the value of the after-tax income
            TextView qty_column= new TextView(this);
            qty_column.setPadding(20, 20, 20, 20);
            qty_column.setId(3000+current);
            qty_column.setText(mycartlist.get(current).getQty().toString());
            qty_column.setTextColor(Color.BLACK);
            qty_column.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            tr.addView(qty_column);
            Log.w("myapp","cart 5");


            TextView price_column = new TextView(this);
            price_column.setPadding(20, 20, 20, 20);
            price_column.setId(4000+current);
            price_column.setText(mycartlist.get(current).getPrice().toString());
            price_column.setTextColor(Color.BLACK);
            price_column.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            tr.addView(price_column);

            Log.w("myapp","cart 4");

            TextView taxable_column = new TextView(this);
            taxable_column.setPadding(20, 20, 20, 20);
            taxable_column.setId(5000+current);
            taxable_column.setText(mycartlist.get(current).getTaxableAmt().toString());
            taxable_column.setTextColor(Color.BLACK);
            taxable_column.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            tr.addView(taxable_column);

            Log.w("myapp","cart 3");

            TextView tax_column = new TextView(this);
            tax_column.setPadding(20, 20, 20, 20);
            tax_column.setId(6000 + current);
            tax_column.setText(mycartlist.get(current).getTAX().toString());
            tax_column.setTextColor(Color.BLACK);
            Log.w("myapp",mycartlist.get(current).getTAX().toString());
            tax_column.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            tr.addView(tax_column);

            Log.w("myapp","cart 2");

            Log.w("myapp","total = "+mycartlist.get(current).getTotal().toString());
            TextView total_column= new TextView(this);
            total_column.setPadding(20, 20, 20, 20);
            total_column.setId(7000+current);
            total_column.setText(mycartlist.get(current).getTotal().toString());
            total_column.setTextColor(Color.BLACK);
            total_column.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            tr.addView(total_column);


            Log.w("myapp", "cart 1");

            Log.w("myapp","cart 0");

            // Add the TableRow to the TableLayout
            tl.addView(tr, new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.FILL_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));
        }
        Button btnTag = new Button(this);
        btnTag.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
        btnTag.setText("Check out" );
        btnTag.setId(1234);
         tl.addView(btnTag);
         //use the mycartlist to populate tables.
        //at the bottom of the screen, there will be an option to submit(place order). If the button is pressed, submit() method gets called.
    }

    public void onaClick(View view)
    {
/*
        String next = "http://fooodies.in/bill/" + items + "/" + qty + "/";
        obj = new HandleBillJSON(next);
        obj.fetchJSON();
        while(obj.parsingComplete);

        item_array  = obj.getItems();

        price_array = obj.get_price();
        tax_array = obj.get_tax();
        item_total_array = obj.get_item_total();
        total = Double.parseDouble(obj.get_total());
        bill_no = obj.get_bill_no();
        //write this data to the database with table named bills or myorders
*/
        Log.w("myapp","submit button pressed");
    }


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

