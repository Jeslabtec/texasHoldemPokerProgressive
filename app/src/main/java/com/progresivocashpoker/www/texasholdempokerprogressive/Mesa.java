package com.progresivocashpoker.www.texasholdempokerprogressive;


import android.os.Handler;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by JuanEsteban on 28/04/2016.
 */
public class Mesa {
    //Objetos que representa c/u de los jugadores
    Jugador[] jugador = new Jugador[10];
    //Objetos de Los botones de apuestas premios
    ClaseApuestaPremio[] ApuestaPremio = new ClaseApuestaPremio[5];
    //Textview que me dice en que fase esta el juego
    TextView AvisoTV;
    ControlesJuego pagarTV;
    ControlesJuego jugarTV;
    ControlesJuego apostarTV;
    ControlesJuego retirarseTV;
    ClaseDelProgresivo ProgresivoTV;
    MensajesAlerta mensaje;


    //variable que dice si se necesita el supervisor o no
    public boolean necesariosupervisor = false;
    private int ApuPreSeleccionado = -1;
    // por defecto se inicia en la etapa 3, acreditar
    private int EstadoJuego = 3;

    // constructor de la clase Mesa:  el programa
    public Mesa(TextView[] v) {
//Creacion de los objetos jugadores que son 7
        for (int i = 0; i < jugador.length; i++) {
            jugador[i] = new Jugador(v[i], v[i + 22], v[i + 32]);
        }
        //Creacion de los objetos ApuestaPremio que son 6
        for (int i = 0; i < ApuestaPremio.length; i++) {
            ApuestaPremio[i] = new ClaseApuestaPremio(v[i + 10], i);
        }
        //Creacion de los 4 objetos de control
        pagarTV = new ControlesJuego(v[16], 1);
        jugarTV = new ControlesJuego(v[17], 2);
        apostarTV = new ControlesJuego(v[18], 3);
        retirarseTV = new ControlesJuego(v[19], 4);

        //Seteo del long click listener de la configuracion
        AvisoTV = v[20];

        //Creacion del objeto progresivo
        ProgresivoTV = new ClaseDelProgresivo(v[21]);
        cambiarBotones();
        //Objeto que contiene los mensajes de alerta
        mensaje = new MensajesAlerta();
    }

    //---------------------------------------------------------------------------------
    //Animaciones de los botones de apuestaPremio llamados desde abajo
    private void animaciondesplazamientoPremio() {
        int Y1 = tablero.dato.getResources().getInteger(R.integer.ApuPreDist1);
        int Y2 = tablero.dato.getResources().getInteger(R.integer.ApuPreDist2);
        int Y3 = tablero.dato.getResources().getInteger(R.integer.ApuPreDist3);


        int X1 = tablero.dato.getResources().getInteger(R.integer.Dis_separaApuPre_1);
        int X2 = tablero.dato.getResources().getInteger(R.integer.Dis_separaApuPre_2);
        int X3 = tablero.dato.getResources().getInteger(R.integer.Dis_separaApuPre_3);

        ApuestaPremio[0].Movimientopremio(-X1, -Y1);
        ApuestaPremio[1].Movimientopremio(-X2, -Y2);
        ApuestaPremio[2].Movimientopremio(-X3, -Y3);
    }

    private void animaciondesplazamientoApuesta() {
        int Y1 = tablero.dato.getResources().getInteger(R.integer.ApuPreDist1);
        int Y2 = tablero.dato.getResources().getInteger(R.integer.ApuPreDist2);
        int Y3 = tablero.dato.getResources().getInteger(R.integer.ApuPreDist3);


        int X1 = tablero.dato.getResources().getInteger(R.integer.Dis_separaApuPre_1);
        int X2 = tablero.dato.getResources().getInteger(R.integer.Dis_separaApuPre_2);
        int X3 = tablero.dato.getResources().getInteger(R.integer.Dis_separaApuPre_3);


        ApuestaPremio[0].Movimientoapuesta(-X1, -Y1);
        ApuestaPremio[1].Movimientoapuesta(-X2, -Y2);
        ApuestaPremio[2].Movimientoapuesta(-X3, -Y3);

    }

    //funcion que cambia el textview mientras es undido
    //Funcion que pregunta quienes estan en cero y los bloquea
    public void restringirJugadores() {
        for (int i = 0; i < jugador.length; i++) {
            if (jugador[i].verapuesta() == 0) {
                jugador[i].Bloquear();
            }
            if (jugador[i].verapuesta() == 1) {
                jugador[i].avisoApuestaAcabada();
            }
        }
    }

    public void restringirJugador(int i) {
        jugador[i].Bloquear();
    }

    //Funcion que habilita a los jugadores en la fase de apuesta
    public void habilitarJugadores() {
        {
            for (int i = 0; i < jugador.length; i++) {
                jugador[i].Habilitar();
            }
        }
    }

    //Funcion que pregunta si hay alguien jugando si lo hay responde con true
    public boolean hayAlguienJugando() {
        for (int i = 0; i < jugador.length; i++) {
            if (jugador[i].verapuesta() != 0 && jugador[i].verSiPausado()) {
                return true;
            }
        }
        return false;
    }

    //Funcion que me dice cuantos jugadores hay en mesa
    public int cuantosJugando() {
        int jugadores = 0;
        for (int i = 0; i < tablero.mesaJuego.jugador.length; i++) {
            if (tablero.mesaJuego.jugador[i].verapuesta() > 0 && tablero.mesaJuego.jugador[i].verSiPausado()) {
                jugadores++;
            }
        }
        return jugadores;
    }

    //Funcion que permite saber que jugadro esta seleccionado
    public int JugadorSeleccionado() {
        for (int i = 0; i < jugador.length; i++) {
            if (jugador[i].EstoySeleccionado()) {
                return i;
            }
        }
        return -1;
    }

    //funcion que permite seleccionar un jugador
    public void SeleccionarJugador(int j) {
        for (int i = 0; i < jugador.length; i++) {
            if (i == j) {
                jugador[i].Seleccionar();
            } else if (jugador[i].jugadortv.isEnabled()) {
                jugador[i].Habilitar();
            }
        }
    }

    public int ApuPreSeleccionado() {
        return ApuPreSeleccionado;
    }

    public void SeleccionarApuPre(int i) {
        ApuPreSeleccionado = i;
    }

    //Funcion que devuelve el estado del juego
    public int verElEstadoDelJuego() {
        return (EstadoJuego);
    }

    //-------------------------------------------------------------------------------------------------------------------------------------------
    //Funcion para iniciar el juego
    public void PonerAJugar() {

        for (int i = 0; i < tablero.mesaJuego.jugador.length; i++) {
            if (tablero.mesaJuego.jugador[i].verapuesta() > 0) {
                tablero.mesaJuego.jugador[i].cargarSuperApuesta();
                tablero.mesaJuego.jugador[i].apostemos();
            }
        }
            jugadaActual++;
            if (jugadaActual == jugadasBonus) {
                ganadorBonus = (int) Math.floor(Math.random() * 7);
                jugadaActual = 0;
                jugadasBonus = getBinomial(16, 0.5);
                EstadoBonusOn();
                BonusCambio(ganadorBonus);
            }

        ProgresivoTV.setAumentoPremio();
        progresivoLoco();

    }

    //Bonus************************************************************************************************
    //variable que dice en que jugada va a haber un ganado
    private int jugadasBonus = getBinomial(16, 0.5);
    //conteo de las jugadas que se reinicia cuando hay un ganador
    private int jugadaActual = 0;
    //
    private int iteracionesBonus = -1;
    private int jugadorBonus = -1;
    private int tiempoBonus = 200;
    private int ganadorBonus = -1;
    Timer t1 = new Timer();
    final Handler handler1 = new Handler();


    public int getBinomial(int n, double p) {
        int x = 0;
        for (int i = 0; i < n; i++) {
            if (Math.random() < p)
                x++;
        }
        return x;
    }

    private void BonusTimer() {
        t1.schedule(new TimerTask() {
            public void run() {
                handler1.post(new Runnable() {
                    public void run() {
                        SeleccionarJugadorBonus();
                    }
                });
            }
        }, tiempoBonus);
    }

    private void BonusCambio(int jugadorGanador) {
        jugadorBonus = 11 + jugadorGanador;
        for (int i = 0; i < jugador.length; i++) {
            jugador[i].bonusScreen(false);
        }
        SeleccionarJugadorBonus();
    }
    //Sirve para ir pasando el jugador hasta que llegue al ganador

    public void SeleccionarJugadorBonus() {
        if (iteracionesBonus == -1) {
            jugador[iteracionesBonus + 1].bonusScreen(true);
        } else if (iteracionesBonus >= 0 && iteracionesBonus < 6) {
            jugador[iteracionesBonus].bonusScreen(false);
            jugador[iteracionesBonus + 1].bonusScreen(true);
        } else if (iteracionesBonus >= 6 && iteracionesBonus < 12) {
            jugador[12 - iteracionesBonus].bonusScreen(false);
            jugador[11 - iteracionesBonus].bonusScreen(true);
        } else if (iteracionesBonus >= 12) {
            jugador[iteracionesBonus - 12].bonusScreen(false);
            jugador[iteracionesBonus - 11].bonusScreen(true);
        }
        if (iteracionesBonus < jugadorBonus) {
            iteracionesBonus++;
            tiempoBonus += 20;
            BonusTimer();
        } else {
            iteracionesBonus = -1;
            jugadorBonus = -1;
            tiempoBonus = 200;
            cambiarBotones();
            pagarBonus();
        }
    }

    private void pagarBonus() {
        if (tablero.mesaJuego.jugador[ganadorBonus].verapuesta() > 0 && tablero.mesaJuego.jugador[ganadorBonus].verSiPausado()) {
            tablero.mesaJuego.jugador[ganadorBonus].cargarapuesta(30);
            ProgresivoTV.PagarProgresivo(30);
        }

    }

    private void EstadoBonusOn() {
        retirarseTV.Bloquear();
        pagarTV.Bloquear();
        jugarTV.Bloquear();
        apostarTV.Bloquear();
        AvisoTV.setBackgroundResource(R.drawable.avisobonus);
        AvisoTV.setText(R.string.Bonus);
        habilitarJugadores();
    }


    //Timer***********************************************************************************************
    //Funcion que se realiza iterativamente durante toda la fase de juego
    final Handler handler = new Handler();
    Timer t = new Timer();

    public void progresivoLoco() {
        t.schedule(new TimerTask() {
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        if (EstadoJuego == 2) {
                            ProgresivoTV.aumentoAleatorio();
                            progresivoLoco();
                        }
                    }
                });
            }
        }, 150);
    }


//*************************************************************************************************************************

    //Funcion que cambia el estado de juego
    public void cambiarElEstadoDelJuego(int NuevoEstado) {
        EstadoJuego = NuevoEstado;
    }

    //*********************************************************************************************************************
    //Que pasa con los textview cuando se une cualquiera de los controles//
    private void BotonesdeApuesta() {
        ApuestaPremio[2].ponerSumando();
        retirarseTV.Habilitar();
        pagarTV.Bloquear();
        jugarTV.Habilitar();
        apostarTV.Seleccionar();

        for (int i = 0; i < ApuestaPremio.length; i++) {
            ApuestaPremio[i].BotonesApuesta();
        }

        AvisoTV.setBackgroundResource(R.drawable.avisoacreditar);
        AvisoTV.setText(R.string.Acreditar);
        habilitarJugadores();
        animaciondesplazamientoApuesta();
    }

    //----------------------------------------------------------------------------------------//
    private void BotonesdePago() {
        retirarseTV.Bloquear();
        pagarTV.Seleccionar();
        jugarTV.Habilitar();
        apostarTV.Habilitar();

        for (int i = 0; i < ApuestaPremio.length; i++) {
            ApuestaPremio[i].BotonesPremio();
        }
        AvisoTV.setBackgroundResource(R.drawable.avisopagar);
        AvisoTV.setText(R.string.Pagar);
        animaciondesplazamientoPremio();
    }

    //--------------------------------------------------------------------------------------------------//
    private void BotonesdeJuego() {
        retirarseTV.Bloquear();
        pagarTV.Habilitar();
        jugarTV.Seleccionar();
        apostarTV.Habilitar();

        for (int i = 0; i < ApuestaPremio.length; i++) {
            ApuestaPremio[i].BotonesPremio();
        }

        SeleccionarJugador(-1);
        restringirJugadores();
        AvisoTV.setBackgroundResource(R.drawable.avisojugar);
        AvisoTV.setText(R.string.Jugar);
        animaciondesplazamientoPremio();
    }

    //---------------------------------------------------------------------------------------------------------------//
    private void BotonesdeRetiro() {
        retirarseTV.Seleccionar();
        pagarTV.Bloquear();
        jugarTV.Bloquear();
        apostarTV.Habilitar();

        for (int i = 0; i < ApuestaPremio.length; i++) {
            ApuestaPremio[i].BotonesDesaparecer();
        }
        SeleccionarJugador(-1);
        restringirJugadores();
        AvisoTV.setBackgroundResource(R.drawable.avisoretirarse);
        AvisoTV.setText(R.string.Retirar);
        animaciondesplazamientoPremio();
    }
    //-------------------------------------------------------------------------------------------------------------------//
    // metodos
    //dependiendo del estado del juego se habilitaran o desabilitaran algunos botones

    public void cambiarBotones() {
        switch (EstadoJuego) {//----------------------------------------------------------------------------------------------
            case 1:
                BotonesdePago();
                break;
            //-------------------------------------------------------------------------------------------------------------------//
            case 2:
                BotonesdeJuego();
                break;
            case 3:
                BotonesdeApuesta();
                break;
            case 4:
                BotonesdeRetiro();
                break;
        }
    }

    //Acciones que permiten confirmar el pago, es valida cuando el codigo ingresado en codigoaut pertenece a un dealer o supervisor
    public int AccionesConfirmarPago() {
        double Progresivo = (int) ProgresivoTV.ValorDelProgresivo();
        double Premio = ApuestaPremio[ApuPreSeleccionado()].ValorNumerico();
        double pago;


        if (ApuPreSeleccionado < 2) {
            pago = Math.floor((double) (Progresivo * (Premio / 100)) / CPPLogin.manip.verValorFicha());

        } else {
            pago = Premio;
        }

        ProgresivoTV.PagarProgresivo((int) pago);
        jugador[JugadorSeleccionado()].cargarapuesta((int) pago);
        jugador[JugadorSeleccionado()].cargarSuperApuesta();
        restringirJugador(JugadorSeleccionado());
        return (int) pago;
    }
}

