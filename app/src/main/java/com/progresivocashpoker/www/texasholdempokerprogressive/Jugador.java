package com.progresivocashpoker.www.texasholdempokerprogressive;

import android.os.Handler;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by JuanEsteban on 25/04/2016.
 */

public class Jugador {

    // atributos
    //Apuesta Guarda la apuesta que tiene la persona disponible, es el valor que s emuestra en pantalla
    private int Apuesta=0;
    //SuperApuesta Evita en la fase de apostar se retiren fichas ya jugadas en la fase de juego
    private int SuperApuesta=0;
    //Es un valor que dice si la persona esta en la mesa o pidio una pausa
    private boolean Enmesa=true;

    public TextView jugadortv,jugadortvdown,jugadortvcirculo;
    //Variable que dice si el jugador fue seleccionado
    private boolean seleccionado=false;
    private boolean bloqueado=false;

    // constructor
    public Jugador (TextView v1, TextView v2, TextView v3)    {
        jugadortv=v1;
        jugadortvdown=v2;
        jugadortvcirculo=v3;

        jugadortv.setOnClickListener(new lTVClickJugadores());
        jugadortv.setOnLongClickListener(new LongclickPausarJugador());
        jugadortv.setText("0");
        jugadortvdown.setText("X "+ String.valueOf(CPPLogin.manip.verValorFicha()));
    }
//cargar la apuesta en el textvie
    public void cargarapuesta(int fichas){
        if(Enmesa) {
            Apuesta += fichas;
            if (Apuesta < SuperApuesta) {
                Apuesta = SuperApuesta;
            }
            jugadortv.setText(String.valueOf(Apuesta));
        }
    }
    //Se llama cuando se inicia el juego
    public void apostemos(){
        if(Enmesa) {
            Apuesta--;
            SuperApuesta--;
            jugadortv.setText(String.valueOf(Apuesta));
        }
    }//Permite igualar la superapuesta a la apuesta cada vez que se juega
    public void cargarSuperApuesta(){
        if(Enmesa) {
            SuperApuesta = Apuesta;
        }
    }
// Permite reiniciar las apuestas cuando un jugaador se retire
    public void reiniciarApuesta(){
        Apuesta=0;
        SuperApuesta=0;
        Enmesa=true;
        seleccionado=false;
        jugadortv.setText(String.valueOf(Apuesta));
    }
    //pregunta si la persona esta en la mesa o no
    public boolean verSiPausado()
    {
        return(Enmesa);
    }
    // mientras la apuesta se mayor que cero permite poner o quitar la pausa
    public void ponerPausado()
    {
        if(SuperApuesta>0) {
            Enmesa = !(Enmesa);
            if (!Enmesa) {
                Pausar();
            }
            else {
                Habilitar();
            }
        }
    }
   //Aviso para el jugador que se queda sin crédito
   final Handler handler = new Handler();
    Timer t = new Timer();
    private int ConteoAlerta=0;
    private boolean Aviso=true;
    private boolean SwitchAviso=false;
    public void ResetearAvisoApuestaAcabada(){
        ConteoAlerta=0;
    }
    public void SwitchAvisoApuestaAcabada(boolean Avi){
        SwitchAviso=Avi;
    }


    //Funcion que avisa al dealer cuando un jugador ya no tiene mas fichas
    public void avisoApuestaAcabada(){
        t.schedule(new TimerTask() {
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        if (tablero.mesaJuego.verElEstadoDelJuego()==2 && !tablero.mesaJuego.GanaronBonus && SwitchAviso) {
                            if(Aviso){
                                ConteoAlerta++;
                                Seleccionar();
                                if(ConteoAlerta<6) {
                                    tablero.mesaJuego.reproducirSonido(2);
                                }
                                Aviso=false;
                            }else{
                                Habilitar();
                                Aviso=true;
                            }
                            avisoApuestaAcabada();
                        }else{
                            if(SwitchAviso) {
                                Habilitar();
                            }else{
                                Bloquear();
                            }

                        }
                    }
                });
            }
        }, 200);
    }

    //Draawables de Bonus
    public void bonusScreen(boolean seleccionado){
        if(seleccionado) {
            jugadortv.setBackgroundResource(R.drawable.jugadorbonusonup);
            jugadortvdown.setBackgroundResource(R.drawable.jugadorbonusondown);
            jugadortvdown.setTextColor(tablero.dato.getResources().getColor(R.color.Negro1));
            jugadortvdown.setText("BONUS");
        }else {
            jugadortv.setBackgroundResource(R.drawable.jugadorbonusoffup);
            jugadortvdown.setBackgroundResource(R.drawable.jugadorbonusoffdown);
            jugadortvdown.setTextColor(tablero.dato.getResources().getColor(R.color.Negro1));
            jugadortvdown.setText("BONUS");
        }
    }


    //  Despausa la mesa
//Retorna la apuesta
    public int verapuesta()
    {
        return Apuesta;
    }
// funciones graficas de los botones

    //Se llama cuando bloqueo al jugador ya que no tiene apuesta
    public void Bloquear() {
        if (Enmesa) {
            jugadortv.setBackgroundResource(R.drawable.jugadorbloqueadotop);
            jugadortvdown.setBackgroundResource(R.drawable.jugadorbloqueadodown);
            jugadortvcirculo.setBackgroundResource(R.drawable.jugadorbloqueadocirculo);

            jugadortvdown.setText("X "+String.valueOf(CPPLogin.manip.verValorFicha()));
            jugadortvdown.setTextColor(tablero.dato.getResources().getColor(R.color.gris4));
            jugadortvcirculo.setTextColor(tablero.dato.getResources().getColor(R.color.gris4));
            jugadortv.setTextColor(tablero.dato.getResources().getColor(R.color.gris4));
            jugadortv.setEnabled(false);
            seleccionado = false;
            bloqueado=true;
        }
    }
    //Se llama cuando se hace el bonus por jugador

//Se llama cuando habilito a los jugadores para poner nuevas apuestas
    public void Habilitar(){
        if (Enmesa) {
            jugadortv.setBackgroundResource(R.drawable.jugadorhabilitadotop);
            jugadortvdown.setText("X "+String.valueOf(CPPLogin.manip.verValorFicha()));
            jugadortvdown.setBackgroundResource(R.drawable.jugadorhabilitadodown);
            jugadortvcirculo.setBackgroundResource(R.drawable.jugadorhabilitadocirculo);
            jugadortvcirculo.setTextColor(tablero.dato.getResources().getColor(R.color.Dorado3));
            jugadortvdown.setTextColor(tablero.dato.getResources().getColor(R.color.Negro1));
            jugadortv.setTextColor(tablero.dato.getResources().getColor(R.color.Negro1));
            jugadortv.setEnabled(true);
            seleccionado = false;
            bloqueado=false;
        }
    }
    //Se llama cuando selecciono al jugador.
    public void Seleccionar(){
        if (Enmesa) {
            jugadortv.setBackgroundResource(R.drawable.jugadorseleccionadotop);
            jugadortvdown.setText("X "+String.valueOf(CPPLogin.manip.verValorFicha()));
            jugadortvdown.setBackgroundResource(R.drawable.jugadorseleccionadodown);
            jugadortvcirculo.setBackgroundResource(R.drawable.jugadorseleccionadocirculo);
            jugadortvcirculo.setTextColor(tablero.dato.getResources().getColor(R.color.Dorado3));
            jugadortvdown.setTextColor(tablero.dato.getResources().getColor(R.color.Negro1));
            jugadortv.setTextColor(tablero.dato.getResources().getColor(R.color.Negro1));
            jugadortv.setEnabled(true);
            seleccionado = true;
        }
    }
    //Cambia la forma del jugador cuando lo pongo en pausa
    public void Pausar(){
        jugadortv.setBackgroundResource(R.drawable.jugadorpausadotop);
        jugadortvdown.setText("X "+String.valueOf(CPPLogin.manip.verValorFicha()));
        jugadortvdown.setBackgroundResource(R.drawable.jugadorpausadodown);
        jugadortvcirculo.setBackgroundResource(R.drawable.jugadorpausadocirculo);
        jugadortvcirculo.setTextColor(tablero.dato.getResources().getColor(R.color.Rojo1));
        jugadortvdown.setTextColor(tablero.dato.getResources().getColor(R.color.Negro1));
        jugadortv.setTextColor(tablero.dato.getResources().getColor(R.color.Negro1));
        jugadortv.setEnabled(true);
        seleccionado=false;
    }
    //Cuando es el jugador seleccionado retorna 1 sino retorna 0
    public boolean EstoySeleccionado(){
        return seleccionado;
    }
    //
    public boolean EstoyBloqueado(){
        return bloqueado;
    }
}
