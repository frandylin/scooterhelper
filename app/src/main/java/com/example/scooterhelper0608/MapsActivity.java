package com.example.scooterhelper0608;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * A demo class that stores and retrieves data objects with each marker.
 */
public class MapsActivity extends AppCompatActivity implements
        GoogleMap.OnMarkerClickListener,
        OnMapReadyCallback {



    private final LatLng Frandy = new LatLng(24.9871, 121.5480);

    private Marker markerSydney;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }



    /** Called when the map is ready. */
    @Override
    public void onMapReady(GoogleMap map) {
        // Add some markers to the map, and add a data object to each marker.

        markerSydney = map.addMarker(new MarkerOptions()
                .position(Frandy)
                .title("停車位置"));
        markerSydney.setTag(0);

        // Move the camera to the map coordinates and zoom in closer.
        map.moveCamera(CameraUpdateFactory.newLatLng(Frandy));
        map.moveCamera(CameraUpdateFactory.zoomTo(12));

        // Set a listener for marker click.
        map.setOnMarkerClickListener(this);
    }

    /** Called when the user clicks a marker. */
    @Override
    public boolean onMarkerClick(final Marker marker) {

        // Retrieve the data from the marker.
        Integer clickCount = (Integer) marker.getTag();

        // Check if a click count was set, then display the click count.
        if (clickCount != null) {
            clickCount = clickCount + 1;
            marker.setTag(clickCount);
            Toast.makeText(this,
                    marker.getTitle() +
                            " has been clicked " + clickCount + " times.",
                    Toast.LENGTH_SHORT).show();
        }

        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false;
    }



}