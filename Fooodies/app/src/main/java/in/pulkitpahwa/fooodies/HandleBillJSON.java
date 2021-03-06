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
public class HandleBillJSON {
    private List<String> item_array  = new ArrayList<String>();
    private List<String> price_array = new ArrayList<String>();
    private List<String> tax_array = new ArrayList<String>();
    private List<String> item_total_array = new ArrayList<String>();
    private Double total;
    private String bill_no;
    private String urlString = null;
    private String baseUrl = "";

    public volatile boolean parsingComplete = true;

    public HandleBillJSON(String url){this.urlString = baseUrl+ url;
        Log.w("myapp",urlString + " called");
    }

    public List<String> getItems()
    {
        return  item_array;
    }
    public List<String> get_price(){return price_array;}
    public List<String> get_tax(){return tax_array;}
    public List<String> get_item_total(){return item_total_array;}
    public String get_bill_no(){return bill_no;}
    public String get_total(){return total.toString();}


    @SuppressLint("NewApi")
    public void readAndParseJSON(String in)
    {
        try{

            JSONObject reader = new JSONObject(in);
            Log.w("myapp","billjson 6");
            JSONObject courseObj = reader.getJSONObject("order");
            JSONArray itemsArray = courseObj.getJSONArray("items");
            JSONArray priceArray = courseObj.getJSONArray("price");
            JSONArray qtyArray = courseObj.getJSONArray("qty");
            total = courseObj.getDouble("total");
            //bill_no = courseObj.getString("bill_no");
            Log.w("myapp","billjson 7");

            if (itemsArray != null)
            {
                Log.w("myapp","bill 10");
                for(int i = 0; i<itemsArray.length(); i++)
                {   item_array.add(itemsArray.get(i).toString()); }
                Log.w("myapp","bill 11");
            }



            if (priceArray!= null)
            {
                Log.w("myapp","bill 12");
                for(int i = 0; i<priceArray.length(); i++)
                {   price_array.add(priceArray.get(i).toString()); }
                Log.w("myapp","bill 13");
            }

            if (qtyArray!= null)
            {
                Log.w("myapp","bill 144");
                for(int i = 0; i<qtyArray.length(); i++)
                {   tax_array.add(qtyArray.get(i).toString());  }
                Log.w("myapp","bill 154");
            }

/*            if (item_totalArray != null)
            {
                Log.w("myapp","bill 10");
                for(int i = 0; i<item_totalArray.length(); i++)
                {   item_total_array.add(item_totalArray.get(i).toString()); }
                Log.w("myapp","bill 11");
            }
            Log.w("myapp","bill 13");
  */
            parsingComplete = false;
        }

        catch(Exception e)
        {
            Log.w("myapp","bill 14");
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
                    Log.w("myapp", " ab 1");
                    URL url =  new URL(urlString);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(30000);
                    Log.w("myapp", " ab 2");
                    conn.setConnectTimeout(35000);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    Log.w("myapp", " ab 3");
                    conn.connect();
                    Log.w("myapp", " ab 4");
                    InputStream stream  = conn.getInputStream();
                    String data = convertStreamToString(stream);
                    readAndParseJSON(data);
                    Log.w("myapp", " ab 5");
                    Log.w("myapp","bill 15");
                    stream.close();
                }
                catch(Exception e)
                {
                    Log.w("myapp","bill 16");
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
