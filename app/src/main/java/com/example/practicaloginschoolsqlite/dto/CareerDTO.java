package com.example.practicaloginschoolsqlite.dto;

import androidx.annotation.NonNull;

import java.util.Objects;

public class CareerDTO {
    private String idCareer;
    private String nameCareer;
    private String durationCareer;

    public CareerDTO(String idCareer, String nameCareer, String durationCareer) {
        this.idCareer = idCareer;
        this.nameCareer = nameCareer;
        this.durationCareer = durationCareer;
    }

    public String getIdCareer() {
        return idCareer;
    }
    public void setIdCareer(String idCareer) {
        this.idCareer = idCareer;
    }


    public String getNameCareer() {
        return nameCareer;
    }

    public void setNameCareer(String nameCareer) {
        this.nameCareer = nameCareer;
    }

    public String getDurationCareer() {
        return durationCareer;
    }

    public void setDurationCareer(String durationCareer) {
        this.durationCareer = durationCareer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CareerDTO rolUserDTO = (CareerDTO) o;
        return Objects.equals(idCareer, rolUserDTO.idCareer) &&
                Objects.equals(nameCareer, rolUserDTO.nameCareer) &&
                Objects.equals(durationCareer, rolUserDTO.durationCareer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCareer, nameCareer, durationCareer);
    }

    @NonNull
    @Override
    public String toString() {
        return nameCareer;
    }
}
