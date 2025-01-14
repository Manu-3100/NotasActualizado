package com.pmdm.notas.NotasAdapter.Dialogs;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.button.MaterialButton;
import com.pmdm.notas.R;

public class AddNotaDF extends DialogFragment {

    public interface AddNotaDialogListener{
        void onNotaAdded(EditText titulo, EditText data, EditText modulo);
    }

    private AddNotaDialogListener listener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setStyle(STYLE_NORMAL, com.google.android.material.R.style.ThemeOverlay_MaterialComponents_Dialog);
    }

    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View vista;
        vista = inflater.inflate(R.layout.fragment_add_nota_dialog, container, false);

        EditText tituloNota = (EditText) vista.findViewById(R.id.dfTitulo);
        EditText dataNota = (EditText) vista.findViewById(R.id.dfData);
        EditText moduloNota = (EditText) vista.findViewById(R.id.dfModulo);
        MaterialButton btAddNota = vista.findViewById(R.id.btnAddNota);
        MaterialButton btCancelar = vista.findViewById(R.id.btnCancelAdd);

        btAddNota.setOnClickListener(v -> {
            if(tituloNota.getText() != null){
                listener.onNotaAdded(tituloNota, dataNota, moduloNota);
                dismiss();
            } else {
                Toast.makeText(getContext(), "COMPLETAR CAMPOS", Toast.LENGTH_SHORT).show();
            }
        });

        btCancelar.setOnClickListener(v -> dismiss());

        return vista;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AddNotaDialogListener){
            listener = (AddNotaDialogListener) context;
        } else {
            throw new RuntimeException(context.toString() + "Debese implementar AddNotaDialogListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}
