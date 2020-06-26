package com.example.practicaloginschoolsqlite.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.practicaloginschoolsqlite.dto.SubjectDTO;
import com.example.practicaloginschoolsqlite.dto.TeacherDTO;
import com.example.practicaloginschoolsqlite.dto.TeacherSubjectDTO;

import java.util.ArrayList;

public class TeacherDAO implements Crud<TeacherDTO> {

    public static final String TABLE_TEACHER = "teacher";
    public static final String FIELD_ID_TEACHER = "idTeacher";
    public static final String FIELD_NAME_TEACHER = "nameTeacher";
    public static final String FIELD_ADDRESS_TEACHER = "addressTeacher";
    public static final String FIELD_PHONE_TEACHER = "phoneTeacher";
    public static final String FIELD_SCHEDULE_TEACHER = "scheduleTeacher";
    public static final String CREATE_TABLE_TEACHER =
            "CREATE TABLE " + TABLE_TEACHER + "(" +
                    FIELD_ID_TEACHER + " TEXT," +
                    FIELD_NAME_TEACHER + " TEXT," +
                    FIELD_ADDRESS_TEACHER + " TEXT," +
                    FIELD_PHONE_TEACHER + " TEXT," +
                    FIELD_SCHEDULE_TEACHER + " TEXT," +
            "PRIMARY KEY (" + FIELD_ID_TEACHER + ")" +
            ")";

    private ConnectionSQLite conn;
    private SQLiteDatabase db;
    Context context;

    public TeacherDAO(Context context) {
        this.context = context;
        conn = new ConnectionSQLite(context, TABLE_TEACHER, null,1);
    }

    @Override
    public void create() {
        conn = new ConnectionSQLite(context, "teacher", null, 1);
    }

    @Override
    public ArrayList<TeacherDTO> read() {
        ArrayList<TeacherDTO> teacherDTOList = new ArrayList();
        db = conn.getReadableDatabase();
        Cursor cursor = db.query(TABLE_TEACHER, null, null, null,null,null, null);
        while(cursor.moveToNext()) {
            TeacherDTO teacherDTO;
            teacherDTO = new TeacherDTO(cursor.getString(0), cursor.getString(1),cursor.getString(2),cursor.getString(3), cursor.getString(4));
            teacherDTOList.add(teacherDTO);
        }
        return teacherDTOList;
    }

    @Override
    public TeacherDTO readbyid(Object id) {
        db = conn.getReadableDatabase();
        String[] parameters = {(String) id};
        
        String[] fields = {FIELD_ID_TEACHER, FIELD_NAME_TEACHER, FIELD_ADDRESS_TEACHER,FIELD_PHONE_TEACHER, FIELD_SCHEDULE_TEACHER};
        TeacherDTO teacherDTO = null;

        try {
            Cursor cursor = db.query(TABLE_TEACHER, fields, FIELD_ID_TEACHER+"=?", parameters,null,null,null);
            cursor.moveToFirst();

            teacherDTO = new TeacherDTO(cursor.getString(0), cursor.getString(1),cursor.getString(2),cursor.getString(3), cursor.getString(4));
            cursor.close();
        } catch (Exception e) {
            Toast.makeText(context, "Ha ocurrido un error al consultar los datos: " + e.getMessage() , Toast.LENGTH_SHORT).show();
        }
        return teacherDTO;
    }

    @Override
    public boolean insert(TeacherDTO obj) {
        SQLiteDatabase db = conn.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FIELD_ID_TEACHER, obj.getIdTeacher());
        values.put(FIELD_NAME_TEACHER, obj.getNameTeacher());
        values.put(FIELD_ADDRESS_TEACHER, obj.getAddressTeacher());
        values.put(FIELD_PHONE_TEACHER, obj.getPhoneTeacher());
        values.put(FIELD_SCHEDULE_TEACHER, obj.getScheduleTeacher());

        boolean result = false;
        Long idResult = db.insert(TABLE_TEACHER, FIELD_ID_TEACHER, values);
        Toast.makeText(context, "ID: " + idResult.toString(), Toast.LENGTH_SHORT).show();
        if(idResult.equals(-1)) {
            Toast.makeText(context, "Ha ocurrido un error al insertar el registro: El ID: " + obj.getIdTeacher() + " ya se encuentra registrado en la base de datos", Toast.LENGTH_SHORT).show();
            result = false;
        } else {
            Toast.makeText(context, "Registro insertado correctamente ID: " + idResult, Toast.LENGTH_SHORT).show();
            db.close();
            result = true;
        }

        return result;
    }

    @Override
    public boolean update(TeacherDTO obj) {
        SQLiteDatabase db = conn.getWritableDatabase();

        String[] parameters = {obj.getIdTeacher()};
        ContentValues values = new ContentValues();
        values.put(FIELD_NAME_TEACHER, obj.getNameTeacher());
        values.put(FIELD_ADDRESS_TEACHER, obj.getAddressTeacher());
        values.put(FIELD_PHONE_TEACHER, obj.getPhoneTeacher());
        values.put(FIELD_SCHEDULE_TEACHER, obj.getScheduleTeacher());

        boolean result = false;
        int idResult = db.update(TABLE_TEACHER, values, FIELD_ID_TEACHER+"=?", parameters);
        Toast.makeText(context, "Se actualizarón los datos del ID: " + obj.getIdTeacher(), Toast.LENGTH_SHORT).show();

        return result;
    }

    @Override
    public boolean delete(String id) {
        SQLiteDatabase db = conn.getWritableDatabase();

        String[] parameters = {id};

        boolean result = false;
        int idResult = db.delete(TABLE_TEACHER, FIELD_ID_TEACHER+"=?", parameters);
        Toast.makeText(context, "Se eliminó el registro con ID: " + id, Toast.LENGTH_SHORT).show();

        return result;
    }

    /*
        Método para que regresa una lista con todos los profesores registrados con el id de la materia (subjectDTO)
     */
    public ArrayList<TeacherDTO> readbyidsubject(SubjectDTO subjectDTO) {
        ArrayList<TeacherDTO> teacherDTOS = new ArrayList<TeacherDTO>();

        //Se crea el objeto TeacherSubjectDAO para mandar a llamar un metodo
        TeacherSubjectDAO teacherSubjectDAO = new TeacherSubjectDAO(context);
        //Se manda a llamar el metodo readbyidsubject que regresa una lista con los objetos registrados en la base de datos filtrados por el id de materia (idSubject)
        ArrayList<TeacherSubjectDTO> teacherSubjectDTOS = teacherSubjectDAO.readbyidsubject(subjectDTO);

        //Se recorre la lista regresada por el metodo y se va llenando la lista de profesores
        for(TeacherSubjectDTO teacherSubjectDTO : teacherSubjectDTOS) {
            //Se manda a llamar el metodo readbyid (TEACHERDAO) para saber los datos del profesor con la lista que regresa teacherSubjectDAO.readbyidsubject y se agregan los objectos a la lista de profesores
            teacherDTOS.add(readbyid(teacherSubjectDTO.getTeacher().getIdTeacher()));
        }

        return teacherDTOS;
    }
}
