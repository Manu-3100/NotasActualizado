package com.pmdm.notas.NotasAdapter.Entities;

import java.io.Serializable;

public class Nota implements Serializable {
    private String titulo;
    private String data;
    private String modulo;
    private String imaxe;
    private String texto;
    private boolean eliminar;

    public Nota(String titulo, String data, String modulo) {
        this.titulo = titulo;
        this.data = data;
        this.modulo = modulo;
        this.eliminar = false;
    }


    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
    }

    public String getImaxe() {
        return imaxe;
    }

    public void setImaxe(String imaxe) {
        this.imaxe = imaxe;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public boolean isEliminar() {
        return eliminar;
    }

    public void setEliminar(boolean eliminar) {
        this.eliminar = eliminar;
    }
}
