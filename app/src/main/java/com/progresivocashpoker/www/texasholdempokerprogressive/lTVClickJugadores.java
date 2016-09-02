package com.progresivocashpoker.www.texasholdempokerprogressive;

import android.view.View;

/**
 * Created by JuanEsteban on 30/04/2016.
 */
public class lTVClickJugadores implements View.OnClickListener {
    private int posicion (int id) {
        switch (id) {
            case R.id.tvJugador1:
                return (0);
            case R.id.tvJugador2:
                return (1);
            case R.id.tvJugador3:
                return (2);
            case R.id.tvJugador4:
                return (3);
            case R.id.tvJugador5:
                return (4);
            case R.id.tvJugador6:
                return (5);
            case R.id.tvJugador7:
                return (6);
            case R.id.tvJugador8:
                return (7);
            case R.id.tvJugador9:
                return (8);
            case R.id.tvJugador10:
                return (9);
            default:
                return(-1);
        }

    }




    @Override
    public void onClick(View v)
    {
        int i=posicion(v.getId());
        switch (tablero.mesaJuego.verElEstadoDelJuego())
            {
                case 1:     // fase de pago
                    tablero.mesaJuego.SeleccionarJugador(i);
                    break;
                case 2:     // fase de juego... en este caso no pasa nada porque solo se da click en iniciar juego, pero nunca se toca el jugador
                    break;
                case 3:     // fase de apuestas
                    tablero.mesaJuego.SeleccionarJugador(i);
                    break;
                case 4:     // fase de retiros.... esta es la mas complicada pues reqiero el nombre del jugador
                    tablero.mesaJuego.SeleccionarJugador(i);
                    tablero.mesaJuego.mensaje.msgConfirmarRetiro().show();
                    break;
            }

    }





}
