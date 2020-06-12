package com.example.practicaloginschoolsqlite;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import com.example.practicaloginschoolsqlite.dao.UserDAO;
import com.example.practicaloginschoolsqlite.dto.UserDTO;



public class User extends AppCompatActivity {
    AdapterUser adapterUser;

    EditText editTextEmail;
    EditText editTextPhone;
    EditText editTextName;
    EditText editTextIdUser;
    Button buttonRegister, buttonConsult, buttonUpdate, buttonDelete, buttonConsultAll;

    UserDTO userDTO;
    UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usercrud);

        this.linkFields();

        userDAO = new UserDAO(this);

        //Se inhabilita el boton de actualizar y eliminar hasta que el usuario
        buttonUpdate.setEnabled(false);
        buttonDelete.setEnabled(false);

        //Botón que registra al usuario en la base de datos
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Se valida que los campos no esten vacíos
                if(editTextIdUser.getText().toString().isEmpty() || editTextName.getText().toString().isEmpty() || editTextPhone.getText().toString().isEmpty() || editTextEmail.getText().toString().isEmpty()) {
                    Toast.makeText(User.this, "Todos los campos deben de ser llenados", Toast.LENGTH_SHORT).show();
                } else {
                    userDTO = new UserDTO(editTextIdUser.getText().toString(), editTextName.getText().toString(), editTextPhone.getText().toString(), editTextEmail.getText().toString());
                    userDAO.insert(userDTO);
                    cleanFields();
                }
            }
        });

        //Boton para consultar por el id del usuario
        buttonConsult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Se valida que el id usuario no este vacío
                if(editTextIdUser.getText().toString().isEmpty()) {
                    Toast.makeText(User.this, "Debes ingresar un ID", Toast.LENGTH_SHORT).show();
                    buttonUpdate.setEnabled(false);
                    buttonDelete.setEnabled(false);
                } else {
                    userDTO = userDAO.readbyid(editTextIdUser.getText().toString());
                    if(userDTO == null) {
                        Toast.makeText(User.this, "El ID ingresado no está registrado en la base de datos", Toast.LENGTH_SHORT).show();
                        cleanFields();
                    } else {
                        editTextName.setText(userDTO.getName());
                        editTextPhone.setText(userDTO.getPhone());
                        editTextEmail.setText(userDTO.getEmail());

                        //Se activan los botones al consultar algún cliente
                        buttonUpdate.setEnabled(true);
                        buttonDelete.setEnabled(true);
                    }
                }
            }
        });

        //Botón que actualiza los campos del usuario
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Se valida que los campos no esten vacíos
                if(editTextIdUser.getText().toString().isEmpty() && editTextName.toString().isEmpty() && editTextPhone.toString().isEmpty() && editTextEmail.toString().isEmpty()) {
                    Toast.makeText(User.this, "Todos los campos deben de ser llenados para actualizar", Toast.LENGTH_SHORT).show();
                } else {
                    userDTO = new UserDTO(editTextIdUser.getText().toString(), editTextName.getText().toString(), editTextPhone.getText().toString(), editTextEmail.getText().toString());
                    userDAO.update(userDTO);
                    cleanFields();
                }

            }
        });

        //Botón que elimina los campos del usuario
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Se valida que los campos no esten vacíos
                if(editTextIdUser.getText().toString().isEmpty() && editTextName.toString().isEmpty() && editTextPhone.toString().isEmpty() && editTextEmail.toString().isEmpty()) {
                    Toast.makeText(User.this, "Todos los campos deben de ser llenados para actualizar", Toast.LENGTH_SHORT).show();
                } else {
                    userDTO = new UserDTO(editTextIdUser.getText().toString(), editTextName.getText().toString(), editTextPhone.getText().toString(), editTextEmail.getText().toString());
                    userDAO.delete(userDTO.getIdUser());
                    cleanFields();
                }
            }
        });

        //Para consultar todos los usuarios y mostrarlos en una alerta con un listView
        buttonConsultAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterUser = new AdapterUser(User.this, userDAO.read());

                AlertDialog.Builder builder = new AlertDialog.Builder(User.this);
                LayoutInflater inflater = getLayoutInflater();
                View viewAlert = inflater.inflate(R.layout.activity_alert_user, null);
                builder.setView(viewAlert);

                ListView listViewUsers = viewAlert.findViewById(R.id.ListViewUsers);
                listViewUsers.setAdapter(adapterUser);

                final AlertDialog dialog = builder.create();
                dialog.show();

                listViewUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        userDTO = (UserDTO) parent.getItemAtPosition(position);
                        editTextIdUser.setText(userDTO.getIdUser());
                        editTextName.setText(userDTO.getName());
                        editTextPhone.setText(userDTO.getPhone());
                        editTextEmail.setText(userDTO.getEmail());

                        //Se activan los botones al consultar algún cliente
                        buttonUpdate.setEnabled(true);
                        buttonDelete.setEnabled(true);

                        dialog.dismiss();
                    }
                });
            }
        });
    }

    private void linkFields() {
        this.editTextIdUser = findViewById(R.id.editTextIdUser);
        this.editTextName = findViewById(R.id.editTextNameUser);
        this.editTextPhone = findViewById(R.id.editTextPhoneUser);
        this.editTextEmail = findViewById(R.id.editTextEmailUser);
        this.buttonRegister = findViewById(R.id.buttonRegister);
        this.buttonConsult = findViewById(R.id.buttonConsult);
        this.buttonConsultAll = findViewById(R.id.buttonConsultAllUser);
        this.buttonUpdate = findViewById(R.id.buttonUpdate);
        this.buttonDelete = findViewById(R.id.buttonDelete);
    }

    private void cleanFields() {
        this.editTextIdUser.setText("");
        this.editTextName.setText("");
        this.editTextPhone.setText("");
        this.editTextEmail.setText("");
    }

}

