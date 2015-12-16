package com.dam.salesianostriana.proyecto_trianadvisor.activities;

import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.dam.salesianostriana.proyecto_trianadvisor.R;
import com.dam.salesianostriana.proyecto_trianadvisor.Servicio;
import com.dam.salesianostriana.proyecto_trianadvisor.pojos_RetroFit.sitios.ResultSitio;
import com.dam.salesianostriana.proyecto_trianadvisor.pojos_RetroFit.sitios.Sitio;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;

import retrofit.Call;
import retrofit.Response;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient = null;
    private Location mCurrentLocation;
    private boolean mRequestingLocationUpdates = true;
    private LocationRequest mLocationRequest;

    private Marker mi_marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Instancia un objeto de tipo GoogleApiClient
        instanciarGoogleApiClient();

        //Activa la detección de localización
        detectarLocalizacion();


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        /*mi_marker = mMap.addMarker(new MarkerOptions().position(posicion_actual));
        //España
        LatLng lat = new LatLng(37.389092, -5.984459);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lat, 13));*/
       //new ObtenerSitiosCercanosTask().execute(37.389092, -5.984459);
    }


    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
        updateUI();

    }

    private void updateUI() {

        LatLng posicion_actual = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
        mi_marker = mMap.addMarker(new MarkerOptions().position(posicion_actual));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(posicion_actual, 13));
        new ObtenerSitiosCercanosTask().execute(posicion_actual.latitude,posicion_actual.longitude);

    }

    protected synchronized void instanciarGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                        // Indico que la API que voy a utilizar
                        // dentro de Google Play Services, es la API
                        // del Servicio de Localización
                .addApi(LocationServices.API)
                .build();

    }
    protected void detectarLocalizacion() {
        mLocationRequest = new LocationRequest();
        // Intervalo de uso normal de la la aplicación
        mLocationRequest.setInterval(10000);
        // Interval de una app que requiera una localización exhaustiva
        mLocationRequest.setFastestInterval(30000);
        // GPS > mejor método de localización / consume más batería
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    protected void startLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
        mRequestingLocationUpdates = true;
        Log.i("POSICION", "POSICION: startLocationUpdates");
    }

    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
        mRequestingLocationUpdates = false;
        Log.i("POSICION", "POSICION: stopLocationUpdates");
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.i("POSICION", "POSICION: onConnected");
        if (mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected() && mRequestingLocationUpdates) {
            stopLocationUpdates();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mGoogleApiClient.isConnected() && !mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    private class ObtenerSitiosCercanosTask extends AsyncTask<Double, Void, Sitio> {

        @Override
        protected Sitio doInBackground(Double... params) {
            if(params!=null){
                Call<Sitio> sitioCall = Servicio.instanciarServicio().obtenerSitiosCercanos(params[0],params[1]);
                Response<Sitio> sitioResponse = null;
                try {
                    sitioResponse = sitioCall.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                assert sitioResponse != null;
                return sitioResponse.body();

            }else{

                return null;
            }
        }

        @Override
        protected void onPostExecute(Sitio sitios) {
            super.onPostExecute(sitios);

            for(int i = 0;i<sitios.getResultSitioses().size();i++){

                ResultSitio sitio_actual = sitios.getResultSitioses().get(i);

                if(sitio_actual.getNombre()!=null) {

                    double latitud = sitio_actual.getCoordenadas().getLatitude();
                    double longitud = sitio_actual.getCoordenadas().getLongitude();

                    LatLng posicion = new LatLng(latitud,longitud);

                    Marker marker = mMap.addMarker(new MarkerOptions()
                            .position(posicion)
                            .title(sitio_actual.getNombre()));

                    if(sitio_actual.getCategoria().equals("Bares")){
                        marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_bar));
                    }else if(sitio_actual.getCategoria().equals("Restaurantes")){
                        marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_restaurante));
                    }
                }

            }

        }
    }
}
