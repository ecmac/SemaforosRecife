package br.com.serttel.semaforosrecife;

import android.os.Bundle;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;

import java.io.IOException;

import static java.lang.Thread.sleep;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mostrarLoadingActivity();
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

    private void mostrarLoadingActivity() {
        Intent intent = new Intent(
                SplashActivity.this,LoadingActivity.class
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
