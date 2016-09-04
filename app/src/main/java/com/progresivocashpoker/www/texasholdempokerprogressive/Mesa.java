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
    ClaseApuestaPremio[] ApuestaPremio = new ClaseApuestaPremio[6];
    //Textview que me dice en que fase esta el juego
    TextView AvisoTV;
    TextView Bono1TV;
    TextView Bono2TV;
    ControlesJuego pagarTV;
    ControlesJuego jugarTV;
    ControlesJuego apostarTV;
    ControlesJuego retirarseTV;
    ClaseDelProgresivo ProgresivoTV;
    MensajesAlerta mensaje;

    administradorDeSonido sonido;
    int clic;
    int aviso;
    int winner;

    //variable que dice si se necesita el supervisor o no
    public boolean necesariosupervisor = false;
    private int ApuPreSeleccionado = -1;
    // por defecto se inicia en la etapa 3, acreditar
    private int EstadoJuego = 3;

    // constructor de la clase Mesa:  el programa
    public Mesa(TextView[] v,administradorDeSonido w) {
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
        Bono1TV=v[42];
        Bono2TV=v[43];
        //conficguracion del sonido
        sonido=w;
        clic = sonido.load(R.raw.clic);
        aviso=sonido.load(R.raw.aviso);
        winner=sonido.load(R.raw.winner);

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
        int Y4 = tablero.dato.getResources().getInteger(R.integer.ApuPreDist4);
        int Y5 = tablero.dato.getResources().getInteger(R.integer.ApuPreDist5);
        int Y6 = tablero.dato.getResources().getInteger(R.integer.ApuPreDist6);

        int X1 = tablero.dato.getResources().getInteger(R.integer.Dis_separaApuPre_1);
        int X2 = tablero.dato.getResources().getInteger(R.integer.Dis_separaApuPre_2);
        int X3 = tablero.dato.getResources().getInteger(R.integer.Dis_separaApuPre_3);
        int X4 = tablero.dato.getResources().getInteger(R.integer.Dis_separaApuPre_4);
        int X5 = tablero.dato.getResources().getInteger(R.integer.Dis_separaApuPre_5);
        int X6 = tablero.dato.getResources().getInteger(R.integer.Dis_separaApuPre_6);

        ApuestaPremio[0].Movimientopremio(-X1, -Y1);
        ApuestaPremio[1].Movimientopremio(-X2, -Y2);
        ApuestaPremio[2].Movimientopremio(-X3, -Y3);
        ApuestaPremio[3].Movimientopremio(-X4, -Y4);
        ApuestaPremio[4].Movimientopremio(-X5, -Y5);
        ApuestaPremio[5].Movimientopremio(-X6, -Y6);
    }

    private void animaciondesplazamientoApuesta() {
        int Y1 = tablero.dato.getResources().getInteger(R.integer.ApuPreDist1);
        int Y2 = tablero.dato.getResources().getInteger(R.integer.ApuPreDist2);
        int Y3 = tablero.dato.getResources().getInteger(R.integer.ApuPreDist3);
        int Y4 = tablero.dato.getResources().getInteger(R.integer.ApuPreDist4);
        int Y5 = tablero.dato.getResources().getInteger(R.integer.ApuPreDist5);
        int Y6 = tablero.dato.getResources().getInteger(R.integer.ApuPreDist6);

        int X1 = tablero.dato.getResources().getInteger(R.integer.Dis_separaApuPre_1);
        int X2 = tablero.dato.getResources().getInteger(R.integer.Dis_separaApuPre_2);
        int X3 = tablero.dato.getResources().getInteger(R.integer.Dis_separaApuPre_3);
        int X4 = tablero.dato.getResources().getInteger(R.integer.Dis_separaApuPre_4);
        int X5 = tablero.dato.getResources().getInteger(R.integer.Dis_separaApuPre_5);
        int X6 = tablero.dato.getResources().getInteger(R.integer.Dis_separaApuPre_6);

        ApuestaPremio[0].Movimientoapuesta(-X1, -Y1);
        ApuestaPremio[1].Movimientoapuesta(-X2, -Y2);
        ApuestaPremio[2].Movimientoapuesta(-X3, -Y3);
        ApuestaPremio[3].Movimientoapuesta(-X4, -Y4);
        ApuestaPremio[4].Movimientoapuesta(-X5, -Y5);
        ApuestaPremio[5].Movimientoapuesta(-X6, -Y6);
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
            if (tablero.mesaJuego.jugador[i].jugadortv.isEnabled() && tablero.mesaJuego.jugador[i].verSiPausado()) {
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

        ProgresivoTV.setAumentoPremio();
        progresivoLoco();

    }
     //Timer***********************************************************************************************
    //Funcion que se realiza iterativamente durante toda la fase de juego
    final Handler handler = new Handler();
    Timer t = new Timer();
    public int iteracionesProgresivoLoco=0;
    public int ganadorParPerfecto=0;
    public void progresivoLoco() {
        t.schedule(new TimerTask() {
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        if (iteracionesProgresivoLoco<20) {
                            iteracionesProgresivoLoco++;
                            ProgresivoTV.aumentoAleatorio();
                            ganadorParPerfecto = (int) Math.floor(Math.random() * 13);
                            PonerCarta(ganadorParPerfecto);
                            progresivoLoco();
                        }else{
                            iteracionesProgresivoLoco=0;
                            retirarseTV.Habilitar();
                            pagarTV.Habilitar();
                            jugarTV.Seleccionar();
                            apostarTV.Habilitar();
                        }

                    }
                });
            }

        }, 200);
    }

    private void PonerCarta(int posicion){
        switch(posicion)
        {case 0:
            Bono1TV.setBackgroundResource(R.drawable.cartaa);
            Bono2TV.setBackgroundResource(R.drawable.cartaa);
            break;
        case 1:
            Bono1TV.setBackgroundResource(R.drawable.carta2);
            Bono2TV.setBackgroundResource(R.drawable.carta2);
            break;
        case 2:
            Bono1TV.setBackgroundResource(R.drawable.carta3);
            Bono2TV.setBackgroundResource(R.drawable.carta3);
            break;
        case 3:
            Bono1TV.setBackgroundResource(R.drawable.carta4);
            Bono2TV.setBackgroundResource(R.drawable.carta4);
            break;
        case 4:
            Bono1TV.setBackgroundResource(R.drawable.carta5);
            Bono2TV.setBackgroundResource(R.drawable.carta5);
            break;
        case 5:
            Bono1TV.setBackgroundResource(R.drawable.carta6);
            Bono2TV.setBackgroundResource(R.drawable.carta6);
            break;
        case 6:
            Bono1TV.setBackgroundResource(R.drawable.carta7);
            Bono2TV.setBackgroundResource(R.drawable.carta7);
            break;
        case 7:
            Bono1TV.setBackgroundResource(R.drawable.carta8);
            Bono2TV.setBackgroundResource(R.drawable.carta8);
            break;
        case 8:
            Bono1TV.setBackgroundResource(R.drawable.carta9);
            Bono2TV.setBackgroundResource(R.drawable.carta9);
            break;
        case 9:
            Bono1TV.setBackgroundResource(R.drawable.carta10);
            Bono2TV.setBackgroundResource(R.drawable.carta10);
            break;
        case 10:
            Bono1TV.setBackgroundResource(R.drawable.cartaj);
            Bono2TV.setBackgroundResource(R.drawable.cartaj);
            break;
        case 11:
            Bono1TV.setBackgroundResource(R.drawable.cartaq);
            Bono2TV.setBackgroundResource(R.drawable.cartaq);
            break;
        case 12:
            Bono1TV.setBackgroundResource(R.drawable.cartak);
            Bono2TV.setBackgroundResource(R.drawable.cartak);
            break;
        }


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
        jugarTV.Bloquear();
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
        pagarTV.Bloquear();
        jugarTV.Bloquear();
        apostarTV.Bloquear();


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
        DineroPagoConEstilo=(int)pago;
        retirarseTV.Bloquear();
        pagarTV.Bloquear();
        jugarTV.Bloquear();
        apostarTV.Bloquear();
        pagarConEstilo();
        return (int) pago;
    }

    final Handler handler3 = new Handler();
    Timer t2 = new Timer();
    private int DineroPagoConEstilo=0;
    private int conteoPagoestilo=0;
    private int cuantosubir=0;
    private int sobrante=0;
//pago con estilo---------------------------------------------------------------------------------------------------------------------------
    private void pagarConEstilo(){
        t2.schedule(new TimerTask() {
            public void run() {
                handler3.post(new Runnable() {
                    public void run() {
                        if (conteoPagoestilo == 0) {
                            cuantosubir = DineroPagoConEstilo / 20;
                            sobrante = DineroPagoConEstilo % 20;
                        }
                        if (conteoPagoestilo < 20) {
                            conteoPagoestilo++;
                            ProgresivoTV.PagarProgresivo(cuantosubir);
                            jugador[JugadorSeleccionado()].cargarapuesta(cuantosubir);
                            reproducirSonido(1);
                            pagarConEstilo();

                        }else if(conteoPagoestilo>=20 && conteoPagoestilo<20+sobrante){
                            conteoPagoestilo++;
                            ProgresivoTV.PagarProgresivo(1);
                            jugador[JugadorSeleccionado()].cargarapuesta(1);
                            reproducirSonido(1);
                            pagarConEstilo();

                        }else {
                            jugador[JugadorSeleccionado()].cargarSuperApuesta();
                            cambiarBotones();
                            restringirJugador(JugadorSeleccionado());
                            conteoPagoestilo=0;
                            DineroPagoConEstilo=0;
                            cuantosubir=0;
                            sobrante=0;
                        }
                    }
                });
            }
        }, 100);
    }
    public void reproducirSonido(int position)
    {
        //Obtenemos el id del sonido
        switch (position){
            case 1:
                sonido.play(clic);
                break;
            case 2:
                sonido.play(aviso);
                break;
            case 3:
                sonido.play(winner);
            default:
                break;
        }

    }
}

