package com.example.finalproject.UI.INTERFAZ_COCINERO;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.finalproject.MODEL.Pedido;
import com.example.finalproject.R;
import com.example.finalproject.UTILIDADES.FirebaseReference;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class recyclerActivity extends AppCompatActivity {
    RecyclerView rvLista;
    List<Pedido> listapedidos;
    private ValueEventListener eventListener;
    private DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        rvLista = (RecyclerView) findViewById(R.id.rvLista);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        rvLista.setLayoutManager(lm);
        rvLista.setHasFixedSize(true);


        listapedidos = new ArrayList<>();
        /// aqui referencia


        final RVAdapter adapter = new RVAdapter(listapedidos);
        rvLista.setAdapter(adapter);


        //para mostrar mi alertDialog
      /*  AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Titulo");
        builder.setMessage("HOY NO TIENES NIGUN PEDIDO");
        builder.setPositiveButton("Aceptar", null);
        AlertDialog dialog = builder.create();
*/

        db = (DatabaseReference) FirebaseDatabase.getInstance().getReference().child(FirebaseReference.PEDIDOS_REFERENCE);


        eventListener = new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot datasnapshot) {
                Pedido ped = new Pedido();

                listapedidos.removeAll(listapedidos);
                for (DataSnapshot snapshot1 :
                        datasnapshot.getChildren()) {
                    ped = snapshot1.getValue(Pedido.class);
                    if (ped.getDescripcion().equalsIgnoreCase("Pendiente")) {


                        listapedidos.add(ped);


                    } else {

                    }


                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("ERROR", error.getMessage());
            }
        };

        db.addValueEventListener(eventListener);


    }


}