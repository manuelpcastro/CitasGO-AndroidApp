package com.ugr.citasgo.Presentadores;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ugr.citasgo.modelos.ModeloCancelacion;
import com.ugr.citasgo.R;

public class Cancelacion extends AppCompatActivity {

    public String hora;
    TextView hora_cita;
    public String dia;
    TextView dia_cita;

    String cita;
    ModeloCancelacion modelo;

    Button cancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_cancelacion);

        Intent intent = getIntent();

        cita = intent.getStringExtra("cita_id");
        dia = intent.getStringExtra("dia");
        dia_cita = (TextView) findViewById(R.id.dia);
        dia_cita.setText(dia);

        hora = intent.getStringExtra("hora");
        hora_cita = (TextView) findViewById(R.id.hora);
        hora_cita.setText(hora);

        SharedPreferences shared = getSharedPreferences("user", MODE_PRIVATE);
        String id = (shared.getString("id_usuario", ""));

        modelo = new ModeloCancelacion(getApplicationContext(), cita, id);

        //button on click -> enviar borrado a la api
        cancelar = findViewById(R.id.cancelar);
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modelo.postCancelacion();
                Intent intent = new Intent(Cancelacion.this, Operaciones.class);
                startActivity(intent);
            }
        });
    }



}
