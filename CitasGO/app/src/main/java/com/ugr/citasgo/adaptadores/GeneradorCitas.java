package com.ugr.citasgo.adaptadores;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class GeneradorCitas {

    ArrayList<DatosCita> citas;

    public GeneradorCitas(){
        citas = new ArrayList<>();
    }

    public void añadirCitas(Fecha fecha, Hora hora_inicio, Hora hora_fin, int intervalo_minutos){
        Hora hora_actual = hora_inicio;
        //Vamos añadiendo citas posibles mientras
        while(!hora_actual.esMayorQue(hora_fin)){
            if(!comprobarHora(hora_actual)) {
                citas.add(new DatosCita(fecha, hora_actual, true, null));
            }
            hora_actual = hora_actual.sumarMinutos(intervalo_minutos);
        }
    }

    private boolean comprobarHora(Hora hora){
        for(int i=0; i<citas.size(); i++){
            if(citas.get(i).getHoraC().getString("hora").equals(hora.getString("hora")))
                return true;
        }
        return false;
    }



    public void setCitasConDisponibilidad(JSONArray arrayOcupadas){
        String hora = "";
        ArrayList<DatosCita> nuevas_citas = new ArrayList<>();

        for(int j=0; j<citas.size(); j++){

            DatosCita cita = citas.get(j);

            for(int i=0; i<arrayOcupadas.length(); i++) {



                // Cogemos la hora
                try {
                    hora = (String) arrayOcupadas.getJSONObject(i).getString("hora_inicio");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //Vemos si coincide con alguna de las disponibles y la modificamos
                Hora hora_ocupada = new Hora(hora + ":00");

                if(cita.getHora().equals(hora_ocupada.toString())){
                    cita.setDisponibilidad(false);
                }

            }

            nuevas_citas.add(citas.get(j));
        }

        citas = nuevas_citas;
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

                citas.add(cita);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    public ArrayList<DatosCita> getCitas() {
        return citas;
    }
}
