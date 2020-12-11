package com.ugr.citasgo.Presentadores;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.ugr.citasgo.modelos.ModeloReserva;
import com.ugr.citasgo.R;
import com.ugr.citasgo.adaptadores.Servicio;

import java.util.ArrayList;

public class Reserva extends AppCompatActivity {

    public String hora;
    TextView hora_cita;
    public String dia;
    TextView dia_cita;
    Spinner selector_servicio;
    Intent intent;

    ModeloReserva modelo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_reserva_cita);
        intent = getIntent();

        SharedPreferences shared = getSharedPreferences("user", MODE_PRIVATE);
        String id_usuario = (shared.getString("id_usuario", null));

        hora = intent.getStringExtra("hora");
        hora_cita = (TextView) findViewById(R.id.hora);
        hora_cita.setText(hora);

        dia = intent.getStringExtra("dia");
        dia_cita = (TextView) findViewById(R.id.dia);
        dia_cita.setText(dia);

        modelo = new ModeloReserva(getApplicationContext(),dia,hora,id_usuario);

        //get the spinner from the xml.
        selector_servicio = findViewById(R.id.selector_servicio);
        //Creamos un array con los servicios
        ArrayList<String> arrayServiciosString;
        arrayServiciosString = servicios();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, arrayServiciosString);
        selector_servicio.setAdapter(adapter);

        Button boton_reserva = (Button) findViewById(R.id.reservar);

        boton_reserva.setOnClickListener(v -> {
                    modelo.postReservaCita(selector_servicio.getSelectedItem().toString().toLowerCase());

                        //Pasamos a la pantalla de seleccion
                        intent = new Intent(Reserva.this, Operaciones.class);
                        startActivity(intent);

                }
        );
    }

    private ArrayList<String> servicios(){
        Servicio[] arrayServicios = Servicio.class.getEnumConstants();
        ArrayList<String> arrayServiciosString = new ArrayList<>();
        for(int i = 0; i<arrayServicios.length; i++){
            arrayServiciosString.add(arrayServicios[i].toString());
        }
        return arrayServiciosString;
    }

}
