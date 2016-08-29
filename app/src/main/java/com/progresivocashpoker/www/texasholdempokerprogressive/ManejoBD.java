package com.progresivocashpoker.www.texasholdempokerprogressive;

import android.os.AsyncTask;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutionException;


/**
 * Clase para el manejo de la conexíon con la base de datos. La conexion se realizara a traves de
 * servicios API, similares a los servicios API de Facebook y Google utilizando php
 */
public class ManejoBD  {
    private int DineroEnProgresivo=0;
    private int ValorFicha=0;
    private double[] PorcentajePremios=new double[6];
    private double PorcentajeAumento;
    private int minimoProgresivo;
    public Integer idTablet=-1;
    public Integer idSede=-1;
    public Integer idDealer=0;// aun falta solucionar el primer problema con el valor del dealer
    /*
    * Funcion login: revisa si las contraseñas suministradas son iguales a las reales
    * */
    public boolean Login(String Usuario, String pass) throws JSONException, ExecutionException, InterruptedException {
        boolean activo=false;
        int resultactive=0;
        JSONObject json=new JSONObject();
        json.put("USR",Usuario);
        json.put("PW",pass);
        String[] parametros={"/isActive",json.toString()};
        String loggin = new ManejoPOST().execute(parametros).get();
        JSONObject jsonresponse=new JSONObject(loggin);
        if(jsonresponse.getString("ERROR").equals("OK")){
            resultactive=jsonresponse.getInt("Active");
            if(resultactive==0){
                Toast.makeText(CPPLogin.ContextoLogin,"Producto no Activado", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(CPPLogin.ContextoLogin,"Iniciando la Aplicacion", Toast.LENGTH_SHORT).show();
                this.idTablet=jsonresponse.getInt("idTablet");        //poner en rest para devolver idTablet
                this.idSede=jsonresponse.getInt("idSede");
                this.DineroEnProgresivo=jsonresponse.getInt("valorProgresivo");
                this.ValorFicha=jsonresponse.getInt("valorFichas");
                this.PorcentajeAumento=jsonresponse.getDouble("pAumento");
                this.minimoProgresivo= jsonresponse.getInt("minimoProgresivo");
                JSONArray porcentajes = jsonresponse.getJSONArray("porcentajes");
                for (int i=0;i<porcentajes.length();i++){
                    this.PorcentajePremios[i]=porcentajes.getDouble(i);
                    }
                activo=true;
            }
        }
        else{
           Toast.makeText(CPPLogin.ContextoLogin,"Error: Usuario o Contraseña incorrectos", Toast.LENGTH_SHORT).show();
        }
        return(activo);
    }
    public boolean VerificarClave(String pass, String relacion) throws ExecutionException, InterruptedException, JSONException { // sobre el rest para esta funcion el URL termina en /isEmployed, datos USR, PW y SID, devuelve relacion.
        boolean result=false;
        JSONObject json=new JSONObject();
        json.put("PW",pass);
        json.put("SID",this.idSede);
        json.put("R",relacion);
        String[] parametros={"/isEmployed",json.toString()};
        String verification = new ManejoPOST().execute(parametros).get();
        JSONObject jsonresponse=new JSONObject(verification);
        if(jsonresponse.getInt("idEmpleado")==-1){
            Toast.makeText(CPPLogin.ContextoLogin,"no existe un "+relacion+" con esa contraseña", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(CPPLogin.ContextoLogin,"Pago validado", Toast.LENGTH_SHORT).show();
            result=true;
        }

        return result;
    }


    /*
    *Actualizaciones en el servidor para realizar tablas administrativas
    * */

    public void EnviarMovimiento(int idTab, String oper, int value) throws ExecutionException, InterruptedException, JSONException {
        JSONObject json=new JSONObject(),jsonresp;
        boolean salida=false;
        json.put("TID",idTab);
        json.put("TOP",oper);
        json.put("VOP",value);
        String[] parametros={"/setMovement",json.toString()};
        do {
            String respuesta = new ManejoPOST().execute(parametros).get();
            jsonresp=new JSONObject(respuesta);
            salida=jsonresp.getBoolean("result");
        }while (!salida);
       // Toast.makeText(tablero.dato,"hecho",Toast.LENGTH_SHORT).show();
    }
    public void GuardarTabla(int idTab,int valprogresive) throws ExecutionException, InterruptedException, JSONException {
        JSONObject json=new JSONObject(),jsonresp;
        boolean salida=false;
        json.put("TID",idTab);
        json.put("VP",valprogresive);
        json.put("BBote",1000);
        String[] parametros={"/saveProgressive",json.toString()};
        do {
            String respuesta = new ManejoPOST().execute(parametros).get();
            jsonresp=new JSONObject(respuesta);
            salida=jsonresp.getBoolean("result");
        }while (!salida);
        // Toast.makeText(tablero.dato,"hecho",Toast.LENGTH_SHORT).show();
    }

    public int verDineroProgresivo(){return DineroEnProgresivo;}
    public int verValorFicha(){
        return ValorFicha;
    }
    public int verPorcentajePremio(int i){
        return (int) PorcentajePremios[i];
    }

    public void setDineroEnProgresivo(int nuevoValor){this.DineroEnProgresivo=nuevoValor;}

    public double verPorcentajeProgresivo(){return PorcentajeAumento;} // esta funcion determina cuanto en total debe subir el progresivo
    public int verMinimoProgresivo(){return minimoProgresivo;} // esta funcion determina cuanto es el minimo del progresivo



    // codigo funcional para realizar consultas a traves del get en Http
    private class ManejoGET extends AsyncTask<String,String,String> {
        @Override
        protected String doInBackground (String... URI){
                String strrespuesta="Iniciando Conexión";
            publishProgress(strrespuesta);
                HttpGet getprueba = new HttpGet("http://www.progresivocashpoker.com/THPServices/index.php"+URI[0]);
                HttpClient cliente = new DefaultHttpClient();

             try{
                 HttpResponse respuesta=cliente.execute(getprueba);
                 String strresp= new BufferedReader(new InputStreamReader(respuesta.getEntity().getContent())).readLine();
                 strrespuesta=strresp;
                 //Toast.makeText(CPPLogin.ContextoLogin,strresp,Toast.LENGTH_LONG).show();
             }
             catch (IOException ex){
                 strrespuesta=ex.getMessage();
                 //Toast.makeText(CPPLogin.ContextoLogin,ex.getMessage(),Toast.LENGTH_LONG).show();
             }
            return (strrespuesta);
            // return("hola mundo");
        }



        @Override
        protected void onPostExecute(String strrespuesta){
            Toast.makeText(CPPLogin.ContextoLogin,strrespuesta, Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onProgressUpdate(String... progreso){
            Toast.makeText(CPPLogin.ContextoLogin,progreso[0], Toast.LENGTH_LONG).show();
        }
    }
    private class ManejoPOST extends AsyncTask<String,String,String> {
        @Override
        protected String doInBackground (String... DatosPOST){
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost("http://www.progresivocashpoker.com/THPServices/index.php"+DatosPOST[0]);
            post.setHeader("Content-type", "application/json; charset=UTF-8");
            post.setHeader("Accept", "application/json");
            try {
                //"/isActive","{"USR":Usuario,"PW":Contraseña}"
                HttpEntity entity= new StringEntity(DatosPOST[1]);
                post.setEntity(entity);
                HttpResponse response = client.execute(post);// ":usuarioLogin";"password",":contraseñaLogin"} y la URL
                //"{"activo":valor,"idTablet":valor,"valorProgresivo"}"
                return (new BufferedReader(new InputStreamReader(response.getEntity().getContent())).readLine());
            }
            catch (IOException e) {
               return (e.getMessage());
            }
        }

    }


}



