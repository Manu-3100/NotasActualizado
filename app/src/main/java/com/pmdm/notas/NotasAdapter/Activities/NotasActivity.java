package com.pmdm.notas.NotasAdapter.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pmdm.notas.Login.MainActivity;
import com.pmdm.notas.NotasAdapter.Adapter.NotasAdapter;
import com.pmdm.notas.NotasAdapter.Dialogs.AddNotaDF;
import com.pmdm.notas.NotasAdapter.Entities.Nota;
import com.pmdm.notas.NotasAdapter.Entities.NotaAmpliada;
import com.pmdm.notas.R;
import com.pmdm.notas.databinding.NotasReciclerBinding;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NotasActivity extends AppCompatActivity implements AddNotaDF.AddNotaDialogListener{

    private NotasReciclerBinding binding = null;
    // declaramos o Recycler view e inicializamolo a null

    RecyclerView rvNota = null;
    // declaramos o adaptador do noso recyclerView
    private NotasAdapter adapter;

    // Almacen de notas
    List<Nota> notasList;

    // creamos unha variable para gardar o nome do usuario co que se inicia
    String usuario;
    //codigo para recibir os datos da nota modificada
    private static final int COD_PETICION = 33;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.notas_recicler);

        // Recibimos o nome do usuario
        usuario = getIntent().getStringExtra("usuario");

        // Asignamos a toolbar á action bar
        setSupportActionBar(binding.toolbar);
        binding.rvNota.setLayoutManager(new LinearLayoutManager(this));

        notasList = new ArrayList<Nota>();
        notasList.add(new Nota("Binding", "15/11/2024", "pmdm"));
        notasList.add(new Nota("ProccesBuilder", "20/09/2024", "psp"));
        notasList.add(new Nota("XML", "15/11/2024", "acda"));
        notasList.add(new Nota("JSON", "01/12/2024", "acda"));
        notasList.add(new Nota("Tragaperras", "10/11/2024", "dein"));
        notasList.add(new Nota("", "", ""));
        ejecutarRecycler();
        adminFAB();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        menu.findItem(R.id.NomeUsuario).setTitle(usuario);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.EngadirNota){
            Toast.makeText(this, "ENGADIR NOTA", Toast.LENGTH_SHORT).show();
            df_fab();
            return true;
        }
        if(id == R.id.EliminarNota){
            // creamos iterador para nuestra lista, para no tener problemas con el índice luego.
            Iterator<Nota> it = notasList.iterator();

            // bucle mientras haya notas en la lista.
            while(it.hasNext()){
                // siguiente elemento.
                Nota nota = it.next();
                // se mira si tiene checkbox
                if (nota.isEliminar()){
                    // devolvemos la posición actual de la nota en la lista
                    int position = notasList.indexOf(nota);
                    // la borramos.
                    it.remove();
                    // notificamos al adapter.
                    adapter.notifyItemRemoved(position);
                }
            }
            Toast.makeText(this, "ELIMINAR NOTA",Toast.LENGTH_SHORT).show();
            return true;
        }
        if(id == R.id.CerrarSesion){
            Toast.makeText(this, "CERRAR SESIÓN",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(NotasActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int position;
        Nota nota;
        if(requestCode == COD_PETICION){
            if(resultCode == RESULT_OK){
                //Toast.makeText(this, "Cambiando datos...", Toast.LENGTH_SHORT).show();
                position = data.getIntExtra("posicion", 0);
                nota = (Nota) data.getSerializableExtra("nota");
                notasList.get(position).setTitulo(nota.getTitulo());
                notasList.get(position).setData(nota.getData());
                notasList.get(position).setModulo(nota.getModulo());
                notasList.get(position).setTexto(nota.getTexto());
                adapter.notifyItemChanged(position);
            }
            else{
                Toast.makeText(this, "ERRO NA EXECUCIÓN", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(this, "ERRO NA EXECUCIÓN", Toast.LENGTH_SHORT).show();
        }
    }
    public void ejecutarRecycler(){
        adapter = new NotasAdapter(notasList, getApplicationContext(), new NotasAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(int position) {
                Toast.makeText(NotasActivity.this, notasList.get(position).getTitulo(), Toast.LENGTH_SHORT).show();
                adapter.notifyItemChanged(position);

                // aqui ten que abrir a actividade nota Ampliada
                Intent intent = new Intent(NotasActivity.this, NotaAmpliada.class);
                intent.putExtra("nota", (Serializable) notasList.get(position));
                intent.putExtra("posicion", position);
                startActivityForResult(intent, COD_PETICION);
            }
        }, new NotasAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(int position) {
                Toast.makeText(NotasActivity.this, "LONG CLICK", Toast.LENGTH_SHORT).show();
            }
        });
        // asignamos o adaptador ao recycler view
        binding.rvNota.setAdapter(adapter);
    }

    public void adminFAB(){
        // Xestión do botón flotante, agora invocando un dialog fragment
        // Inicializa el Floating Action Button
        FloatingActionButton fab = binding.fab;
        fab.setOnClickListener(view -> {df_fab();});
    }

    public void df_fab(){
        // Aquí podese engadir a lóxica para engadir unha nova ruta
        AddNotaDF dialog = new AddNotaDF();
        // Engadir a actividade como listener do dialogo para poder recibir os datos
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // Engadir o dialogo ao fragment manager co nome onRutaAdded
        transaction.add(dialog, "onNotaAdded");
        // Mostrar o dialogo na pantalla
        transaction.commit();
    }

    @Override
    public void onNotaAdded(EditText titulo, EditText data, EditText modulo) {
        Nota novaNota = new Nota(titulo.getText().toString(), data.getText().toString(), modulo.getText().toString());
        notasList.add(novaNota);
        adapter.notifyItemInserted(notasList.size() - 1);
        // Mostrar mensaxe de éxito
        Toast.makeText(this, "NOTA AGREGADA: "+ titulo.getText().toString(), Toast.LENGTH_SHORT).show();
    }

}
