package br.com.serttel.semaforosrecife;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.util.JsonReader;
import android.util.JsonToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    //private Semaforo[] semaforos;
    String linkStart, linkNext;
    int limit, total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        /*DEMO SYDNEY, AUSTRALIA

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        **/


        //semaforos = new Semaforo[50];
        try {
            BufferedReader fileReader = new BufferedReader(new InputStreamReader(getAssets().open("dadosRecifeSemaforo.json")));
            JsonReader jsonReader = new JsonReader(fileReader); //classe que reconhece arquivo como json
            parseJSON(jsonReader);
            jsonReader.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        //ponto mais central
        LatLng central = new LatLng(-8.082688,-34.906625);
        mMap.moveCamera(CameraUpdateFactory.zoomTo(12f));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(central));
    }

    public void parseJSON (JsonReader jsonReader){

        String ut = null, loc1 =  null, loc2 = null, func = null, ss = null, sc = null;
        int sem = 0, _id = 0, i = 0;
        double lg = 0, lt = 0;

        try {
            jsonReader.beginObject();

            while(!jsonReader.peek().equals(JsonToken.END_DOCUMENT)) {

                if (jsonReader.peek().equals(JsonToken.NAME)) {
                    String nextName = jsonReader.nextName();

                    if(nextName.equals("records")){
                        //na verdade não faz nada exatamente
                    }

                    else if(nextName.equals("utilizacao")) {
                        /*"records" eh o nome do array de semaforos.
                         * nao sendo ele, ja eh o primeiro atributo de um semaforo
                         */

                        ut = jsonReader.nextString();

                        boolean loop = true;
                        while(loop){

                            String nextNameObj = jsonReader.nextName();

                            if (nextNameObj.equals("localizacao1")){
                                loc1 = jsonReader.nextString();
                            }
                            else if (nextNameObj.equals("localizacao2")){
                                loc2 = jsonReader.nextString();
                            }
                            else if (nextNameObj.equals("funcionamento")){
                                func = jsonReader.nextString();
                            }
                            else if (nextNameObj.equals("sinalsonoro")){
                                ss = jsonReader.nextString();
                            }
                            else if (nextNameObj.equals("semaforo")){
                                sem = jsonReader.nextInt();
                            }
                            else if (nextNameObj.equals("sinalizadorciclista")){
                                sc = jsonReader.nextString();
                            }
                            else if (nextNameObj.equals("Longitude")){
                                lg = jsonReader.nextDouble();
                            }
                            else if (nextNameObj.equals("Latitude")){
                                lt = jsonReader.nextDouble();
                            }
                            else if (nextNameObj.equals("_id")){
                                _id = jsonReader.nextInt();
                                loop = false;
                                //semaforos[i] = new Semaforo(ut, loc1, loc2, func, ss, sem, sc, lg, lt, _id); //cria novo semaforo e adiciona ao array

                                LatLng pos = new LatLng(lt, lg);
                                mMap.addMarker(new MarkerOptions().position(pos).title("Semaforo " +sem));

                                i++;
                            }
                        }
                    }
                    else if(nextName.equals("_links")){
                        jsonReader.beginObject();
                        jsonReader.nextName();
                        linkStart = jsonReader.nextString();
                        jsonReader.nextName();
                        linkNext = jsonReader.nextString();
                        jsonReader.endObject();
                    }
                    else if(nextName.equals("limit")){
                        limit = jsonReader.nextInt();
                    }
                    else if(nextName.equals("total")){
                        total = jsonReader.nextInt();
                    }
                    else{
                        System.out.println("WHAT ARE YOU DOING HERE");
                    }

                }
                else if (jsonReader.peek().equals(JsonToken.BEGIN_ARRAY)) {
                    jsonReader.beginArray();
                }
                else if (jsonReader.peek().equals(JsonToken.BEGIN_OBJECT)){
                    jsonReader.beginObject();
                }
                else if (jsonReader.peek().equals(JsonToken.END_OBJECT)) {
                    jsonReader.endObject();
                }
                else if (jsonReader.peek().equals(JsonToken.END_ARRAY)) {
                    jsonReader.endArray();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}