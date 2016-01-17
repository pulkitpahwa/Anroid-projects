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
public class HandleCoursesJSON {
    private List<String> course_name  = new ArrayList<String>();
    private List<String> course_id  = new ArrayList<String>();
    private String image= "";
    private String urlString = null;
    private String baseUrl = "http://fooodies.in/menu/mcourse/";

    public volatile boolean parsingComplete = true;

    public HandleCoursesJSON(String url){this.urlString = baseUrl+ url;
        Log.w("myapp",urlString + " called");
    }

    public List<String> getName()
    {
        return course_name;
    }
    public List<String> getCourseid(){return course_id;}
    public String getimage(){return image;}



    @SuppressLint("NewApi")
    public void readAndParseJSON(String in)
    {
        try{

            Log.w("myapp", "course readandparse created");
            JSONObject reader = new JSONObject(in);
            Log.w("myapp","course 6");
            JSONObject courseObj = reader.getJSONObject("courses");
            Log.w("myapp","course 7");
            Log.w("myapp",courseObj.toString());
            JSONArray nameArray = courseObj.getJSONArray("name");
            Log.w("myapp",nameArray.toString());
            JSONArray idArray = courseObj.getJSONArray("id");
            Log.w("myapp","course 9");
            Log.w("myapp",courseObj.toString());

            Log.w("myapp",nameArray.toString());
            Log.w("myapp",idArray.toString());

            if (nameArray != null)
            {
                Log.w("myapp","course 10");
                for(int i = 0; i<nameArray.length(); i++)
                {
                    course_name.add(nameArray.get(i).toString());
                }
                Log.w("myapp","course 11");
            }


            if (idArray != null)
            {
                Log.w("myapp","course 12");
                for(int i = 0; i<idArray.length(); i++)
                {
                    course_id.add(idArray.get(i).toString());
                }
            }
            Log.w("myapp","course 13");
            parsingComplete = false;
        }

        catch(Exception e)
        {
            Log.w("myapp","course 14");
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
                    Log.w("myapp","course 1");
                    conn.setConnectTimeout(35000);
                    conn.setRequestMethod("GET");
                    Log.w("myapp","course 2");
                    conn.setDoInput(true);
                    conn.connect();
                    Log.w("myapp","course 3");
                    InputStream stream  = conn.getInputStream();
                    Log.w("myapp","course 4");
                    String data = convertStreamToString(stream);
                    Log.w("myapp","course 5");
                    readAndParseJSON(data);
                    Log.w("myapp","course 15");

                    Log.w("myapp",course_name.toString());
                    Log.w("myapp",course_id.toString());

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
