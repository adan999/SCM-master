package com.scm.scm.Model;

import lombok.Data;

import java.util.Date;

@Data
public class Compra {
    private int idCompras;

    private String NombreProd;

    private double Precio;

    private int Cantidad;

    private String Unidad;

    private double Total;

    private String Fecha;

    private String Estado;
}
