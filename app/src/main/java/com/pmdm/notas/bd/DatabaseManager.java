package com.pmdm.notas.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseManager {
    private static DatabaseManager instance;
    private SQLiteDatabase database;
    private NotasBbHelper dbHelper;

    private DatabaseManager(Context context) {
        dbHelper = new NotasBbHelper(context.getApplicationContext());
        database = dbHelper.getWritableDatabase(); // Abrir conexión
    }

    public static synchronized DatabaseManager getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseManager(context);
        }
        return instance;
    }

    public SQLiteDatabase getDatabase() {
        return database;
    }

    public void closeDatabase() {
        if (database != null && database.isOpen()) {
            database.close();
        }
    }

    public static long insertarDatos(SQLiteDatabase db, String tabla, ContentValues values){
        return db.insert(NotasReaderContract.UsuariosEntry.TABLE_NAME, null, values);
    }

}
