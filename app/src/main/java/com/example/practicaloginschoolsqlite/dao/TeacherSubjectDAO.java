package com.example.practicaloginschoolsqlite.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.practicaloginschoolsqlite.TeacherSubject;
import com.example.practicaloginschoolsqlite.dto.SubjectDTO;
import com.example.practicaloginschoolsqlite.dto.TeacherDTO;
import com.example.practicaloginschoolsqlite.dto.TeacherSubjectDTO;

import java.util.ArrayList;

public class TeacherSubjectDAO implements Crud<TeacherSubjectDTO> {

    public static final String TABLE_TEACHER_SUBJECT = "teacherSubject";
    public static final String FIELD_ID_TEACHER_SUBJECT = "idTeacherSubject";
    public static final String FIELD_ID_TEACHER = "idTeacherTS";
    public static final String FIELD_ID_SUBJECT = "idSubjectTS";
    public static final String CREATE_TABLE_TEACHER_SUBJECT =
            "CREATE TABLE " + TABLE_TEACHER_SUBJECT + " (" +
                    FIELD_ID_TEACHER_SUBJECT + " TEXT," +
                    FIELD_ID_TEACHER + " TEXT," +
                    FIELD_ID_SUBJECT + " TEXT," +
            "PRIMARY KEY (" + FIELD_ID_TEACHER_SUBJECT + "), " +
            "FOREIGN KEY (" + FIELD_ID_TEACHER + ") REFERENCES " + TeacherDAO.TABLE_TEACHER + "(" + TeacherDAO.FIELD_ID_TEACHER + "), " +
            "FOREIGN KEY (" + FIELD_ID_SUBJECT + ") REFERENCES " + SubjectDAO.TABLE_SUBJECT + "(" + SubjectDAO.FIELD_ID_SUBJECT + ") " +
            ")";

    private ConnectionSQLite conn;
    private SQLiteDatabase db;
    Context context;

    //Variables para consultar los objetos y mandarlos a la base de datos.
    TeacherDAO teacherDAO;
    SubjectDAO subjectDAO;

    public TeacherSubjectDAO(Context context) {
        this.context = context;
        conn = new ConnectionSQLite(context, TABLE_TEACHER_SUBJECT, null,1);
    }

    @Override
    public void create() {
        conn = new ConnectionSQLite(context, TABLE_TEACHER_SUBJECT, null, 1);
    }

    @Override
    public ArrayList<TeacherSubjectDTO> read() {
        ArrayList<TeacherSubjectDTO> teacherSubjectDTOList = new ArrayList<TeacherSubjectDTO>();
        db = conn.getReadableDatabase();
        Cursor cursor = db.query(TABLE_TEACHER_SUBJECT, null, null, null,null,null, null);

        while(cursor.moveToNext()) {
            teacherDAO = new TeacherDAO(context);
            TeacherDTO teacherDTO = teacherDAO.readbyid(cursor.getString(1));

            subjectDAO = new SubjectDAO(context);
            SubjectDTO subjectDTO = subjectDAO.readbyid(cursor.getString(2));

            TeacherSubjectDTO teacherSubjectDTO;
            teacherSubjectDTO = new TeacherSubjectDTO(cursor.getString(0), teacherDTO,subjectDTO);
            teacherSubjectDTOList.add(teacherSubjectDTO);
        }
        return teacherSubjectDTOList;
    }

    @Override
    public TeacherSubjectDTO readbyid(Object id) {
        db = conn.getReadableDatabase();
        String[] parameters = {(String) id};
        String[] fields = {FIELD_ID_TEACHER_SUBJECT, FIELD_ID_TEACHER, FIELD_ID_SUBJECT};
        TeacherSubjectDTO  teacherSubjectDTO = null;

        try {
            Cursor cursor = db.query(TABLE_TEACHER_SUBJECT, fields, FIELD_ID_TEACHER_SUBJECT + "=?", parameters, null, null, null);
            cursor.moveToFirst();

            teacherDAO = new TeacherDAO(context);
            TeacherDTO teacherDTO = teacherDAO.readbyid(cursor.getString(1));

            subjectDAO = new SubjectDAO(context);
            SubjectDTO subjectDTO = subjectDAO.readbyid(cursor.getString(2));

            teacherSubjectDTO = new TeacherSubjectDTO(cursor.getString(0), teacherDTO, subjectDTO);

            cursor.close();
        } catch (Exception e) {
            Toast.makeText(context, "Ha ocurrido un error al consultar los datos: " + e.getMessage() , Toast.LENGTH_SHORT).show();
        }
        return teacherSubjectDTO;
    }

    /*
        Metodo para leer todos los datos de la TABLA, filtrados por el id de la materia (idSubject)
     */
    public ArrayList<TeacherSubjectDTO> readbyidsubject(SubjectDTO subjectDTO) {
        ArrayList<TeacherSubjectDTO> arrayListTeacherSubjectDTO = new ArrayList<TeacherSubjectDTO>();
        db = conn.getReadableDatabase();
        String[] parameters = {subjectDTO.getIdSubject()};
        String[] fields = {FIELD_ID_TEACHER_SUBJECT, FIELD_ID_TEACHER, FIELD_ID_SUBJECT};

        try {
            Cursor cursor = db.query(TABLE_TEACHER_SUBJECT, fields, FIELD_ID_SUBJECT + "=?", parameters, null, null, null);

            while(cursor.moveToNext()) {
                teacherDAO = new TeacherDAO(context);
                TeacherDTO teacherDTO = teacherDAO.readbyid(cursor.getString(1));

                subjectDAO = new SubjectDAO(context);
                SubjectDTO subjectDTO1 = subjectDAO.readbyid(cursor.getString(2));

                TeacherSubjectDTO teacherSubjectDTO = new TeacherSubjectDTO(cursor.getString(0), teacherDTO, subjectDTO1);
                arrayListTeacherSubjectDTO.add(teacherSubjectDTO);
            }


            cursor.close();
        } catch (Exception e) {
            Toast.makeText(context, "Ha ocurrido un error al consultar los datos: " + e.getMessage() , Toast.LENGTH_SHORT).show();
        }
        return arrayListTeacherSubjectDTO;
    }


    @Override
    public boolean insert(TeacherSubjectDTO obj) {
        SQLiteDatabase db = conn.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FIELD_ID_TEACHER_SUBJECT, obj.getIdTeacherSubject());
        values.put(FIELD_ID_TEACHER, obj.getTeacher().getIdTeacher());
        values.put(FIELD_ID_SUBJECT, obj.getSubject().getIdSubject());

        boolean result = false;
        Long idResult = db.insert(TABLE_TEACHER_SUBJECT, FIELD_ID_TEACHER_SUBJECT, values);
        Toast.makeText(context, "ID: " + idResult.toString(), Toast.LENGTH_SHORT).show();
        if(idResult.equals(-1)) {
            Toast.makeText(context, "Ha ocurrido un error al insertar el registro: El ID: " + obj.getIdTeacherSubject() + " ya se encuentra registrado en la base de datos", Toast.LENGTH_SHORT).show();
            result = false;
        } else {
            Toast.makeText(context, "Registro insertado correctamente ID: " + obj.getIdTeacherSubject(), Toast.LENGTH_SHORT).show();
            db.close();
            result = true;
        }

        return result;
    }

    @Override
    public boolean update(TeacherSubjectDTO obj) {
        SQLiteDatabase db = conn.getWritableDatabase();

        String[] parameters = {obj.getIdTeacherSubject()};
        ContentValues values = new ContentValues();
        values.put(FIELD_ID_TEACHER, obj.getTeacher().getIdTeacher());
        values.put(FIELD_ID_SUBJECT, obj.getSubject().getIdSubject());

        boolean result = false;
        int idResult = db.update(TABLE_TEACHER_SUBJECT, values, FIELD_ID_TEACHER_SUBJECT +"=?", parameters);
        Toast.makeText(context, "Se actualizarón los datos del ID: " + obj.getIdTeacherSubject(), Toast.LENGTH_SHORT).show();

        return result;
    }

    @Override
    public boolean delete(String id) {
        SQLiteDatabase db = conn.getWritableDatabase();

        String[] parameters = {id};

        boolean result = false;
        int idResult = db.delete(TABLE_TEACHER_SUBJECT, FIELD_ID_TEACHER_SUBJECT +"=?", parameters);

        Toast.makeText(context, "Se eliminó el registro con ID: " + id, Toast.LENGTH_SHORT).show();

        return result;
    }
}
