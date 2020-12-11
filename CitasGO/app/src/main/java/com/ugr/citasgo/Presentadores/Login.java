package com.ugr.citasgo.Presentadores;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ugr.citasgo.modelos.ModeloLogin;
import com.ugr.citasgo.R;


public class Login extends AppCompatActivity {
    EditText uname, pwd;
    Button loginBtn;
    Button registroBtn;
    SharedPreferences pref;
    Intent intent;
    String usuario;
    ModeloLogin modelo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_login);
        uname = (EditText)findViewById(R.id.txtName);
        pwd = (EditText)findViewById(R.id.txtPwd);

        modelo = new ModeloLogin(getApplicationContext());

        //login
        loginBtn = (Button)findViewById(R.id.btnLogin);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = uname.getText().toString();
                String password = pwd.getText().toString();
                modelo.postLogin(username, password);

                if(modelo.getIdentificado()) {
                    //Guardamos el idUsuario

                    pref = getSharedPreferences("user", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("id_usuario", modelo.getAutenticacion());
                    editor.commit();

                    //Pasamos a la pantalla de seleccion
                    Intent intent = new Intent(Login.this, Operaciones.class);
                    startActivity(intent);
                }
            }
        });

        registroBtn = (Button)findViewById(R.id.btnRegistro);
        registroBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(Login.this, Registro.class);
                startActivity(intent);
            }
        });

    }


}
