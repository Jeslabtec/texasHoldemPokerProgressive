package com.progresivocashpoker.www.texasholdempokerprogressive;

import android.widget.TextView;

import java.text.NumberFormat;

/**
 * Created by user on 02/06/2016.
 * Clase que controla todo lo relacionado con el valor del progresivo
 */

public class ClaseDelProgresivo {
    public TextView ProgresivoTV;

    private int ValorFicha;
    private double Rand1;
    private double Rand2;
    private double AumentoPremio;
    private double AumentoPremioAnterior=0;
    private double Dinero;
    NumberFormat format = NumberFormat.getCurrencyInstance();



    // construtctor de la clase del progresivo
    public ClaseDelProgresivo(TextView v) {
        ProgresivoTV = v;
        ValorFicha = CPPLogin.manip.verValorFicha();
        Rand1 = Math.random();
        Rand2 = 0;
        Dinero =(double) CPPLogin.manip.verDineroProgresivo();
        AumentoPremio = Dinero;
        ProgresivoTV.setText(String.valueOf(format.format(CPPLogin.manip.verDineroProgresivo())));
    }





    public void cambiePremio(int nuevoValor)
    {
        AumentoPremio=nuevoValor;
    }



    public void setAumentoPremio(){
        AumentoPremio+= Math.round((double)tablero.mesaJuego.cuantosJugando())*ValorFicha*(Rand1)*CPPLogin.manip.verPorcentajeProgresivo()+AumentoPremioAnterior;
        Rand2=1-Rand1;
        AumentoPremioAnterior= Math.round((double)tablero.mesaJuego.cuantosJugando())*ValorFicha*(Rand2)*CPPLogin.manip.verPorcentajeProgresivo();
        Rand1= Math.random();
    }


    public void aumentoAleatorio(){
        Dinero=(1-0.7788) *  (AumentoPremio) + (0.7788) * (Dinero);
        ProgresivoTV.setText(format.format(Math.round(Dinero)));
    }


    public double ValorDelProgresivo(){return Dinero;}


    public void PagarProgresivo(int fichas){
        Dinero-=fichas*CPPLogin.manip.verValorFicha();
        AumentoPremio-=fichas*CPPLogin.manip.verValorFicha();
        if(Dinero<CPPLogin.manip.verMinimoProgresivo()){
            CPPLogin.manip.setDineroEnProgresivo(CPPLogin.manip.verMinimoProgresivo());
            Dinero=(double) CPPLogin.manip.verMinimoProgresivo();
            AumentoPremio=Dinero;
            ProgresivoTV.setText(format.format(CPPLogin.manip.verMinimoProgresivo()));
        }else{
            CPPLogin.manip.setDineroEnProgresivo((int)Dinero);
            ProgresivoTV.setText(format.format(Dinero));
        }
    }
}
