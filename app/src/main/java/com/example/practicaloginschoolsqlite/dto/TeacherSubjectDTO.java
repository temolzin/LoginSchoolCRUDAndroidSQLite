package com.example.practicaloginschoolsqlite.dto;

import androidx.annotation.NonNull;

import java.util.Objects;

public class TeacherSubjectDTO {
    private String idTeacherSubject;
    private TeacherDTO teacher;
    private SubjectDTO subject;

    public TeacherSubjectDTO(String idTeacherSubject, TeacherDTO teacher, SubjectDTO subject) {
        this.idTeacherSubject = idTeacherSubject;
        this.teacher = teacher;
        this.subject = subject;
    }

    public String getIdTeacherSubject() {
        return idTeacherSubject;
    }
    public void setIdTeacherSubject(String idTeacherSubject) {
        this.idTeacherSubject = idTeacherSubject;
    }


    public TeacherDTO getTeacher() {
        return teacher;
    }

    public void setTeacher(TeacherDTO teacher) {
        this.teacher = teacher;
    }

    public SubjectDTO getSubject() {
        return subject;
    }

    public void setSubject(SubjectDTO subject) {
        this.subject = subject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeacherSubjectDTO rolUserDTO = (TeacherSubjectDTO) o;
        return Objects.equals(idTeacherSubject, rolUserDTO.idTeacherSubject) &&
                Objects.equals(teacher, rolUserDTO.teacher) &&
                Objects.equals(subject, rolUserDTO.subject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTeacherSubject, teacher, subject);
    }

    @NonNull
    @Override
    public String toString() {
        return teacher.getNameTeacher() + ", " + subject.getNameSubject();
    }
}
