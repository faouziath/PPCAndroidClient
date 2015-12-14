package com.example.fy.ppc;

import android.os.Bundle;
import android.view.View;

import client.ClientActivity;
import client.ClientUISetting;
import common.Message;
import common.User;

public class ActionProcessActivity extends ClientActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_process);
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

    }

}
