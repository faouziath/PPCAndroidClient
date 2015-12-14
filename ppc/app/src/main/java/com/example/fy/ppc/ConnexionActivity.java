package com.example.fy.ppc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import client.ClientActivity;
import client.ClientUISetting;
import common.Couple;
import common.Message;
import common.User;

public class ConnexionActivity extends ClientActivity implements View.OnClickListener {
    public  String currentUserId;
    public Couple currentCouple;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);
        ClientUISetting.initComponent(this, ClientUISetting.ActivityType.CONNEXION);
    }

    @Override
    protected  void onReceive(Message response)
    {
        switch (response.getSubject()){
            case CONNECT:
                ClientUISetting.setUserInfo(this,currentUserId, (Couple) response.getBody());
                break;
        }
    }
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.ConnectButtonId){
            ClientUISetting.connectUser(this);
        }
//        else if(v.getId() == R.id.btnFinished){
//            Intent intent = new Intent(this, AddCoupleActivity.class);
//            //intent.putExtra(EXTRA_MESSAGE, match_selected);
//            startActivity(intent);
//        }
    }
}
