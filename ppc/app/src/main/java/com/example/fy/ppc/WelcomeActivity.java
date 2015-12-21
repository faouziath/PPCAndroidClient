package com.example.fy.ppc;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import client.ClientActivity;
import client.ClientUISetting;
import common.Couple;
import common.Message;

public class WelcomeActivity extends ClientActivity implements
        View.OnClickListener {

    private static final String TAG = WelcomeActivity.class.getSimpleName();
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;

    public String currentUserId;
    public Couple currentCouple;
    public String partnerUserId;

    @Override
    protected synchronized void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        ClientUISetting.initComponent(this, ClientUISetting.ActivityType.WELCOME);
        ClientUISetting.setWelcomInfo(this);
        startService(new Intent(this, SyncService.class)
                        .putExtra(SyncService.USER_ID, currentUserId)
                        .putExtra(SyncService.COUPLE_ID, currentCouple)
        );
        partnerUserId = currentCouple.getPatner(currentUserId);
        Message mess = new Message(Message.Subject.GET_COUPLE, currentCouple);
        periodicSendReceive(mess, 2000);

    }

    private Message getNotificationMessage() {
        return (Message) getIntent().getSerializableExtra(SyncService.NOTIFICATION_MESSAGE);
    }

    @Override
    protected void onReceive(Message response) {
        switch (response.getSubject()) {
            case CONNECT:
                break;
            case GET_COUPLE:
                currentCouple = (Couple) response.getBody();
                TextView point1 = (TextView) this.findViewById(R.id.PointMonsieurId);
                point1.setText(Integer.toString(currentCouple.GetPCpartener1()));
                TextView point2 = (TextView) this.findViewById(R.id.PointMadameId);
                point2.setText(Integer.toString(currentCouple.GetPCpartener2()));
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ActionButton) {
            Intent intent = new Intent(this, AddActionreal.class);
            intent.putExtra("currentCouple", currentCouple);
            intent.putExtra("currentUserId", currentUserId);
            startActivity(intent);
        } else if (v.getId() == R.id.HistoryButton) {
            Intent intent = new Intent(this, HistoryActivity.class);
            intent.putExtra("currentCouple", currentCouple);
            intent.putExtra("currentUserId", currentUserId);
            startActivity(intent);
        } else if (v.getId() == R.id.TOUButton) {
            String partnerUserId = currentCouple.getPatner(currentUserId);
            sendReceive(new Message(Message.Subject.TOU_REQUEST, currentUserId, partnerUserId, null));
        }
    }





    @Override
    protected synchronized void onStart() {
        super.onStart();

    }

    @Override
        protected synchronized void onResume() {
            super.onResume();
            System.out.println("ON RESUME");
            Message message = getNotificationMessage();
            if (message != null) {
                handleMessage(message);
            }
    }




    private void handleMessage(Message message) {
        switch (message.getSubject()) {
            case TOU_ACK:
                Toast.makeText(getApplicationContext(),
                        "TOU ACK", Toast.LENGTH_LONG)
                        .show();
                break;
            case TOU_REQUEST:
                Toast.makeText(getApplicationContext(),
                        "NOUVELLE DEMANDE DE TOU", Toast.LENGTH_LONG)
                        .show();
                AlertDialog alertDialog = new AlertDialog.Builder(WelcomeActivity.this).create();
                alertDialog.setTitle("Alert");
                alertDialog.setMessage("TA CHERIE VEUT SAVOIR OU TU ES");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "ENVOYER POSITION",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                Intent intentR = new Intent(WelcomeActivity.this ,MainActivity.class);
                                intentR.putExtra("partnerUserId", partnerUserId);
                                intentR.putExtra("currentUserId", currentUserId);
                                startActivity(intentR);


                                dialog.dismiss();
                            }
                        });
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "MENSONGE",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                launchPiker();
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
                break;
            case TOU_REFUSE:
                ArrayList lalisteR = (ArrayList) message.getBody();
                double latitudeR = (double) lalisteR.get(0);
                double longitudeR = (double) lalisteR.get(1);
                Intent intentR = new Intent(WelcomeActivity.this, LaMap.class);
                intentR.putExtra("latitude", latitudeR);
                intentR.putExtra("longitude", longitudeR);
                intentR.putExtra("currentCouple", currentCouple);
                intentR.putExtra("currentUserId", currentUserId);
                startActivity(intentR);

                break;


            case TOU_POSITION:
                ArrayList laliste = (ArrayList) message.getBody();
                double latitude = (double) laliste.get(0);
                double longitude = (double) laliste.get(1);
                String txt = "latitude: " + latitude + "longitude: " + longitude;
                Toast.makeText(getApplicationContext(), txt
                        , Toast.LENGTH_LONG)
                        .show();

                Intent intent = new Intent(WelcomeActivity.this, LaMap.class);
                intent.putExtra("latitude", latitude);
                intent.putExtra("longitude", longitude);
                intent.putExtra("currentCouple", currentCouple);
                intent.putExtra("currentUserId", currentUserId);
                startActivity(intent);


        }
    }

    protected void launchPiker() {

        Intent intent = new Intent(WelcomeActivity.this, Picker.class);
        intent.putExtra("userId", currentUserId);
        intent.putExtra("partenaireId", partnerUserId);
        startActivity(intent);
    }


}


