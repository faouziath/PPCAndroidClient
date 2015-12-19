package com.example.fy.ppc;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationListener;

import java.util.ArrayList;

import client.ClientActivity;
import common.Message;


public class Geolocalisation extends ClientActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {
    // LogCat tag
    private static final String TAG = Geolocalisation.class.getSimpleName();
    private String userId ;
    private String partenaireUserId ;

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;

    private Location mLastLocation;

    // Google client to interact with Google API
    private GoogleApiClient mGoogleApiClient;

    // boolean flag to toggle periodic location updates
    // private boolean mRequestingLocationUpdates = false;

     private LocationRequest mLocationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

      //DEBUG
        int flag =0;
        if(flag==0) {
            userId = "id1";
            partenaireUserId = "id2";
        }
        else
        {
            userId="id2";
            partenaireUserId="id1";
        }
        //FIN DEBUG

        setContentView(R.layout.activity_geolocalisation);
        if (checkPlayServices()) {
            // Building the GoogleApi client

            buildGoogleApiClient();

        }
        addListenerOnButton();
        periodicSendReceive(new Message(Message.Subject.SYNC, userId), 2000);

        startService(new Intent(this, SyncService.class).putExtra(SyncService.USER_ID, userId));

        Message message = getNotificationMessage();
        if (message != null) {
            // ...
        }
        };

    private Message getNotificationMessage() {
        return (Message) getIntent().getSerializableExtra(SyncService.NOTIFICATION_MESSAGE);
    }

    public void addListenerOnButton() {

        Button button = (Button) findViewById(R.id.btnTOU);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                sendReceive(new Message(Message.Subject.TOU_REQUEST, userId, partenaireUserId, null));
            }

        });

    }

    /**
     * Creating location request object
     * */
    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setSmallestDisplacement(10); // 10 meters
    }

    /**
     * Starting the location updates
     * */
    protected void startLocationUpdates() {
        if(mLocationRequest== null)
            createLocationRequest();
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);

    }

    /**
     * Stopping location updates
     */
    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
    }

    /**
     * Method to send the location
     * */
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

    protected void sendToUser(String toP, String fromP,ArrayList bodyP ){
        sendReceive(new Message(Message.Subject.P2P,toP,fromP,bodyP));
    }

    @Override
    protected void onReceive(Message message) {
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
                AlertDialog alertDialog = new AlertDialog.Builder(Geolocalisation.this).create();
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
                                    sendReceive(new Message(Message.Subject.TOU_POSITION, userId, partenaireUserId, list));
                                    }
                                dialog.dismiss();
                                }
                            });
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "MENSONGE",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            sendReceive(new Message(Message.Subject.TOU_REFUSE,userId, partenaireUserId,null));
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

                Intent intent = new Intent(Geolocalisation.this, LaMap.class);
                intent.putExtra("latitude",latitude );
                intent.putExtra("longitude", longitude);
                startActivity(intent);

                }


    }

    /**
     * Creating google api client object
     * */
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
    }

    /**
     * Method to verify google play services on the device
     * */
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
    }

    /**
     * Google api callback methods
     */
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


}