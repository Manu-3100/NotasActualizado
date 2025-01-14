package com.pmdm.notas.NotasAdapter.Entities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pmdm.notas.NotasAdapter.Activities.NotasActivity;
import com.pmdm.notas.R;

import java.io.Serializable;
import java.util.ArrayList;

public class NotaAmpliada extends AppCompatActivity {

    private EditText etTitulo;
    private EditText etData;
    private EditText etModulo;
    private EditText etTexto;
    private Button btGardar;
    private Button btCancelar;
    Nota nota;
    int position;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.nota_ampliada);

        etTitulo = (EditText) findViewById(R.id.cambiarTitulo);
        etData = (EditText) findViewById(R.id.cambiarData);
        etModulo = (EditText) findViewById(R.id.cambiarModulo);
        etTexto = (EditText) findViewById(R.id.etTexto);
        btGardar = (Button) findViewById(R.id.btGardar);
        btCancelar = (Button) findViewById(R.id.btCancelar);

        Intent intent = getIntent();
        // recibese o arrayLis
        nota = (Nota) intent.getSerializableExtra("nota");
        position = intent.getIntExtra("posicion", 0);

        etTitulo.setText(nota.getTitulo());
        etData.setText(nota.getData());
        etModulo.setText(nota.getModulo());
        etTexto.setText(nota.getTexto());

        btGardar.setOnClickListener(view -> gardarClick(nota));
        btCancelar.setOnClickListener(view -> cancelarClick(nota));

        // el floating button para volver para atrás:
        FloatingActionButton goBack = findViewById(R.id.btAtras);

        // configurar la función del botón BACK:
        goBack.setOnClickListener(view -> {
            Intent i = new Intent();
            Toast.makeText(this, "CAMBIOS NON GARDADOS", Toast.LENGTH_SHORT).show();

            //devolver datos (vacío)
            i.putExtra("nota", (Serializable) nota);
            i.putExtra("posicion", position);

            setResult(RESULT_OK, i);
            super.finish();
        });
    }

    public void gardarClick(Nota nota){
        Intent datos_volta = new Intent();

        if(etTitulo.getText() != null && etData.getText() != null && etModulo.getText() != null){
            Toast.makeText(this, "GARDANDO CAMBIOS", Toast.LENGTH_SHORT).show();

            nota.setTitulo(etTitulo.getText().toString());
            nota.setData(etData.getText().toString());
            nota.setModulo(etModulo.getText().toString());
            nota.setTexto(etTexto.getText().toString());

            //devolver datos...
            datos_volta.putExtra("nota", (Serializable) nota);
            datos_volta.putExtra("posicion", position);
            setResult(RESULT_OK, datos_volta);

            super.finish();
        } else {
            Toast.makeText(this, "COMPLETE CAMPOS", Toast.LENGTH_SHORT).show();
        }
    }
    public void cancelarClick(Nota nota){
        Intent i = new Intent();
        Toast.makeText(this, "CANCELANDO CAMBIOS", Toast.LENGTH_SHORT).show();

        //devolver datos (vacío)
        i.putExtra("nota", (Serializable) nota);
        i.putExtra("posicion", position);

        setResult(RESULT_OK, i);
        super.finish();
    }
}
