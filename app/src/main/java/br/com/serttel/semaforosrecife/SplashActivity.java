package br.com.serttel.semaforosrecife;

import android.os.Bundle;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;

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
        }, 1500);
    }

    private void mostrarLoadingActivity() {
        Intent intent = new Intent(
                SplashActivity.this,LoadingActivity.class
        );
        startActivity(intent);
        finish();
    }


}
