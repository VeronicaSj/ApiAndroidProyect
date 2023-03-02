package com.example.proyecto1mertrimestre.model;

public class Duck {

    //la api que he elegido tiene las imagenes de patos nombradas con numeros y puedes acceder a
    // cada imagen utilizando el nombre
    private String imagen;

    public Duck(String imagenId) {
        this.imagen = imagenId;
    }

    public String getImagen(){
        return this.imagen;
    }

    public String toString(){
        return imagen;
    }
}
