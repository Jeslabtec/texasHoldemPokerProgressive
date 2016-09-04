package com.progresivocashpoker.www.texasholdempokerprogressive;

import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.util.concurrent.ExecutionException;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class tablero extends AppCompatActivity {

    static AppCompatActivity dato;
    //manejo de los sonidos
    static Mesa mesaJuego;
    administradorDeSonido sound;

    int clic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablero);

        dato=this;
        TextView[] datos = {(TextView) findViewById(R.id.tvJugador1),
                (TextView) findViewById(R.id.tvJugador2),
                (TextView) findViewById(R.id.tvJugador3),
                (TextView) findViewById(R.id.tvJugador4),
                (TextView) findViewById(R.id.tvJugador5),
                (TextView) findViewById(R.id.tvJugador6),
                (TextView) findViewById(R.id.tvJugador7),
                (TextView) findViewById(R.id.tvJugador8),
                (TextView) findViewById(R.id.tvJugador9),
                (TextView) findViewById(R.id.tvJugador10),
                (TextView) findViewById(R.id.tvPremioApuesta1),
                (TextView) findViewById(R.id.tvPremioApuesta2),
                (TextView) findViewById(R.id.tvPremioApuesta3),
                (TextView) findViewById(R.id.tvPremioApuesta4),
                (TextView) findViewById(R.id.tvPremioApuesta5),
                (TextView) findViewById(R.id.tvPremioApuesta6),
                (TextView) findViewById(R.id.tvPagar),
                (TextView) findViewById(R.id.tvJugar),
                (TextView) findViewById(R.id.tvApostar),
                (TextView) findViewById(R.id.tvRetiroTotal),
                (TextView) findViewById(R.id.TextviewAviso),
                (TextView) findViewById(R.id.TVprogresivo),
                (TextView) findViewById(R.id.tvJugador1Down),
                (TextView) findViewById(R.id.tvJugador2Down),
                (TextView) findViewById(R.id.tvJugador3Down),
                (TextView) findViewById(R.id.tvJugador4Down),
                (TextView) findViewById(R.id.tvJugador5Down),
                (TextView) findViewById(R.id.tvJugador6Down),
                (TextView) findViewById(R.id.tvJugador7Down),
                (TextView) findViewById(R.id.tvJugador8Down),
                (TextView) findViewById(R.id.tvJugador9Down),
                (TextView) findViewById(R.id.tvJugador10Down),
                (TextView) findViewById(R.id.tvJugador1Circulo),
                (TextView) findViewById(R.id.tvJugador2Circulo),
                (TextView) findViewById(R.id.tvJugador3Circulo),
                (TextView) findViewById(R.id.tvJugador4Circulo),
                (TextView) findViewById(R.id.tvJugador5Circulo),
                (TextView) findViewById(R.id.tvJugador6Circulo),
                (TextView) findViewById(R.id.tvJugador7Circulo),
                (TextView) findViewById(R.id.tvJugador8Circulo),
                (TextView) findViewById(R.id.tvJugador9Circulo),
                (TextView) findViewById(R.id.tvJugador10Circulo),
                (TextView) findViewById(R.id.TVBono1),
                (TextView) findViewById(R.id.TVBono2)
        };


        // sección de Alert Dialgos, en esta sección se colocará el valor de cada Alert dialog para la confirmacion de  el pago de premios
       //-----------------------------------------------------------------------------------------------------//
        //Mensaje de confirmacion de retiro//
        //Administrador de sonido---------------------------------------------

        sound = new administradorDeSonido(getApplicationContext());
        // Set volume rocker mode to media volume
        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        // Lee los sonidos que figuran en res/raw

        mesaJuego = new Mesa(datos,sound);
        // sección de Alert Dialgos, en esta sección se colocará el valor de cada Alert dialog para la confirmacion de  el pago de premios
        //-----------------------------------------------------------------------------------------------------//
        //Mensaje de confirmacion de retiro//
        //Administrador de sonido---------------------------------------------
    }

    @Override
    protected void onPause(){
        try {
            CPPLogin.manip.GuardarTabla(CPPLogin.manip.idTablet,(int)mesaJuego.ProgresivoTV.ValorDelProgresivo());
            super.onPause();
        } catch (ExecutionException e) {
            Toast.makeText(tablero.dato,e.getMessage(), Toast.LENGTH_LONG).show();
        } catch (InterruptedException e) {
            Toast.makeText(tablero.dato,e.getMessage(), Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            Toast.makeText(tablero.dato,e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy(){
        try {
            CPPLogin.manip.GuardarTabla(CPPLogin.manip.idTablet,(int)mesaJuego.ProgresivoTV.ValorDelProgresivo());
            super.onDestroy();
        } catch (ExecutionException e) {
            Toast.makeText(tablero.dato,e.getMessage(), Toast.LENGTH_LONG).show();
        } catch (InterruptedException e) {
            Toast.makeText(tablero.dato,e.getMessage(), Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            Toast.makeText(tablero.dato,e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
    }


}
