package com.ugr.citasgo.adaptadores;

import androidx.annotation.NonNull;

public class Hora {

    private int hora;
    private int minutos;

    public Hora (int hora, int minutos){
        this.hora = hora;
        this.minutos = minutos;
    }

    public Hora (String hora){
        String[] partes = hora.split(":");
        this.hora = Integer.parseInt(partes[0]);
        this.minutos = Integer.parseInt(partes[1]);
    }

    public int getHora() {
        return this.hora;
    }

    public int getMinutos() {
        return this.minutos;
    }

    public String getString (String s){
        String salida = "";
        int caso=0;
        switch(s){
            case "hora":
                caso = getHora();
                break;

            case "min":
                caso = getMinutos();
                break;
        }
        salida = Integer.toString(caso);
        return salida;
    }

    @NonNull
    @Override
    public String toString() {

        return this.hora + ":00";
    }

    public boolean esMayorQue(Hora h){

        if(this.hora == h.getHora()){
            /*
            if(this.minutos > h.getMinutos())
                return true;
            else
                return false;
            */
            return false;
        } else if(this.hora > h.getHora())
            return true;
         else
            return false;

    }

    public Hora sumarMinutos(int minutos){
        int nuevos_minutos = this.minutos + minutos;
        int nueva_hora = this.hora;
        while(nuevos_minutos >= 60){
            nuevos_minutos -= 60;
            nueva_hora++;
        }
        return new Hora(nueva_hora,nuevos_minutos);
    }
}

