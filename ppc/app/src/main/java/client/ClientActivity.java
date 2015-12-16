package client;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;

import common.*;

public class ClientActivity extends Activity {
  protected int receiveCount = 0;
  
  protected void sendReceive(Message message) {
    new SendReceiveTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, message);
  }

  protected void periodicSendReceive(Message message, int delay) {
    new PeriodicSendReceiveTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, message, delay);
  }

  protected void onReceive(Message message) {}

  public class SendReceiveTask extends AsyncTask<Object, Void, Message> {

    @Override
    protected Message doInBackground(Object... objects) {
      Message receivedMessage = null;

      try {
        receivedMessage = new TcpClient().sendReceive((Message) objects[0]);
      }
      catch (Exception e) {
        e.printStackTrace();
      }

      return receivedMessage;
    }

    @Override
    protected void onPostExecute(Message message) {
      ++receiveCount;
      onReceive(message);
    }
  }

  public class PeriodicSendReceiveTask extends AsyncTask<Object, Object, Void> {

    @Override
    protected Void doInBackground(Object... objects) {
      Message receivedMessage = null;

      while(true) {
        SystemClock.sleep((int) objects[1]);

        try {
          receivedMessage = new TcpClient().sendReceive((Message) objects[0]);
        }
        catch (Exception e) {
          e.printStackTrace();
        }

        publishProgress(receivedMessage);
      }
    }
  
    @Override
    protected void onProgressUpdate(Object... objects) {
      ++receiveCount;
      onReceive((Message) objects[0]);
    }
  }
}
