package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ReporteMesas extends AppCompatActivity {

    private DatabaseReference BDReferencia;
    private Button btnAgregar;
    private Spinner spLugar;
    private RecyclerView recyclerView;
    private MesaRecyclerAdapter mesaRecyclerAdapter;
    private boolean puedeCargar = false;
    private List<Mesa> lista;
    private MesaViewHolder mesaViewHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_mesas);

        BDReferencia = FirebaseDatabase.getInstance().getReference("Mesas");
        btnAgregar = findViewById(R.id.btnAgregarMesa);
        spLugar = (Spinner) findViewById(R.id.spLugar);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.lugar, android.R.layout.simple_spinner_item);
        spLugar.setAdapter(adapter);
        recyclerView = (RecyclerView)findViewById(R.id.rvMesas);
        mesaRecyclerAdapter = new MesaRecyclerAdapter();
        lista = new ArrayList<>();

        recyclerView.setAdapter(mesaRecyclerAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(ReporteMesas.this, 3));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0){
                    int itemsVisibles = recyclerView.getLayoutManager().getChildCount();
                    int itemsTotales = recyclerView.getLayoutManager().getItemCount();
                    int primerItemVisible = ((GridLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                    if (puedeCargar){
                        if (itemsVisibles + primerItemVisible >= itemsTotales){
                            Log.i("TAG", "CARGA: " + itemsVisibles + " - " + primerItemVisible + " - " + itemsTotales);
                            puedeCargar = false;
                        }
                    }
                }
            }
        });


        spLugar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder dialog = new AlertDialog.Builder(ReporteMesas.this);
                dialog.setTitle("Agregar Nueva Mesa");
                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        agregarMesa();
                    }
                });

                dialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }

    private void agregarMesa(){
        String lugar = spLugar.getSelectedItem().toString();
        if (!lugar.equals("Seleccione una opción...")){
            String id = BDReferencia.push().getKey();
            Mesa mesa = new Mesa(id, "Disponible", lugar);
            BDReferencia.child(id).setValue(mesa);
            Toast.makeText(getApplicationContext(), "Mesa Agregada", Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(getApplicationContext(), "Error al Agregar Mesa", Toast.LENGTH_SHORT).show();
        }

    }

    protected void onStart(){
        super.onStart();
        BDReferencia.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                lista.clear();

                for (DataSnapshot postSnapshot : snapshot.getChildren()){
                    Mesa mesa = postSnapshot.getValue(Mesa.class);
                    lista.add(mesa);
                }

                MesaRecyclerAdapter mesa = new MesaRecyclerAdapter(lista);
                recyclerView.setAdapter(mesa);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}