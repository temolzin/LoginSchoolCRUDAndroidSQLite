package com.example.practicaloginschoolsqlite.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.practicaloginschoolsqlite.dto.SubjectDTO;

import java.util.ArrayList;

public class SubjectDAO implements Crud<SubjectDTO> {

    public static final String TABLE_SUBJECT = "subject";
    public static final String FIELD_ID_SUBJECT = "idSubject";
    public static final String FIELD_NAME_SUBJECT = "nameSubject";
    public static final String FIELD_CREDIT_SUBJECT = "creditSubject";
    public static final String CREATE_TABLE_SUBJECT =
            "CREATE TABLE " + TABLE_SUBJECT + " (" +
                    FIELD_ID_SUBJECT + " TEXT," +
                    FIELD_NAME_SUBJECT + " TEXT," +
                    FIELD_CREDIT_SUBJECT + " TEXT," +
            "PRIMARY KEY (" + FIELD_ID_SUBJECT + ")" +
            ")";

    private ConnectionSQLite conn;
    private SQLiteDatabase db;
    Context context;

    public SubjectDAO(Context context) {
        this.context = context;
        conn = new ConnectionSQLite(context, TABLE_SUBJECT, null,1);
    }

    @Override
    public void create() {
        conn = new ConnectionSQLite(context, TABLE_SUBJECT, null, 1);
    }

    @Override
    public ArrayList<SubjectDTO> read() {
        ArrayList<SubjectDTO> subjectDTOList = new ArrayList();
        db = conn.getReadableDatabase();
        Cursor cursor = db.query(TABLE_SUBJECT, null, null, null,null,null, null);
        while(cursor.moveToNext()) {
            SubjectDTO subjectDTO;
            subjectDTO = new SubjectDTO(cursor.getString(0), cursor.getString(1),cursor.getInt(2));
            subjectDTOList.add(subjectDTO);
        }
        return subjectDTOList;
    }

    @Override
    public SubjectDTO readbyid(Object id) {
        db = conn.getReadableDatabase();
        String[] parameters = {(String) id};
        String[] fields = {FIELD_ID_SUBJECT, FIELD_NAME_SUBJECT, FIELD_CREDIT_SUBJECT};
        SubjectDTO subjectDTO = null;

        try {
            Cursor cursor = db.query(TABLE_SUBJECT, fields, FIELD_ID_SUBJECT+"=?", parameters,null,null,null);
            cursor.moveToFirst();

            subjectDTO = new SubjectDTO(cursor.getString(0), cursor.getString(1),cursor.getInt(2));
            cursor.close();
        } catch (Exception e) {
            Toast.makeText(context, "Ha ocurrido un error al consultar los datos: " + e.getMessage() , Toast.LENGTH_SHORT).show();
        }
        return subjectDTO;
    }


    @Override
    public boolean insert(SubjectDTO obj) {
        SQLiteDatabase db = conn.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FIELD_ID_SUBJECT, obj.getIdSubject());
        values.put(FIELD_NAME_SUBJECT, obj.getNameSubject());
        values.put(FIELD_CREDIT_SUBJECT, obj.getCreditSubject());

        boolean result = false;
        Long idResult = db.insert(TABLE_SUBJECT, FIELD_ID_SUBJECT, values);
        Toast.makeText(context, "ID: " + idResult.toString(), Toast.LENGTH_SHORT).show();
        if(idResult.equals(-1)) {
            Toast.makeText(context, "Ha ocurrido un error al insertar el registro: El ID: " + obj.getIdSubject() + " ya se encuentra registrado en la base de datos", Toast.LENGTH_SHORT).show();
            result = false;
        } else {
            Toast.makeText(context, "Registro insertado correctamente ID: " + obj.getIdSubject(), Toast.LENGTH_SHORT).show();
            db.close();
            result = true;
        }

        return result;
    }

    @Override
    public boolean update(SubjectDTO obj) {
        SQLiteDatabase db = conn.getWritableDatabase();

        String[] parameters = {obj.getIdSubject()};
        ContentValues values = new ContentValues();
        values.put(FIELD_NAME_SUBJECT, obj.getNameSubject());
        values.put(FIELD_CREDIT_SUBJECT, obj.getCreditSubject());

        boolean result = false;
        int idResult = db.update(TABLE_SUBJECT, values, FIELD_ID_SUBJECT+"=?", parameters);
        Toast.makeText(context, "Se actualizarón los datos del ID: " + obj.getIdSubject(), Toast.LENGTH_SHORT).show();

        return result;
    }

    @Override
    public boolean delete(String id) {
        SQLiteDatabase db = conn.getWritableDatabase();

        String[] parameters = {id};

        boolean result = false;
        int idResult = db.delete(TABLE_SUBJECT, FIELD_ID_SUBJECT+"=?", parameters);

        Toast.makeText(context, "Se eliminó el registro con ID: " + id, Toast.LENGTH_SHORT).show();

        return result;
    }
}
