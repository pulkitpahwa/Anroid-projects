package in.pulkitpahwa.ninetofive;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.renderscript.ScriptIntrinsicYuvToRGB;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;

import in.pulkitpahwa.ninetofive.DatabaseHandler.DbSettings;

public class TimeChecker extends AsyncTask<String, String, String> {
    private DbSettings db;
    public static Integer count = 0;
    public Integer a = 60000;
    public long timer;

    private static final int unique = 785548;
    private Context context;

    public TimeChecker(Context context) {
        count = count+1;
        this.context = context;
        Log.w("myapp","hi");
        db = new DbSettings(context);
        timer = System.currentTimeMillis() + 2000;
        Log.w("myapp","hi");
    }

    @Override
    protected void onPostExecute(String arg0) {
        super.onPostExecute(arg0);
        Log.w("myapp", "done");
        sendNotifications();
    }


    @Override
    protected void onCancelled() {
        super.onCancelled();
        Log.w("myapp", "cancelled");
    }

    @Override
    protected String doInBackground(String... arg0) {
        Log.w("myapp", "background");
        while(System.currentTimeMillis() <timer)
        {
            synchronized (this)
            {
                try{
                    wait(500);
                    Log.w("myapp","app progress");
                }
                catch (Exception e)
                {
                    Log.w("myapp","error");

                }
            }
        }
        return "apple";
    }

    public int getcount()
    {
        return count;
    }

    protected void sendNotifications() {
        NotificationCompat.Builder notification;
        notification = new NotificationCompat.Builder(this.context);
        notification.setAutoCancel(true);
        Log.w("myapp", "1");
        notification.setSmallIcon(R.drawable.side_plank);
        notification.setTicker("Ready to exercise");
        notification.setWhen(System.currentTimeMillis());
        notification.setContentTitle("hello world");
        notification.setContentText("beautiful");

        Intent intent = new Intent(context, MainActivity.class);
        Log.w("myapp", "2");
        PendingIntent pi = PendingIntent.getActivity(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        Log.w("myapp", "21");
        notification.setContentIntent(pi);
        Log.w("myapp", "22");
        NotificationManager nm = (NotificationManager) context.getSystemService
                        (Context.NOTIFICATION_SERVICE);

        Log.w("myapp","3");
        nm.notify(785548123, notification.build());
        Log.w("myapp", "4");
    }
}
