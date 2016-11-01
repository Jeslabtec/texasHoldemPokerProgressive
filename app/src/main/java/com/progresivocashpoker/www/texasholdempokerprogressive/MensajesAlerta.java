package com.progresivocashpoker.www.texasholdempokerprogressive;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

/**
 * Created by user on 02/06/2016.
 */
public class MensajesAlerta {

    AlertDialog msgConfirmarPago() {
        AlertDialog.Builder creaMensajes = new AlertDialog.Builder(tablero.dato);
        creaMensajes.setMessage("¿Confirma el pago de este premio?");
        creaMensajes.setCancelable(true);
        creaMensajes.setPositiveButton(R.string.Confirmar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                AbrirCodiaut();
                dialog.cancel();
            }
        });
        creaMensajes.setNegativeButton(R.string.Cancelar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                tablero.mesaJuego.SeleccionarJugador(-1);
                dialog.cancel();
            }
        });
        return creaMensajes.create();
    }



    //------------------------------------------------------------------------------------------------------------------------------//
    AlertDialog msgConfirmarRetiro() {

        AlertDialog.Builder creaMensajes = new AlertDialog.Builder(tablero.dato);
        creaMensajes.setMessage("¿Seguro qué desea retirarse?");
        creaMensajes.setCancelable(true);
        creaMensajes.setPositiveButton(R.string.Confirmar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                msgConfirmarDinero().show();
                dialog.cancel();
            }
        });
        creaMensajes.setNegativeButton(R.string.Cancelar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        return creaMensajes.create();
    }

    //-----------------------------------------------------------------------------------------------------------//
//Mensaje de error cuando aun no tengo apuestas
    AlertDialog msgErrorApuesta() {
        AlertDialog.Builder creaMensajes = new AlertDialog.Builder(tablero.dato);
        creaMensajes.setMessage(R.string.DebSelJug);
        creaMensajes.setCancelable(true);
        creaMensajes.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        return creaMensajes.create();

    }

    //-----------------------------------------------------------------------------------------------------------//

    //Mensaje que permite confirmar el pago del dinero.
    AlertDialog msgConfirmarDinero() {
        AlertDialog.Builder creaMensajes = new AlertDialog.Builder(tablero.dato);

        creaMensajes.setMessage(tablero.dato.getResources().getString(R.string.pagarAlJugador) + " "+
                tablero.mesaJuego.jugador[tablero.mesaJuego.JugadorSeleccionado()].verapuesta()+ " "+
                tablero.dato.getResources().getString(R.string.fichas));

        creaMensajes.setCancelable(true);
        creaMensajes.setPositiveButton(R.string.Confirmar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int i=tablero.mesaJuego.JugadorSeleccionado();
                tablero.mesaJuego.jugador[i].reiniciarApuesta();
                tablero.mesaJuego.restringirJugador(i);
                tablero.mesaJuego.SeleccionarJugador(-1);
                if (!tablero.mesaJuego.hayAlguienJugando()) {
                    tablero.mesaJuego.cambiarElEstadoDelJuego(3);
                    tablero.mesaJuego.cambiarBotones();
                }
                dialog.cancel();
            }
        });

        return creaMensajes.create();
    }

    //--------------------------------------------------------------------------------------------------------------------------------//
    AlertDialog msgPedirALgunaApuesta() {
        AlertDialog.Builder creaMensajes = new AlertDialog.Builder(tablero.dato);
        creaMensajes.setMessage(R.string.Paracontinuar);
        creaMensajes.setCancelable(true);
        creaMensajes.setPositiveButton(R.string.Confirmar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        return creaMensajes.create();
    }
    //-----------------------------------------------------------------------------------------------------------//
    public void AbrirCodiaut()
    {
        Intent orden=new Intent(tablero.dato,Codigoaut.class);
        tablero.dato.startActivity(orden);
    }
}
