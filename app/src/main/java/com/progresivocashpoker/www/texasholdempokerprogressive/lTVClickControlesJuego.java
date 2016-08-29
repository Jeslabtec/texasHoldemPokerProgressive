package com.progresivocashpoker.www.texasholdempokerprogressive;

import android.view.View;
import android.widget.Toast;

import org.json.JSONException;

import java.util.concurrent.ExecutionException;

/**
 * Created by JuanEsteban on 03/05/2016.
 */
public class lTVClickControlesJuego implements View.OnClickListener {

    @Override
    public void onClick(View v) {
        if (tablero.mesaJuego.hayAlguienJugando() || tablero.mesaJuego.verElEstadoDelJuego()==2) {
            switch (v.getId()) {
                case R.id.tvPagar:
                    tablero.mesaJuego.cambiarElEstadoDelJuego(1);
                    tablero.mesaJuego.cambiarBotones();
                    break;
                case R.id.tvJugar:
                    tablero.mesaJuego.cambiarElEstadoDelJuego(2);
                    try {
                        CPPLogin.manip.EnviarMovimiento(CPPLogin.manip.idTablet,"entrada",tablero.mesaJuego.cuantosJugando());
                    } catch (ExecutionException e) {
                        Toast.makeText(tablero.dato,e.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (InterruptedException e) {
                        Toast.makeText(tablero.dato,e.getMessage(), Toast.LENGTH_LONG).show();
                        //e.printStackTrace();
                    } catch (JSONException e) {
                        Toast.makeText(tablero.dato,e.getMessage(), Toast.LENGTH_LONG).show();
                        //e.printStackTrace();
                    }
                    tablero.mesaJuego.cambiarBotones();
                    tablero.mesaJuego.PonerAJugar();
                    break;
                case R.id.tvApostar:
                    tablero.mesaJuego.cambiarElEstadoDelJuego(3);
                    tablero.mesaJuego.cambiarBotones();
                    break;
                case R.id.tvRetiroTotal:
                    tablero.mesaJuego.cambiarElEstadoDelJuego(4);
                    tablero.mesaJuego.cambiarBotones();
                    break;
                default:
                    break;
            }
        } else {
            if (R.id.tvApostar!=v.getId()) {
                tablero.mesaJuego.mensaje.msgPedirALgunaApuesta().show();
                tablero.mesaJuego.cambiarElEstadoDelJuego(3);
                tablero.mesaJuego.cambiarBotones();
            }else if(R.id.tvApostar==v.getId()){
                tablero.mesaJuego.cambiarElEstadoDelJuego(3);
                tablero.mesaJuego.cambiarBotones();
            }
        }
    }
}
