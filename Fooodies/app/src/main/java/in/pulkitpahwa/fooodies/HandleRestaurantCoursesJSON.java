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
public class HandleRestaurantCoursesJSON {
    private List<String> course_name  = new ArrayList<String>();
    private List<Integer> course_id  = new ArrayList<Integer>();
    private String readerror;
    private String urlString = null;

    private Boolean wrong = false;
    private String secret_key;


    public volatile boolean parsingComplete = true;

    public HandleRestaurantCoursesJSON(String url){this.urlString =  url;
        Log.w("myapp",urlString + " called");
    }

    public List<String> getName()
    {
        return course_name;
    }
    public List<Integer> getCourseid(){return course_id;}
    public Boolean getwrong()
    {
        return  this.wrong;
    }
    public String getSecret_key(){return secret_key;}

    @SuppressLint("NewApi")
    public void readAndParseJSON(String in)
    {
        try {


            Log.w("myapp", "course readandparse created");
            JSONObject reader = new JSONObject(in);
            Log.w("myapp", "course 6");
            try {
                readerror = reader.getString("error");
                wrong = true;
                Log.w("myapp","error");
            } catch (Exception e) {
                JSONObject courseObj = reader.getJSONObject("menu");

                Log.w("myapp", "course 7");
                secret_key = reader.getString("mid");
                JSONArray nameArray = courseObj.getJSONArray("course");
                Log.w("myapp", "course 8");
                JSONArray idArray = courseObj.getJSONArray("id");
                Log.w("myapp", "course 9");

                Log.w("myapp", nameArray.toString());
                Log.w("myapp", idArray.toString());

                if (nameArray != null) {
                    Log.w("myapp", "course 10");
                    for (int i = 0; i < nameArray.length(); i++) {
                        course_name.add(nameArray.get(i).toString());
                    }
                    Log.w("myapp", "course 11");
                }


                if (idArray != null) {
                    Log.w("myapp", "course 12");
                    for (int i = 0; i < idArray.length(); i++) {
                        course_id.add(idArray.getInt(i));
                    }
                }
                Log.w("myapp", "course 13");
                parsingComplete = false;
            }
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

