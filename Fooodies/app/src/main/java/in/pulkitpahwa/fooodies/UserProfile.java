package in.pulkitpahwa.fooodies;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class UserProfile extends ActionBarActivity {
    private String name;
    private String address;
    private String contact;
    private String email;
    private String city;
    private Intent i;
    private String restaurant;
    private String app_id;
    private String items;
    private String qty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        restaurant = extras.getString("restaurant");
        app_id = extras.getString("app_id");
        items = extras.getString("items");
        qty = extras.getString("qty");
        i = new Intent(this, PlaceHomeDelivery.class);

    }

    public void sendOrder(View view)
    {
        EditText editname = (EditText) findViewById(R.id.textView15);
        name = editname.getText().toString();
        //address
        EditText editaddress= (EditText) findViewById(R.id.textView17);
        address = editaddress.getText().toString();
        //city
        EditText editcity = (EditText) findViewById(R.id.textView19);
        city = editcity.getText().toString();
        //contact
        EditText editcontact = (EditText) findViewById(R.id.textView21);
        contact = editcontact.getText().toString();
        //email
        EditText editemail = (EditText) findViewById(R.id.textView23);
        email = editname.getText().toString();

        Bundle extras = new Bundle();
        extras.putString("name", name);
        extras.putString("address", address);
        extras.putString("city", city);
        extras.putString("contact", contact);
        extras.putString("email", email);
        extras.putString("restaurant", restaurant);
        extras.putString("items",items);
        extras.putString("qty",qty);
        extras.putString("app_id", app_id);
        i.putExtras(extras);
        i.putExtras(extras);
        startActivity(i);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_profile, menu);
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
