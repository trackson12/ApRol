package com.example.aprol.ui.Usuario;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aprol.MainActivity;
import com.example.aprol.R;
import com.example.aprol.objeto.Cliente;
import com.example.aprol.rest.APIUtils;
import com.example.aprol.rest.RestCliente;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    Button registrar;
    Button entrar;
    EditText usuario;
    EditText pwd;

    private Context context;
    RestCliente clienteRest;
    APIUtils util;
    private SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.login);

        usuario = (EditText) findViewById(R.id.etUsuLogin);
        pwd = (EditText) findViewById(R.id.etPwdLogin);
        registrar = (Button)findViewById(R.id.btnRegistrar);
        entrar = (Button)findViewById(R.id.btnAceptar);

        registrar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegistroActivity.class);
                startActivity(i);
            }
        });

        if(isNetworkAvailable()) {
            clienteRest = APIUtils.getService();
        }else{
            Toast.makeText(getBaseContext(), "Es necesaria una conexión a internet", Toast.LENGTH_SHORT).show();
        }
        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Call<Cliente> call = clienteRest.usuario_registrado(usuario.getText().toString());
                Call<Cliente> call = clienteRest.findById(usuario.getText().toString());
                call.enqueue(new Callback<Cliente>() {
                    @Override
                    public void onResponse(Call<Cliente> call, Response<Cliente> response) {

                        if (response.isSuccessful()){
                            Toast.makeText(getBaseContext(), "Existe el cliente", Toast.LENGTH_SHORT).show();

                            Intent i = new Intent(v.getContext(), MainActivity.class);
                            startActivityForResult(i,0);

                        }else {
                            Toast.makeText(getBaseContext(), "8------D", Toast.LENGTH_SHORT).show();

                        }

                    }

                    @Override
                    public void onFailure(Call<Cliente> call, Throwable t) {

                    }
                });
            }
        });
    }
    private boolean isNetworkAvailable() {
        /*
        ConnectivityManager connectivityManager
                = (ConnectivityManager) this.getSystemService
                (Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();

         */
        ConnectivityManager connMgr = (ConnectivityManager) LoginActivity.this
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();
    }

}
