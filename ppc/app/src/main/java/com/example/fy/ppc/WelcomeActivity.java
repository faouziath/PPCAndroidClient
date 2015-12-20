package com.example.fy.ppc;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;

import client.ClientActivity;
import client.ClientUISetting;
import common.Couple;
import common.Message;

public class WelcomeActivity  extends ClientActivity implements
        View.OnClickListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private static final String TAG = WelcomeActivity.class.getSimpleName();
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;

    public String currentUserId;
    public Couple currentCouple;
    public String partnerUserId;

    private Location mLastLocation;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ClientUISetting.initComponent(this, ClientUISetting.ActivityType.WELCOME);
        ClientUISetting.setWelcomInfo(this);
        startService(new Intent(this, SyncService.class)
                .putExtra(SyncService.USER_ID, currentUserId)
                .putExtra(SyncService.COUPLE_ID, currentCouple)
        );

        if (checkPlayServices()) {
            buildGoogleApiClient();
        }

        partnerUserId = currentCouple.getPatner(currentUserId);
    }

    private Message getNotificationMessage() {
        return (Message) getIntent().getSerializableExtra(SyncService.NOTIFICATION_MESSAGE);
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

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "This device is not supported.", Toast.LENGTH_LONG)
                        .show();
                finish();
            }
            return false;
        }
        return true;
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkPlayServices();

        Message message = getNotificationMessage();
        if (message != null) {
            handleMessage(message);
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = "
                + result.getErrorCode());
    }

    @Override
    public void onConnected(Bundle arg0) {
        startLocationUpdates();

        stopLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int arg0) {
        mGoogleApiClient.connect();
    }
    @Override
    public void onLocationChanged(Location location) {
        // Assign the new location
        mLastLocation = location;

        Toast.makeText(getApplicationContext(), "Location changed!",
                Toast.LENGTH_SHORT).show();


    }

    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setSmallestDisplacement(10); // 10 meters
    }

    protected void startLocationUpdates() {
        if(mLocationRequest== null)
            createLocationRequest();
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);

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
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "ENVOYER POSITOIN",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (checkPlayServices()) {
                                    // Building the GoogleApi client
                                    startLocationUpdates();
                                    stopLocationUpdates();
                                    ArrayList list = getLocation();
                                    // Une fois connecte a l'API DE GOOGLE, on lance l'envoi d'ici !!
                                    sendReceive(new Message(Message.Subject.TOU_POSITION, currentUserId, partnerUserId, list));
                                }
                                dialog.dismiss();
                            }
                        });
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "MENSONGE",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                launchPiker();
                                // sendReceive(new Message(Message.Subject.TOU_REFUSE,userId, partenaireUserId,null));
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
                break;
            case TOU_REFUSE:
                Toast.makeText(getApplicationContext(),
                        "INDISPONIBLE POUR LE MOMENT", Toast.LENGTH_LONG)
                        .show();
                break;

            case TOU_POSITION:
                ArrayList laliste = (ArrayList)message.getBody();
                double latitude = (double) laliste.get(0);
                double longitude = (double) laliste.get(1);
                String txt = "latitude: " + latitude + "longitude: " + longitude;
                Toast.makeText(getApplicationContext(),txt
                        , Toast.LENGTH_LONG)
                        .show();

                Intent intent = new Intent(WelcomeActivity.this, LaMap.class);
                intent.putExtra("latitude",latitude );
                intent.putExtra("longitude", longitude);
                startActivity(intent);

        }
    }

    protected void launchPiker(){
        Picker pik = null;
        Intent intent = new Intent(pik, Picker.class);
        intent.putExtra("userId",currentUserId);
        intent.putExtra("partenaireId",partnerUserId);
        startActivity(intent);
    }

    private ArrayList getLocation() {

        mLastLocation = LocationServices.FusedLocationApi
                .getLastLocation(mGoogleApiClient);

        if (mLastLocation != null) {
            double latitude = mLastLocation.getLatitude();
            double longitude = mLastLocation.getLongitude();
            System.out.println("ca se passe ici bro");
            System.out.println(latitude);
            System.out.println(longitude);
            ArrayList list = new ArrayList();
            list.add(latitude);
            list.add(longitude);

            return list;

        } else {
            System.out.println("SYSTEME FUCK");
        }
        return null;
    }
}
