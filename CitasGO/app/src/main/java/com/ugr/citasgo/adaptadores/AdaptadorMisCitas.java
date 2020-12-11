package com.ugr.citasgo.adaptadores;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ugr.citasgo.Presentadores.Cancelacion;
import com.ugr.citasgo.R;

import java.util.ArrayList;

public class AdaptadorMisCitas extends ArrayAdapter<DatosCita>{

    private final Activity context;
    private ArrayList<DatosCita> items;

    //View lookup cache
    private static class ViewHolder {
        TextView txtHora;
        TextView txtServicio;
        TextView txtDia;
    }

    public AdaptadorMisCitas(Activity context, int layout, ArrayList<DatosCita> items) {
        super(context, layout, items);
        this.context = context;
        this.items = items;
    }

    public void actualizarCitas(ArrayList<DatosCita> citas){
        this.items.clear();
        for(int i=0; i<citas.size(); i++){
            items.add(citas.get(i));
        }
        //this.items = citas;
        notifyDataSetChanged();
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Cogemos la cita para esta fila
        DatosCita cita = getItem(position);
        ViewHolder viewHolder;
        final View resultado;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.lista_mis_citas, parent, false);
            viewHolder.txtHora = (TextView) convertView.findViewById(R.id.hora);
            viewHolder.txtServicio = (TextView) convertView.findViewById(R.id.servicio);
            viewHolder.txtDia = convertView.findViewById(R.id.dia);

            resultado = convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            resultado = convertView;
        }

        viewHolder.txtHora.setText(cita.getHora());
        viewHolder.txtServicio.setText(cita.getServicio().toString());
        String dia = cita.getDiaC().getString("day") + "/" + cita.getDiaC().getString("month");

        viewHolder.txtDia.setText(dia);

        crearVentanaCancelacion(context, resultado, position);


        return convertView;
    }

    public void crearVentanaCancelacion(final Activity context, View v, final int posicion) {
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Object object = getItem(posicion);
                DatosCita cita = (DatosCita) object;

                    Intent intent = new Intent(context, Cancelacion.class);
                    intent.putExtra("cita_id", cita.getID());
                    intent.putExtra("dia", cita.getDia());
                    intent.putExtra("hora", cita.getHora());
                    context.startActivity(intent);

            }
        });
    }
}

