package com.hatem.notificationapp;



import android.app.Notification;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

//This class acts as a timer which sends broadcasts to mock up front camera access.
public class MThread extends Thread {
    private Random rnd = new Random();
    private Context context;
     NotificationCompat.Builder mBuilder;
     NotificationCompat.Builder mBuilder2;
     NotificationCompat.Builder mBuilder3;
     NotificationCompat.Builder mBuilder4;
    private boolean flag = false;
    private int counter = 0;
    private int counter2 =0;
    private int counter3 =0;
    private int counter4 =0;
    private int limit =6;
    public MThread(Context context) {
        this.context = context;

        //Creating the notifications which will be sent later.
        mBuilder = new NotificationCompat.Builder(this.context, "1").setSmallIcon(R.drawable.sc_processing).setContentTitle("Processing...").setContentText("Snapchat is processing captured photos.").setPriority(NotificationCompat.PRIORITY_MAX).setVibrate(new long[0]);
        mBuilder2 = new NotificationCompat.Builder(this.context, "2").setSmallIcon(R.drawable.snapchat).setContentTitle("Camera is accessed").setContentText("Snapchat is using your front camera.").setPriority(NotificationCompat.PRIORITY_HIGH).setVibrate(new long[0]);
        mBuilder3 = new NotificationCompat.Builder(this.context, "3").setSmallIcon(R.drawable.fb_processing).setContentTitle("Processing...").setContentText("Facebook is processing captured photos.").setPriority(NotificationCompat.PRIORITY_MAX).setVibrate(new long[0]);
        mBuilder4 = new NotificationCompat.Builder(this.context, "4").setSmallIcon(R.drawable.facebook).setContentTitle("Camera is accessed").setContentText("Facebook is using your front camera.").setPriority(NotificationCompat.PRIORITY_MAX).setVibrate(new long[0]);
    }

    @Override
    public void run() {

        final NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this.context);

        mBuilder.setColor(Color.rgb(160, 52, 6));
        mBuilder2.setColor(Color.rgb(160,52,6));
        mBuilder3.setColor(Color.rgb(160,52,6));
        mBuilder4.setColor(Color.rgb(160,52,6));
        mBuilder.setUsesChronometer(true);
        mBuilder2.setUsesChronometer(true);
        mBuilder3.setUsesChronometer(true);
        mBuilder4.setUsesChronometer(true);


        while (!flag && limit != 0) {
            int type = rnd.nextInt(7) + 1;
            if (type == 1) {
                int fbLevel = rnd.nextInt(2) + 1;
                fbLevel =1;
                if(fbLevel==2){
                //notify access FB
                    counter++;
                    log(counter+counter2+counter3+counter4 +"");
                    notificationManager.cancel(2);
                    mBuilder4.setWhen(System.currentTimeMillis());
                    notificationManager.notify(4, mBuilder4.build());
                    scheduleCancel(4,notificationManager);
                    delay(5,10);

                }else if (fbLevel == 1) {
                    //notify access FB
                    limit = limit -2;
                    counter++;
                    log(counter+counter2+counter3+counter4 +"");
                    notificationManager.cancel(2);
                    mBuilder4.setWhen(System.currentTimeMillis());
                    notificationManager.notify(4, mBuilder4.build());

                    // wait
                    delay(10,15);

                    //notify FB processing
                    counter2++;
                    log(counter+counter2+counter3+counter4 +"");
                    notificationManager.cancel(4);
                    mBuilder3.setWhen(System.currentTimeMillis());
                    notificationManager.notify(3, mBuilder3.build());
                    scheduleCancel(3,notificationManager);
                }
            } else if (type == 3) {
                int scLevel = rnd.nextInt(2) + 1;
                scLevel =1;
                if(scLevel == 2){
                //notify access SC
                    counter3++;
                    log(counter+counter2+counter3+counter4 +"");
                mBuilder2.setWhen(System.currentTimeMillis());
                notificationManager.cancel(4);
                notificationManager.notify(2, mBuilder2.build());
                scheduleCancel(2,notificationManager);
                delay(5,10);
                }else if (scLevel == 1) {
                    limit = limit -2;
                    counter3++;
                    log(counter+counter2+counter3+counter4 +"");
                    mBuilder2.setWhen(System.currentTimeMillis());
                    notificationManager.cancel(4);
                    notificationManager.notify(2, mBuilder2.build());

                    // wait
                    delay(10,15);

                    //notify SC processing
                    counter4++;
                    log(counter+counter2+counter3+counter4 +"");
                    notificationManager.cancel(2);
                    mBuilder.setWhen(System.currentTimeMillis());
                    notificationManager.notify(1, mBuilder.build());
                    scheduleCancel(1,notificationManager);
                }
            } else  {

                //wait
                delay(12,18);
            }
        }

    }

    //Generating a random delay.
    private void delay(int min, int max) {
        int delay = rnd.nextInt(max+1 - min) + min;
        try {
            Thread.sleep(delay*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //Randomly schedule canceling a notification for a specified notification.
    private void scheduleCancel(final int id, final NotificationManagerCompat manager){
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                manager.cancel(id);
            }
        }, ((10)+2)*1000);
    }

    //Setting sound and vibration preferences according to toggle buttons.
    public void switchToggled(boolean sound, boolean vibration){
        if(sound && vibration){
            mBuilder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE).setVibrate(new long[]{500, 500, 500, 500});
            mBuilder2.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE).setVibrate(new long[]{500, 500, 500, 500});
            mBuilder3.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE).setVibrate(new long[]{500, 500, 500, 500});
            mBuilder4.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE).setVibrate(new long[]{500, 500, 500, 500});
        } else if(sound && !vibration){
            mBuilder.setDefaults(Notification.DEFAULT_SOUND).setVibrate(new long[0]);
            mBuilder2.setDefaults(Notification.DEFAULT_SOUND).setVibrate(new long[0]);
            mBuilder3.setDefaults(Notification.DEFAULT_SOUND).setVibrate(new long[0]);
            mBuilder4.setDefaults(Notification.DEFAULT_SOUND).setVibrate(new long[0]);
        }else if(!sound && vibration){
            mBuilder.setDefaults(Notification.DEFAULT_VIBRATE).setVibrate(new long[]{500, 500, 500, 500});
            mBuilder2.setDefaults(Notification.DEFAULT_VIBRATE).setVibrate(new long[]{500, 500, 500, 500});
            mBuilder3.setDefaults(Notification.DEFAULT_VIBRATE).setVibrate(new long[]{500, 500, 500, 500});
            mBuilder4.setDefaults(Notification.DEFAULT_VIBRATE).setVibrate(new long[]{500, 500, 500, 500});
        }else {
            mBuilder.setDefaults(Notification.BADGE_ICON_LARGE).setVibrate(new long[0]);
            mBuilder2.setDefaults(Notification.BADGE_ICON_LARGE).setVibrate(new long[0]);
            mBuilder3.setDefaults(Notification.BADGE_ICON_LARGE).setVibrate(new long[0]);
            mBuilder4.setDefaults(Notification.BADGE_ICON_LARGE).setVibrate(new long[0]);
        }
    }

    //This function saves the number of nudges have been displayed in a text file in the app directory.
    public void log(String text)
    {
        File logFile = new File(this.context.getFilesDir(),"log.txt");
        if (!logFile.exists())
        {
            try
            {
                logFile.createNewFile();
            }
            catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        try
        {   logFile.delete();
            logFile.createNewFile();
            BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
            buf.write(text);
            buf.flush();
            buf.close();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void interrupt() {
        this.flag = true;
        super.interrupt();
    }
}