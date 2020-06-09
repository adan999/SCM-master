package com.scm.scm.Model;


import lombok.Data;


public class TipoMaterial {

    private int idMaterialCat;
    private String codigoTipo;
    private String nomCategoria;
    private String unidadMedida;
    private double cantidad;
    private String nomUsuario;

    public int getIdMaterialCat() {
        return idMaterialCat;
    }

    public void setIdMaterialCat(int idMaterialCat) {
        this.idMaterialCat = idMaterialCat;
    }

    public String getCodigoTipo() {
        return codigoTipo;
    }

    public void setCodigoTipo(String codigoTipo) {
        this.codigoTipo = codigoTipo;
    }

    public String getNomCategoria() {
        return nomCategoria;
    }

    public void setNomCategoria(String nomCategoria) {
        this.nomCategoria = nomCategoria;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public String getNomUsuario() {
        return nomUsuario;
    }

    public void setNomUsuario(String nomUsuario) {
        this.nomUsuario = nomUsuario;
    }
}
