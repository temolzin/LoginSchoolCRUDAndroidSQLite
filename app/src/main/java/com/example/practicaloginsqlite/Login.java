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

import com.example.practicaloginsqlite.dao.UserDAO;

import java.util.List;

public class Login extends AppCompatActivity {
    EditText editTextUsername;
    EditText editTextPassword;
    Button buttonLogin;
    RadioButton radioButtonLoginUserRegister, radioButtonLoginAccessRegister;
    Spinner spinnerLoginTipoUsuario;
    ArrayAdapter<RolUser> adaptador;
    List<RolUser> listaTiposUsuario;
    UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getWindow().setStatusBarColor(Color.parseColor("#D64B4B"));

        userDAO = new UserDAO(this);
        userDAO.create();

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
    }

    public void linkFields() {
        this.editTextUsername = findViewById(R.id.editTextUserName);
        this.editTextPassword = findViewById(R.id.editTextPassword);
        this.buttonLogin = findViewById(R.id.buttonLogin);
        this.radioButtonLoginUserRegister = findViewById(R.id.radioButtonLoginRegisterUser);
        this.radioButtonLoginAccessRegister = findViewById(R.id.radioButtonLoginAccess);
    }




}
