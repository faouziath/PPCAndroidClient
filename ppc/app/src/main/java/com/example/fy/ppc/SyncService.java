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
import common.Couple;
import common.Message;

public class SyncService extends IntentService {
  private static final int SYNC_DELAY = 2000;
  
  public static final String USER_ID = "USER_ID";
  public static final String COUPLE_ID = "COUPLE_ID";
  public static final String NOTIFICATION_MESSAGE = "NOTIFICATION_MESSAGE";
  public static final int NOTIFICATION_ID = 1;

  private String currentUserId;
  private Couple currentCouple;
  
  public SyncService() {
    super("SyncService");
  }
  
  @Override
  protected void onHandleIntent(Intent intent) {
    if (intent != null) {
      currentUserId = intent.getStringExtra(USER_ID);
      currentCouple = (Couple) intent.getSerializableExtra(COUPLE_ID);

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
    String texte = "default";
    if (message.getSubject() != Message.Subject.NULL) {
      switch (message.getSubject()){
        case TOU_REQUEST:
          texte = "Vous avez une nouvelle demande de T OU !";
          break;
        case TOU_POSITION:
          texte = "Voici ma position mon amour";
          break;
        case TOU_REFUSE:
          texte = "Voici ma position mon amour";
          break;
      }
      notify(texte, message);
    }
  }
  
  private void notify(final String text, Message message) {
    ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).notify(
      NOTIFICATION_ID,
      new Notification.Builder(this)
        .setSmallIcon(R.mipmap.ic_launcher)
        .setContentTitle(getString(R.string.app_name))
        .setContentText(text)
        .setAutoCancel(true)
        .setContentIntent(
                TaskStackBuilder.create(this)
                        .addParentStack(WelcomeActivity.class)
                        .addNextIntent(
                                new Intent(this, WelcomeActivity.class)
                                        .putExtra(NOTIFICATION_MESSAGE, message)
                                        .putExtra("currentCouple", currentCouple)
                                        .putExtra("currentUserId", currentUserId).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        )
                        .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        )
        .build()
    );
  }
}
