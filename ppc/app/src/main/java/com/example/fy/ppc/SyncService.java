package com.example.fy.ppc;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import client.TcpClient;
import common.Message;

public class SyncService extends IntentService {
  private static final int SYNC_DELAY = 2000;
  
  public static final String USER_ID = "USER_ID";
  public static final int NOTIFICATION_ID = 1;
  
  public SyncService() {
    super("SyncService");
  }
  
  // Runs in a background thread.
  @Override
  protected void onHandleIntent(Intent intent) {
    if (intent != null) {
      while(true) {
        SystemClock.sleep(SYNC_DELAY);
        
        try {
          handleMessage(new TcpClient().sendReceive(
            new Message(Message.Subject.SYNC, intent.getStringExtra(USER_ID))
          ));
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
  }
  
  private void handleMessage(Message message) {
    notify("subject: " + message.getSubject().toString());
  }
  
  private void notify(final String text) {
    ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).notify(
      NOTIFICATION_ID,
      new Notification.Builder(this)
        .setSmallIcon(R.mipmap.ic_launcher)
        .setContentTitle(getString(R.string.app_name))
        .setContentText(text)
        .setContentIntent(
          TaskStackBuilder.create(this)
            .addParentStack(Geolocalisation.class)
            .addNextIntent(new Intent(this, Geolocalisation.class))
            .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        )
        .build()
    );
  }
}
