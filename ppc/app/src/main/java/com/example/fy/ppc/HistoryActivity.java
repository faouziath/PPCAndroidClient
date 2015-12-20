package com.example.fy.ppc;

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
import common.Couple;
import common.Historique;
import common.Message;

public class HistoryActivity extends ClientActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    public  String currentUserId;
    public Couple currentCouple;
    public List<String> currentHistorique;
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
                List<String> list =((Historique)response.getBody()).toStringList();
                currentHistorique = list;
                GridView gridview = (GridView) findViewById(R.id.gridView);
                gridview.setAdapter(new MyAdapter(this, list));
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View v,
                            int position, long id) {
            Toast.makeText(HistoryActivity.this, "" + currentHistorique.get(position),
                    Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.ConnectButtonId){
        }

    }
}
