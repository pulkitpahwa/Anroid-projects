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
 * Created by pulkit on 15/4/15.
 */
public class HandleItemsJSON {
    private List<String> item_name  = new ArrayList<String>();
    private List<String> item_id  = new ArrayList<String>();
    private List<String> item_image  = new ArrayList<String>();
    private List<String> price = new ArrayList<String>();
    private List<String> tax = new ArrayList<String>();
    private String urlString = null;
    private String courseName = null;
    private String baseUrl = "http://fooodies.in/menu/mitems/";

    public volatile boolean parsingComplete = true;

    public HandleItemsJSON(String url){this.urlString = baseUrl+ url;
        Log.w("myapp",urlString + " called");
    }

    public List<String> getName()
    {
        return item_name;
    }
    public List<String> getItemid(){return item_id;}
    public List<String> getPrice(){return price;}
    public List<String> getimages(){return item_image;}
    public List<String> getTax(){return tax;}
    public String getCourseName(){return courseName;}



    @SuppressLint("NewApi")
    public void readAndParseJSON(String in)
    {
        try{

            Log.w("myapp", "course readandparse created");
            JSONObject reader = new JSONObject(in);
            Log.w("myapp","item 6");
            JSONObject courseObj = reader.getJSONObject("items");
            Log.w("myapp","item 7");
            JSONArray nameArray = courseObj.getJSONArray("name");
            JSONArray idArray = courseObj.getJSONArray("id");
            JSONArray priceArray = courseObj.getJSONArray("price");
            JSONArray taxArray = courseObj.getJSONArray("tax");
            JSONArray imagesArray = courseObj.getJSONArray("images");
            Log.w("myapp","item 9");

            Log.w("myapp",nameArray.toString());
            Log.w("myapp",idArray.toString());
            Log.w("myapp",priceArray.toString());


            if (nameArray != null)
            {
                Log.w("myapp","item 10");
                for(int i = 0; i<nameArray.length(); i++)
                {
                    item_name.add(nameArray.get(i).toString());
                }
                Log.w("myapp","item 11");
            }
            if (imagesArray != null)
            {
                Log.w("myapp","item 10");
                for(int i = 0; i<imagesArray.length(); i++)
                {
                    item_image.add(imagesArray.get(i).toString());
                }
                Log.w("myapp","item 11");
            }

            if (idArray != null)
            {
                Log.w("myapp","item 12");
                for(int i = 0; i<idArray.length(); i++)
                {
                    item_id.add(idArray.get(i).toString());
                }
            }

            if (priceArray != null)
            {
                Log.w("myapp","item 12");
                for(int i = 0; i<priceArray.length(); i++)
                {
                    price.add(priceArray.get(i).toString());
                }
            }

            if (taxArray != null)
            {
                Log.w("myapp","item 12a");
                for(int i = 0; i<taxArray.length(); i++)
                {
                    tax.add(taxArray.get(i).toString());
                }
                Log.w("myapp","item 12b");
            }

            Log.w("myapp","item 13");
            parsingComplete = false;
        }

        catch(Exception e)
        {
            Log.w("myapp","item 14");
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
                    Log.w("myapp","item 1");
                    conn.setConnectTimeout(35000);
                    conn.setRequestMethod("GET");
                    Log.w("myapp","item 2");
                    conn.setDoInput(true);
                    conn.connect();
                    Log.w("myapp","item 3");
                    InputStream stream  = conn.getInputStream();
                    Log.w("myapp","item 4");
                    String data = convertStreamToString(stream);
                    Log.w("myapp","item 5");
                    readAndParseJSON(data);
                    Log.w("myapp","item 15");

                    Log.w("myapp",item_name.toString());
                    Log.w("myapp",item_id.toString());

                    stream.close();
                }
                catch(Exception e)
                {
                    Log.w("myapp","item 16");
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
