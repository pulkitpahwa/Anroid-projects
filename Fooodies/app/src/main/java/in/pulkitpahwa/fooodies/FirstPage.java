package in.pulkitpahwa.fooodies;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.os.Handler;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class FirstPage extends ActionBarActivity {

    private ProgressBar progressBar;
    private Handler handler= new Handler();
    private int progressStatus = 0;
    private String number;
    private RequestAppID obj;
    private CreateAppID db = new CreateAppID(this);
    private LocationManager locationManager;
    private LocationListener locationListener=null;

    //    private Button btnGetLocation = null;
//    private EditText editLocation = null;
    private ProgressBar pb =null;

    private static final String TAG = "Debug";
    private Boolean flag = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);
        setRequestedOrientation(ActivityInfo
                .SCREEN_ORIENTATION_PORTRAIT);

        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        progressBar.getProgressDrawable().setColorFilter(Color.parseColor("#6a1815"), PorterDuff.Mode.SRC_IN);

        Log.w("myapp","01");
        progressBar.setProgress(10);

        progressBar.setVisibility(View.VISIBLE);
        Log.w("myapp","02");


        locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);

        flag = displayGpsStatus();
        Log.w("myapp","restaurant form 1");
        Log.w("myapp",flag.toString());
        if (flag) {

            Log.w("myapp", "location on");

//            editLocation.setText("Please!! move your device to"+
//                    " see the changes in coordinates."+"\nWait..");

//            pb.setVisibility(View.VISIBLE);
            locationListener = new MyLocationListener();
            Log.w("myapp","restaurant form 2");
            //locationMangaer.requestLocationUpdates();
            locationManager.requestLocationUpdates(LocationManager
                    .GPS_PROVIDER, 5000, 10,locationListener);
            Log.w("myapp","location detected");
        } else {
            alertbox("Gps Status!!", "Your GPS is: OFF");
        }

        try {
            //check if app_id exist. if not, request for app_id.
            Log.w("myapp", "entered thread1");
            int count = db.getIDsCount();
            progressBar.setProgress(20);
            Log.w("myapp", "count = " + Integer.toString(count));
            if (Integer.toString(count) == "0") {

                //new Thread(new Runnable() {
                   // public void run() {
                        try {
                            Log.w("myapp", "0");
                            TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
                            number = tm.getLine1Number();
                            Log.w("myapp", number);

                        } catch (Exception e) {
                            number = "000";
                            Log.w("myapp", "1");
                        }
                        Log.w("myapp", "2");
                        obj = new RequestAppID();
                        obj.fetchJSON(number);
                        Log.w("myapp", "3");
                        while (obj.parsingComplete) ;
                        db.addAppID(obj.getId());
                        Log.w("myapp","appid = " + obj.getId());
                        //Log.w("myapp", "appid " + obj.getId().toString());
                        progressStatus = 100;
                        Log.w("myapp", "5");
                        if (progressStatus == 100) {
                            homepage();
                        }

                   // }

                //}).start();


                new Thread(new Runnable() {
                    public void run() {
                        Log.w("myapp", "Entered thread 2");
                        while (progressStatus < 90) {
                            progressStatus += 1;
                            Log.w("myapp", "5");
                            if (progressStatus == 100) {
                                Log.w("myapp", Integer.toString(progressStatus));
                                progressBar.setVisibility(View.INVISIBLE);
                                Log.w("myapp", "8");
                            }
                            // Update the progress bar and display the
                            //current value in the text view
                            handler.post(new Runnable() {
                                public void run() {
                                    progressBar.setProgress(progressStatus);
                                    Log.w("myapp", "555555555555555555555555");
                                    Log.w("myapp", Integer.toString(progressStatus));
                                }
                            });
                            try {
                                // Sleep for 200 milliseconds.
                                //Just to display the progress slowly
                                Log.w("myapp", "6");
                                Thread.sleep(400);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                Log.w("myapp", "7");
                            }
                        }
                    }
                }).start();

            } else {
                Log.w("myapp", "inside else");
                progressStatus = 100;
                Log.w("myapp", "5");
                if (progressStatus == 100) {
                    homepage();
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
            Log.w("myapp", "error occured");
        }
    }

    private Boolean displayGpsStatus() {
        Log.w("myapp","restaurant form 4");
        ContentResolver contentResolver = getBaseContext()
                .getContentResolver();
        boolean gpsStatus = Settings.Secure
                .isLocationProviderEnabled(contentResolver,
                        LocationManager.GPS_PROVIDER);
        Log.w("myapp","restaurant form 5");
        if (gpsStatus) {
            return true;

        } else {
            return false;
        }
    }

    /*----------Method to create an AlertBox ------------- */
    protected void alertbox(String title, String mymessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        Log.w("myapp","restaurant form 6");
        builder.setMessage("Your Device's GPS is Disable")
                .setCancelable(false)
                .setTitle("** Gps Status **")
                .setPositiveButton("Gps On",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // finish the current activity
                                // AlertBoxAdvance.this.finish();
                                Intent myIntent = new Intent(
                                        Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(myIntent);
                                dialog.cancel();
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // cancel the dialog box
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    /*----------Listener class to get coordinates ------------- */
    private class MyLocationListener implements LocationListener {
        @Override
        public void onLocationChanged(Location loc) {
            Log.w("myapp","restaurant form 7");
//            editLocation.setText("");
//            pb.setVisibility(View.INVISIBLE);
            Toast.makeText(getBaseContext(), "Location changed : Lat: " +
                            loc.getLatitude() + " Lng: " + loc.getLongitude(),
                    Toast.LENGTH_SHORT).show();
            String longitude = "Longitude: " +loc.getLongitude();
            Log.w("myapp", longitude);
            String latitude = "Latitude: " +loc.getLatitude();
            Log.w("myapp", latitude);
            Log.w("myapp","restaurant form 8")
            ;
    /*----------to get City-Name from coordinates ------------- */
            String cityName=null;
            Geocoder gcd = new Geocoder(getBaseContext(),
                    Locale.getDefault());
            List<Address> addresses;
            Log.w("myapp","restaurant form 9");
            try {
                addresses = gcd.getFromLocation(loc.getLatitude(), loc
                        .getLongitude(), 1);
                if (addresses.size() > 0)
                {
                    System.out.println(addresses.get(0).getLocality());
                    Log.w("myapp",addresses.get(0).getLocality());
                }
                Log.w("myapp","restaurant form 10");
                cityName=addresses.get(0).getLocality();
            } catch (IOException e) {
                e.printStackTrace();
                Log.w("myapp","restaurant form 11");
            }

            String s = longitude+"\n"+latitude +
                    "\n\nMy Currrent City is: "+cityName;
            db.addUserLocation(longitude, latitude);
            Log.w("myapp","hello");
            Log.w("myapp",db.getUserLocation().get(2) + "...." + db.getUserLocation().get(1));
            Log.w("myapp",s);
//            editLocation.setText(s);
        }

        @Override
        public void onProviderDisabled(String provider) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onProviderEnabled(String provider) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onStatusChanged(String provider,
                                    int status, Bundle extras) {
            // TODO Auto-generated method stub
        }
    }



    public void homepage()
    {
        Intent home = new Intent(this, ChooseOption.class);
        startActivity(home);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_first_page, menu);
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

