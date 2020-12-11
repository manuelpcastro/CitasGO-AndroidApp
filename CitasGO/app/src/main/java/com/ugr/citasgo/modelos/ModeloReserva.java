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

public class ModeloReserva {

    Context actividad;
    String id_usuario;
    Boolean reservado;

    Fecha dia_;
    Hora hora_;

    public ModeloReserva(Context contexto, String dia, String hora, String id){
        actividad = contexto;
        dia_ = new Fecha(dia);
        hora_ = new Hora(hora);
        id_usuario = id;
        reservado=false;
    }

    public Boolean getReservado() {
        return reservado;
    }

    public void postReservaCita(String servicio){


        //Datos de inicio de sesion en un JSON
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id_usuario);
            jsonObject.put("servicio", servicio);
            jsonObject.put("day", dia_.getString("day"));
            jsonObject.put("month", dia_.getString("month"));
            jsonObject.put("year", dia_.getString("year"));
            jsonObject.put("hora_inicio", hora_.getString("hora"));
            jsonObject.put("hora_fin", hora_.sumarMinutos(60).getString("hora"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Hacemos POST
        AndroidNetworking.post("http://10.0.2.2:8080/api/reserva")
                .addJSONObjectBody(jsonObject)
                .setTag("reserva_cita")
                .setPriority(Priority.IMMEDIATE)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        // en respuesta
                        if(!response.equals("correcto")) {
                            Toast.makeText(actividad, "Reserva Correcta", Toast.LENGTH_SHORT).show();
                            reservado = true;
                        }
                        else {
                            Toast.makeText(actividad, response, Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                     /*   System.out.println("onError errorCode : " + error.getErrorCode());
                        System.out.println("onError errorBody : " + error.getErrorBody());
                        System.out.println("onError errorDetail : " + error.getErrorDetail()); */
                        Toast.makeText(actividad,error.toString(),Toast.LENGTH_SHORT).show();
                    }
                });

    }
}
