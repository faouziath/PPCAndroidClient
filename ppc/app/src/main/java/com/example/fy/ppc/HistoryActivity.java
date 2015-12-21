package com.example.fy.ppc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import client.ClientActivity;
import client.ClientUISetting;
import client.MyAdapter;
import common.ActionReal;
import common.Couple;
import common.Historique;
import common.Message;

public class HistoryActivity extends ClientActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    public  String currentUserId;
    public Couple currentCouple;
    public Historique currentHist;
    public List<String> currentHistString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ClientUISetting.setGrid(this);
    }

    @Override
    protected  void onReceive(Message response)
    {
        System.out.println("dfdfd");
        System.out.println(response == null);
        switch (response.getSubject()){
            case HISTORIQUE:
                // references to our text
                currentHist = (Historique)response.getBody();
                List<String> list =((Historique)response.getBody()).toStringList();
                currentHistString = list;
                GridView gridview = (GridView) findViewById(R.id.gridView);
                gridview.setAdapter(new MyAdapter(this, list));
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View v,
                            int position, long id) {
        Intent intent = new Intent(this, ActionProcessActivity.class);
        intent.putExtra("currentHistPos", position);
        intent.putExtra("currentHist", currentHist);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.ConnectButtonId){
        }

    }
}
