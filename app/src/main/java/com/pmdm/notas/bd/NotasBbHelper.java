package com.pmdm.notas.bd;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
public class NotasBbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Notas.db";
    private static final int DATABASE_VERSION = 1;
    private static final String SQL_CREATE_NOTAS_ENTRIES =
            "CREATE TABLE " + NotasReaderContract.NotasEntry.TABLE_NAME + " (" +
                    NotasReaderContract.NotasEntry._ID + " INTEGER PRIMARY KEY," +
                    NotasReaderContract.NotasEntry.COLUMN_NAME_NOMBRE + " TEXT," +
                    NotasReaderContract.NotasEntry.COLUMN_NAME_FECHA_CREACION + " TEXT," +
                    NotasReaderContract.NotasEntry.COLUMN_NAME_NOMBRE_IMAGEN + " TEXT)";
    private static final String SQL_DELETE_NOTAS_ENTRIES =
            "DROP TABLE IF EXISTS " + NotasReaderContract.NotasEntry.TABLE_NAME;

    private static final String SQL_CREATE_USUARIOS_ENTRIES =
            "CREATE TABLE " + NotasReaderContract.UsuariosEntry.TABLE_NAME + " (" +
                    NotasReaderContract.UsuariosEntry._ID+ " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    NotasReaderContract.UsuariosEntry.COLUMN_NAME_NOMBRE + " TEXT," +
                    NotasReaderContract.UsuariosEntry.COLUMN_NAME_CONTRASEÑA + " TEXT)";

    private static final String SQL_DELETE_USUARIOS_ENTRIES =
            "DROP TABLE IF EXISTS " + "usuarios";

    public NotasBbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_USUARIOS_ENTRIES);
        //db.execSQL(SQL_CREATE_NOTAS_ENTRIES);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_USUARIOS_ENTRIES);
        //db.execSQL(SQL_DELETE_NOTAS_ENTRIES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public Cursor getUsuarios(SQLiteDatabase bd) {
        return bd.query("usuarios",
                null,
                null,
                null,
                null,
                null,
                null,
                null);
    }
}
