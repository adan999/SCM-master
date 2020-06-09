package com.scm.scm.Model;

//Modelo de Tipo de Usuario, donde las variables hacen referencia a las columnas de su respectiva tabla en la base de datos
public class TipoUsuario {

    private int idTipoU;

    private String nomTipoU;

    //En esta seccion se generan los metodos get() y set()
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
