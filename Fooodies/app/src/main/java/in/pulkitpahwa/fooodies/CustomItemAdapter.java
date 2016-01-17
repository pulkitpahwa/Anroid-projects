package in.pulkitpahwa.fooodies;

/**
 * Created by pulkit on 15/4/15.
 */
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;
//import


class CustomItemAdapter extends ArrayAdapter
{
    private List<String> nameData  = new ArrayList<String>();
    private List<String> idData  = new ArrayList<String>();
    private List<String> priceData  = new ArrayList<String>();
    private List<String> imageData  = new ArrayList<String>();
    private String courseName= new String();
    private Context cont;



    CustomItemAdapter(Context context,List<String> names, List<String> id,List<String> price , String courseName,List<String> images   ) {
        super(context, R.layout.custom_row,  names);
        cont = context;
        Log.w("myapp","course adapter 1");
        this.nameData  = names;
        Log.w("myapp","course adapter 2");
        this.idData = id;
        Log.w("myapp","course adapter 3");
        this.priceData = price;
        this.courseName = courseName;
        this.imageData = images;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.w("myapp","item adapter 5");
        LayoutInflater namesInflater = LayoutInflater.from(getContext());
        Log.w("myapp","item adapter 6");
        View customView = namesInflater.inflate(R.layout.custom_item_row, parent, false);
        Log.w("myapp","item adapter 7");
        TextView item_name = (TextView) customView.findViewById(R.id.item_name);
        Log.w("myapp","item adapter 8");
        item_name.setText(nameData.get(position));
        TextView item_price = (TextView) customView.findViewById(R.id.item_price);
        Log.w("myapp","item 9");
        item_price.setText(priceData.get(position));
        Log.w("myapp","item 10");
        ImageView myimage = (ImageView) customView.findViewById(R.id.image_item);
        myimage.setImageResource(R.drawable.loader);
        //new DownloadImageTask(myimage).execute("http://fooodies.in/media/"+imageData.get(position));

        Picasso.with(cont)
                .load("http://fooodies.in/"+ imageData.get(position))
                .into(myimage);
        return customView;
    }

/*    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }
        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon = BitmapFactory.decodeStream(in);
                Log.w("myapp","get image");
            } catch (Exception e) {
                Log.w("myapp", e.getMessage());
                e.printStackTrace();
            }
            return mIcon;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
*/
}
