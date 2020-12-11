package com.ugr.citasgo.Presentadores;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import java.util.Calendar;
import android.widget.TextView;
import android.widget.Toast;

import com.ugr.citasgo.R;
import com.ugr.citasgo.adaptadores.Fecha;

public class Calendario extends AppCompatActivity {

    CalendarView calendario;
    TextView fecha;
    Button boton;
    String dia;
    Fecha diaSeleccionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_calendario);
        Intent intent = getIntent();

        calendario = ((CalendarView) findViewById(R.id.calendario));
        fecha = (TextView) findViewById(R.id.fecha);

        diaSeleccionado = getHoy();

        dia = diaSeleccionado.toString();

        calendario.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            diaSeleccionado = new Fecha (dayOfMonth,month+1,year);
            dia = diaSeleccionado.toString();
            fecha.setText(dia);
        });

        boton = (Button) findViewById(R.id.consultarcitas);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!diaSeleccionado.esMayorQue(getHoy())){
                    Toast.makeText(getApplicationContext(),"Es un dia pasado",Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(Calendario.this, Dia.class);
                    intent.putExtra("dia", dia);
                    startActivity(intent);
                }
            }
        });

    }

    private Fecha getHoy(){
        int dia_actual = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        int mes_actual = Calendar.getInstance().get(Calendar.MONTH);
        int anyo_actual = Calendar.getInstance().get(Calendar.YEAR);

        Fecha today = new Fecha (dia_actual,mes_actual+1, anyo_actual);
        return today;
    }
}
