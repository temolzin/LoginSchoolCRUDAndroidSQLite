package com.example.practicaloginschoolsqlite.dto;

import androidx.annotation.NonNull;

import java.util.Objects;

public class TeacherDTO {

    private String idTeacher;
    private String nameTeacher;
    private String addressTeacher;
    private String phoneTeacher;
    private String scheduleTeacher;

    public TeacherDTO(String idTeacher, String nameTeacher, String addressTeacher, String phoneTeacher, String scheduleTeacher) {
        this.idTeacher = idTeacher;
        this.nameTeacher = nameTeacher;
        this.addressTeacher = addressTeacher;
        this.phoneTeacher = phoneTeacher;
        this.scheduleTeacher = scheduleTeacher;
    }

    public String getIdTeacher() {
        return idTeacher;
    }

    public void setIdTeacher(String idTeacher) {
        this.idTeacher = idTeacher;
    }

    public String getNameTeacher() {
        return nameTeacher;
    }

    public void setNameTeacher(String nameTeacher) {
        this.nameTeacher = nameTeacher;
    }

    public String getAddressTeacher() {
        return addressTeacher;
    }

    public void setAddressTeacher(String addressTeacher) {
        this.addressTeacher = addressTeacher;
    }

    public String getPhoneTeacher() {
        return phoneTeacher;
    }

    public void setPhoneTeacher(String phoneTeacher) {
        this.phoneTeacher = phoneTeacher;
    }

    public String getScheduleTeacher() {
        return scheduleTeacher;
    }

    public void setScheduleTeacher(String scheduleTeacher) {
        this.scheduleTeacher = scheduleTeacher;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTeacher,nameTeacher,addressTeacher,phoneTeacher, scheduleTeacher);
    }

    @NonNull
    @Override
    public String toString() {
        return getNameTeacher();
    }
}
