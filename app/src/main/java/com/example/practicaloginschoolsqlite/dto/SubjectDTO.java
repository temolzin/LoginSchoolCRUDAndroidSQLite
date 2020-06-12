package com.example.practicaloginschoolsqlite.dto;

import androidx.annotation.NonNull;

import java.util.Objects;

public class SubjectDTO {
    private String idSubject;
    private String nameSubject;
    private int creditSubject;

    public SubjectDTO(String idSubject, String nameSubject, int creditSubject) {
        this.idSubject = idSubject;
        this.nameSubject = nameSubject;
        this.creditSubject = creditSubject;
    }

    public String getIdSubject() {
        return idSubject;
    }
    public void setIdSubject(String idSubject) {
        this.idSubject = idSubject;
    }


    public String getNameSubject() {
        return nameSubject;
    }

    public void setNameSubject(String nameSubject) {
        this.nameSubject = nameSubject;
    }

    public int getCreditSubject() {
        return creditSubject;
    }

    public void setCreditSubject(int creditSubject) {
        this.creditSubject = creditSubject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubjectDTO rolUserDTO = (SubjectDTO) o;
        return Objects.equals(idSubject, rolUserDTO.idSubject) &&
                Objects.equals(nameSubject, rolUserDTO.nameSubject) &&
                Objects.equals(creditSubject, rolUserDTO.creditSubject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idSubject, nameSubject, creditSubject);
    }

    @NonNull
    @Override
    public String toString() {
        return nameSubject;
    }
}
