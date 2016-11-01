package com.progresivocashpoker.www.texasholdempokerprogressive;

import android.view.View;

/**
 * Created by USER on 28/05/2016.
 */
public class LongclickPausarJugador implements View.OnLongClickListener {

    @Override
    public boolean onLongClick(View v) {
        if(tablero.mesaJuego.verElEstadoDelJuego()!=1) {
            switch (v.getId()) {
                case R.id.tvJugador1:     // fase de pago
                    tablero.mesaJuego.jugador[0].ponerPausado();
                    break;
                case R.id.tvJugador2:     // fase de juego... en este caso no pasa nada porque solo se da click en iniciar juego, pero nunca se toca el jugador
                    tablero.mesaJuego.jugador[1].ponerPausado();
                    break;
                case R.id.tvJugador3:     // fase de apuestas
                    tablero.mesaJuego.jugador[2].ponerPausado();
                    break;
                case R.id.tvJugador4:     // fase de retiros.... esta es la mas complicada pues reqiero el nombre del jugador
                    tablero.mesaJuego.jugador[3].ponerPausado();
                    break;
                case R.id.tvJugador5:     // fase de juego... en este caso no pasa nada porque solo se da click en iniciar juego, pero nunca se toca el jugador
                    tablero.mesaJuego.jugador[4].ponerPausado();
                    break;
                case R.id.tvJugador6:     // fase de apuestas
                    tablero.mesaJuego.jugador[5].ponerPausado();
                    break;
                case R.id.tvJugador7:     // fase de retiros.... esta es la mas complicada pues reqiero el nombre del jugador
                    tablero.mesaJuego.jugador[6].ponerPausado();
                    break;
                case R.id.tvJugador8:     // fase de juego... en este caso no pasa nada porque solo se da click en iniciar juego, pero nunca se toca el jugador
                    tablero.mesaJuego.jugador[7].ponerPausado();
                    break;
                case R.id.tvJugador9:     // fase de apuestas
                    tablero.mesaJuego.jugador[8].ponerPausado();
                    break;
                case R.id.tvJugador10:     // fase de retiros.... esta es la mas complicada pues reqiero el nombre del jugador
                    tablero.mesaJuego.jugador[9].ponerPausado();
                    break;
            }
        }
        return false;
    }
}
