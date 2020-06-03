package com.example.practicaloginsqlite;

import androidx.annotation.NonNull;

public class RolUser {
    private int idTipoUsuario;
    private String nombretipousuario;

    public RolUser(){

    }

    public RolUser(int idTipoUsuario, String nombretipousuario) {
        this.idTipoUsuario = idTipoUsuario;
        this.nombretipousuario = nombretipousuario;
    }

    public int getIdTipoUsuario() {
        return idTipoUsuario;
    }

    public void setIdTipoUsuario(int idTipoUsuario) {
        this.idTipoUsuario = idTipoUsuario;
    }

    public String getNombretipousuario() {
        return nombretipousuario;
    }

    public void setNombretipousuario(String nombretipousuario) {
        this.nombretipousuario = nombretipousuario;
    }

    @NonNull
    @Override
    public String toString() {
        return nombretipousuario;
    }
}
