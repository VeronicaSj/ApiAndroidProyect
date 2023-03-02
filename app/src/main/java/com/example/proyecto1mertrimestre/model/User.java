package com.example.proyecto1mertrimestre.model;

public class User {
    private String nombre;
    private String contrasenia;

    public User(String nombre, String contrasenia){
        setNombre(nombre);
        setContrasenia(contrasenia);
    }

    //setters
    public void setNombre(String nombre){
        if(parametroApto(nombre)){
            this.nombre=nombre;
        }
    }
    public void setContrasenia(String contrasenia){
        if(parametroApto(contrasenia)){
            this.contrasenia=contrasenia;
        }
    }

    //getters
    public String getContrasenia(){
        return this.contrasenia;
    }
    public String getNombre(){
        return this.nombre;
    }

    public boolean parametroApto(String parametro){
        boolean res =false;
        if(parametro!=null && parametro.length()>=3 &&
            parametro.length()<=10 && soloCaracteresAZMinuscula(parametro)){
            res=true;
        }
        return res;
    }

    //esta es una funcion auxiliar que nos permite comprobar que los parametros de
    // usuario tengan solo caracteres de la 'a' a la 'z' en minuscula
    private boolean soloCaracteresAZMinuscula(String parametro){
        boolean res=true;
        //no tengo que comprobar que parametro no sea nulo, ya lo he comprobado antes

        for(int i =0; i<=parametro.length()-1; i++){
            if(parametro.charAt(i)<'a' || parametro.charAt(i)>'z'){
                res=false;
            }
        }
        return res;
    }

    //funcion que devuelve un mensage utilizado para comunicarse con otras funciones
    public String obtenerMsg(){
        //declaracion de variables
        String res="valido";
        boolean nomApto;
        boolean pwApto;

        //comprobamos que los parametros indibidualmente son aptos
        nomApto= parametroApto(this.nombre);
        if (nomApto){
            pwApto= parametroApto(this.contrasenia);
            if (pwApto){
            }else{
                res="contrasenia no valida";
            }
        }else{
            res="nombre no valido";
        }

        return res;
    }
}
