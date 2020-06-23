package com.example.practicaloginschoolsqlite.dto;

import androidx.annotation.NonNull;

import java.util.Objects;

public class StudentSubjectDTO {
    private String idStudentSubject;
    private StudentDTO student;
    private SubjectDTO subject;

    public StudentSubjectDTO(String idStudentSubject, StudentDTO student, SubjectDTO subject) {
        this.idStudentSubject = idStudentSubject;
        this.student = student;
        this.subject = subject;
    }

    public String getIdStudentSubject() {
        return idStudentSubject;
    }
    public void setIdStudentSubject(String idStudentSubject) {
        this.idStudentSubject = idStudentSubject;
    }


    public StudentDTO getStudent() {
        return student;
    }

    public void setStudent(StudentDTO student) {
        this.student = student;
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
        StudentSubjectDTO rolUserDTO = (StudentSubjectDTO) o;
        return Objects.equals(idStudentSubject, rolUserDTO.idStudentSubject) &&
                Objects.equals(student, rolUserDTO.student) &&
                Objects.equals(subject, rolUserDTO.subject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idStudentSubject, student, subject);
    }

    @NonNull
    @Override
    public String toString() {
        return student.getNameStudent() + ", " + subject.getNameSubject();
    }
}
