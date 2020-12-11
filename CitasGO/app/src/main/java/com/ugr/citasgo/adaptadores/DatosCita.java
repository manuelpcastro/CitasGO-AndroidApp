package com.ugr.citasgo.adaptadores;

public class DatosCita {

    String id;
    Fecha dia;
    Hora hora;
    Boolean disponibilidad;
    Servicio servicio;

    public DatosCita(Fecha dia, Hora hora, Boolean disponibilidad, Servicio servicio){
        this.dia = dia;
        this.hora = hora;
        this.disponibilidad = disponibilidad;
        if(disponibilidad == false){
            this.servicio = servicio;
        }
    }

    public String getHora() {
        return hora.toString();
    }

    public Servicio getServicio() {
        return servicio;
    }

    public Boolean getDisponibilidad() {
        return disponibilidad;
    }

    public String getDia() { return dia.toString(); }

    public String getPrecio() { return servicio.getPrecio();}

    public Hora getHoraC() { return hora; }

    public Fecha getDiaC() { return dia; }

    public void setID(String id){
        this.id = id;
    }

    public String getID(){
        return this.id;
    }

    public void setDisponibilidad(boolean d){
        this.disponibilidad = d;
    }

}
