package in.pulkitpahwa.fooodies;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.widget.AdapterView.OnItemSelectedListener;


public class LocationFinder extends ActionBarActivity {

    Spinner statespinner, cityspinner, locationspinner;
    ArrayAdapter<String> stateadapter, cityadapter, locationadapter;
    private String baseurl = "http://45.55.134.122/profiles/mstate/1/";
    private String cityurl = "http://45.55.134.122/profiles/mcity/1/";
    private String cityId  = null;
    private HandleJSON obj;
    private String state_id,city_id , location_id;
    private List<Integer> StateId  = new ArrayList<Integer>();
    private List<Integer> CityId  = new ArrayList<Integer>();
    private List<Integer> LocationId  = new ArrayList<Integer>();
    private List<String> StateNames  = new ArrayList<String>();
    private List<String> CityNames  = new ArrayList<String>();
    private List<String> LocationNames  = new ArrayList<String>();
    private Intent i;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_finder);
        //String finalUrl = baseurl + "1";//state_id.toString();
        Log.v("msg123", "helloworld");
        Log.w("myapp", "object called");

    }

    @Override
    protected void onStart() {
        super.onStart();
        i = new Intent(this, RestaurantHome.class);
        obj = new HandleJSON(baseurl);
        obj.fetchJSON();
        Log.w("myapp", "object called 2");
        while (obj.parsingComplete) ;
        StateId = obj.getStateId();
        StateNames = obj.getStateNames();
        Log.w("myapp", StateNames.toString());
        Log.w("myapp", String.valueOf(StateId));
        statespinner = (Spinner)findViewById(R.id.statespinner);
        stateadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, StateNames);
        stateadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statespinner.setAdapter(stateadapter);

        statespinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getBaseContext(), parent.getSelectedItem() + " selected", Toast.LENGTH_SHORT).show();
                Toast.makeText(getBaseContext(), "Fetching data from the internet", Toast.LENGTH_LONG).show();
                //String state = parent.getSelectedItem().toString();
                state_id = StateId.get(StateNames.indexOf(parent.getSelectedItem())).toString();
                obj = new HandleJSON("city","http://45.55.134.122/profiles/mcity/1/"+state_id); // this will call the constructor 2. :)
                obj.fetchCityJSON();
                Log.w("myapp","object called city1");
                while(obj.parsingComplete);
                CityId = obj.getCityId();
                CityNames = obj.getCityNames();
                Log.w("myapp", "city names testing 1");
                Log.w("myapp",  CityNames.toString());
                Log.w("myapp",  String.valueOf(CityId));
                cityOpen();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        cityspinner = (Spinner)findViewById(R.id.cityspinner);
        cityspinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getBaseContext(), parent.getSelectedItem() + " selected", Toast.LENGTH_SHORT).show();
                Toast.makeText(getBaseContext(), "Fetching data from the internet", Toast.LENGTH_SHORT).show();
                String city = parent.getSelectedItem().toString();
                Log.w("myapp","helloword");
                city_id = CityId.get(CityNames.indexOf(city)).toString();
                Log.w("myapp",city_id);
                if(city == "------------") {
                    Log.w("myapp", "inside if  block");
                }
                else{
                    Log.w("myapp", "inside else");
                    String myurl = "http://45.55.134.122/profiles/mlocation/1/" + state_id + "/"+ city_id;
                    Log.w("myapp", myurl);
                    obj = new HandleJSON("city", myurl); // this will call the constructor 2. :)
                    obj.fetchLocationJSON();
                    Log.w("myapp", "object called location1");
                    while (obj.parsingComplete) ;
                    LocationId = obj.getLocationId();
                    LocationNames = obj.getLocationNames();
                    Log.w("myapp", "cityid fetched. cityid = " + city_id);
                    locationOpen();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        locationspinner = (Spinner)findViewById(R.id.locationspinner);

        locationspinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getBaseContext(), parent.getSelectedItem() + " selected", Toast.LENGTH_SHORT).show();
                Toast.makeText(getBaseContext(), "Fetching data from the internet", Toast.LENGTH_SHORT).show();
                String location = parent.getSelectedItem().toString();
                location_id = LocationId.get(LocationNames.indexOf(location)).toString();
                if(location == "------------") {
                    Log.w("myapp", "inside location if  block");
                }
                else {


                    Log.w("myapp","now we willl call the intent ");
                    String next = state_id + "/" + city_id + "/" + location_id + "/";
                    Log.w("myapp","intent being called");

                    i.putExtra("next_url", next);
                    Log.w("myapp","intent being called");
                    startActivity(i);
                    Log.w("myapp","activity started");
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_location_finder, menu);
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

    public void cityOpen()
    {
       // cityspinner = (Spinner)findViewById(R.id.cityspinner);

        cityadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, CityNames);
        cityadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cityspinner.setAdapter(cityadapter);



    }

    public void locationOpen()
    {
        Log.v("myapp", "location names testing");
        Log.v("myapp",  LocationNames.toString());
        Log.v("myapp",  String.valueOf(LocationId));
        locationadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, LocationNames);
        locationadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationspinner.setAdapter(locationadapter);
    }
}

