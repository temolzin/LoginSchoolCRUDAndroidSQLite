package com.example.practicaloginsqlite;

import android.os.Bundle;
import android.os.PersistableBundle;
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

import com.example.practicaloginsqlite.dao.RolUserDAO;
import com.example.practicaloginsqlite.dto.RolUserDTO;

public class RolUser extends AppCompatActivity {

    EditText editTextIdRol;
    EditText editTextNameRol;
    Button buttonRegisterRolUser, buttonConsultRolUser, buttonUpdateRolUser, buttonDeleteRolUser, buttonConsultAllRolUser;

    RolUserDAO rolUserDAO;
    RolUserDTO rolUserDTO;
    
    AdapterRolUser adapterRolUser;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rolusercrud);

        this.linkFields();

        rolUserDAO = new RolUserDAO(this);

        //Se inhabilita el boton de actualizar y eliminar hasta que el usuario
        buttonUpdateRolUser.setEnabled(false);
        buttonDeleteRolUser.setEnabled(false);

        //Botón que registra al rol de usuario en la base de datos
        buttonRegisterRolUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Se valida que los campos no esten vacíos
                if(editTextIdRol.getText().toString().isEmpty() || editTextNameRol.getText().toString().isEmpty()) {
                    Toast.makeText(RolUser.this, "Todos los campos deben de ser llenados", Toast.LENGTH_SHORT).show();
                } else {
                    rolUserDTO = new RolUserDTO(editTextIdRol.getText().toString(), editTextNameRol.getText().toString());
                    rolUserDAO.insert(rolUserDTO);
                    cleanFields();
                }
            }
        });

        //Boton para consultar por el id del rol de usuario
        buttonConsultRolUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Se valida que el id usuario no este vacío
                if(editTextIdRol.getText().toString().isEmpty()) {
                    Toast.makeText(RolUser.this, "Debes ingresar un ID", Toast.LENGTH_SHORT).show();
                    buttonUpdateRolUser.setEnabled(false);
                    buttonDeleteRolUser.setEnabled(false);
                } else {
                    rolUserDTO = rolUserDAO.readbyid(editTextIdRol.getText().toString());
                    if(rolUserDTO == null) {
                        Toast.makeText(RolUser.this, "El ID ingresado no está registrado en la base de datos", Toast.LENGTH_SHORT).show();
                        cleanFields();
                    } else {
                        editTextNameRol.setText(rolUserDTO.getNameRol());

                        //Se activan los botones al consultar algún cliente
                        buttonUpdateRolUser.setEnabled(true);
                        buttonDeleteRolUser.setEnabled(true);
                    }
                }
            }
        });

        //Botón que actualiza los campos del rol de usuario
        buttonUpdateRolUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Se valida que los campos no esten vacíos
                if(editTextIdRol.getText().toString().isEmpty() && editTextNameRol.toString().isEmpty()) {
                    Toast.makeText(RolUser.this, "Todos los campos deben de ser llenados para actualizar", Toast.LENGTH_SHORT).show();
                } else {
                    rolUserDTO = new RolUserDTO(editTextIdRol.getText().toString(), editTextNameRol.getText().toString());
                    rolUserDAO.update(rolUserDTO);
                    cleanFields();
                }

            }
        });

        //Botón que elimina los campos del usuario
        buttonDeleteRolUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Se valida que los campos no esten vacíos
                if(editTextIdRol.getText().toString().isEmpty() && editTextNameRol.toString().isEmpty()) {
                    Toast.makeText(RolUser.this, "Todos los campos deben de ser llenados para actualizar", Toast.LENGTH_SHORT).show();
                } else {
                    rolUserDTO = new RolUserDTO(editTextIdRol.getText().toString(), editTextNameRol.getText().toString());
                    rolUserDAO.delete(rolUserDTO.getIdRol());
                    cleanFields();
                }
            }
        });

        //Para consultar todos los rol de usuarios y mostrarlos en una alerta con un listView
        buttonConsultAllRolUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterRolUser = new AdapterRolUser(RolUser.this, rolUserDAO.read());

                AlertDialog.Builder builder = new AlertDialog.Builder(RolUser.this);
                LayoutInflater inflater = getLayoutInflater();
                View viewAlert = inflater.inflate(R.layout.activity_alert_user, null);
                builder.setView(viewAlert);

                ListView listViewUsers = viewAlert.findViewById(R.id.ListViewUsers);
                listViewUsers.setAdapter(adapterRolUser);

                final AlertDialog dialog = builder.create();
                dialog.show();

                listViewUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        rolUserDTO = (RolUserDTO) parent.getItemAtPosition(position);
                        editTextIdRol.setText(rolUserDTO.getIdRol());
                        editTextNameRol.setText(rolUserDTO.getNameRol());

                        //Se activan los botones al consultar algún cliente
                        buttonUpdateRolUser.setEnabled(true);
                        buttonDeleteRolUser.setEnabled(true);

                        dialog.dismiss();
                    }
                });
            }
        });

    }

    public void linkFields() {
        editTextIdRol = findViewById(R.id.editTextIdRolUser);
        editTextNameRol = findViewById(R.id.editTextNameRolUser);
        buttonRegisterRolUser = findViewById(R.id.buttonRegisterRolUser);
        buttonConsultRolUser = findViewById(R.id.buttonConsultRolUser);
        buttonConsultAllRolUser = findViewById(R.id.buttonConsultAllRolUser);
        buttonUpdateRolUser = findViewById(R.id.buttonUpdateRolUser);
        buttonDeleteRolUser = findViewById(R.id.buttonDeleteRolUser);
    }

    private void cleanFields() {
        this.editTextIdRol.setText("");
        this.editTextNameRol.setText("");
    }
}
