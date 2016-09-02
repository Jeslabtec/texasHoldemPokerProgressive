package com.progresivocashpoker.www.texasholdempokerprogressive;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Santigo Lopez on 02/06/2016.
 * Clase que crea los objetos apuesta premio.
 * Estos objetos tienen la respnsabilidad de ademas de poner el valor de la apuesta
 * en los jugadores, guarda cada uno de los 6 premios disponibles en el juego.
 * Esta clase permite cambiar las características físicas de dchos botones y guardar los valores
 * del porcentaje y los valores de apuesta
 */
public class ClaseApuestaPremio {
    //Cada uno de los posibles valres que pueden tener las monedas (preguntar a lopez porque esta el valor 0.0

    private final int[] VectorValSuma = {1,2,0} ;
    // Objeto de la UI, en este caso un textview, ver como lo puedo retirar
    private TextView ApuestaPremioTV;
    //String que guarda el porcentaje para mostrarlo en el textview
    private int Premio;
    // double donde se termina guardando efectivamente el porcentaje por motivos de calculo
    //Double que guarda el valor de la suma
    private int valorSuma;

    //Solo se usa en el apuesta premio del mas.
    private boolean Restando=false;


    //Permite saber al objeto cual boton es
    private int QuienSoy;

    public  ClaseApuestaPremio(TextView v, int i){
        //i es un valor que indica cual es el numero del boton
        //configuracion del textview
        ApuestaPremioTV=v;
        ApuestaPremioTV.setOnClickListener(new lTVClickBtnApeustasPremios());
        QuienSoy=i;
        Premio=CPPLogin.manip.verPorcentajePremio(i);
        valorSuma=VectorValSuma[i];


    }
    //Pone los botones en forma de premio
    public void BotonesPremio(){

        ApuestaPremioTV.setText(String.valueOf(Premio));
        ApuestaPremioTV.setVisibility(View.VISIBLE);
        switch (QuienSoy)
        {case 0:
            ApuestaPremioTV.setBackgroundResource(R.drawable.premioescalerareal);
            break;
            case 1:
                ApuestaPremioTV.setBackgroundResource(R.drawable.premioescaleracolor);
                break;
            case 2:
                ApuestaPremioTV.setBackgroundResource(R.drawable.premiopoker);
                break;
            case 3:
                ApuestaPremioTV.setBackgroundResource(R.drawable.premiofull);
                break;
            case 4:
                ApuestaPremioTV.setBackgroundResource(R.drawable.premiocolor);
                break;
            case 5:
                ApuestaPremioTV.setBackgroundResource(R.drawable.premioescalerasucia);
                break;
        }

    }
    //pone a los botones en forma de apuesta
    public void BotonesApuesta(){
        ApuestaPremioTV.setVisibility(View.VISIBLE);
        if(QuienSoy==2){
            ApuestaPremioTV.setText("+");
        }else{

            ApuestaPremioTV.setText(String.valueOf(valorSuma));
        }
        ApuestaPremioTV.setBackgroundResource(R.drawable.botonapuesta);
    }
    public void Movimientopremio(int finX,int finY){
        ObjectAnimator animacionX= ObjectAnimator.ofFloat(ApuestaPremioTV, View.TRANSLATION_X,0,finX);
        ObjectAnimator animacionY= ObjectAnimator.ofFloat(ApuestaPremioTV, View.TRANSLATION_Y,0,finY);
        ObjectAnimator Agrandadorx = ObjectAnimator.ofFloat(ApuestaPremioTV, View.SCALE_X, 1.3f);
        ObjectAnimator Agrandadory = ObjectAnimator.ofFloat(ApuestaPremioTV, View.SCALE_Y, 1.3f);
        AnimatorSet conjunto = new AnimatorSet();
        conjunto.playTogether(animacionX,animacionY,Agrandadorx,Agrandadory);
        conjunto.setInterpolator(new FastOutLinearInInterpolator());
        conjunto.setDuration(1000);
        conjunto.start();
    }
    public void Movimientoapuesta(int finX,int finY){
        ObjectAnimator animacionX= ObjectAnimator.ofFloat(ApuestaPremioTV, View.TRANSLATION_X,0,finX);
        ObjectAnimator animacionY= ObjectAnimator.ofFloat(ApuestaPremioTV, View.TRANSLATION_Y,0,finY);
        ObjectAnimator Agrandadorx = ObjectAnimator.ofFloat(ApuestaPremioTV, View.SCALE_X, 1f);
        ObjectAnimator Agrandadory = ObjectAnimator.ofFloat(ApuestaPremioTV, View.SCALE_Y, 1f);
        AnimatorSet conjunto = new AnimatorSet();
        conjunto.playTogether(animacionX,animacionY,Agrandadorx,Agrandadory);
        conjunto.setInterpolator(new FastOutLinearInInterpolator());
        conjunto.setDuration(1000);
        conjunto.start();

    }

    //desaparece los botones
    public void BotonesDesaparecer(){
        ApuestaPremioTV.setVisibility(View.INVISIBLE);
    }
    //me devuelve el valor numerico del icono

    public int ValorNumerico(){
        if(tablero.mesaJuego.verElEstadoDelJuego()==3){
            return valorSuma;
        }else if(tablero.mesaJuego.verElEstadoDelJuego()==1){
            return  Premio;
        }
        return 0;
    }
    boolean verSiRestando() {
        return Restando;
    }

    //Pone a sumar el dispositivo, me asugura que cada vez que se inicie la fase de apostar este configurado en suma
    void ponerSumando() {
        Restando = false;
        ApuestaPremioTV.setText("+");
    }

    //Cambia a restar o sumar cuando se presiona ese boton
    void cambiarRestando() {
        if (Restando) {
            Restando = false;
            ApuestaPremioTV.setText("+");

        } else {
            Restando = true;
            ApuestaPremioTV.setText("-");
        }
    }

}
