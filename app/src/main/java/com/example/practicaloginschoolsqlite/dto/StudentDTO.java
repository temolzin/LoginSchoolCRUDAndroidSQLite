package com.example.practicaloginschoolsqlite.dto;

import androidx.annotation.NonNull;

import java.util.Objects;

public class StudentDTO {

    private String idStudent;
    private String nameStudent;
    private int ageStudent;
    private String semesterStudent;
    private String genreStudent;
    private CareerDTO idCareer;

    public StudentDTO(String idStudent, String nameStudent, int ageStudent, String semesterStudent, String genreStudent, CareerDTO idCareer) {
        this.idStudent = idStudent;
        this.nameStudent = nameStudent;
        this.ageStudent = ageStudent;
        this.semesterStudent = semesterStudent;
        this.genreStudent = genreStudent;
        this.idCareer = idCareer;
    }

    public String getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(String idStudent) {
        this.idStudent = idStudent;
    }

    public String getNameStudent() {
        return nameStudent;
    }

    public void setNameStudent(String nameStudent) {
        this.nameStudent = nameStudent;
    }

    public int getAgeStudent() {
        return ageStudent;
    }

    public void setAgeStudent(int ageStudent) {
        this.ageStudent = ageStudent;
    }

    public String getSemesterStudent() {
        return semesterStudent;
    }

    public void setSemesterStudent(String semesterStudent) {
        this.semesterStudent = semesterStudent;
    }

    public String getGenreStudent() {
        return genreStudent;
    }

    public void setGenreStudent(String genreStudent) {
        this.genreStudent = genreStudent;
    }

    public CareerDTO getIdCareer() {
        return idCareer;
    }

    public void setIdCareer(CareerDTO idCareer) {
        this.idCareer = idCareer;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idStudent,nameStudent,ageStudent,semesterStudent, genreStudent, idCareer);
    }

    @NonNull
    @Override
    public String toString() {
        return getNameStudent();
    }
}
