package com.ugr.citasgo.adaptadores;

import androidx.annotation.NonNull;

public enum Servicio {
    CORTE_PELO("CORTE PELO", 5), CORTE_BARBA("CORTE BARBA", 4), CORTE_COMPLETO("CORTE COMPLETO", 7);

    private String valor;
    private int precio;

    Servicio(String s, int p) {
        this.valor = s;
        this.precio = precio;
    }

    public String getPrecio(){
        return Integer.toString(this.precio);
    }

    public static Servicio getServicio(String s){
        s = s.toUpperCase();
        Servicio servicio = Servicio.CORTE_PELO;
        switch(s){
            case "CORTE PELO":
                servicio = Servicio.CORTE_PELO;
                break;
            case "CORTE BARBA":
                servicio = Servicio.CORTE_BARBA;
            break;
            case "CORTE COMPLETO":
                servicio = Servicio.CORTE_COMPLETO;
            break;
        }
        return servicio;
    }

    @NonNull
    @Override
    public String toString() {
        return this.valor;
    }
}
