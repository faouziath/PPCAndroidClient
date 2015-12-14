package com.example.fy.ppc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import client.ClientActivity;
import client.ClientUISetting;
import common.Action;
import common.Couple;
import common.Message;

public class AddActionreal extends ClientActivity implements  AdapterView.OnItemSelectedListener,View.OnClickListener  {
    public  String currentUserId;
    public Couple currentCouple;
    public String actionDescToSend;
    public String evaluer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addactionreal);
        ClientUISetting.initComponent(this, ClientUISetting.ActivityType.CREATE_ACTION);
        ClientUISetting.setSpinnerStart(this);
    }

    @Override
    protected  void onReceive(Message response)
    {
        switch (response.getSubject()){
            case ACTIONS:
                ClientUISetting.setSpinnerEnd(this,(List<String>) response.getBody(),R.id.SpinnerAcAR);
                break;
        }
    }
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.SendButtonAR){
            RadioGroup radioBtn = (RadioGroup) this.findViewById(R.id.radioBtn);
            int selectedId = radioBtn.getCheckedRadioButtonId();

            // find the radiobutton by returned id
            RadioButton radioBtnMoi = (RadioButton) this.findViewById(selectedId);
            Toast.makeText(AddActionreal.this, radioBtnMoi.getText(), Toast.LENGTH_SHORT).show();
            if(radioBtnMoi.getText().equals("Moi")) {
                evaluer = currentUserId;
            }
            else{
                evaluer = currentCouple.getPatner(currentUserId);
            }

            ClientUISetting.sendActionR(this);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner sp = (Spinner)parent;
        actionDescToSend = (String)parent.getItemAtPosition(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
