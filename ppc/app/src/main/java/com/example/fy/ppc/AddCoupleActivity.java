package com.example.fy.ppc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

<<<<<<< HEAD:ppc/app/src/main/java/com/example/fy/ppc/AddCoupleActivity.java
public class AddCoupleActivity extends AppCompatActivity {
=======
import client.ClientActivity;
import common.Message;


public class StartedActivity extends ClientActivity {
>>>>>>> 748b5f009d0616cda2b229f3aa216bdb5e404ef6:ppc/app/src/main/java/com/example/fy/ppc/StartedActivity.java

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
<<<<<<< HEAD:ppc/app/src/main/java/com/example/fy/ppc/AddCoupleActivity.java
        setContentView(R.layout.activity_addcouple);
=======
        setContentView(R.layout.activity_started);
        int flag =0;

        if(flag == 0){
            synch("id1");
            sendToUser("id2","id1","BODDDYYY");
        }
        else{
            synch("id2");
            sendToUser("id1","id2","BODDDYYY COPAIN");
    }
    }

    protected void sendToUser(String toP, String fromP,String bodyP ){
        periodicSendReceive(new Message(Message.Subject.P2P,toP,fromP,bodyP),1000);
    }

    protected void synch(String idparam){
        periodicSendReceive(new Message(Message.Subject.SYNC,idparam),1000);
    }

    @Override
    protected void onReceive(Message message){
        System.out.println("MESSaGE ENTRANT");
        if(message.getSubject() == Message.Subject.P2P)
        System.out.println(message.getBody()+ " from " + message.getFrom());

>>>>>>> 748b5f009d0616cda2b229f3aa216bdb5e404ef6:ppc/app/src/main/java/com/example/fy/ppc/StartedActivity.java
    }
}
