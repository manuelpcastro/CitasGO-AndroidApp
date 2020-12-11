package com.ugr.citasgo.Presentadores;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.ugr.citasgo.adaptadores.DatosCita;
import com.ugr.citasgo.R;
import com.ugr.citasgo.adaptadores.AdaptadorReservaCitas;
import com.ugr.citasgo.modelos.ModeloDia;
import java.util.ArrayList;

public class Dia extends AppCompatActivity {

    TextView dia;
    ArrayList<DatosCita> arrayCitas;
    ListView lista;
    String fecha;

    ModeloDia modelo;
    AdaptadorReservaCitas adaptador;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_dia);

        Intent intent = getIntent();
        fecha = intent.getStringExtra("dia");



        //Actualizamos vista

        dia = findViewById(R.id.dia);
        dia.setText(fecha);


        //Mostramos las citas
        arrayCitas = new ArrayList<>();

        adaptador = new AdaptadorReservaCitas(Dia.this,R.layout.lista_citas, arrayCitas);
        //Creamos el modelo
        modelo = new ModeloDia(getApplicationContext(), fecha, adaptador);

        lista = (ListView)findViewById(R.id.listahoras);
        lista.setAdapter(adaptador);
        lista.setClickable(true);

        adaptador.actualizarCitas(modelo.getCitas());
        System.out.println(arrayCitas.toString());
    }


/*
    public void onResume() {
        super.onResume();
        adaptador.actualizarCitas(modelo.getCitas());
    }
*/

}
