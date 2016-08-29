package com.progresivocashpoker.www.texasholdempokerprogressive;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.TextView;

/**
 * Created by user on 02/06/2016.
 */

public class ControlesJuego {
    private TextView ControlTV;
    private boolean Seleccionado=false;

    private int quienSoy;
    private boolean grande=false;


    public ControlesJuego(TextView v, int i) {
        ControlTV = v;
        ControlTV.setOnClickListener(new lTVClickControlesJuego());
        quienSoy=i;
    }

    public void Bloquear() {
        Hacer_peque();
        ControlTV.setBackgroundResource(R.drawable.fichagris);
        ControlTV.setEnabled(false);
        Seleccionado=false;
    }
    public void Habilitar(){
        Hacer_peque();
        switch (quienSoy) {
            case 1:
                ControlTV.setBackgroundResource(R.drawable.fichapagar);
                break;
            case 2:
                ControlTV.setBackgroundResource(R.drawable.fichajugar);
                break;
            case 3:
                ControlTV.setBackgroundResource(R.drawable.fichaacreditar);
                break;
            case 4:
                ControlTV.setBackgroundResource(R.drawable.ficharetirar);
                break;
        }
        ControlTV.setEnabled(true);
        Seleccionado=false;
    }
    public void Seleccionar(){
        Hacer_grande();
        switch (quienSoy) {
            case 1:
                ControlTV.setBackgroundResource(R.drawable.fichapagar);
                break;
            case 2:
                ControlTV.setBackgroundResource(R.drawable.fichajugar);
                break;
            case 3:
                ControlTV.setBackgroundResource(R.drawable.fichaacreditar);
                break;
            case 4:
                ControlTV.setBackgroundResource(R.drawable.ficharetirar);
                break;
        }
        ControlTV.setEnabled(true);
        Seleccionado=true;
    }
    private void Hacer_grande() {
        if (!grande) {
            ObjectAnimator Agrandadorx = ObjectAnimator.ofFloat(ControlTV, View.SCALE_X, 1.3f);
            ObjectAnimator Agrandadory = ObjectAnimator.ofFloat(ControlTV, View.SCALE_Y, 1.3f);
            AnimatorSet conjunto = new AnimatorSet();
            conjunto.playTogether(Agrandadorx, Agrandadory);
            conjunto.setInterpolator(new BounceInterpolator());
            conjunto.setDuration(500);
            conjunto.start();
            grande = true;
        }
    }
    private void Hacer_peque(){
            if(grande){
                ObjectAnimator Agrandadorx= ObjectAnimator.ofFloat(ControlTV, View.SCALE_X,1f);
                ObjectAnimator Agrandadory= ObjectAnimator.ofFloat(ControlTV, View.SCALE_Y,1f);
                AnimatorSet conjunto=new AnimatorSet();
                conjunto.playTogether(Agrandadorx,Agrandadory);
                conjunto.setInterpolator(new BounceInterpolator());
                conjunto.setDuration(500);
                conjunto.start();
                grande=false;
            }
    }
}
