package com.example.tiber.trackersupervisor;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.provider.Telephony;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tiber.trackersupervisor.Clase.AsyncRequests.MyAsyncTask;
import com.example.tiber.trackersupervisor.Clase.ServerConnection;
import com.example.tiber.trackersupervisor.SharedClasses.Communication.Exceptions.KeyNotMappedException;
import com.example.tiber.trackersupervisor.SharedClasses.Communication.RequestedAction;
import com.example.tiber.trackersupervisor.SharedClasses.Objects.Client;
import com.example.tiber.trackersupervisor.SharedClasses.Objects.LocationData;
import com.example.tiber.trackersupervisor.SharedClasses.Utils.DateUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback
                    ,View.OnClickListener{

    private GoogleMap mMap;
    protected Client selectedClient;
    Button btnRfreshLast;
    Geocoder geocoder;
    TextView tvAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        String selectedClientJson = getIntent().getStringExtra("selectedClient");
        selectedClient = new Gson().fromJson(selectedClientJson,Client.class);
        this.setTitle(selectedClient.getName());
        btnRfreshLast = (Button) findViewById(R.id.btnRefreshLast);
        btnRfreshLast.setOnClickListener(this);
        tvAddress = (TextView) findViewById(R.id.tvAddress);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        /*
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        */
        new GetLatestLocationOfClientAsync(this).execute();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnRefreshLast:
                new GetLatestLocationOfClientAsync(this).execute();
                break;
            default:
                Toast.makeText(this,"MapActivity::onClick switch default....",Toast.LENGTH_SHORT).show();
        }
    }


    class GetLatestLocationOfClientAsync extends MyAsyncTask {

        public GetLatestLocationOfClientAsync(Context context) {
            super(context);
        }


        private LocationData locationData = null;
        @Override
        protected Void doInBackground(Void... params) {
            ServerConnection<Client,LocationData> connection =
                    new ServerConnection<Client,LocationData>(context);

            try {
                locationData = connection.execute(RequestedAction.GET_LATEST_LOCATION_OF_CLIENT,selectedClient);
            } catch (KeyNotMappedException e) {
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(locationData == null ){
                Toast.makeText(context,"Problems getting location data",Toast.LENGTH_LONG).show();
            }
            else {
                mMap.clear();
                LatLng clientLatestLocation = new LatLng(locationData.getLatitude(), locationData.getLongitude());
                mMap.addMarker(new MarkerOptions().position(clientLatestLocation).title(selectedClient.getName() + "'s latest location"));
                moveToCurrentLocation(clientLatestLocation);
                updateAddress(locationData);
            }
        }
    }

    private void moveToCurrentLocation(LatLng currentLocation)
    {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation,15));
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 1000, null);

    }

    private void updateAddress(LocationData location){

        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this,"Error getting address using geocoder...",Toast.LENGTH_LONG).show();
            return;
        }
        if(addresses.size() > 0) {
            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String knownName = addresses.get(0).getFeatureName();
            String date = (DateUtil.fromIntFormat(location.getDate())).toString();
            String endl = "\n";
            tvAddress.setText(
                    address + endl + city + endl + knownName + endl + date
            );
        }
        else{
            tvAddress.setText("no location yet...");
        }

    }
}
