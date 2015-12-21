package com.example.fy.ppc;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import client.ClientActivity;
import client.ClientUISetting;
import common.ActionReal;
import common.Couple;
import common.Historique;
import common.Message;
import common.User;

public class ActionProcessActivity extends ClientActivity implements View.OnClickListener{
    public  String currentUserId;
    public Couple currentCouple;
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
                boolean answerStatus =(boolean)response.getBody();
                if(answerStatus){
                    Toast.makeText(ActionProcessActivity.this, "" + "Sucess" ,
                            Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(ActionProcessActivity.this, "" + "Failure" ,
                            Toast.LENGTH_SHORT).show();
                }
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
        else if(v.getId() == R.id.btnRefuse){
            ClientUISetting.sendUpdateAR(this, "REFUSE");
        }

    }

}
