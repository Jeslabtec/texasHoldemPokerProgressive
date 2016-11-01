package com.progresivocashpoker.www.texasholdempokerprogressive;


import android.os.Handler;
import android.widget.TextView;

import org.json.JSONException;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

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
    //ANIMACION QUE PERMITE UBICAR LOS BOTONES DE PREMIO EN SU POSICION CORRECTA, COMPRENDE MOVIMIENTO EN X Y Y
    private void animaciondesplazamientoPremio() {
        float Y1 = tablero.dato.getResources().getDimension(R.dimen.ApuPreDist1);
        float Y2 = tablero.dato.getResources().getDimension(R.dimen.ApuPreDist2);
        float Y3 = tablero.dato.getResources().getDimension(R.dimen.ApuPreDist3);
        float Y4 = tablero.dato.getResources().getDimension(R.dimen.ApuPreDist4);
        float Y5 = tablero.dato.getResources().getDimension(R.dimen.ApuPreDist5);
        float Y6 = tablero.dato.getResources().getDimension(R.dimen.ApuPreDist6);

        float X1 = tablero.dato.getResources().getDimension(R.dimen.Dis_separaApuPre_1);
        float X2 = tablero.dato.getResources().getDimension(R.dimen.Dis_separaApuPre_2);
        float X3 = tablero.dato.getResources().getDimension(R.dimen.Dis_separaApuPre_3);
        float X4 = tablero.dato.getResources().getDimension(R.dimen.Dis_separaApuPre_4);
        float X5 = tablero.dato.getResources().getDimension(R.dimen.Dis_separaApuPre_5);
        float X6 = tablero.dato.getResources().getDimension(R.dimen.Dis_separaApuPre_6);
        ApuestaPremio[0].Movimientopremio(-X2, -Y2);
        ApuestaPremio[1].Movimientopremio(-X3, -Y3);
        ApuestaPremio[2].Movimientopremio(-X4, -Y4);
        ApuestaPremio[3].Movimientopremio(-X5, -Y5);
        ApuestaPremio[4].Movimientopremio(-X6, -Y6);
        ApuestaPremio[5].Movimientopremio(-X6, -Y6);
    }
    //ANIMACION QUE PERMITE UBICAR LOS BOTONES DE APUESTA EN SU POSICION CORRECTA, COMPRENDE MOVIMIENTO EN X Y Y
    private void animaciondesplazamientoApuesta() {
        float Y1 = tablero.dato.getResources().getDimension(R.dimen.ApuPreDist1);
        float Y2 = tablero.dato.getResources().getDimension(R.dimen.ApuPreDist2);
        float Y3 = tablero.dato.getResources().getDimension(R.dimen.ApuPreDist3);
        float Y4 = tablero.dato.getResources().getDimension(R.dimen.ApuPreDist4);
        float Y5 = tablero.dato.getResources().getDimension(R.dimen.ApuPreDist5);
        float Y6 = tablero.dato.getResources().getDimension(R.dimen.ApuPreDist6);

        float X1 = tablero.dato.getResources().getDimension(R.dimen.Dis_separaApuPre_1);
        float X2 = tablero.dato.getResources().getDimension(R.dimen.Dis_separaApuPre_2);
        float X3 = tablero.dato.getResources().getDimension(R.dimen.Dis_separaApuPre_3);
        float X4 = tablero.dato.getResources().getDimension(R.dimen.Dis_separaApuPre_4);
        float X5 = tablero.dato.getResources().getDimension(R.dimen.Dis_separaApuPre_5);
        float X6 = tablero.dato.getResources().getDimension(R.dimen.Dis_separaApuPre_6);

        ApuestaPremio[0].Movimientoapuesta(-X1, -Y1);
        ApuestaPremio[1].Movimientoapuesta(-X2, -Y2);
        ApuestaPremio[2].Movimientoapuesta(-X3, -Y3);
        ApuestaPremio[3].Movimientoapuesta(-X4, -Y4);
        ApuestaPremio[4].Movimientoapuesta(-X5, -Y5);
        ApuestaPremio[5].Movimientoapuesta(-X6, -Y6);
    }

    //funcion que cambia el textview mientras es undido
    //Funcion que pregunta quienes estan en cero y los bloquea
    public void restringirJugadoresjuego() {
        for (int i = 0; i < jugador.length; i++) {
            if (jugador[i].verapuesta() == 0) {
                jugador[i].SwitchAvisoApuestaAcabada(false);
                jugador[i].Bloquear();
                //CUANDO UN JUGADOR SOLO TIENE UN CREDITO EL JUEGO DEBE AVISAR AL DEALER
            }else if (jugador[i].verapuesta() == 1) {
                 //PERMITE PRENDER EL AVISO DE APUESTA ACABADA
                jugador[i].SwitchAvisoApuestaAcabada(true);
                //PRENDE EL AVISO APUESTA ACABADA
                jugador[i].avisoApuestaAcabada();
            }
        }
    }
    //FUNCION QUE ES LLAMADA CUANDO SE PRESIONA RETIRAR, SIMPLEMENTE BLOQUEA LOS JUGADORES SIN CREDITO
    public void restringirJugadoresretiro() {
        for (int i = 0; i < jugador.length; i++) {
            if (jugador[i].verapuesta() == 0) {
                jugador[i].Bloquear();
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
        //1. LUEGO DE INHHABILITAR LOS JUGADORES SIN CREDITO CARGO LA SUPERAPUESTA
        //DE LOS QUE SI TIENEN CREDITO Y LUEGO LES QUITO DE A 1 CREDITO CON APOSTEMOS
        for (int i = 0; i < tablero.mesaJuego.jugador.length; i++) {
            if (tablero.mesaJuego.jugador[i].verapuesta() > 0) {
                tablero.mesaJuego.jugador[i].cargarSuperApuesta();
                tablero.mesaJuego.jugador[i].apostemos();
            }
        }
        //CONDICIONAL QUE PREGUNTA SI HAY BONUS EN LA ESTA PARTIDA
        if(jugadaActual==jugadasBonus){
            unGanadorBonus=true;
            //CONDICIONAL QUE ASEGURA QUE JUGADA ACTUAL NO SOBREASE JUGADA BONUS
        }else if(jugadaActual>jugadasBonus){
            jugadaActual=0;
        }
        //DE ACUERDO AL NUMERO DE JUGADORES EN LA PARTIDA ACTUAL SE SETEA UN AUMENTO DEL PREMIO EN EL
        //PROGRESIVO
        ProgresivoTV.setAumentoPremio();
        //INICIO DE LA FUNCION TEMPORIZADA DEL ESTADO DE JUEGO, ENTRE OTRAS COSAS PERMITE QUE SE VEA LENTAMENTE
        //EL AUMENTO DEL PROGRESIVO
        progresivoLoco();
        //VARIABLE QUE AL IGUALARSE CON BONUS HABILITA CUALQUIERA DE LOS DOS BONUS
        jugadaActual++;
    }

    //Bonus************************************************************************************************
    //variable que dice en que jugada va a haber un ganadoR
    private int jugadasBonus = getBinomial(4, 0.5);
    //conteo de las jugadas que se reinicia cuando hay un ganador
    private int jugadaActual = 0;    //
    //PERMITE CONTAR LAS ITERACIONES EN CADA BONUS
    private int iteracionesBonus = -1;
    //INDICA EL TIEMPO QUE VA A DEMORARSE EL TEMPORIZADOR DEL BONUS EN MILISEGUNDOS
    private int tiempoBonus = 300;
    //INDICA CUAL FUE EL JUGADOR QUE GANO EL BONUS INDIVIDUAL
    private int ganadorBonus = (int) Math.floor(Math.random() * 10);
    private int pagoBonus;
    Timer t1 = new Timer();
    final Handler handler1 = new Handler();
    private int bonus1,bonus2;
    private boolean Bonusactive=true;
    private boolean unGanadorBonus=false;
    public boolean GanaronBonus=false;
    //Parte estadistica
    public int getBinomial(int n, double p) {
        int x = 0;
        for (int i = 0; i < n; i++) {
            if (Math.random() < p)
                x++;
        }
        return x;
    }
    //Timer que ejecuta las acciones visuales en el momento del bonus
    private void BonusTimer() {
        t1.schedule(new TimerTask() {
            public void run() {
                handler1.post(new Runnable() {
                    public void run() {
                        reproducirSonido(2);
                        if(bonus1>0) {
                            SeleccionarJugadorBonus();
                        }else{
                            Bonustodos();
                        }

                    }
                });
            }
        }, tiempoBonus);
    }
    //Permite rotar la ubicacion de cada jugador
    private void BonusCambio() {
        for (int i = 0; i < jugador.length; i++) {
            jugador[i].bonusScreen(false);
        }
        SeleccionarJugadorBonus();    }
    //Sirve ara ir pasando el jugador hasta que llegue al ganador
    public void SeleccionarJugadorBonus() {
        if (iteracionesBonus == -1) {
            jugador[iteracionesBonus + 1].bonusScreen(true);
        } else if (iteracionesBonus >= 0 && iteracionesBonus < 9) {
            jugador[iteracionesBonus].bonusScreen(false);
            jugador[iteracionesBonus + 1].bonusScreen(true);
        } else if (iteracionesBonus >= 9 && iteracionesBonus < 18) {
            jugador[18 - iteracionesBonus].bonusScreen(false);
            jugador[17 - iteracionesBonus].bonusScreen(true);
        } else if (iteracionesBonus >= 18) {
            jugador[iteracionesBonus - 18].bonusScreen(false);
            jugador[iteracionesBonus - 17].bonusScreen(true);
        }
        if (iteracionesBonus < 17 + ganadorBonus) {
            iteracionesBonus++;
            tiempoBonus += 20;
            BonusTimer();
        } else {
            iteracionesBonus = -1;
            pagarBonus();
        }
    }
    //Funcion que paga a un jugador el bonus
    private void pagarBonus() {
        if (jugador[ganadorBonus].verSiPausado() && jugador[ganadorBonus].jugadortv.isEnabled()) {
            pagarConEstiloBonus();
            try {
                CPPLogin.manip.EnviarMovimiento(CPPLogin.manip.idTablet, "salida", pagoBonus);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{
            EstadoBonusOff();
        }
    }
    private void Bonustodos(){
        if(iteracionesBonus<2*pagoBonus-2){
            for(int i=0;i<jugador.length;i++){
                jugador[i].bonusScreen(Bonusactive);
                if (jugador[i].verSiPausado() && jugador[i].jugadortv.isEnabled() && Bonusactive) {
                    ProgresivoTV.PagarProgresivo(1);
                    jugador[i].cargarapuesta(1);
                    jugador[i].cargarSuperApuesta();
                    ;
                }
            }
            iteracionesBonus++;
            Bonusactive=!Bonusactive;
            BonusTimer();
        }else{
            iteracionesBonus=-1;
            PagarBonusTodos();
            Bonusactive=true;
        }
    }
    private void PagarBonusTodos(){
        int contganadores=0;
        for(int i=0;i<jugador.length;i++){
            if (jugador[i].verSiPausado() && jugador[i].jugadortv.isEnabled()) {
                jugador[i].cargarSuperApuesta();
                contganadores++;
            }
        }
        EstadoBonusOff();
        try {
            CPPLogin.manip.EnviarMovimiento(CPPLogin.manip.idTablet,"salida",pagoBonus*contganadores);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        };
    }
    private void EstadoBonusOff(){
        for(int i=0;i<jugador.length;i++){
            if (!jugador[i].verSiPausado()) {
                jugador[i].ponerPausado();
                jugador[i].ponerPausado();
            }else if(jugador[i].jugadortv.isEnabled()) {
                jugador[i].Habilitar();
                if(jugador[i].verapuesta()==0){
                    jugador[i].SwitchAvisoApuestaAcabada(true);
                    jugador[i].avisoApuestaAcabada();
                }
            }else{
                jugador[i].Bloquear();
            }
        }
        GanaronBonus=false;
        retirarseTV.Bloquear();
        pagarTV.Habilitar();
        jugarTV.Seleccionar();
        apostarTV.Habilitar();
        AvisoTV.setBackgroundResource(R.drawable.avisojugar);
        AvisoTV.setText(R.string.Jugar);
        animaciondesplazamientoPremio();
    }
    private void EstadoBonusOn() {
        retirarseTV.Bloquear();
        pagarTV.Bloquear();
        jugarTV.Bloquear();
        apostarTV.Bloquear();
        AvisoTV.setBackgroundResource(R.drawable.avisobonus);
        if(bonus1>0) {
            AvisoTV.setText("+"+String.valueOf(pagoBonus)+ " JUGADOR");
        }else{
            AvisoTV.setText("+"+String.valueOf(pagoBonus)+ " JUGADORES");
        }
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
                            if (unGanadorBonus) {
                                GanaronBonus=true;
                                unGanadorBonus=false;
                                jugadaActual = 0;
                                jugadasBonus = 3;
                                //bonus1=getBinomial(16,0.0625);
                                //bonus2=getBinomial(160,0.1875);
                                bonus1=(bonus1==0)?(1):(0);
                                if (bonus1>0) {
                                    ganadorBonus = (int) Math.floor(Math.random() * 10);
                                    pagoBonus= getBinomial(5,0.2)+5;
                                    tiempoBonus=300;
                                    EstadoBonusOn();
                                    BonusCambio();
                                }
                                else{
                                    pagoBonus= getBinomial(5,0.2)+5;
                                    EstadoBonusOn();
                                    tiempoBonus=Math.round(3000/pagoBonus);
                                    Bonustodos();
                                }
                            }else{
                                    retirarseTV.Habilitar();
                                    pagarTV.Habilitar();
                                    jugarTV.Seleccionar();
                                    apostarTV.Habilitar();
                                }
                            iteracionesProgresivoLoco = 0;
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
        Bono1TV.setBackgroundResource(R.drawable.cartamazo);
        Bono2TV.setBackgroundResource(R.drawable.cartamazo);
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
        Bono1TV.setBackgroundResource(R.drawable.cartamazo);
        Bono2TV.setBackgroundResource(R.drawable.cartamazo);
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
        restringirJugadoresjuego();
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
        restringirJugadoresretiro();
        AvisoTV.setBackgroundResource(R.drawable.avisoretirarse);
        AvisoTV.setText(R.string.Retirar);
        animaciondesplazamientoPremio();
        Bono1TV.setBackgroundResource(R.drawable.cartamazo);
        Bono2TV.setBackgroundResource(R.drawable.cartamazo);
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
        cuantosubir = DineroPagoConEstilo / 20;
        sobrante = DineroPagoConEstilo % 20;
        conteoPagoestilo=(cuantosubir==0)?(20):(0);

        retirarseTV.Bloquear();
        pagarTV.Bloquear();
        jugarTV.Bloquear();
        apostarTV.Bloquear();
        pagarConEstilo();
        return (int) pago;
    }
    final Handler handler3 = new Handler();
    private int DineroPagoConEstilo=0;
    private int conteoPagoestilo=0;
    private int cuantosubir=0;
    private int sobrante=0;
//pago con estilo---------------------------------------------------------------------------------------------------------------------------
private void pagarConEstilo(){
    t1.schedule(new TimerTask() {
        public void run() {
            handler3.post(new Runnable() {
                public void run() {
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
                        restringirJugador(JugadorSeleccionado());
                        cambiarBotones();
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
    private void pagarConEstiloBonus(){
        t1.schedule(new TimerTask() {
            public void run() {
                handler3.post(new Runnable() {
                    public void run() {
                        if(conteoPagoestilo<pagoBonus){
                            conteoPagoestilo++;
                            ProgresivoTV.PagarProgresivo(1);
                            jugador[ganadorBonus].cargarapuesta(1);
                            reproducirSonido(1);
                            pagarConEstiloBonus();
                        }else {
                            jugador[ganadorBonus].cargarSuperApuesta();
                            conteoPagoestilo=0;
                            EstadoBonusOff();
                        }
                    }
                });
            }
        }, 500);
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

