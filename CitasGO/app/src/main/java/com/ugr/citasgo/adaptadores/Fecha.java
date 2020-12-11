package com.ugr.citasgo.adaptadores;

import androidx.annotation.NonNull;

public class Fecha {

    private int dia;
    private int mes;
    private int year;

    public Fecha (int dia, int mes, int year){
        this.dia = dia;
        this.mes = mes;
        this.year = year;
    }

    public Fecha (String fecha){
        String[] partes = fecha.split("/");
        this.dia = Integer.parseInt(partes[0]);
        this.mes = Integer.parseInt(partes[1]);
        this.year = Integer.parseInt(partes[2]);
    }

    public int getDia() {
        return dia;
    }

    public int getMes() {
        return mes;
    }

    public int getYear() {
        return year;
    }

    public String getString (String s){
        String salida = "";
        int caso=0;
        switch(s){
            case "day":
                caso = getDia();
                break;

            case "month":
                caso = getMes();
                break;
            case "year":
                caso = getYear();
                break;

        }
        salida = Integer.toString(caso);
        return salida;
    }

    @NonNull
    @Override
    public String toString() {
        return this.dia + "/" + this.mes + "/" + this.year;
    }

    public boolean esMayorQue(Fecha f){
        if(this.year > f.getYear()){
            return true;
        }

        else if (this.year < f.getYear()){
            return false;
        }

        else {

            if(this.mes > f.getMes()){
                return true;
            }

            else if(this.mes < f.getMes()){
                return false;
            }

            else{

                if(this.dia >= f.getDia()){
                    return true;
                }

                else {
                    return false;
                }
            }
        }
    }
}
