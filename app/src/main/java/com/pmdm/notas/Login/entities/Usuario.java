package com.pmdm.notas.Login.entities;

import static com.pmdm.notas.bd.NotasReaderContract.UsuariosEntry.COLUMN_NAME_CONTRASENHA;
import static com.pmdm.notas.bd.NotasReaderContract.UsuariosEntry.COLUMN_NAME_NOMBRE;
import static com.pmdm.notas.bd.NotasReaderContract.UsuariosEntry.TABLE_NAME;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.pmdm.notas.bd.NotasBbHelper;

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
        boolean esta = false;
        SQLiteDatabase db = ndbh.getReadableDatabase();
        Cursor usuarios = ndbh.getUsuarios(db);

        int columIndex;

        if(usuarios != null) {
            while (usuarios.moveToNext()) {

                columIndex = usuarios.getColumnIndex(COLUMN_NAME_NOMBRE);

                if(columIndex != -1) {

                    if (usuarios.getString(columIndex).equals(this.nome)) {
                        esta = true;
                        break;
                    }
                }else{
                    break;
                }
            }
        }
        return esta;
    }
    public boolean comprobarContrasenha(NotasBbHelper ndbh){
        boolean esta = false;
        SQLiteDatabase db = ndbh.getReadableDatabase();
        Cursor contrasenhas = ndbh.getUsuarios(db);
        int columIndex;

        if(contrasenhas != null) {
            while (contrasenhas.moveToNext()) {
                columIndex = contrasenhas.getColumnIndex(COLUMN_NAME_CONTRASENHA);

                if(columIndex != -1) {
                    if (contrasenhas.getString(columIndex).equals(this.contrasinal)) {
                        esta = true;
                        break;
                    }
                }else{
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
        cv.put(COLUMN_NAME_CONTRASENHA, this.contrasinal);

        db.insert(TABLE_NAME, null, cv);

    }
}
