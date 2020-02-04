package com.example.aprol.ui.Usuario;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.aprol.R;
import com.google.android.material.navigation.NavigationView;

public class RegistroActivity extends AppCompatActivity {

    Button volver;
    Button registra;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro);
        volver = (Button)findViewById(R.id.btnVolverRegistro);

        volver.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegistroActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
    }
}
