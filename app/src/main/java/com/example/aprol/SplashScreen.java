package com.example.aprol;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.telecom.Call;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aprol.objeto.Cliente;
import com.example.aprol.rest.APIUtils;
import com.example.aprol.rest.RestCliente;
import com.example.aprol.ui.Usuario.LoginActivity;
import com.google.android.gms.common.api.Response;

import java.util.Calendar;

public class SplashScreen extends AppCompatActivity {

    private RestCliente clienteRest;
    private APIUtils api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        if(isNetworkAvailable()) {
           Cliente c = (Cliente) APIUtils.getService();
        }else{
            Toast.makeText(getApplicationContext(), "Es necesaria una conexión a internet", Toast.LENGTH_SHORT).show();
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashScreen.this, com.example.aprol.ui.Usuario.LoginActivity.class);
                startActivity(i);

                String token_sqLite = BdController.selectToken(getBaseContext());
                if (token_sqLite.equals("")) {
                    Intent intent = new Intent(SplashScreen.this, com.example.aprol.ui.Usuario.LoginActivity.class);
                    startActivity(intent);
                } else {
                    buscarToken(token_sqLite);
                }
            }
        },2000);

    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) this.getSystemService
                (Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    private void buscarToken(String tok) {
        Call<Cliente> call = clienteRest.comproToken(tok);
        call.enqueue(new Callback<Cliente>() {

        });
    }
}
