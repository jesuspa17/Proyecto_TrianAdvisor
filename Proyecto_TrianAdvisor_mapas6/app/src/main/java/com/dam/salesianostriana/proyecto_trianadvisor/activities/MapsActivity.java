package com.dam.salesianostriana.proyecto_trianadvisor.activities;

import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.dam.salesianostriana.proyecto_trianadvisor.MyItem;
import com.dam.salesianostriana.proyecto_trianadvisor.PopupAdapter;
import com.dam.salesianostriana.proyecto_trianadvisor.R;
import com.dam.salesianostriana.proyecto_trianadvisor.Servicio;
import com.dam.salesianostriana.proyecto_trianadvisor.pojos_RetroFit.sitios.Foto;
import com.dam.salesianostriana.proyecto_trianadvisor.pojos_RetroFit.sitios.ResultSitio;
import com.dam.salesianostriana.proyecto_trianadvisor.pojos_RetroFit.sitios.Sitio;
import com.dam.salesianostriana.proyecto_trianadvisor.pojos_RetroFit.valoraciones.Valoracion;
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
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import retrofit.Call;
import retrofit.Response;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, GoogleMap.OnInfoWindowClickListener{

    //***COMENTAR LA CLASE***/

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient = null;
    private Location mCurrentLocation;
    private boolean mRequestingLocationUpdates = true;
    private LocationRequest mLocationRequest;
    private ClusterManager<MyItem> mClusterManager;

    private Marker mi_marker;
    private Map<Marker, ResultSitio> all_marcadores = new HashMap<Marker, ResultSitio>();

    String imagen_user;

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

        mMap.getUiSettings().setZoomControlsEnabled(true);

        mMap.setOnCameraChangeListener(mClusterManager);
        mMap.setOnMarkerClickListener(mClusterManager);
        mMap.setOnInfoWindowClickListener(this);

        mMap.setInfoWindowAdapter(mClusterManager.getMarkerManager());


        /*mi_marker = mMap.addMarker(new MarkerOptions().position(posicion_actual));
        //España
        LatLng lat = new LatLng(37.389092, -5.984459);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lat, 13));*/
        //new ObtenerSitiosCercanosTask().execute(37.389092, -5.984459);
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

      /*mi_marker = mMap.addMarker(new MarkerOptions().position(posicion_actual));
        mi_marker.setTitle("Usted");
        mi_marker.showInfoWindow();*/

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

    //Realiza la consulta que obtiene todos los sitios cercanos a mí.
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

                        //Clustering
                        /*MyItem myItem = new MyItem(posicion.latitude,posicion.longitude);
                        mClusterManager.addItem(myItem);*/

                        String URL_BASE = "{\"sitio\": { \"__type\": \"Pointer\", \"className\": \"sitio\", " +
                                "\"objectId\": \""+obj_id+"\" } }";

                        String url_valoraciones = "";

                        try {
                            url_valoraciones = URLEncoder.encode(URL_BASE, "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }

                        all_marcadores.put(marker, new ResultSitio(obj_id, nom, direccion, foto));

                        mClusterManager.getMarkerCollection().setOnInfoWindowAdapter(new PopupAdapter(MapsActivity.this, getLayoutInflater(), all_marcadores));

                        new ObtenerValoracionUnSitioTask().execute(url_valoraciones);
                    }
                }
            }
        }
    }

    //Devuelve las valoraciones de un sitio dado el objectId
    private class ObtenerValoracionUnSitioTask extends AsyncTask<String, Void, Valoracion> {
        @Override
        protected Valoracion doInBackground(String... params) {
            if (params != null) {

                Call<Valoracion> valoracionCall = Servicio.instanciarServicio().cargarValoracionesUnSitio(params[0]);
                Response<Valoracion> result = null;
                try {
                    result = valoracionCall.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                assert result != null;

                if(result.code() == 200 || result.code() == 201){
                    return result.body();
                }else{
                    return null;
                }
            } else {
                return null;
            }
        }
        @Override
        protected void onPostExecute(Valoracion valoracion) {
            super.onPostExecute(valoracion);

            float total_valoraciones = 0;
            if (valoracion != null) {
                for (int i = 0; i < valoracion.getResults().size(); i++) {
                    if(valoracion.getResults().get(i).getValoracion()!=null){
                        total_valoraciones += valoracion.getResults().get(i).getValoracion();
                    }
                }
                total_valoraciones = total_valoraciones / valoracion.getResults().size();
            }

           //mClusterManager.getMarkerCollection().setOnInfoWindowAdapter(new PopupAdapter(MapsActivity.this, getLayoutInflater(), all_marcadores, total_valoraciones));
           //mMap.setInfoWindowAdapter(new PopupAdapter(MapsActivity.this,getLayoutInflater(), all_marcadores,total_valoraciones));
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

}
