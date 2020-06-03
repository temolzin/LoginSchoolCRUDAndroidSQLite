package com.example.practicaloginsqlite.dto;

public class RolUserDTO {
    private String idRol;
    private String nomRol;

    public RolUserDTO(String idRol, String nomRol) {
        this.idRol = idRol;
        this.nomRol = nomRol;
    }

    public String getIdRol() {
        return idRol;
    }

    public void setIdRol(String idRol) {
        this.idRol = idRol;
    }

    public String getNomRol() {
        return nomRol;
    }

    public void setNomRol(String nomRol) {
        this.nomRol = nomRol;
    }
}
