package com.example.practicaloginsqlite.dto;

public class AccessDTO {
    private String idAccess;
    private String idRolUser;
    private String idUser;
    private RolUserDTO objRolUser;
    private UserDTO objUser;
    private String userName;
    private String password;

    public AccessDTO(String idAccess, String idRolUser, String idUser, String userName, String password) {
        this.idAccess = idAccess;
        this.idRolUser = idRolUser;
        this.idUser = idUser;
        this.userName = userName;
        this.password = password;
    }

    public AccessDTO(String idAccess, RolUserDTO objRolUser, UserDTO objUser, String userName, String password) {
        this.idAccess = idAccess;
        this.objRolUser = objRolUser;
        this.objUser = objUser;
        this.userName = userName;
        this.password = password;
    }

    public String getIdAccess() {
        return idAccess;
    }

    public void setIdAccess(String idAccess) {
        this.idAccess = idAccess;
    }

    public RolUserDTO getObjRolUser() {
        return objRolUser;
    }

    public void setObjRolUser(RolUserDTO objRolUser) {
        this.objRolUser = objRolUser;
    }

    public UserDTO getObjUser() {
        return objUser;
    }

    public void setObjUser(UserDTO objUser) {
        this.objUser = objUser;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIdRolUser() {
        return idRolUser;
    }

    public void setIdRolUser(String idRolUser) {
        this.idRolUser = idRolUser;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
}
