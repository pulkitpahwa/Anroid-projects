/**
 * Created by pulkit on 13/4/15.
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
import org.json.JSONObject;
import org.json.JSONArray;

import android.annotation.SuppressLint;
import android.util.Log;


public class HandleNextJSON {
    private List<String> name  = new ArrayList<String>();
    private List<String> nv  = new ArrayList<String>();
    private List<String> hd  = new ArrayList<String>();
    private List<Integer> fid  = new ArrayList<Integer>();
    private List<String> ot = new ArrayList<String>();
    private List<String> ct = new ArrayList<String>();


    private String urlString = null;
    private String baseUrl = "http://fooodies.in/restaurant/";

    public volatile boolean parsingComplete = true;

    public HandleNextJSON(String  url)
    {
        this.urlString = url;
    }

    @SuppressLint("NewApi")
    public void readAndParseJSON(String in)
    {
        try{
            String url ;
            JSONObject reader = new JSONObject(in);
            JSONObject restaurantObj = reader.getJSONObject("restaurant");
            JSONArray nameArray = restaurantObj.getJSONArray("name");
            JSONArray nvArray = restaurantObj.getJSONArray("nv");
            JSONArray fidArray = restaurantObj.getJSONArray("fid");
            JSONArray otArray = restaurantObj.getJSONArray("ot");
            JSONArray hdArray = restaurantObj.getJSONArray("hd");
            JSONArray ctArray = restaurantObj.getJSONArray("ct");

            if (nameArray != null)
            {
                for(int i = 0; i<nameArray.length(); i++)
                {
                    name.add(nameArray.get(i).toString());
                }
            }


            if (nvArray != null)
            {
                for(int i = 0; i<nvArray.length(); i++)
                {
                    nv.add(nvArray.get(i).toString());
                }
            }

            if (fidArray != null)
            {
                for(int i = 0; i<fidArray.length(); i++)
                {
                    fid.add(Integer.parseInt(fidArray.get(i).toString()));
                }
            }

            if (otArray != null)
            {
                for(int i = 0; i<otArray.length(); i++)
                {
                    ot.add(otArray.get(i).toString());
                }
            }

            if (hdArray != null)
            {
                for(int i = 0; i<hdArray.length(); i++)
                {
                    hd.add(hdArray.get(i).toString());
                }
            }

            if (ctArray != null)
            {
                for(int i = 0; i<ctArray.length(); i++)
                {
                    ct.add(ctArray.get(i).toString());
                }
            }
            parsingComplete = false;
        }

        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void fetchJSON()
    {
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run()
            {
                try
                {
                    URL url =  new URL(urlString);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(30000);
                    conn.setConnectTimeout(35000);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream stream  = conn.getInputStream();
                    String data = convertStreamToString(stream);
                    readAndParseJSON(data);
                    stream.close();
                }
                catch(Exception e)
                {
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


