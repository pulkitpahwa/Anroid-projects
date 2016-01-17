package in.pulkitpahwa.fooodies;

import android.annotation.SuppressLint;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by pulkit on 26/4/15.
 */
public class RequestAppID {
    private String app_id;
    private String baseUrl = "";

    public volatile boolean parsingComplete = true;

    public RequestAppID(){
        Log.w("myapp", "requestapiid called");
    }

    public String getId()
    {
        return app_id;
    }


    @SuppressLint("NewApi")
    public void readAndParseJSON(String in)
    {
        try{

            Log.w("myapp", "course readandparse created");
            JSONObject reader = new JSONObject(in);
            Log.w("myapp","course 6");
            JSONObject courseObj = reader.getJSONObject("user");
            Log.w("myapp","course 7");
            JSONArray idArray = courseObj.getJSONArray("app");
            app_id = idArray.get(0).toString();
            //JSONArray error = courseObj.getJSONArray("error");
            //String error = error[0];
            Log.w("myapp","course 13");
            parsingComplete = false;
        }

        catch(Exception e)
        {
            Log.w("myapp","course 14");
            e.printStackTrace();
        }
    }

    public void fetchJSON(final String number)
    {
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run()
            {


                try
                {
                    URL url =  new URL("http://45.55.134.122/profiles/myapp/"+number);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(40000);
                    conn.setConnectTimeout(45000);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream stream  = conn.getInputStream();
                    Log.w("myapp","course 4");
                    String data = convertStreamToString(stream);
                    Log.w("myapp","course 5");
                    readAndParseJSON(data);
                    Log.w("myapp","course 15");
                    stream.close();
                }
                catch(Exception e)
                {
                    Log.w("myapp","course 16");
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }


    static String convertStreamToString(InputStream is)
    {
        java.util.Scanner sc = new Scanner(is).useDelimiter("\\A");
        return sc.hasNext() ? sc.next() : " ";
    }


}
