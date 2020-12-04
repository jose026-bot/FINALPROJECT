package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ReporteIngredientes extends AppCompatActivity {

    public static final String INGREDIENTE_NOMBRE = "net.simplifiedcoding.loginrodrigo.ingrediente_nombre";
    public static final String INGREDIENTE_ID = "net.simplifiedcoding.loginrodrigo.ingrediente_id";
    public static final String INGREDIENTE_CANTIDAD = "net.simplifiedcoding.loginrodrigo.ingrediente_cantidad";
    private DatabaseReference BDReferencia;
    private List<Ingrediente> lista;
    private ListView lvIngredientes;
    private Button btnAgregar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_ingredientes);
        BDReferencia = FirebaseDatabase.getInstance().getReference("Ingredientes");
        lvIngredientes = findViewById(R.id.lvIngredientes);
        btnAgregar = findViewById(R.id.btnAgregar);
        lista = new ArrayList<>();
        lvIngredientes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                CharSequence[] items = {"Actualizar", "Eliminar"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(ReporteIngredientes.this);

                dialog.setTitle("Elegir una opción");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0){
                            showDialogUpdate(ReporteIngredientes.this, position);
                        }else {
                            showDialogDelete(position);
                        }
                    }
                });
                dialog.show();
                return true;
            }
        });
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ReporteIngredientes.class);

                startActivity(intent);
            }
        });
    }

    protected void onStart(){
        super.onStart();
        BDReferencia.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                lista.clear();

                for (DataSnapshot postSnapshot : snapshot.getChildren()){
                    Ingrediente ingrediente = postSnapshot.getValue(Ingrediente.class);
                    lista.add(ingrediente);
                }

                ListaIngredientes ingredientesAdapter = new ListaIngredientes(ReporteIngredientes.this, lista);
                lvIngredientes.setAdapter(ingredientesAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void showDialogUpdate(Activity activity, int position){

        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.actualiza_ingrediente);
        dialog.setTitle("Actualizar");

        final EditText txtActualizaIngrediente = (EditText) dialog.findViewById(R.id.txtActualizaIngrediente);
        final EditText txtActualizarCantidad = (EditText) dialog.findViewById(R.id.txtActualizaCantidad);
        Button btnActualizar = (Button) dialog.findViewById(R.id.btnActualizar);

        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 0.95);
        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels * 0.7);
        dialog.getWindow().setLayout(width, height);
        dialog.show();

        final Ingrediente ingrediente = lista.get(position);
        txtActualizaIngrediente.setText(ingrediente.getIngrediente_nombre());
        txtActualizarCantidad.setText(String.valueOf(ingrediente.getIngrediente_cantidad()));

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nom = txtActualizaIngrediente.getText().toString().trim();
                int cant = Integer.parseInt(txtActualizarCantidad.getText().toString().trim());
                if (!TextUtils.isEmpty(nom)){
                    actualizarIngrediente(ingrediente.getIngrediente_id(), nom, cant);
                    dialog.dismiss();
                }
            }
        });
    }

    private void showDialogDelete(final int id){
        final AlertDialog.Builder dialogDelete = new AlertDialog.Builder(ReporteIngredientes.this);
        final Ingrediente ingrediente = lista.get(id);
        dialogDelete.setTitle("Advertencia");
        dialogDelete.setMessage("¿Está seguro de eliminar el registro?");
        dialogDelete.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                eliminarIngrediente(ingrediente.getIngrediente_id());
            }
        });

        dialogDelete.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialogDelete.show();
    }

    private boolean actualizarIngrediente(String id, String nombre, int cantidad){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Ingredientes").child(id);

        Ingrediente ingrediente = new Ingrediente(id, nombre, cantidad);
        databaseReference.setValue(ingrediente);
        Toast.makeText(getApplicationContext(), "Ingrediente Actualizado", Toast.LENGTH_SHORT).show();
        return true;
    }

    private boolean eliminarIngrediente(String id){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Ingredientes").child(id);
        databaseReference.removeValue();
        Toast.makeText(getApplicationContext(), "Ingrediente Eliminado", Toast.LENGTH_SHORT).show();

        return true;
    }
}