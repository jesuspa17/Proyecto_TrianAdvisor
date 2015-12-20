package com.dam.salesianostriana.proyecto_trianadvisor.activities;

import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.dam.salesianostriana.proyecto_trianadvisor.R;
import com.dam.salesianostriana.proyecto_trianadvisor.Servicio;
import com.dam.salesianostriana.proyecto_trianadvisor.adaptadores.PopupAdapter;
import com.dam.salesianostriana.proyecto_trianadvisor.pojos_RetroFit.sitios.Foto;
import com.dam.salesianostriana.proyecto_trianadvisor.pojos_RetroFit.sitios.ResultSitio;
import com.dam.salesianostriana.proyecto_trianadvisor.pojos_RetroFit.sitios.Sitio;
import com.dam.salesianostriana.proyecto_trianadvisor.pojos_adapter.MyItem;
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
import com.google.maps.android.clustering.ClusterManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import retrofit.Call;
import retrofit.Response;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, GoogleMap.OnInfoWindowClickListener{

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient = null;
    private Location mCurrentLocation;
    private boolean mRequestingLocationUpdates = true;
    private LocationRequest mLocationRequest;
    private ClusterManager<MyItem> mClusterManager;

    private Map<Marker, ResultSitio> all_marcadores = new HashMap<Marker, ResultSitio>();

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


        mClusterManager = new ClusterManager<MyItem>(this, mMap);

        //Se añaden opciones al mapa.
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setOnCameraChangeListener(mClusterManager);
        mMap.setOnMarkerClickListener(mClusterManager);
        mMap.setOnInfoWindowClickListener(this);
        mMap.setInfoWindowAdapter(mClusterManager.getMarkerManager());


    }


    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
        actualizarInterfaz();

    }

    private void actualizarInterfaz() {

        if(mMap!=null){
            mMap.clear();
        }
        mClusterManager.clearItems();

        LatLng posicion_actual = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(posicion_actual, 13));

        new ObtenerSitiosCercanosTask().execute(posicion_actual.latitude, posicion_actual.longitude);
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
    public void onConnectionSuspended(int i) {}

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {}

    //AsyncTask que realiza la consulta que obtiene todos los sitios cercanos a mí y pinta
    //en el mapa cada sitio en forma de marker.
    private class ObtenerSitiosCercanosTask extends AsyncTask<Double, Void, Sitio> {
        @Override
        protected Sitio doInBackground(Double... params) {
            if (params != null) {

                Call<Sitio> sitioCall = Servicio.instanciarServicio().obtenerSitiosCercanos(params[0], params[1]);
                Response<Sitio> sitioResponse = null;
                try {
                    sitioResponse = sitioCall.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                assert sitioResponse != null;
                if(sitioResponse.code() == 200 || sitioResponse.code() == 201){
                    return sitioResponse.body();
                }else{
                    return null;
                }
            } else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(Sitio sitios) {
            super.onPostExecute(sitios);

            if (sitios != null) {

                for (int i = 0; i < sitios.getResultSitioses().size(); i++) {

                    ResultSitio sitio_actual = sitios.getResultSitioses().get(i);

                    if (sitio_actual.getNombre() != null) {

                        double latitud = sitio_actual.getCoordenadas().getLatitude();
                        double longitud = sitio_actual.getCoordenadas().getLongitude();
                        String obj_id = sitio_actual.getObjectId();
                        String nom = sitio_actual.getNombre();
                        String direccion = sitio_actual.getDireccion();
                        Foto foto = sitio_actual.getFoto();

                        LatLng posicion = new LatLng(latitud, longitud);

                        Marker marker = mClusterManager.getMarkerCollection().addMarker(new MarkerOptions()
                                .position(posicion)
                                .title(sitio_actual.getNombre()));

                        if (sitio_actual.getCategoria().equals("Bares")) {
                            marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_bar));
                        } else if (sitio_actual.getCategoria().equals("Restaurantes")) {
                            marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_restaurante));
                        }

                        all_marcadores.put(marker, new ResultSitio(obj_id, nom, direccion, foto));
                        mClusterManager.getMarkerCollection().setOnInfoWindowAdapter(new PopupAdapter(MapsActivity.this, getLayoutInflater(), all_marcadores));

                    }
                }
            }
        }
    }

    @Override
    public void onInfoWindowClick(Marker marker) {

        ResultSitio resultSitio =  all_marcadores.get(marker);
        String obj_id = resultSitio.getObjectId();

        Intent i = new Intent(MapsActivity.this,DetalleActivity.class);
        Log.i("OBJECT_RECIBIDO",obj_id);
        i.putExtra("object_id",obj_id);

        startActivity(i);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent i = new Intent(MapsActivity.this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        this.finish();
    }

}
