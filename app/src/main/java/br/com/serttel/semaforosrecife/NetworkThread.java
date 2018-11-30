package br.com.serttel.semaforosrecife;

import android.util.JsonReader;
import android.util.JsonToken;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class NetworkThread extends Thread {

    private GoogleMap mMap;
    private Semaforo[] semaforos;
    private String linkStart;
    private String linkNext;
    private int limit, total;

    public NetworkThread(GoogleMap mMap,String linkStart, String linkNext, int limit, int total){
        this.mMap = mMap;
        this.semaforos = new Semaforo[50];
        this.linkStart = linkStart;
        this.linkNext = linkNext;
        this.limit = limit;
        this.total = total;
    }

    public Semaforo[] getSemaforos() {
        return semaforos;
    }

    public GoogleMap getmMap() {

        return mMap;
    }

    public String getLinkNext() {
        return linkNext;
    }

    public int getTotal() {
        return total;
    }

    public int getLimit() {

        return limit;
    }

    public String getLinkStart() {

        return linkStart;
    }

    private void jsonRequisition() {

        HttpURLConnection httpConnection = null;
        BufferedReader bufferedReader = null;

        boolean isOnline = false;

        while(!isOnline){
            try {
                URL url = new URL("http://desafio.serttel.com.br/dadosRecifeSemaforo.json");
                httpConnection = (HttpURLConnection) url.openConnection();
                httpConnection.connect(); //conexao http para acessar o arquivo

                InputStream inputStream = httpConnection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream)); //obter o arquivo a partir da conexao

                JsonReader jsonReader = new JsonReader(bufferedReader); //classe que reconhece arquivo como json
                parseJSON(jsonReader);
                jsonReader.close();

            }
            catch (IOException e) {
                Toast toast = Toast.makeText(getApplicationContext(), "Erro ao conectar. Verifique a internet.",Toast.LENGTH_LONG);
            }
        }


    }

    private void parseJSON (JsonReader jsonReader){

        String ut, loc1 =  null, loc2 = null, func = null, ss = null, sc = null;
        int sem = 0, _id = 0, i = 0;
        double lg = 0, lt = 0;

        try {
            jsonReader.beginObject();

            while(!jsonReader.peek().equals(JsonToken.END_DOCUMENT)) {

                if (jsonReader.peek().equals(JsonToken.NAME)) {
                    String nextName = jsonReader.nextName();

                    if(nextName.equals("utilizacao")) {
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
                                semaforos[i] = new Semaforo(ut, loc1, loc2, func, ss, sem, sc, lg, lt, _id); //cria novo semaforo e adiciona ao array

                                LatLng pos = new LatLng(lt, lg);

                                Marker marker = mMap.addMarker(new MarkerOptions().position(pos).title("Semaforo #" + sem));
                                marker.setTag(i);

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

    @Override
    public void run() {

        jsonRequisition();

    }
}
