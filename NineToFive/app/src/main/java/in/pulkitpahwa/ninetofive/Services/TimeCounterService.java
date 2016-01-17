package in.pulkitpahwa.ninetofive.Services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import in.pulkitpahwa.ninetofive.Exercise;
import in.pulkitpahwa.ninetofive.MainActivity;
import in.pulkitpahwa.ninetofive.R;

public class TimeCounterService extends Service {

    private static TimeCounterService instance;


    public TimeCounterService() {
        Log.w("myapp","service instantiated");
/*        objectsCounter += 1;

        Log.w("myapp", Integer.toString(count()));
        if(objectsCounter == 2)
        {
            onDestroy();
        }
  */  }

    public static TimeCounterService getTimeCounterServiceInstance()
    {
        if (instance == null)
        {
            instance = new TimeCounterService();
        }
        return instance;
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Runnable r = new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i < 20; i++) {
                    synchronized (this) {
                        try {
                            wait(1000);
                            Log.w("myapp", Integer.toString(i));
                        } catch (Exception e) {
                            Log.w("myapp", "error cauge");
                        }
                    }
                }
                sendNotifications();
            }
        };
        Thread bt = new Thread(r);
        bt.start();
        return Service.START_STICKY;
    }


    @Override
    public IBinder onBind(Intent intent) {
        Log.w("myapp", "onbind method");
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        Log.w("myapp", "on destroyd");
        super.onDestroy();
    }

   /* public int count()
    {
        return objectsCounter;
    }
*/
    public void sendNotifications()
    {
        NotificationCompat.Builder notification;
        notification = new NotificationCompat.Builder(this).setAutoCancel(true)
                .setSmallIcon(R.drawable.side_plank)
                .setTicker("Ready to exercise")
                .setWhen(System.currentTimeMillis())
                .setContentTitle("Time to exercise")
                .setContentText("beautiful");


        Intent i = new Intent(this, Exercise.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,i,0);
        Log.w("myapp", "2");
//        PendingIntent pi = PendingIntent.getActivity(context, 0, intent,
//                PendingIntent.FLAG_UPDATE_CURRENT);
        Log.w("myapp", "21");
        notification.setContentIntent(pendingIntent);
        Log.w("myapp", "22");
        NotificationManager nm = (NotificationManager) getSystemService
                (Context.NOTIFICATION_SERVICE);

        Log.w("myapp","3");
        long [] pattern = {0, 100, 1000};
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(pattern, 0);
        v.vibrate(500);
        nm.notify(785548123, notification.build());
        Log.w("myapp", "4");

    }
}
