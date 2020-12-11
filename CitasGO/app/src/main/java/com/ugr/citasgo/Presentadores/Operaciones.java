package com.ugr.citasgo.Presentadores;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ugr.citasgo.R;

public class Operaciones extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.ugr.citasgo.R.layout.vista_operaciones);

        Button consulta = (Button) findViewById(R.id.consultarcitas);
        Button miscitas = (Button) findViewById(R.id.miscitas);
        Button salir = (Button) findViewById(R.id.salir);

        //Que pasa cuando clickamos un boton
        View.OnClickListener gestionOperaciones = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent;
                Class siguiente_actividad = Operaciones.class;
                switch(v.getId()){
                    case R.id.consultarcitas:
                        siguiente_actividad = Calendario.class;
                        break;
                    case R.id.miscitas:
                        siguiente_actividad = MisCitas.class;
                        break;
                    case R.id.salir:
                        SharedPreferences prf = getSharedPreferences("user",MODE_PRIVATE);
                        SharedPreferences.Editor editor = prf.edit();
                        siguiente_actividad = Login.class;
                        editor.clear();
                        editor.commit();
                        break;
                }
                intent = new Intent(Operaciones.this, siguiente_actividad);
                startActivity(intent);
            }
        };

        consulta.setOnClickListener(gestionOperaciones);
        miscitas.setOnClickListener(gestionOperaciones);
        salir.setOnClickListener(gestionOperaciones);
    }


}
