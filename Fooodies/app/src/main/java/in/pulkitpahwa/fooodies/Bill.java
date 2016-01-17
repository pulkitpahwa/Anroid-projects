package in.pulkitpahwa.fooodies;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class Bill extends ActionBarActivity {
    private Integer basket_id = 0;
    private String item_name = new String();
    private String item_id = new String();
    private Double item_price ;
    private Integer item_qty = 1;
    private Double taxable ;
    private Double tax ;
    private Double total = 0.0;
    private DatabaseHandler db = new DatabaseHandler(this);
  //  private HandleBillJSON obj;
    private String restaurant;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String url = extras.getString("next_url");
        restaurant = extras.getString("restaurant");

        item_name = extras.getString("item");
        item_price = Double.parseDouble(extras.getString("price"));
        item_id = extras.getString("id");

        tax = Double.parseDouble(extras.getString("tax"));
        taxable = item_price;
        Button button4= (Button) findViewById(R.id.button4);
        button4.setVisibility(View.INVISIBLE);

        TextView  result = (TextView) findViewById(R.id.textView13);
        result.setVisibility(View.INVISIBLE);

        TextView  quantity = (TextView) findViewById(R.id.quantity);
        quantity.setText("Quantity 1");

        TextView itemname = (TextView) findViewById(R.id.item_name);
        itemname.setText(item_name);
        TextView unit_price= (TextView) findViewById(R.id.unit_price);
        unit_price.setText(item_price.toString());
        Log.w("myapp","bill 6");
        total = item_price;
        TextView total_price = (TextView) findViewById(R.id.total_price);
        total_price.setText(total.toString());
        Log.w("myapp","bill 7");
//        TextView unit_price = (TextView) findViewById(R.id.unit_price);
//        unit_price.setText(price);


    }

    public void increment(View view)
    {
        item_qty = item_qty + 1;
        taxable = taxable + item_price;

        Log.w("myapp","bill 8");
        TextView total_price = (TextView) findViewById(R.id.total_price);
        total_price.setText(taxable.toString());
        Log.w("myapp","bill 9");
        TextView quantity= (TextView) findViewById(R.id.quantity);
        quantity.setText("Quantity " + item_qty.toString());
        Log.w("myapp","bill a1");
        //increase the qty count
        //calculate the total price
    }


    public void decrement(View view)
    {
        if(item_qty == 0)
        {
            Log.w("myapp","bill a2 ");
        }
        else {
            item_qty = item_qty - 1;
            taxable = taxable - item_price;
            Log.w("myapp","bill a3");
            TextView total_price = (TextView) findViewById(R.id.total_price);
            total_price.setText(taxable.toString());
            TextView quantity = (TextView) findViewById(R.id.quantity);
            Log.w("myapp","bill a4");
            quantity.setText("Quantity " + item_qty.toString());
        }
        //decrease the qty count. min = 0
        //calculate and display the total price
    }

    public void basket(View view)
    {
        //String cartname,String cartid, int cartqty, Double cartprice,Double carttaxable, Double carttax, Double carttotal
        //db.addContact(new Contact("Ravi", "9100000000"));
        total = taxable * (1 + 0.01 * tax);
        Log.w("myapp", "total = " + total.toString());
        try{
            MyCart basket = db.getBasket(Integer.parseInt(item_id));
            int basket_id = basket.getBasketID();
            db.updateBasketByItemId(Integer.parseInt(item_id), item_qty, item_price, taxable, tax, total);
        }
        catch(Exception e) {
            db.addBasket(item_name, item_id, item_qty, item_price, taxable, tax, total);
            Log.w("myapp", "bill a6");
            TextView result = (TextView) findViewById(R.id.textView13);
            result.setVisibility(View.VISIBLE);

            Button button3 = (Button) findViewById(R.id.button3);
            button3.setVisibility(View.INVISIBLE);

            Button button4 = (Button) findViewById(R.id.button4);
            button4.setVisibility(View.VISIBLE);

            TextView total = (TextView) findViewById(R.id.total);
            total.setVisibility(View.INVISIBLE);

            TextView total_price = (TextView) findViewById(R.id.total_price);
            total_price.setVisibility(View.INVISIBLE);


            //Toast.makeText(getBaseContext(), item_name + " added to basket", Toast.LENGTH_LONG).show();
            // this method gets called when the user clicks the add to basket button
            // MyCart user = new MyCart(this, item_name, item_id.toString(), total.DoubleValue(), item_qty);


            Log.w("myapp", "bill a7");
            // Log.w("myapp", mycartlist.toString());
            //Log.w("myapp", Integer.toString(mycartlist.size()));

            Log.w("myapp", "bill a8");

        }
    }

    public void submit(View view)
    {
        Intent i =new Intent(this, CartCheck.class);
        Bundle extras = new Bundle();
        extras.putString("restaurant", restaurant);
        i.putExtras(extras);
        startActivity(i);



    /*    List<MyCart> mycartlist  = db.getAllBaskets();
        String items = mycartlist.get(0).getItemID();
        String qty = mycartlist.get(0).getQty().toString();
        try {
            for (int i = 1; i < mycartlist.size(); i++) {
                items += "-" + mycartlist.get(i).getItemID();
                qty += "-" + mycartlist.get(i).getQty().toString();
            }
        }
        catch(Exception e){
        }
        String next = "http://fooodies.in/bill/" + items + "/" + qty + "/";
        obj = new HandleCoursesJSON(next);
        obj.fetchJSON();
        while(obj.parsingComplete);

        item_array  = obj.getItems();

        price_array = obj.get_price();
        tax_array = obj.get_tax();
        item_total_array = obj.get_item_total();
        total = obj.get_total();
        bill_no = obj.get_bill_no();
*/
    }






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bill, menu);
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
