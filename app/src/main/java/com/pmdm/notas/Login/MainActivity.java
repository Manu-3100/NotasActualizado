package com.pmdm.notas.Login;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.pmdm.notas.Login.entities.Usuario;
import com.pmdm.notas.NotasAdapter.Activities.NotasActivity;
import com.pmdm.notas.R;

import com.pmdm.notas.bd.NotasBbHelper;
import com.pmdm.notas.databinding.LoginBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    NotasBbHelper ndbh;
    SQLiteDatabase db;
    LoginBinding binding;

    // para mostrar o no la contraseña
    private boolean contraseñaVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = LoginBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        // necesitamos inicializar NotasDbHelper para poder acceder a la base de datos
        ndbh = new NotasBbHelper(this);

        // con esto podemos ver todo el tiempo la base de datos
        db = ndbh.getWritableDatabase();

        ndbh = new NotasBbHelper(this);
        // ver o no ver la contraseña:
        binding.etPassword.setOnTouchListener((v, event) -> {
            // mirar si estamos clicando en el ojo:
            if (event.getAction() == MotionEvent.ACTION_UP) {
                // indice del drawable de la derecha:
                int drawableDerecha = 2;
                if (event.getRawX() >= (binding.etPassword.getRight() - binding.etPassword.getCompoundDrawables()[drawableDerecha].getBounds().width())) {
                    alternadorContrasena();
                    return true;
                }
            }
            return false;
        });

        Button botonAcceder = binding.btAcceder;
        botonAcceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Usuario usuario = new Usuario(binding.etNome.getText().toString(), binding.etPassword.getText().toString());
                if(usuario.comprobarUsuario(ndbh) && usuario.comprobarContrasenha(ndbh)){
                    Intent intent = new Intent(MainActivity.this, NotasActivity.class);
                    intent.putExtra("usuario", usuario.getNome());
                    startActivity(intent);
                    Toast.makeText(MainActivity.this, "Accediendo", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "Usuario ou contrasinal incorrectos", Toast.LENGTH_SHORT).show();
                }

            }
        });
        // evento al clicar en registrar:
        Button botonRegistrar = binding.btRexistrarse;
        botonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Usuario usuario = new Usuario(binding.etNome.getText().toString(), binding.etPassword.getText().toString());
               if(!usuario.comprobarUsuario(ndbh)){
                   usuario.insertUser(ndbh);
                   Intent intent = new Intent(MainActivity.this, NotasActivity.class);
                   intent.putExtra("usuario", usuario.getNome());
                   startActivity(intent);
                   Toast.makeText(MainActivity.this, "Usuario Registrado", Toast.LENGTH_SHORT).show();
               } else {
                   Toast.makeText(MainActivity.this, "Usuario Ya esta Registrado", Toast.LENGTH_SHORT).show();
               }
            }
        });
        // revisar que los datos introducidos estén bien introducidos.
        // revisar si está registrado o no (mirar si está dentro de la lista)
        // si no está registrado sacar un toast y pedir que se registre.
        // si se registra sin querer y ya está registrado.
    }

    // alternar entre visible y inivisible
    private void alternadorContrasena() {
        if (contraseñaVisible) {
            // Si la contraseña está visible, la ocultamos:
            // Oculta la contraseña
            binding.etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            // Ojo cerrado
            binding.etPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_visible, 0);
        } else {
            // si está en invisible la hacemos visible
            // enseñamos contraseña
            binding.etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            // Ojo abierto
            binding.etPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_invisible, 0);
        }
        // Cambia el estado de la visibilidad de la contraseña:
        contraseñaVisible = !contraseñaVisible;

        // Para que el cursor se quede al final detodo:
        binding.etPassword.setSelection(binding.etPassword.getText().length());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // cerramos la conexion con la base de datos
        if (db.isOpen() && db != null) {
            db.close();
        }
    }
}