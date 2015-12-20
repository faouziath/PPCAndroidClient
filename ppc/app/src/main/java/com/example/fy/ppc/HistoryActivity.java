package com.example.fy.ppc;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import client.ClientActivity;
import client.MyAdapter;
import common.Couple;
import common.Historique;
import common.Message;

public class HistoryActivity extends ClientActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    public  String currentUserId;
    public Couple currentCouple;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        List<String> list = new ArrayList<String>();
        list.add("aaa");
        list.add("dfd");
        list.add("aaa");
        list.add("aaa");
        list.add("dfd");
        list.add("aaa");
        list.add("aaa");
        list.add("dfd");
        list.add("aaa");
        GridView gridview = (GridView) findViewById(R.id.gridView);
        gridview.setAdapter(new MyAdapter(this, list));

    }

    @Override
    protected  void onReceive(Message response)
    {
        switch (response.getSubject()){
            case HISTORIQUE:
                // references to our text
                //private String[] texts = {"aaa", "bbb", "ccc", "ddd", "eee", "fff", "eee", "hhh", "iii"};
                List<String> list = new ArrayList<String>();
                list.add("aaa");
                list.add("dfd");
                list.add("aaa");
                //List<String> list = ((Historique)response.getBody()).toStringList();
                GridView gridview = (GridView) findViewById(R.id.gridView);
                gridview.setAdapter(new MyAdapter(this,list));
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View v,
                            int position, long id) {
        if(v.getId() == R.id.ConnectButtonId){
            Toast.makeText(HistoryActivity.this, "" + position,
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.ConnectButtonId){
        }

    }
}
