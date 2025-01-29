package com.pmdm.notas.Login.entities;

import static com.pmdm.notas.bd.NotasReaderContract.UsuariosEntry.COLUMN_NAME_CONTRASEÑA;
import static com.pmdm.notas.bd.NotasReaderContract.UsuariosEntry.COLUMN_NAME_NOMBRE;
import static com.pmdm.notas.bd.NotasReaderContract.UsuariosEntry.TABLE_NAME;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.pmdm.notas.bd.NotasBbHelper;
import com.pmdm.notas.bd.NotasReaderContract;

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

    public boolean comprobarUsuario(NotasBbHelper ndbh) {
        boolean esta = true;
        SQLiteDatabase db = ndbh.getReadableDatabase();
        Cursor usuarios = ndbh.getUsuarios(db);
        String usuario = "";
        int columIndex;

        if(usuarios != null) {
            while (usuarios.moveToNext()) {
                columIndex = usuarios.getColumnIndex(COLUMN_NAME_NOMBRE);

                if (columIndex == -1)
                    esta = false;
                else
                    usuario = usuarios.getString(columIndex);

                if (usuario.equals(this.nome)){
                    esta = true;
                    break;
                }
            }
        }
        return esta;
    }

    public void insertUser(NotasBbHelper ndbh) {

        SQLiteDatabase db = ndbh.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME_NOMBRE, this.nome);
        cv.put(COLUMN_NAME_CONTRASEÑA, this.contrasinal);

        db.insert(TABLE_NAME, null, cv);



    }


}
