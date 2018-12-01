package br.com.serttel.semaforosrecife;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.widget.Toast;

import java.io.IOException;

import static java.lang.Thread.sleep;

public class LoadingActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mostrarMapsActivity();
            }
        }, 2000);
    }

    private void mostrarMapsActivity() {

        while(!isOnline()){
            try {
                sleep(1500);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Intent intent = new Intent(
                LoadingActivity.this,MapsActivity.class
        );
        startActivity(intent);
        finish();
    }

    public boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();

        Process ipProcess = null;
        try {
            ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int exitValue = ipProcess.waitFor();

            return (exitValue == 0);
        }
        catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return false;
    }
}
