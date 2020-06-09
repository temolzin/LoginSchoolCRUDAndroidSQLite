package com.example.practicaloginsqlite.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.practicaloginsqlite.dto.RolUserDTO;

import java.util.ArrayList;

public class RolUserDAO implements Crud<RolUserDTO> {

    public static final String TABLE_ROL_USER = "roluser";
    public static final String FIELD_ID_ROL_USER = "idRolUser";
    public static final String FIELD_NAME_ROL_USER = "nameRol";

    public static final String CREATE_TABLE_ROL_USER =
            "CREATE TABLE " + TABLE_ROL_USER + "(" +
                    FIELD_ID_ROL_USER + " TEXT," +
                    FIELD_NAME_ROL_USER + " TEXT, " +
                    "PRIMARY KEY (" + FIELD_ID_ROL_USER + ")" +
                    ")";

    private ConnectionSQLite conn;
    private SQLiteDatabase db;
    Context context;

    public RolUserDAO(Context context) {
        this.context = context;
        conn = new ConnectionSQLite(context, TABLE_ROL_USER, null,1);
    }

    @Override
    public void create() {
        conn = new ConnectionSQLite(context, "user", null, 1);
    }

    @Override
    public ArrayList<RolUserDTO> read() {
        ArrayList<RolUserDTO> rolUserDTOList = new ArrayList();
        db = conn.getReadableDatabase();
        Cursor cursor = db.query(TABLE_ROL_USER, null, null, null,null,null, null);
        while(cursor.moveToNext()) {
            RolUserDTO rolUserDTO;
            rolUserDTO = new RolUserDTO(cursor.getString(0), cursor.getString(1));
            rolUserDTOList.add(rolUserDTO);
        }
        return rolUserDTOList;
    }

    @Override
    public RolUserDTO readbyid(Object id) {
        db = conn.getReadableDatabase();
        String[] parameters = {(String) id};
        String[] fields = {FIELD_ID_ROL_USER, FIELD_NAME_ROL_USER};
        RolUserDTO rolUserDTO = null;

        try {
            Cursor cursor = db.query(TABLE_ROL_USER, fields, FIELD_ID_ROL_USER+"=?", parameters,null,null,null);
            cursor.moveToFirst();

            rolUserDTO = new RolUserDTO(cursor.getString(0), cursor.getString(1));
            cursor.close();
        } catch (Exception e) {
            Toast.makeText(context, "Ha ocurrido un error al consultar los datos: " + e.getMessage() , Toast.LENGTH_SHORT).show();
        }
        return rolUserDTO;
    }

    @Override
    public boolean update(RolUserDTO obj) {
        SQLiteDatabase db = conn.getWritableDatabase();

        String[] parameters = {obj.getIdRol()};
        ContentValues values = new ContentValues();
        values.put(FIELD_NAME_ROL_USER, obj.getNameRol());

        boolean result = false;
        int idResult = db.update(TABLE_ROL_USER, values, FIELD_ID_ROL_USER+"=?", parameters);
        Toast.makeText(context, "Se actualizarón los datos del ID: " + obj.getIdRol(), Toast.LENGTH_SHORT).show();

        return result;
    }

    @Override
    public boolean delete(String id) {
        SQLiteDatabase db = conn.getWritableDatabase();

        String[] parameters = {id};

        boolean result = false;
        int idResult = db.delete(TABLE_ROL_USER, FIELD_ID_ROL_USER+"=?", parameters);
        Toast.makeText(context, "Se eliminó el registro con ID: " + id, Toast.LENGTH_SHORT).show();

        return result;
    }

    @Override
    public boolean insert(RolUserDTO obj) {
        SQLiteDatabase db = conn.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FIELD_ID_ROL_USER, obj.getIdRol());
        values.put(FIELD_NAME_ROL_USER, obj.getNameRol());

        boolean result = false;
        Long idResult = db.insert(TABLE_ROL_USER, FIELD_ID_ROL_USER, values);
        Toast.makeText(context, "ID: " + idResult.toString(), Toast.LENGTH_SHORT).show();
        if(idResult.equals(-1)) {
            Toast.makeText(context, "Ha ocurrido un error al insertar el registro: El ID: " + obj.getIdRol() + " ya se encuentra registrado en la base de datos", Toast.LENGTH_SHORT).show();
            result = false;
        } else {
            Toast.makeText(context, "Registro insertado correctamente ID: " + idResult, Toast.LENGTH_SHORT).show();
            db.close();
            result = true;
        }

        return result;
    }
}
