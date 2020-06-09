package com.example.practicaloginsqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.practicaloginsqlite.dao.AccessDAO;
import com.example.practicaloginsqlite.dao.RolUserDAO;
import com.example.practicaloginsqlite.dao.UserDAO;
import com.example.practicaloginsqlite.dto.AccessDTO;

import java.util.List;

public class Login extends AppCompatActivity {
    EditText editTextUsername;
    EditText editTextPassword;
    Button buttonLogin;
    RadioButton radioButtonLoginUserRegister, radioButtonLoginAccessRegister, radioButtonLoginRolUserRegister;
    UserDAO userDAO;
    AccessDAO accessDAO;
    RolUserDAO rolUserDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getWindow().setStatusBarColor(Color.parseColor("#D64B4B"));

        userDAO = new UserDAO(this);
        userDAO.create();

        accessDAO = new AccessDAO(this);
        accessDAO.create();

        rolUserDAO = new RolUserDAO(this);
        rolUserDAO.create();

        this.linkFields();

        radioButtonLoginUserRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), User.class);
                startActivity(intent);
            }
        });

        radioButtonLoginAccessRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Access.class);
                startActivity(intent);
            }
        });

        radioButtonLoginRolUserRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RolUser.class);
                startActivity(intent);
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextUsername.getText().toString().isEmpty() || editTextPassword.getText().toString().isEmpty()) {
                    Toast.makeText(Login.this, "Ingresa todos los datos", Toast.LENGTH_SHORT).show();
                } else {
                    //Consulta LOGIN
                    AccessDTO accessDTO = accessDAO.readbyusernameandpassword(editTextUsername.getText().toString(), editTextPassword.getText().toString());
                    if(accessDTO != null) {
                        Intent intent = new Intent(getApplicationContext(), PanelAdmin.class);
                        intent.putExtra("nameUser",accessDTO.getObjUser().getName());
                        intent.putExtra("nameRol",accessDTO.getObjRolUser().getNameRol());
                        startActivity(intent);
                    } else {
                        Toast.makeText(Login.this, "Usuario y/o Contraseña incorrectos", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }

    public void linkFields() {
        this.editTextUsername = findViewById(R.id.editTextUserName);
        this.editTextPassword = findViewById(R.id.editTextPassword);
        this.buttonLogin = findViewById(R.id.buttonLogin);
        this.radioButtonLoginUserRegister = findViewById(R.id.radioButtonLoginRegisterUser);
        this.radioButtonLoginAccessRegister = findViewById(R.id.radioButtonLoginAccess);
        this.radioButtonLoginRolUserRegister = findViewById(R.id.radioButtonLoginRegisterRol);
    }




}
