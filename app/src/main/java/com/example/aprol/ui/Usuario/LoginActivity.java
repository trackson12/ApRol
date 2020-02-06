package com.example.aprol.ui.Usuario;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
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
import com.example.aprol.ui.DBController;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import okio.HashingSink;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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



            }
        });

        //Comprueba si hay conexion
        if(isNetworkAvailable()) {
            clienteRest = APIUtils.getService();

            entrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Llava al servidor y busca al cliente
                    retrofit2.Call<Cliente> call = clienteRest.findById(usuario.getText().toString());
                    call.enqueue(new Callback<Cliente>() {
                        @Override
                        public void onResponse(retrofit2.Call<Cliente> call, retrofit2.Response<Cliente> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(getBaseContext(), "Existe el cliente", Toast.LENGTH_SHORT).show();

                                Intent i = new Intent(v.getContext(), MainActivity.class);
                                startActivityForResult(i, 0);
                            }else {
                                Toast.makeText(getBaseContext(), "No esta registrado", Toast.LENGTH_SHORT).show();

                            }

                        }

                        //Sacara un mensaje de error si el servidor no responde
                        @Override
                        public void onFailure(retrofit2.Call<Cliente> call, Throwable t) {

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

    /*private void buscarUsuario(){
        retrofit2.Call<Cliente> call = clienteRest.findById(usuario.getText().toString());
        Toast toast = Toast.makeText(getApplicationContext(), "hola", Toast.LENGTH_LONG);
        //toast.show();
        Log.e("cosa","a");
        call.enqueue(new Callback<Cliente>() {
            @Override
            public void onResponse(retrofit2.Call<Cliente> call, retrofit2.Response<Cliente> response) {
                if (response.isSuccessful()) {
                    Cliente cli = response.body();
                    if (cli != null) {
                        //para coger la id del dispositivo
                        String android_id = Settings.Secure.getString(getContentResolver(),
                                Settings.Secure.ANDROID_ID);
                        //para coger la fecha actual y pasarla a md5
                        Date currentTime = Calendar.getInstance().getTime();
                        String token = currentTime.toString();
                        token = md5(token);
                        cli.setToken(token);

                        actualizarToken(cli.getId(), cli);
                        DBController.insertarData(getApplicationContext(), token);
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);

                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "No existe el usuario, registre por favor", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"Por favor introduce datos ",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Cliente> call, Throwable t) {

            }
        });
    }

    private void actualizarToken(int id, Cliente cli) {
        retrofit2.Call<Cliente> call = clienteRest.update(usuario.getText().toString() , cli);
        Toast toast = Toast.makeText(getApplicationContext(), "hola", Toast.LENGTH_LONG);
        //toast.show();
        Log.e("cosa","a");
        call.enqueue(new Callback<Cliente>() {

            @Override
            public void onResponse(Call<Cliente> call, Response<Cliente> response) {

            }

            @Override
            public void onFailure(Call<Cliente> call, Throwable t) {

            }
        });
    }

    private String md5(String token) {
        try{
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(token.getBytes());
            byte messageDigest[] = digest.digest();

            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i<messageDigest.length;i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            return hexString.toString();
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return "";
    }*/
}
