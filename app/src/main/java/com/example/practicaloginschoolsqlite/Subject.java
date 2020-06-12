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

import com.example.practicaloginschoolsqlite.dao.SubjectDAO;
import com.example.practicaloginschoolsqlite.dto.SubjectDTO;

public class Subject extends AppCompatActivity {

    EditText editTextIdSubject;
    EditText editTextNameSubject;
    EditText editTextCreditSubject;
    Button buttonRegisterSubject, buttonConsultSubject, buttonUpdateSubject, buttonDeleteSubject, buttonConsultAllSubject;

    SubjectDAO subjectDAO;
    SubjectDTO subjectDTO;
    
    AdapterSubject adapterSubject;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjectcrud);

        this.linkFields();

        subjectDAO = new SubjectDAO(this);

        //Se inhabilita el boton de actualizar y eliminar hasta que el usuario
        buttonUpdateSubject.setEnabled(false);
        buttonDeleteSubject.setEnabled(false);

        //Botón que registra al rol de usuario en la base de datos
        buttonRegisterSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Se valida que los campos no esten vacíos
                if(editTextIdSubject.getText().toString().isEmpty() || editTextNameSubject.getText().toString().isEmpty()) {
                    Toast.makeText(Subject.this, "Todos los campos deben de ser llenados", Toast.LENGTH_SHORT).show();
                } else {
                    subjectDTO = new SubjectDTO(editTextIdSubject.getText().toString(), editTextNameSubject.getText().toString(), Integer.parseInt(editTextCreditSubject.getText().toString()));
                    subjectDAO.insert(subjectDTO);
                    cleanFields();
                }
            }
        });

        //Boton para consultar por el id del rol de usuario
        buttonConsultSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Se valida que el id usuario no este vacío
                if(editTextIdSubject.getText().toString().isEmpty()) {
                    Toast.makeText(Subject.this, "Debes ingresar un ID", Toast.LENGTH_SHORT).show();
                    buttonUpdateSubject.setEnabled(false);
                    buttonDeleteSubject.setEnabled(false);
                } else {
                    subjectDTO = subjectDAO.readbyid(editTextIdSubject.getText().toString());
                    if(subjectDTO == null) {
                        Toast.makeText(Subject.this, "El ID ingresado no está registrado en la base de datos", Toast.LENGTH_SHORT).show();
                        cleanFields();
                    } else {
                        editTextNameSubject.setText(subjectDTO.getNameSubject());
                        editTextCreditSubject.setText(subjectDTO.getCreditSubject() + "");

                        //Se activan los botones al consultar algún cliente
                        buttonUpdateSubject.setEnabled(true);
                        buttonDeleteSubject.setEnabled(true);
                    }
                }
            }
        });

        //Botón que actualiza los campos del rol de usuario
        buttonUpdateSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Se valida que los campos no esten vacíos
                if(editTextIdSubject.getText().toString().isEmpty() && editTextNameSubject.toString().isEmpty()) {
                    Toast.makeText(Subject.this, "Todos los campos deben de ser llenados para actualizar", Toast.LENGTH_SHORT).show();
                } else {
                    subjectDTO = new SubjectDTO(editTextIdSubject.getText().toString(), editTextNameSubject.getText().toString(), Integer.parseInt(editTextCreditSubject.getText().toString()));
                    subjectDAO.update(subjectDTO);
                    cleanFields();
                }

            }
        });

        //Botón que elimina los campos del usuario
        buttonDeleteSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Se valida que los campos no esten vacíos
                if(editTextIdSubject.getText().toString().isEmpty() && editTextNameSubject.toString().isEmpty()) {
                    Toast.makeText(Subject.this, "Todos los campos deben de ser llenados para actualizar", Toast.LENGTH_SHORT).show();
                } else {
                    subjectDTO = new SubjectDTO(editTextIdSubject.getText().toString(), editTextNameSubject.getText().toString(), Integer.parseInt(editTextCreditSubject.getText().toString()));
                    subjectDAO.delete(subjectDTO.getIdSubject());
                    cleanFields();
                }
            }
        });

        //Para consultar todos los rol de usuarios y mostrarlos en una alerta con un listView
        buttonConsultAllSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterSubject = new AdapterSubject(Subject.this, subjectDAO.read());

                AlertDialog.Builder builder = new AlertDialog.Builder(Subject.this);
                LayoutInflater inflater = getLayoutInflater();
                View viewAlert = inflater.inflate(R.layout.activity_alert_subject, null);
                builder.setView(viewAlert);

                ListView listViewSubjects = viewAlert.findViewById(R.id.ListViewUsers);
                listViewSubjects.setAdapter(adapterSubject);

                final AlertDialog dialog = builder.create();
                dialog.show();

                listViewSubjects.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        subjectDTO = (SubjectDTO) parent.getItemAtPosition(position);
                        editTextIdSubject.setText(subjectDTO.getIdSubject());
                        editTextNameSubject.setText(subjectDTO.getNameSubject());
                        editTextCreditSubject.setText(subjectDTO.getCreditSubject() + "");

                        //Se activan los botones al consultar algún cliente
                        buttonUpdateSubject.setEnabled(true);
                        buttonDeleteSubject.setEnabled(true);

                        dialog.dismiss();
                    }
                });
            }
        });

    }

    public void linkFields() {
        editTextIdSubject = findViewById(R.id.editTextIdSubject);
        editTextNameSubject = findViewById(R.id.editTextNameSubject);
        editTextCreditSubject = findViewById(R.id.editTextCreditSubject);
        buttonRegisterSubject = findViewById(R.id.buttonRegisterSubject);
        buttonConsultSubject = findViewById(R.id.buttonConsultSubject);
        buttonConsultAllSubject = findViewById(R.id.buttonConsultAllSubject);
        buttonUpdateSubject = findViewById(R.id.buttonUpdateSubject);
        buttonDeleteSubject = findViewById(R.id.buttonDeleteSubject);
    }

    private void cleanFields() {
        this.editTextIdSubject.setText("");
        this.editTextNameSubject.setText("");
        this.editTextCreditSubject.setText("");
    }
}
