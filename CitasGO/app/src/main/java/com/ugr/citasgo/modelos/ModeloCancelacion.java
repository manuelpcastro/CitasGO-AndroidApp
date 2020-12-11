package com.ugr.citasgo.modelos;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.ugr.citasgo.adaptadores.Fecha;
import com.ugr.citasgo.adaptadores.Hora;
import com.ugr.citasgo.Presentadores.Operaciones;

import org.json.JSONException;
import org.json.JSONObject;

public class ModeloCancelacion {

    Context actividad;
    String id_usuario;
    String id_cita;
    Fecha dia_;
    Hora hora_;

    public ModeloCancelacion(Context contexto, String id_cita, String id){
        this.actividad = contexto;
        this.id_cita = id_cita;
        this.id_usuario = id;
    }

    public void postCancelacion(){


        //Datos de inicio de sesion en un JSON
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("_id", id_cita);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        //Hacemos POST
        AndroidNetworking.post("http://10.0.2.2:8080/api/citas/delete")
                .addBodyParameter("_id", id_cita)
                .setTag("cancelar_cita")
                .setPriority(Priority.IMMEDIATE)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        // en respuesta
                        if(response.contains("1:")) {
                            Toast.makeText(actividad, "Cancelacion Correcta", Toast.LENGTH_SHORT).show();

                            //Pasamos a la pantalla de seleccion

                        }
                        else {
                            Toast.makeText(actividad, response, Toast.LENGTH_SHORT).show();
                        }
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
}
