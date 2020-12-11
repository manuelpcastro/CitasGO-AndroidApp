package com.ugr.citasgo.modelos;

import android.content.Context;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

public class ModeloLogin {

    Context actividad;
    String autenticacion;
    Boolean identificado;

    public ModeloLogin(Context contexto){
        actividad = contexto;
        identificado = false;
    }

    public String getAutenticacion() {
        return autenticacion;
    }

    public Boolean getIdentificado() {
        return identificado;
    }

    public void postLogin(String usuario, String pass){

        AndroidNetworking.initialize(actividad);

        //Datos de inicio de sesion en un JSON
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", usuario);
            jsonObject.put("pass", pass);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        //Hacemos POST
        AndroidNetworking.post("http://10.0.2.2:8080/api/login")
                .addBodyParameter("email", usuario)
                .addBodyParameter("pass", pass)
                .setTag("login")
                .setPriority(Priority.IMMEDIATE)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        // en respuesta
                        if(!response.contains("-1:")) {
                            Toast.makeText(actividad, "Login Correcto", Toast.LENGTH_SHORT).show();
                            autenticacion = response.replace("\"", "");
                            identificado = true;
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
