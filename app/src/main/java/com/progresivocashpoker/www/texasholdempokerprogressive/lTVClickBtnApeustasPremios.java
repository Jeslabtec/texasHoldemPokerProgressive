package com.progresivocashpoker.www.texasholdempokerprogressive;

import android.view.View;

/**
 * Created by JuanEsteban on 02/05/2016.
 */
public class lTVClickBtnApeustasPremios implements View.OnClickListener   {


    public int Apuesta (int id) {
        switch (id) {
            case R.id.tvPremioApuesta1:
                 tablero.mesaJuego.SeleccionarApuPre(0);
                return 0;
            case R.id.tvPremioApuesta2:
                tablero.mesaJuego.SeleccionarApuPre(1);
                return 1;
            case R.id.tvPremioApuesta3:
                if (tablero.mesaJuego.verElEstadoDelJuego() == 3) {
                    tablero.mesaJuego.ApuestaPremio[2].cambiarRestando();
                    return 2;
                }else{
                    tablero.mesaJuego.SeleccionarApuPre(2);
                    return 2;
                }
            default:
                return -1;

        }

    }

    @Override
    public void onClick(View v)
    {
        int i=Apuesta(v.getId());

        switch (tablero.mesaJuego.verElEstadoDelJuego())

           {//----------------------------------------------------------------------------------------------------//
               case 1: // fase pagar


                   if (i==0 || i==1) {


                       tablero.mesaJuego.necesariosupervisor = true;}
                   else{
                       tablero.mesaJuego.necesariosupervisor = false;
                   }
                   //Configura si se necesita supervisor o no.

                   if (tablero.mesaJuego.JugadorSeleccionado()>=0) {
                       tablero.mesaJuego.mensaje.msgConfirmarPago().show();
                   }
                   else
                   {
                       tablero.mesaJuego.mensaje.msgErrorApuesta().show();
                   }

               break;
               //-----------------------------------------------------------------------------------------------------------------------//
               case 2: //jugar

               break;
               //-----------------------------------------------------------------------------------------------------------------//
               case 3: //apostar
                   int jug=tablero.mesaJuego.JugadorSeleccionado();
                   int Apu=0;

                   if(i!=2) {
                       Apu = tablero.mesaJuego.ApuestaPremio[i].ValorNumerico();
                   }
                   if (jug>=0) {
                       if (tablero.mesaJuego.ApuestaPremio[2].verSiRestando()) {
                           tablero.mesaJuego.jugador[jug].cargarapuesta(-Apu);
                       }else{
                           tablero.mesaJuego.jugador[jug].cargarapuesta(Apu);
                       }
                   }
                   else
                   {
                        tablero.mesaJuego.mensaje.msgErrorApuesta().show();
                   }
                   //tablero.mesaJuego.dealerJuego.tomarFicha(i);
               break;
               //---------------------------------------------------------------------------------------------------------------------------------------//
               case 4: //retirar

               break;
           }
    }
}
