package com.example.practicarecursoshumanos;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PanelAdmin extends AppCompatActivity {
    TextView textViewNombreCompleto;
    TextView textViewTipoUsuario;
    Button buttonSalir;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paneladmin);
        this.asociarCampos();

        buttonSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });
    }

    public void asociarCampos() {
        this.textViewNombreCompleto = findViewById(R.id.textViewNombreCompleto);
        this.textViewTipoUsuario = findViewById(R.id.textViewTipoUsuario);
        this.buttonSalir = findViewById(R.id.buttonSalir);
    }
}
