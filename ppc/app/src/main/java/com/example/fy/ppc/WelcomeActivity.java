package com.example.fy.ppc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import client.ClientActivity;
import client.ClientUISetting;
import common.Couple;
import common.Message;

public class WelcomeActivity  extends ClientActivity implements View.OnClickListener{
    public String currentUserId;
    public Couple currentCouple;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ClientUISetting.initComponent(this, ClientUISetting.ActivityType.WELCOME);
        ClientUISetting.setWelcomInfo(this);
    }
    @Override
    protected  void onReceive(Message response)
    {
        switch (response.getSubject()){
            case CONNECT:
                break;
        }
    }
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.ActionButton){
            Intent intent = new Intent(this, AddActionreal.class);
            intent.putExtra("currentCouple", currentCouple);
            intent.putExtra("currentUserId", currentUserId);
            startActivity(intent);
        }
        else if(v.getId() == R.id.HistoryButton){
            Intent intent = new Intent(this, HistoryActivity.class);
            intent.putExtra("currentCouple", currentCouple);
            intent.putExtra("currentUserId", currentUserId);
            startActivity(intent);
        }
        else if (v.getId() == R.id.TOUButton) {
            String partnerUserId = currentCouple.getPatner(currentUserId);
            sendReceive(new Message(Message.Subject.TOU_REQUEST, currentUserId, partnerUserId, null));
        }
    }
}
