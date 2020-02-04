package com.example.aprol.ui.Usuario;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aprol.R;

public class LoginActivity extends AppCompatActivity {

    Button registrar;
    Button entrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        registrar = (Button)findViewById(R.id.btnRegistrar);

        registrar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegistroActivity.class);
                startActivity(i);
            }
        });
    }
}
