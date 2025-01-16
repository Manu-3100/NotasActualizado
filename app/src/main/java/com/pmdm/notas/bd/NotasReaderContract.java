package com.pmdm.notas.bd;

import android.provider.BaseColumns;

public final class NotasReaderContract {
    private NotasReaderContract() {}

    public static final class UsuariosEntry implements BaseColumns {
        public static final String TABLE_NAME = "usuarios";
        public static final String COLUMN_NAME_NOMBRE = "nombre";
        public static final String COLUMN_NAME_CONTRASEÑA = "contraseña";
    }
    public static final class NotasEntry implements BaseColumns {
        public static final String TABLE_NAME = "notas";
        public static final String COLUMN_NAME_NOMBRE = "nombre";
        public static final String COLUMN_NAME_FECHA_CREACION = "fecha_creacion";
        public static final String COLUMN_NAME_NOMBRE_IMAGEN = "nombre_imagen";
    }
}
