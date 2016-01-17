package in.pulkitpahwa.fooodies;

import android.annotation.SuppressLint;
import android.util.Log;

import java.io. IOException ;
import java.io. InputStream ;
import java.io. InputStreamReader ;
import java.io. StringWriter ;
import java.io. UnsupportedEncodingException ;
import java.net. HttpURLConnection ;
import java.net.URL;
import java.util. ArrayList ;
import java.util. List ;
import java.util. Map ;
import java.util.Scanner;

import org.json.JSONArray;
import org.json. JSONObject ;
import org.xmlpull.v1. XmlPullParser ;
import org.xmlpull.v1. XmlPullParserFactory ;

/**
 * Created by pulkit on 13/4/15.
 */
public class HandleMyJSON {
    private List<String> name  = new ArrayList<String>();
    private List<String> nv  = new ArrayList<String>();
    private List<String> hd  = new ArrayList<String>();
    private List<Integer> fid  = new ArrayList<Integer>();
    private List<String> ot = new ArrayList<String>();
    private List<String> ct = new ArrayList<String>();


    private String urlString = null;
    private String baseUrl = "http://fooodies.in/restaurant/";

    public volatile boolean parsingComplete = true;

    public HandleMyJSON(String url){this.urlString = baseUrl+ url;
        Log.w("myapp",urlString + " called");
    }

    public List<String> getName()
    {
        return name;
    }
    public List<String> getNv(){return nv;}
    public List<String> getHd(){return hd;}
    public List<Integer> getFid(){return fid;}
    public List<String> getOt(){return ot;}
    public List<String> getCt(){return ct;}



    @SuppressLint("NewApi")
    public void readAndParseJSON(String in)
    {
        try{

            Log.w("myapp", "restaurant readandparse created");
            JSONObject reader = new JSONObject(in);
            Log.w("myapp","restaurant 6");
            JSONObject restaurantObj = reader.getJSONObject("restaurant");
            Log.w("myapp","restaurant 7");
            JSONArray nameArray = restaurantObj.getJSONArray("name");
            Log.w("myapp","restaurant 8");
            JSONArray nvArray = restaurantObj.getJSONArray("nv");
            Log.w("myapp","restaurant 9");
            JSONArray fidArray = restaurantObj.getJSONArray("fid");
            Log.w("myapp","restaurant 10");
            JSONArray otArray = restaurantObj.getJSONArray("ot");
            Log.w("myapp","restaurant 11");
            JSONArray hdArray = restaurantObj.getJSONArray("hd");
            Log.w("myapp","restaurant 12");
            JSONArray ctArray = restaurantObj.getJSONArray("ct");
            Log.w("myapp","restaurant 13");

            Log.w("myapp",otArray.toString());
            Log.w("myapp",ctArray.toString());
            Log.w("myapp",hdArray.toString());

            if (nameArray != null)
            {
                Log.w("myapp","restaurant 14");
                for(int i = 0; i<nameArray.length(); i++)
                {
                    name.add(nameArray.get(i).toString());
                }
                Log.w("myapp","restaurant 15");
            }


            if (nvArray != null)
            {
                Log.w("myapp","restaurant 16");
                for(int i = 0; i<nvArray.length(); i++)
                {
                    nv.add(nvArray.get(i).toString());
                }
            }
            Log.w("myapp","restaurant 17");
            if (fidArray != null)
            {
                Log.w("myapp","restaurant 18");
                for(int i = 0; i<fidArray.length(); i++)
                {
                    fid.add(Integer.parseInt(fidArray.get(i).toString()));
                }
            }

            if (otArray != null)
            {
                Log.w("myapp","restaurant 19");
                for(int i = 0; i<otArray.length(); i++)
                {
                    ot.add(otArray.get(i).toString());
                }
            }

            if (hdArray != null)
            {   Log.w("myapp","restaurant 20");
                for(int i = 0; i<hdArray.length(); i++)
                {
                    hd.add(hdArray.get(i).toString());
                }
            }

            if (ctArray != null)
            {
                Log.w("myapp","restaurant 21");
                for(int i = 0; i<ctArray.length(); i++)
                {
                    ct.add(ctArray.get(i).toString());
                }
            }
            parsingComplete = false;
        }

        catch(Exception e)
        {
            Log.w("myapp","restaurant 22");
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
                    Log.w("myapp","restaurant 1");
                    conn.setConnectTimeout(35000);
                    conn.setRequestMethod("GET");
                    Log.w("myapp","restaurant 2");
                    conn.setDoInput(true);
                    conn.connect();
                    Log.w("myapp","restaurant 3");
                    InputStream stream  = conn.getInputStream();
                    Log.w("myapp","restaurant 4");
                    String data = convertStreamToString(stream);
                    Log.w("myapp","restaurant 5");
                    readAndParseJSON(data);
                    Log.w("myapp","restaurant 23");

                    Log.w("myapp",name.toString());
                    Log.w("myapp",ct.toString());
                    Log.w("myapp",hd.toString());

                    stream.close();
                }
                catch(Exception e)
                {
                    Log.w("myapp","restaurant 24");
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
