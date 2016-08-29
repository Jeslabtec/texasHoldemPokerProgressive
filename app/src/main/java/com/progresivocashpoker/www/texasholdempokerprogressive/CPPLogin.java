package com.progresivocashpoker.www.texasholdempokerprogressive;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 *  Pantalla de inicio basada en ingresar Usuario y contraseña para el manejo de base de datos
 */
public class CPPLogin extends AppCompatActivity {
    static ManejoBD manip;  // Atributo de la clase creado para manejar tod lo concerniente a
    // coneccion de la BD
    static AppCompatActivity ContextoLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cpplogin);
        final EditText Usuario = (EditText) findViewById(R.id.edtUsuario);
        final EditText Pw = (EditText) findViewById(R.id.edtPassword);
        final Button lg = (Button) findViewById(R.id.btnLogin);


        manip=new ManejoBD();
        ContextoLogin=this;



        lg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(manip.Login(Usuario.getText().toString(),Pw.getText().toString())){
                        Intent pTablero=new Intent(CPPLogin.this,tablero.class);
                        startActivity(pTablero);
                        finish();
                    }
                    else{
                        Toast.makeText(CPPLogin.this,"No puede Cargarse el pantallazo inicial", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(CPPLogin.this,e.getMessage(), Toast.LENGTH_SHORT);
                }
            }
        });
    }
}

