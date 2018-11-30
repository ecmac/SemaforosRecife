package br.com.serttel.semaforosrecife;

import android.app.AlertDialog;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements GoogleMap.OnMarkerClickListener,
        OnMapReadyCallback {

    private GoogleMap mMap;
    private Semaforo[] semaforos;
    String linkStart, linkNext;
    int limit, total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this); }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;



        NetworkThread networkThread =
                new NetworkThread(linkStart, linkNext, limit, total, getApplicationContext(), this);
        networkThread.start();
        try {
            networkThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        semaforos = networkThread.getSemaforos();
        linkStart = networkThread.getLinkStart();
        linkNext = networkThread.getLinkNext();
        limit = networkThread.getLimit();
        total = networkThread.getTotal();

        /**System.out.println("TO STRING DO ARRAY DE SEMAFOROS DEPOIS DA THREAD");
        System.out.println(semaforos.toString());**/

        addToMap();

        //ponto mais central
        LatLng central = new LatLng(-8.082688,-34.906625);
        mMap.moveCamera(CameraUpdateFactory.zoomTo(12f));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(central));

        mMap.setOnMarkerClickListener(this);
    }



    @Override
    public boolean onMarkerClick(final Marker marker){

        Integer id = (Integer) marker.getTag();

        AlertDialog.Builder builder =  new AlertDialog.Builder(this);
        builder.setMessage(semaforos[id].prettyInfo())
                .setTitle("Informacoes do semaforo");
        AlertDialog dialog = builder.create();
        dialog.show();

        return false;
    }

    public void addToMap(){

        for(int i=0; i < 50; i++){
            LatLng pos = new LatLng(semaforos[i].getLatidude(), semaforos[i].getLongitude());

            Marker marker = mMap.addMarker
                    (new MarkerOptions().position(pos).title("Semaforo #" + semaforos[i].getSemaforo()));
            marker.setTag(i);
        }

    }

}
