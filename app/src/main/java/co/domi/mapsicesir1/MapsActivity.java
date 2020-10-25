package co.domi.mapsicesir1;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.annotation.SuppressLint;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.SphericalUtil;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private String user;
    private String password;
    private Marker marker;
    private ArrayList<Marker> markers;

    private LocationManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        user = getIntent().getExtras().getString("user");
        password = getIntent().getExtras().getString("password");
        markers= new ArrayList<>();


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        manager = (LocationManager) getSystemService(LOCATION_SERVICE);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @SuppressLint("MissingPermission") 
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000,3,this);

        setInitialPos();

        mMap.setOnMapClickListener(this);
        mMap.setOnMapLongClickListener(this);
        mMap.setOnMarkerClickListener(this);
    }


    @SuppressLint("MissingPermission")
    public void setInitialPos(){
        Location location=manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if(location !=null) {
            updateMyLocation(location);
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        updateMyLocation(location);

        
    }

    public void updateMyLocation(Location location) {
        LatLng mypos = new LatLng(location.getLatitude(), location.getLongitude());
        if(marker==null) {

            marker= mMap.addMarker(new MarkerOptions().position(mypos).title("mi posición"));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mypos,17));
        }else{
            marker.setPosition(mypos);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mypos,17));
        }
    }

    private void computedDistance(){
        for(int i=0 ; i<markers.size() ; i++ ){
            Marker point=markers.get(i);
            LatLng markerLoc = point.getPosition();
            LatLng myLoc = marker.getPosition();

            double meters = SphericalUtil.computeDistanceBetween(markerLoc,myLoc);
            if(meters<100){
                //cambia el texview de los metros para el marcador de hueco mas cercano
            }

        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onMapClick(LatLng latLng) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,16));
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
       Marker p= mMap.addMarker(new MarkerOptions().position(latLng).title("Marcador").snippet("Este es un marcador de prueba"));
       markers.add(p);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Toast.makeText(this,marker.getPosition().latitude+ ", "+ marker.getPosition().longitude,Toast.LENGTH_LONG).show();
        marker.showInfoWindow();
        return false;
    }
}