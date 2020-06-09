package com.example.practicaloginsqlite.dto;

import androidx.annotation.NonNull;

import java.util.Objects;

public class RolUserDTO {
    private String idRol;
    private String nameRol;

    public RolUserDTO(String idRol, String nameRol) {
        this.idRol = idRol;
        this.nameRol = nameRol;
    }

    public String getIdRol() {
        return idRol;
    }

    public void setIdRol(String idRol) {
        this.idRol = idRol;
    }

    public String getNameRol() {
        return nameRol;
    }

    public void setNameRol(String nameRol) {
        this.nameRol = nameRol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RolUserDTO rolUserDTO = (RolUserDTO) o;
        return Objects.equals(idRol, rolUserDTO.idRol) &&
                Objects.equals(nameRol, rolUserDTO.nameRol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRol,nameRol);
    }

    @NonNull
    @Override
    public String toString() {
        return "IDRol: " + this.getIdRol() + ", Rol: " + getNameRol();
    }
}
