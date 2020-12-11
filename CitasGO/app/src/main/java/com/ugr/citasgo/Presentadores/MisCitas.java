package com.ugr.citasgo.Presentadores;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.ugr.citasgo.R;
import com.ugr.citasgo.adaptadores.AdaptadorMisCitas;
import com.ugr.citasgo.adaptadores.DatosCita;
import com.ugr.citasgo.modelos.ModeloMisCitas;

import java.util.ArrayList;

public class MisCitas extends AppCompatActivity {

    ArrayList<DatosCita> arrayCitas;
    ListView lista;
    public String fecha;


    ModeloMisCitas modelo;
    AdaptadorMisCitas adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_mis_citas);

        String id_usuario;
        SharedPreferences shared = getSharedPreferences("user", MODE_PRIVATE);
        id_usuario = (shared.getString("id_usuario", null));

        //Citas
        arrayCitas = new ArrayList<>();
        adaptador = new AdaptadorMisCitas(MisCitas.this, R.layout.lista_mis_citas, arrayCitas);
        modelo = new ModeloMisCitas(getApplicationContext(), id_usuario, adaptador);
        lista = (ListView)findViewById(R.id.listacitas);
        lista.setAdapter(adaptador);

        lista.setClickable(true);
    }


}