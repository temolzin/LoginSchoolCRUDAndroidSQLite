package com.example.practicaloginschoolsqlite.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.practicaloginschoolsqlite.dto.UserDTO;

import java.util.ArrayList;

public class StudentDAO implements Crud<UserDTO> {

    public static final String TABLE_USER = "user";
    public static final String FIELD_ID_USER = "idUser";
    public static final String FIELD_NAME_USER = "name";
    public static final String FIELD_PHONE_USER = "phone";
    public static final String FIELD_EMAIL_USER = "email";
    public static final String CREATE_TABLE_USER =
            "CREATE TABLE " + TABLE_USER + "(" +
                    FIELD_ID_USER + " TEXT," +
                    FIELD_NAME_USER + " TEXT," +
                    FIELD_PHONE_USER + " TEXT," +
                    FIELD_EMAIL_USER + " TEXT," +
            "PRIMARY KEY (" + FIELD_ID_USER + ")" +
            ")";

    private ConnectionSQLite conn;
    private SQLiteDatabase db;
    Context context;

    public StudentDAO(Context context) {
        this.context = context;
        conn = new ConnectionSQLite(context, TABLE_USER, null,1);
    }

    @Override
    public void create() {
        conn = new ConnectionSQLite(context, "user", null, 1);
    }

    @Override
    public ArrayList<UserDTO> read() {
        ArrayList<UserDTO> userDTOList = new ArrayList();
        db = conn.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER, null, null, null,null,null, null);
        while(cursor.moveToNext()) {
            UserDTO userDTO;
            userDTO = new UserDTO(cursor.getString(0), cursor.getString(1),cursor.getString(2),cursor.getString(3));
            userDTOList.add(userDTO);
        }
        return userDTOList;
    }

    @Override
    public UserDTO readbyid(Object id) {
        db = conn.getReadableDatabase();
        String[] parameters = {(String) id};
        String[] fields = {FIELD_ID_USER, FIELD_NAME_USER, FIELD_PHONE_USER, FIELD_EMAIL_USER};
        UserDTO userDTO = null;

        try {
            Cursor cursor = db.query(TABLE_USER, fields, FIELD_ID_USER+"=?", parameters,null,null,null);
            cursor.moveToFirst();

            userDTO = new UserDTO(cursor.getString(0), cursor.getString(1),cursor.getString(2),cursor.getString(3));
            cursor.close();
        } catch (Exception e) {
            Toast.makeText(context, "Ha ocurrido un error al consultar los datos: " + e.getMessage() , Toast.LENGTH_SHORT).show();
        }
        return userDTO;
    }


    @Override
    public boolean insert(UserDTO obj) {
        SQLiteDatabase db = conn.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FIELD_ID_USER, obj.getIdUser());
        values.put(FIELD_NAME_USER, obj.getName());
        values.put(FIELD_PHONE_USER, obj.getPhone());
        values.put(FIELD_EMAIL_USER, obj.getEmail());

        boolean result = false;
        Long idResult = db.insert(TABLE_USER, FIELD_ID_USER, values);
        Toast.makeText(context, "ID: " + idResult.toString(), Toast.LENGTH_SHORT).show();
        if(idResult.equals(-1)) {
            Toast.makeText(context, "Ha ocurrido un error al insertar el registro: El ID: " + obj.getIdUser() + " ya se encuentra registrado en la base de datos", Toast.LENGTH_SHORT).show();
            result = false;
        } else {
            Toast.makeText(context, "Registro insertado correctamente ID: " + idResult, Toast.LENGTH_SHORT).show();
            db.close();
            result = true;
        }

        return result;
    }

    @Override
    public boolean update(UserDTO obj) {
        SQLiteDatabase db = conn.getWritableDatabase();

        String[] parameters = {obj.getIdUser()};
        ContentValues values = new ContentValues();
        values.put(FIELD_NAME_USER, obj.getName());
        values.put(FIELD_PHONE_USER, obj.getPhone());
        values.put(FIELD_EMAIL_USER, obj.getEmail());

        boolean result = false;
        int idResult = db.update(TABLE_USER, values, FIELD_ID_USER+"=?", parameters);
        Toast.makeText(context, "Se actualizarón los datos del ID: " + obj.getIdUser(), Toast.LENGTH_SHORT).show();

        return result;
    }

    @Override
    public boolean delete(String id) {
        SQLiteDatabase db = conn.getWritableDatabase();

        String[] parameters = {id};

        boolean result = false;
        int idResult = db.delete(TABLE_USER, FIELD_ID_USER+"=?", parameters);
//        int idResult = db.delete(TABLE_USER, null, null);
        Toast.makeText(context, "Se eliminó el registro con ID: " + id, Toast.LENGTH_SHORT).show();

        return result;
    }
}
