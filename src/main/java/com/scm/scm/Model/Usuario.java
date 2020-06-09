package com.scm.scm.Model;

//Modelo de Usuario, donde las variables hacen referencia a las columnas de su respectiva tabla en la base de datos
public class Usuario {

    private String nomUsuario;

    private String contrasena;

    private int idTipoU;

    private String nomTipoU;

    //En esta seccion se generan los metodos get() y set()
    public String getNomUsuario() {
        return nomUsuario;
    }

    public void setNomUsuario(String nomUsuario) {
        this.nomUsuario = nomUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public int getIdTipoU() {
        return idTipoU;
    }

    public void setIdTipoU(int idTipoU) {
        this.idTipoU = idTipoU;
    }

    public String getNomTipoU() {
        return nomTipoU;
    }

    public void setNomTipoU(String nomTipoU) {
        this.nomTipoU = nomTipoU;
    }
}
