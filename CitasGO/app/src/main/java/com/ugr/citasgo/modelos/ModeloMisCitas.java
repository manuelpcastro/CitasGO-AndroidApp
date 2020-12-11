package com.ugr.citasgo.modelos;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.ugr.citasgo.adaptadores.AdaptadorMisCitas;
import com.ugr.citasgo.adaptadores.DatosCita;
import com.ugr.citasgo.adaptadores.Fecha;
import com.ugr.citasgo.adaptadores.Hora;
import com.ugr.citasgo.adaptadores.Servicio;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class ModeloMisCitas {

    ArrayList<DatosCita> arrayCitas;
    Context actividad;
    String id_usuario;
    JSONArray array;
    AdaptadorMisCitas ad;

    public ModeloMisCitas(Context contexto, String id, AdaptadorMisCitas adaptador){
        arrayCitas = new ArrayList<>();
        this.actividad = contexto;
        id_usuario = id;
        ad = adaptador;
        getCitasServidor(id_usuario);
    }

    public ArrayList<DatosCita> getCitas(){
        return arrayCitas;
    }



    private void getCitasServidor(String id_usuario){

        //Hacemos GET
        AndroidNetworking.get("http://10.0.2.2:8080/api/citas/usuario")
                .addQueryParameter("_id", id_usuario)
                .setTag("consulta_citas")
                .setPriority(Priority.IMMEDIATE)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // en respuesta
                        System.out.println(response.toString() + "\n\n AHORA ");
                        array = response;
                        conseguirCitas(array);
                        ad.actualizarCitas(getCitas());
                        Toast.makeText(actividad, "Cargando citas", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        System.out.println("onError errorCode : " + error.getErrorCode());
                        System.out.println("onError errorBody : " + error.getErrorBody());
                        System.out.println("onError errorDetail : " + error.getErrorDetail());
                        Toast.makeText(actividad,error.toString(),Toast.LENGTH_SHORT).show();

                    }
                });

    }

    public void conseguirCitas(JSONArray arrayUsuario){

        String id;
        Hora hora;
        Fecha fecha;
        Servicio servicio;

        DatosCita cita;

        for(int i=0; i<arrayUsuario.length(); i++) {
            // Cogemos la hora
            try {
                String actual;
                actual = arrayUsuario.getJSONObject(i).getString("_id");
                id = actual;
                actual = arrayUsuario.getJSONObject(i).getString("servicio");
                servicio = Servicio.getServicio(actual);
                actual = arrayUsuario.getJSONObject(i).getString("hora_inicio");
                hora = new Hora(actual+":00");
                actual = arrayUsuario.getJSONObject(i).getString("day") + "/" + arrayUsuario.getJSONObject(i).getString("month") + "/" + arrayUsuario.getJSONObject(i).getString("year");
                fecha = new Fecha(actual);
                cita = new DatosCita(fecha,hora,false, servicio);
                cita.setID(id);
                arrayCitas.add(cita);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
