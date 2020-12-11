package com.ugr.citasgo.Presentadores;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.ugr.citasgo.modelos.ModeloRegistro;
import com.ugr.citasgo.R;

public class Registro extends AppCompatActivity {

    EditText uname, uemail, pwd;
    Button registroBtn;
    SharedPreferences pref;
    Intent intent;

    ModeloRegistro modelo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_registro);

        uname = (EditText) findViewById(R.id.txtName);
        uemail = (EditText) findViewById(R.id.txtEmail);
        pwd = (EditText) findViewById(R.id.txtPwd);

        registroBtn = (Button) findViewById(R.id.btnRegistro);

        modelo = new ModeloRegistro(getApplicationContext());

        registroBtn.setOnClickListener(v -> {
            String username = uname.getText().toString();
            String email = uemail.getText().toString();
            String password = pwd.getText().toString();

            modelo.postRegistro(username, email, password);

            if(modelo.getIdentificado()) {
                //Guardamos el idUsuario
                pref = getSharedPreferences("user", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("id_usuario", modelo.getAutenticacion());
                editor.commit();

                //Pasamos a la pantalla de seleccion
                intent = new Intent(Registro.this, Login.class);
                startActivity(intent);
            }
        });
    }

}