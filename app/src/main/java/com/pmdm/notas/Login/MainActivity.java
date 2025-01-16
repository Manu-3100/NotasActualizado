package com.pmdm.notas.Login;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.pmdm.notas.Login.entities.Usuario;
import com.pmdm.notas.NotasAdapter.Activities.NotasActivity;
import com.pmdm.notas.R;
import com.pmdm.notas.bd.DatabaseManager;
import com.pmdm.notas.bd.NotasBbHelper;
import com.pmdm.notas.bd.NotasReaderContract;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    // lista de la clase Usuario, para guardar los usuarios.
    private List<Usuario> usuariosList;

    // creamos las variables de nombre de usuario.
    private EditText user;
    private EditText pass;

    // para mostrar o no la contraseña
    private boolean contraseñaVisible = false;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.login);

        DatabaseManager dbManager = DatabaseManager.getInstance(getApplicationContext());
        db = dbManager.getDatabase();

        generarListaUsuarios();
        user = findViewById(R.id.etNome);
        pass = findViewById(R.id.etPassword);

        // ver o no ver la contraseña:
        pass.setOnTouchListener((v, event) -> {
            // mirar si estamos clicando en el ojo:
            if (event.getAction() == MotionEvent.ACTION_UP){
                // indice del drawable de la derecha:
                int drawableDerecha = 2;
                if (event.getRawX() >= (pass.getRight() - pass.getCompoundDrawables()[drawableDerecha].getBounds().width())){
                    alternadorContrasena();
                    return true;
                }
            }
            return false;
        });

        Button botonAcceder = findViewById(R.id.btAcceder);
        botonAcceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comprobarUsuario();
            }
        });
        // evento al clicar en registrar:
        Button botonRegistrar = findViewById(R.id.btRexistrarse);
        botonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anadirUsuario();
            }
        });
        // revisar que los datos introducidos estén bien introducidos.
        // revisar si está registrado o no (mirar si está dentro de la lista)
        // si no está registrado sacar un toast y pedir que se registre.
        // si se registra sin querer y ya está registrado.
    }
    private void generarListaUsuarios(){
        usuariosList = new ArrayList<>();
        usuariosList.add(new Usuario("nadir","nadir12345678"));
        usuariosList.add(new Usuario("aroa","abc12345678."));
        usuariosList.add(new Usuario("manolo", "gallegoAB"));
        usuariosList.add(new Usuario("administrador", "admin123."));
    }

    public void comprobarUsuario(){
        // cogemos la información que nos ha introducido el usuario.
        String usuario = user.getText().toString().trim();
        String contrasinal = pass.getText().toString().trim();

        // boleanos para comprobar que usuario y contraseña existen.
        Boolean usuarioExiste = false;
        Boolean contrasinalExiste = false;

        // este toast es por si no completa bien todos los campos.
        if (usuario.isEmpty() || contrasinal.isEmpty()){
            Toast.makeText(this, "COMPLETE TODOS OS CAMPOS", Toast.LENGTH_SHORT).show();
        }
        // recorrer la lista para ver si usuario existe o no y contraseña existen
        for (Usuario persona : usuariosList){
            if (persona.getNome().equals(usuario)){
                usuarioExiste = true;
            }
            if (persona.getContrasinal().equals(contrasinal)){
                contrasinalExiste = true;
            }
            // si el usuario y la contraseña son correctos tiene que iniciar la actividad.
            if (usuarioExiste && contrasinalExiste){
                Intent intent = new Intent(MainActivity.this, NotasActivity.class);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
                Toast.makeText(this, "ACCEDENDO...", Toast.LENGTH_SHORT).show();
                break;
            }
        }
        // compruebo la contraseña si está correcta o no.
        // si el usuario y la contraseña no coinciden decirle que usuario no existe.
        if (usuarioExiste && !contrasinalExiste){
            Toast.makeText(this, "CONTRASINAL INCORRECTO", Toast.LENGTH_SHORT).show();
        }
        else if (!usuarioExiste){
            Toast.makeText(this, "USUARIO NON EXISTE", Toast.LENGTH_SHORT).show();
        }
        // vaciar los campos de text
        user.setText("");
        pass.setText("");
    }
    public void anadirUsuario(){
        // cogemos la información que nos ha introducido el usuario.
        String usuario = user.getText().toString().trim();
        String contrasinal = pass.getText().toString().trim();

        // comprobar que contraseña sea segura:
        if (contrasinal.length() < 8){
            Toast.makeText(this, "CONTRASINAL NON E SEGURO", Toast.LENGTH_SHORT).show();
        }else{
            // recorremos la lista para comprobar que realmente ese usuario no existe:
            for (Usuario persona : usuariosList){
                if (usuario.isEmpty() || contrasinal.isEmpty()){
                    Toast.makeText(this, "COMPLETE TODOS OS CAMPOS", Toast.LENGTH_SHORT).show();
                    break;
                }

                else if (!persona.getNome().equals(usuario)){
                    // añadir usuario nuevo a la lista
                    usuariosList.add(new Usuario(usuario,contrasinal));
                    ContentValues values = new ContentValues();
                    values.put("nombre", usuario);
                    values.put("contraseña", contrasinal);

//                    try (NotasBbHelper dbHelper = new NotasBbHelper(MainActivity.this)) {
//                        SQLiteDatabase db = dbHelper.getWritableDatabase();
//                        long newRowID = db.insert(NotasReaderContract.UsuariosEntry.TABLE_NAME, null, values);
//                        //System.out.println(newRowID);
//                    } catch (SQLiteException e) {
//                        System.err.println(e.getMessage());
//                        throw new RuntimeException(e);
//                    }
                    long id = DatabaseManager.insertarDatos(db, NotasReaderContract.UsuariosEntry.TABLE_NAME, values);

                    // creamos nuestra actividad
                    Intent intent2 = new Intent(MainActivity.this, NotasActivity.class);

                    // para que aparezca el nombre del usuario que tenga la sesión iniciada.
                    intent2.putExtra("usuario", usuario);

                    // iniciar actividad:
                    startActivity(intent2);

                    Toast.makeText(this, "USUARIO NOVO AÑADIDO", Toast.LENGTH_SHORT).show();
                    break;
                }
                else if (persona.getNome().equals(usuario)){
                    Toast.makeText(this, "USUARIO XA EXISTENTE", Toast.LENGTH_SHORT).show();
                    break;
                }
            }
        }
        // vaciar los campos de texto
        user.setText("");
        pass.setText("");
    }

    // alternar entre visible y inivisible
    private void alternadorContrasena() {
        if (contraseñaVisible) {
            // Si la contraseña está visible, la ocultamos:
            // Oculta la contraseña
            pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
            // Ojo cerrado
            pass.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_visible, 0);
        } else {
            // si está en invisible la hacemos visible
            // enseñamos contraseña
            pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            // Ojo abierto
            pass.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_invisible, 0);
        }
        // Cambia el estado de la visibilidad de la contraseña:
        contraseñaVisible = !contraseñaVisible;

        // Para que el cursor se quede al final detodo:
        pass.setSelection(pass.getText().length());
    }
}