package in.pulkitpahwa.fooodies;

/**
 * Created by pulkit on 9/4/15.
 */
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
//import android.content.Intent;
//  import android.view.View;
import org.json.JSONObject;
import org.json.JSONArray;
//import org.xmlpull.v1.XmlPullParser;
//import org.xmlpull.v1.XmlPullParserFactory;

import android.annotation.SuppressLint;
import android.util.Log;

public class HandleJSON {
    private List<Integer> StateId  = new ArrayList<Integer>();
    private List<Integer> CityId  = new ArrayList<Integer>();
    private List<Integer> LocationId  = new ArrayList<Integer>();
    private List<String> StateNames  = new ArrayList<String>();
    private List<String> CityNames  = new ArrayList<String>();
    private List<String> LocationNames  = new ArrayList<String>();
    private String urlString = null;
    private String cityUrlString = null;

    public volatile boolean parsingComplete = true;

    public HandleJSON(String  url)
    {
        this.urlString = url;
    }
    public HandleJSON(String city, String url) {this.cityUrlString = url;}

    public List<Integer> getStateId()
    {
        return StateId;
    }
    public List<Integer> getCityId()
    {
        Log.w("myapp","returning city Id");
        Log.w("myapp",CityId.toString());
        return CityId;
    }
    public List<Integer> getLocationId()
    {
        return LocationId;
    }
    public List<String> getStateNames()
    {
        return StateNames;
    }
    public List<String> getCityNames()
    {
        Log.w("myapp","returning city names");
        Log.w("myapp",CityNames.toString());
        return CityNames;
    }
    public List<String> getLocationNames()
    {
        return LocationNames;
    }


    @SuppressLint("NewApi")
    public void readAndParseStateJSON(String in)
    {
        try{
            Log.w("myapp","object called 4");
            String url ;//this will take the value of state_id;
            JSONObject reader = new JSONObject(in);
            Log.w("myapp","object called 4.1");
            JSONObject statesObj = reader.getJSONObject("state");
            Log.w("myapp","object called 4.2");
            JSONArray statearray = statesObj.getJSONArray("name");
            Log.w("myapp","object called 4.3");
            if (statearray != null)
            {
                Log.w("myapp","object called 4.forloop");
                for(int i = 0; i<statearray.length(); i++)
                {
                    StateNames.add(statearray.get(i).toString());
                }
            }

            Log.w("myapp","object called 4.4");
            JSONArray stateIdArray = statesObj.getJSONArray("id");
            if (stateIdArray != null)
            {
                for(int i = 0; i<stateIdArray.length(); i++)
                {
                    StateId.add(Integer.parseInt(stateIdArray.get(i).toString()));
                }
            }
            Log.w("myapp","object called 4.5");
            parsingComplete = false;
        }

        catch(Exception e)
        {
            Log.w("myapp","object called 4.catch");
            e.printStackTrace();
        }
    }

    public void fetchJSON()
    {
        Log.w("myapp","object called b/w 2 and 3");
        Thread thread = new Thread(new Runnable(){
            @Override
        public void run()
            {
                try
                {
                    Log.w("myapp", "object called 3");
                    URL url =  new URL(urlString);
                    Log.w("myapp","object called a1");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    Log.w("myapp","object a2");
                    conn.setReadTimeout(30000);
                    Log.w("myapp","object called a3");
                    conn.setConnectTimeout(35000);
                    Log.w("myapp","object called a4");
                    conn.setRequestMethod("GET");
                    Log.w("myapp","object a5");
                    conn.setDoInput(true);
                    Log.w("myapp","object called a6");
                    conn.connect();
                    Log.w("myapp", "object called aa");
                    InputStream stream  = conn.getInputStream();
                    Log.w("myapp","object called a7");
                    String data = convertStreamToString(stream);
                    Log.w("myapp", "object called a8");
                    readAndParseStateJSON(data);
                    Log.w("myapp", "object called a9");
                    stream.close();
                }
                catch(Exception e)
                {
                    Log.w("myapp","object called catch");
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    @SuppressLint("NewApi")
    public void readAndParseCityJSON(String in)
    {
        try{
            Log.w("myapp","object called city4");
            String url ;//this will take the value of state_id;
            JSONObject reader = new JSONObject(in);
            Log.w("myapp","object called city4.1");
            JSONObject cityObj = reader.getJSONObject("city");
            Log.w("myapp","object called city4.2");
            JSONArray cityarray = cityObj.getJSONArray("name");
            Log.w("myapp","object called city4.3");
            if (cityarray != null)
            {
                CityNames.add("------------");
                Log.w("myapp","object called city4.forloop");
                for(int i = 0; i<cityarray.length(); i++)
                {
                    CityNames.add(cityarray.get(i).toString());
                }
                Log.w("myapp", CityNames.toString());
            }

            Log.w("myapp","object called city4.4");
            JSONArray cityIdArray = cityObj.getJSONArray("id");
            if (cityIdArray != null)
            {
                CityId.add(-1);
                for(int i = 0; i<cityIdArray.length(); i++)
                {
                    CityId.add(Integer.parseInt(cityIdArray.get(i).toString()));
                }
                Log.w("myapp", CityId.toString());
            }
            Log.w("myapp","object called city4.5");
            parsingComplete = false;
        }

        catch(Exception e)
        {
            Log.w("myapp","object called city4.catch");
            e.printStackTrace();
        }
    }

    public void fetchCityJSON()
    {
        Log.w("myapp","object called city b/w 2 and 3");
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run()
            {
                try
                {

                    URL url =  new URL(cityUrlString);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(30000);
                    conn.setConnectTimeout(35000);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream stream  = conn.getInputStream();
                    String data = convertStreamToString(stream);
                    readAndParseCityJSON(data);
                    stream.close();
                }
                catch(Exception e)
                {
                    Log.w("myapp","object called citycatch");
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    @SuppressLint("NewApi")
    public void readAndParseLocationJSON(String in)
    {
        try{
            Log.w("myapp","object called location read");
            String url ;//this will take the value of state_id;
            JSONObject reader = new JSONObject(in);
            Log.w("myapp","object called location json called");
            JSONObject locationObj = reader.getJSONObject("location");
            Log.w("myapp","object called location json object parsed");
            JSONArray locationarray = locationObj.getJSONArray("name");
            Log.w("myapp","object called location json name array parsed");
            if (locationarray != null)
            {
                Log.w("myapp","object called location.forloop");
                LocationNames.add("------------");

                for(int i = 0; i<locationarray.length(); i++)
                {
                    LocationNames.add(locationarray.get(i).toString());
                }
                Log.w("myapp", LocationNames.toString());
            }

            Log.w("myapp","object called location.forloop completed");
            JSONArray locationIdArray = locationObj.getJSONArray("id");
            if (locationIdArray != null)
            {
                LocationId.add(-1);
                for(int i = 0; i< locationIdArray.length(); i++)
                {
                    LocationId.add(Integer.parseInt( locationIdArray.get(i).toString()));
                }
                Log.w("myapp", LocationId.toString());
            }
            Log.w("myapp","object called locationid for loop completed");
            parsingComplete = false;
        }

        catch(Exception e)
        {
            Log.w("myapp","object called location read .catch");
            e.printStackTrace();
        }
    }

    public void fetchLocationJSON()
    {
        Log.w("myapp","object called location fetch");
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run()
            {
                try
                {

                    URL url =  new URL(cityUrlString);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(8000);
                    conn.setConnectTimeout(8000);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream stream  = conn.getInputStream();
                    String data = convertStreamToString(stream);
                    readAndParseLocationJSON(data);
                    stream.close();
                }
                catch(Exception e)
                {
                    Log.w("myapp","object called location fetch catch");
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }



    static String convertStreamToString(InputStream is)
    {
        Log.w("myapp","object called b/w 3 and 4");
        java.util.Scanner sc = new Scanner(is).useDelimiter("\\A");
        return sc.hasNext() ? sc.next() : " ";
    }

}
