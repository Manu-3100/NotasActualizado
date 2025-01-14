package com.pmdm.notas.Login.entities;

import androidx.annotation.Nullable;

import java.util.Objects;

public class Usuario {

    private String nome;
    private String contrasinal;

    public Usuario(String nome, String contrasinal) {
        this.nome = nome;
        this.contrasinal = contrasinal;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getContrasinal() {
        return contrasinal;
    }

    public void setContrasinal(String contrasinal) {
        this.contrasinal = contrasinal;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) return true; // Compara referencias
        if (obj == null || getClass() != obj.getClass()) 
            return false; // Verifica el tipo
        Usuario usuario = (Usuario) obj;
        return Objects.equals(nome, usuario.nome) &&
                Objects.equals(contrasinal, usuario.contrasinal); // Compara los atributos
    }
}
