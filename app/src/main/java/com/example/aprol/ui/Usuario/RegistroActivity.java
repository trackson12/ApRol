package com.example.aprol.ui.Usuario;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.aprol.R;
import com.example.aprol.objeto.Cliente;
import com.example.aprol.rest.APIUtils;
import com.example.aprol.rest.RestCliente;
import com.google.android.material.navigation.NavigationView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistroActivity extends AppCompatActivity {

    Button volver;
    Button registra;
    EditText usuario;
    EditText correo;
    EditText pwd;
    RestCliente clienteRest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.registro);
        volver = (Button)findViewById(R.id.btnVolverRegistro);
        registra = (Button)findViewById(R.id.btnOkRegistro);
        usuario = (EditText) findViewById(R.id.etUsuRegistro);
        correo = (EditText)findViewById(R.id.etEmailRegistro);
        pwd = (EditText)findViewById(R.id.etPwdRegistro);

        volver.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegistroActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
        if(isNetworkAvailable()) {
            clienteRest = APIUtils.getService();
        }else{
            Toast.makeText(getBaseContext(), "Es necesaria una conexión a internet", Toast.LENGTH_SHORT).show();
        }
        registra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (comprobarEmail(correo.getText().toString())){





                    Cliente p = new Cliente(usuario.getText().toString(),correo.getText().toString(),pwd.getText().toString());
                    // Vamos a ver en que modo estamos
                    //salvamos el producto
                    salvarProducto(p);
                }else {
                    Toast.makeText(RegistroActivity.this, "Email no valido", Toast.LENGTH_SHORT).show();

                }
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
        ConnectivityManager connMgr = (ConnectivityManager) RegistroActivity.this
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();
    }
    private void salvarProducto(Cliente p) {
        // Llamamos al metodo de crear
        Call<Cliente> call = clienteRest.create(p);
        call.enqueue(new Callback<Cliente>() {
            // Si todo ok
            @Override
            public void onResponse(Call<Cliente> call, Response<Cliente> response) {
                if(response.isSuccessful()){
                    Toast.makeText(RegistroActivity.this, "Cliente guardado", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(RegistroActivity.this, "Error al guardar cliente", Toast.LENGTH_SHORT).show();

                }

            }

            // Si error
            @Override
            public void onFailure(Call<Cliente> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }
    private boolean comprobarEmail(String email){

        // Patrón para validar el email
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");


        Matcher mather = pattern.matcher(email);

        if (mather.find() == true) {
            return true;
        } else {
            return false;
        }

    }
}
