package com.example.fy.ppc;

import android.os.Bundle;
import android.view.View;

import java.util.List;

import client.ClientActivity;
import client.ClientUISetting;
import common.ActionReal;
import common.Historique;
import common.Message;
import common.User;

public class ActionProcessActivity extends ClientActivity implements View.OnClickListener{
    public int currentHistPos;
    public Historique currentHist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_process);
        ClientUISetting.setActionARInfo(this);
    }
    @Override
    protected  void onReceive(Message response)
    {
        switch (response.getSubject()){
            case UPDATE_AR:


                break;
        }
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.btnAccept){
            ClientUISetting.sendUpdateAR(this, "VALIDER");
        }
        else if(v.getId() == R.id.btnRefuse){
            ClientUISetting.sendUpdateAR(this, "REFUSE");
        }

    }

}
