package com.example.aprol.ui.Usuario;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.Calendar;
import java.util.Date;

public class LoginActivity extends AppCompatActivity {

    //Nos llevara al activity de registrar ususario
    private Button registrar;

    //Realizara la funcion de encontrar al usuario en la base de datos
    private Button entrar;

    //Recoge el usuario del layout
    private EditText usuario;

    //Recoge el usuario del layout
    private EditText pwd;

    private Context context;
    //Interfaz para la clase Cliente
    private RestCliente clienteRest;

    private APIUtils util;
    private SensorManager sensorManager;

    private Calendar cal = Calendar.getInstance();

    private Date date = cal.getTime();
    private SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
    private String date1 = format1.format(date);

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

                Date token = Calendar.getInstance().getTime();

            }
        });

        //Comprueba si hay conexion
        if(isNetworkAvailable()) {
            clienteRest = APIUtils.getService();

            entrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Llava al servidor y busca al cliente
                    Call<Cliente> call = clienteRest.findById(usuario.getText().toString());
                    call.enqueue(new Callback<Cliente>() {
                        @Override
                        public void onResponse(Call<Cliente> call, Response<Cliente> response) {

                            //Comprueba si el servidor responde , en caso positivo pasa a la siguiente activity
                            if (response.isSuccessful()){
                                Toast.makeText(getBaseContext(), "Existe el cliente", Toast.LENGTH_SHORT).show();

                                Intent i = new Intent(v.getContext(), MainActivity.class);
                                startActivityForResult(i,0);

                            }else {
                                Toast.makeText(getBaseContext(), "No esta registrado", Toast.LENGTH_SHORT).show();

                            }

                        }

                        //Sacara un mensaje de error si el servidor no responde
                        @Override
                        public void onFailure(Call<Cliente> call, Throwable t) {
                            Toast.makeText(getBaseContext(), "El servidor no responde", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });


            /**
            Cliente p = new Cliente(date1);
            Call<Cliente> call = clienteRest.update(usuario.getText().toString(),p);
            call.enqueue(new Callback<Cliente>() {

                @Override
                public void onResponse(Call<Cliente> call, Response<Cliente> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(LoginActivity.this, "Cliente no expirado", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(LoginActivity.this, "Cliente expirado", Toast.LENGTH_SHORT).show();

                    }

                }

                // Si error
                @Override
                public void onFailure(Call<Cliente> call, Throwable t) {
                    Log.e("ERROR: ", t.getMessage());
                }
            });

            **/




        }else{
            Toast.makeText(getBaseContext(), "Es necesaria una conexión a internet", Toast.LENGTH_SHORT).show();
        }

    }


    //Comprueba si hay red
    private boolean isNetworkAvailable() {

        ConnectivityManager connMgr = (ConnectivityManager) LoginActivity.this
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();
    }

}
