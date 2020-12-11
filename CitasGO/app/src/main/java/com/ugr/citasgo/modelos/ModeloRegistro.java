package com.ugr.citasgo.modelos;

import android.content.Context;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

public class ModeloRegistro {

    Context actividad;
    String autenticacion;
    Boolean identificado;

    public ModeloRegistro(Context contexto){
        identificado = false;
        actividad = contexto;
    }

    public String getAutenticacion() {
        return autenticacion;
    }

    public Boolean getIdentificado() {
        return identificado;
    }

    public void postRegistro(String nombre, String email, String pass) {

        AndroidNetworking.initialize(actividad);

        //Datos de registro en un JSON
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("nombre", nombre);
            jsonObject.put("email", email);
            jsonObject.put("pass", pass);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Hacemos POST
        AndroidNetworking.post("http://10.0.2.2:8080/api/registro")
                .addJSONObjectBody(jsonObject)
                .setTag("registro_usuario")
                .setPriority(Priority.IMMEDIATE)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        if(!response.contains("-1:")) {
                            // en respuesta
                            Toast.makeText(actividad, "Registro Correcto", Toast.LENGTH_SHORT).show();
                            autenticacion = response.replace("\"", "");
                            identificado=true;
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
                        Toast.makeText(actividad, "Los datos no son correctos: " + error, Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
