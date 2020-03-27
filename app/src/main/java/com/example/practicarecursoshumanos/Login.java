package com.example.practicarecursoshumanos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    EditText editTextUsername;
    EditText editTextPassword;
    Button buttonLogin;
    RadioButton radioButtonRegistrarse;
    String[] tiposUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.asociarCampos();

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"hhjksafjkhlsafkhjlsfa fas jsfkahjkhlsajkhlsafkjhlfakjh ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void asociarCampos() {
        this.editTextUsername = findViewById(R.id.editTextUserName);
        this.editTextPassword = findViewById(R.id.editTextPassword);
        this.buttonLogin = findViewById(R.id.buttonLogin);
        this.radioButtonRegistrarse = findViewById(R.id.radioButtonLoginRegister);
    }
}
