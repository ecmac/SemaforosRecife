package br.com.serttel.semaforosrecife;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LoadingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mostrarMainActivity();
            }
        }, 2000);

        /**while(!isOnline()){
         Toast.makeText(getApplicationContext(), "Erro ao conectar. Verifique a internet.", Toast.LENGTH_LONG).show();
         try {
         sleep(1000);
         } catch (InterruptedException e) {
         e.printStackTrace();
         }
         }**/
    }

    private void mostrarMainActivity() {
        Intent intent = new Intent(
                LoadingActivity.this,MapsActivity.class
        );
        startActivity(intent);
        finish();
    }
}
