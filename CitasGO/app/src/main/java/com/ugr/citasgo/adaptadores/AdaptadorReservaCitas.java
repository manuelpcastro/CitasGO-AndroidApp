package com.ugr.citasgo.adaptadores;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ugr.citasgo.Presentadores.Reserva;
import com.ugr.citasgo.R;

import java.util.ArrayList;

public class AdaptadorReservaCitas extends ArrayAdapter<DatosCita>{

    private final Activity context;
    private ArrayList<DatosCita> items;

    //View lookup cache
    private static class ViewHolder {
        TextView txtHora;
        TextView txtDisponibilidad;
    }

    public AdaptadorReservaCitas(Activity context, int layout, ArrayList<DatosCita> items) {
        super(context, layout, items);
        this.context = context;
        this.items = items;
    }

    public void actualizarCitas(ArrayList<DatosCita> citas){
        this.items.clear();
        for(int i=0; i<citas.size(); i++){
            items.add(citas.get(i));
        }
        notifyDataSetChanged();
    }

    @Override
    public boolean isEnabled(int position){
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Cogemos la cita para esta fila
        DatosCita cita = getItem(position);
        ViewHolder viewHolder;
        final View resultado;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.lista_citas, parent, false);
            viewHolder.txtHora = (TextView) convertView.findViewById(R.id.hora);
            viewHolder.txtDisponibilidad = (TextView) convertView.findViewById(R.id.disponibilidad);

            resultado = convertView;
            convertView.setTag(viewHolder);
        } else{
            viewHolder = (ViewHolder) convertView.getTag();
            resultado = convertView;
        }

        viewHolder.txtHora.setText(cita.getHora());
        if(cita.getDisponibilidad()) {
            viewHolder.txtDisponibilidad.setText("DISPONIBLE");
            viewHolder.txtDisponibilidad.setTextColor(Color.parseColor("#008000"));

            crearVentanaReserva(context, resultado ,position);



        } else {
            viewHolder.txtDisponibilidad.setText("OCUPADO");
            viewHolder.txtDisponibilidad.setTextColor(Color.parseColor("#FF0000"));
        }



        return convertView;
    }

    public void crearVentanaReserva(final Activity context, View v, final int posicion){
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Object object = getItem(posicion);
                DatosCita cita = (DatosCita) object;
                

                if(cita.getDisponibilidad()){
                    Intent intent = new Intent(context, Reserva.class);
                    intent.putExtra("dia", cita.getDia());
                    intent.putExtra("hora",cita.getHora());
                    context.startActivity(intent);
                }
            }
        });
    }

};