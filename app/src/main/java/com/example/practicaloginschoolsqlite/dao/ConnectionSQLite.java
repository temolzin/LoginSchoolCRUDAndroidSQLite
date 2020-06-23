package com.example.practicaloginschoolsqlite.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class ConnectionSQLite extends SQLiteOpenHelper {

    public ConnectionSQLite(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UserDAO.CREATE_TABLE_USER);
        db.execSQL(RolUserDAO.CREATE_TABLE_ROL_USER);
        db.execSQL(AccessDAO.CREATE_TABLE_ACCESS);
        db.execSQL(SubjectDAO.CREATE_TABLE_SUBJECT);
        db.execSQL(CareerDAO.CREATE_TABLE_CARRER);
        db.execSQL(TeacherDAO.CREATE_TABLE_TEACHER);
        db.execSQL(StudentDAO.CREATE_TABLE_STUDENT);
        db.execSQL(TeacherSubjectDAO.CREATE_TABLE_TEACHER_SUBJECT);
        db.execSQL(StudentSubjectDAO.CREATE_TABLE_STUDENT_SUBJECT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS USER");
        db.execSQL("DROP TABLE IF EXISTS ROLUSER");
        db.execSQL("DROP TABLE IF EXISTS ACCESS");
        db.execSQL("DROP TABLE IF EXISTS SUBJECT");
        db.execSQL("DROP TABLE IF EXISTS CAREER");
        db.execSQL("DROP TABLE IF EXISTS STUDENT");
        db.execSQL("DROP TABLE IF EXISTS TEACHER");
        db.execSQL("DROP TABLE IF EXISTS TEACHERSUBJECT");
        db.execSQL("DROP TABLE IF EXISTS STUDENTSUBJECT");
    }
}
