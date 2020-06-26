package com.example.practicaloginschoolsqlite;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.practicaloginschoolsqlite.dao.CareerDAO;
import com.example.practicaloginschoolsqlite.dto.CareerDTO;

public class Career extends AppCompatActivity {

    EditText editTextIdCareer;
    EditText editTextNameCareer;
    EditText editTextDurationCareer;
    Button buttonRegisterCareer, buttonConsultCareer, buttonUpdateCareer, buttonDeleteCareer, buttonConsultAllCareer;

    CareerDAO careerDAO;
    CareerDTO careerDTO;
    
    AdapterCareer adapterCareer;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_careercrud);

        this.linkFields();

        careerDAO = new CareerDAO(this);

        //Se inhabilita el boton de actualizar y eliminar hasta que el usuario
        buttonUpdateCareer.setEnabled(false);
        buttonDeleteCareer.setEnabled(false);

        //Botón que registra al rol de usuario en la base de datos
        buttonRegisterCareer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Se valida que los campos no esten vacíos
                if(editTextIdCareer.getText().toString().isEmpty() || editTextNameCareer.getText().toString().isEmpty()|| editTextDurationCareer.getText().toString().isEmpty()) {
                    Toast.makeText(Career.this, "Todos los campos deben de ser llenados", Toast.LENGTH_SHORT).show();
                } else {
                    careerDTO = new CareerDTO(editTextIdCareer.getText().toString(), editTextNameCareer.getText().toString(), editTextDurationCareer.getText().toString());
                    careerDAO.insert(careerDTO);
                    cleanFields();
                }
            }
        });

        //Boton para consultar por el id del rol de usuario
        buttonConsultCareer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Se valida que el id usuario no este vacío
                if(editTextIdCareer.getText().toString().isEmpty()) {
                    Toast.makeText(Career.this, "Debes ingresar un ID", Toast.LENGTH_SHORT).show();
                    buttonUpdateCareer.setEnabled(false);
                    buttonDeleteCareer.setEnabled(false);
                } else {
                    careerDTO = careerDAO.readbyid(editTextIdCareer.getText().toString());
                    if(careerDTO == null) {
                        Toast.makeText(Career.this, "El ID ingresado no está registrado en la base de datos", Toast.LENGTH_SHORT).show();
                        cleanFields();
                    } else {
                        editTextNameCareer.setText(careerDTO.getNameCareer());
                        editTextDurationCareer.setText(careerDTO.getDurationCareer());

                        //Se activan los botones al consultar algún cliente
                        buttonUpdateCareer.setEnabled(true);
                        buttonDeleteCareer.setEnabled(true);
                    }
                }
            }
        });

        //Botón que actualiza los campos del rol de usuario
        buttonUpdateCareer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Se valida que los campos no esten vacíos
                if(editTextIdCareer.getText().toString().isEmpty() || editTextNameCareer.getText().toString().isEmpty()|| editTextDurationCareer.getText().toString().isEmpty()) {
                    Toast.makeText(Career.this, "Todos los campos deben de ser llenados para actualizar", Toast.LENGTH_SHORT).show();
                } else {
                    careerDTO = new CareerDTO(editTextIdCareer.getText().toString(), editTextNameCareer.getText().toString(), editTextDurationCareer.getText().toString());
                    careerDAO.update(careerDTO);
                    cleanFields();
                }

            }
        });

        //Botón que elimina los campos del usuario
        buttonDeleteCareer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Se valida que los campos no esten vacíos
                if(editTextIdCareer.getText().toString().isEmpty() && editTextNameCareer.toString().isEmpty()) {
                    Toast.makeText(Career.this, "Todos los campos deben de ser llenados para actualizar", Toast.LENGTH_SHORT).show();
                } else {
                    careerDTO = new CareerDTO(editTextIdCareer.getText().toString(), editTextNameCareer.getText().toString(), editTextDurationCareer.getText().toString());
                    careerDAO.delete(careerDTO.getIdCareer());
                    cleanFields();
                }
            }
        });

        //Para consultar todos los rol de usuarios y mostrarlos en una alerta con un listView
        buttonConsultAllCareer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterCareer = new AdapterCareer(Career.this, careerDAO.read());

                AlertDialog.Builder builder = new AlertDialog.Builder(Career.this);
                LayoutInflater inflater = getLayoutInflater();
                View viewAlert = inflater.inflate(R.layout.activity_alert_user, null);
                builder.setView(viewAlert);

                ListView listViewUsers = viewAlert.findViewById(R.id.ListViewUsers);
                listViewUsers.setAdapter(adapterCareer);

                final AlertDialog dialog = builder.create();
                dialog.show();

                listViewUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        careerDTO = (CareerDTO) parent.getItemAtPosition(position);
                        editTextIdCareer.setText(careerDTO.getIdCareer());
                        editTextNameCareer.setText(careerDTO.getNameCareer());
                        editTextDurationCareer.setText(careerDTO.getDurationCareer());

                        //Se activan los botones al consultar algún cliente
                        buttonUpdateCareer.setEnabled(true);
                        buttonDeleteCareer.setEnabled(true);

                        dialog.dismiss();
                    }
                });
            }
        });

    }

    public void linkFields() {
        editTextIdCareer = findViewById(R.id.editTextIdCareer);
        editTextNameCareer = findViewById(R.id.editTextNameCareer);
        editTextDurationCareer = findViewById(R.id.editTextDurationCareer);
        buttonRegisterCareer = findViewById(R.id.buttonRegisterCareer);
        buttonConsultCareer = findViewById(R.id.buttonConsultCareer);
        buttonConsultAllCareer = findViewById(R.id.buttonConsultAllCareer);
        buttonUpdateCareer = findViewById(R.id.buttonUpdateCareer);
        buttonDeleteCareer = findViewById(R.id.buttonDeleteCareer);
    }

    private void cleanFields() {
        this.editTextIdCareer.setText("");
        this.editTextNameCareer.setText("");
        this.editTextDurationCareer.setText("");
    }
}
