package com.example.practicaloginsqlite;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PanelAdmin extends AppCompatActivity {
    public TextView textViewNameUser;
    public TextView textViewRolUser;
    Button buttonSalir;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);                                                                 
        setContentView(R.layout.activity_paneladmin);
        this.asociarCampos();

        Bundle parametros = this.getIntent().getExtras();
        if(parametros != null){
            String nameUser = parametros.getString("nameUser");
            textViewNameUser.setText(nameUser);
            String nameRolUser = parametros.getString("nameRol");
            textViewRolUser.setText(nameRolUser);
        }

        buttonSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });
    }

    public void asociarCampos() {
        this.textViewNameUser = findViewById(R.id.textViewFullNameUser);
        this.textViewRolUser = findViewById(R.id.textViewRolUser);
        this.buttonSalir = findViewById(R.id.buttonSalir);
    }
}
