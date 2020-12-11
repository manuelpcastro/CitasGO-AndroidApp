package com.ugr.citasgo.modelos;

import android.content.Context;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.ugr.citasgo.adaptadores.AdaptadorReservaCitas;
import com.ugr.citasgo.adaptadores.DatosCita;
import com.ugr.citasgo.adaptadores.Fecha;
import com.ugr.citasgo.adaptadores.GeneradorCitas;
import com.ugr.citasgo.adaptadores.Hora;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ModeloDia {

    Context actividad;
    Fecha fecha_;
    ArrayList<DatosCita> arrayCitas;
    GeneradorCitas generadorCitas;
    AdaptadorReservaCitas ad;

    public ModeloDia(Context contexto, String fecha, AdaptadorReservaCitas adaptador) {
        actividad = contexto;
        fecha_ = new Fecha(fecha);
        arrayCitas = new ArrayList<>();
        ad = adaptador;
        generadorCitas = new GeneradorCitas();
        generadorCitas.añadirCitas(fecha_, new Hora("08:00"), new Hora("15:00"), 60);
        getCitasServidor();

    }

    public ArrayList<DatosCita> getCitas(){
        return generadorCitas.getCitas();
    }

    private void getCitasServidor(){

        //Datos de la fecha en un JSON
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("day", fecha_.getString("day"));
            jsonObject.put("month", fecha_.getString("month"));
            jsonObject.put("year", fecha_.getString("year"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println(jsonObject.toString());

        //Hacemos POST
        AndroidNetworking.post("http://10.0.2.2:8080/api/citas")
                .addJSONObjectBody(jsonObject)
                .setTag("consulta_citas")
                .setPriority(Priority.IMMEDIATE)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {

                        System.out.println(response.toString());
                        // en respuesta
                        generadorCitas.setCitasConDisponibilidad(response);
                        ad.actualizarCitas(generadorCitas.getCitas());
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
}
