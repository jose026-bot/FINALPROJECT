package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AgregarIngrediente extends AppCompatActivity {

    private DatabaseReference BDReferencia;
    private Button btnGrabar;
    private EditText txtIngrediente, txtCantidad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BDReferencia = FirebaseDatabase.getInstance().getReference("Ingredientes");

        txtIngrediente = findViewById(R.id.txtActualizaIngrediente);
        txtCantidad = findViewById(R.id.txtActualizaCantidad);
        btnGrabar = findViewById(R.id.btnActualizar);

        btnGrabar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarIngrediente();
                Intent intent = new Intent(getApplicationContext(), ReporteIngredientes.class);
                startActivity(intent);
            }
        });
    }

    private void agregarIngrediente(){
        String nombIngrediente = txtIngrediente.getText().toString().trim();
        String stringCantidad = txtCantidad.getText().toString().trim();

        if (!TextUtils.isEmpty(nombIngrediente) && !TextUtils.isEmpty(stringCantidad)){
            String id = BDReferencia.push().getKey();
            int cantidad = Integer.parseInt(txtCantidad.getText().toString());
            Ingrediente ingrediente = new Ingrediente(id, nombIngrediente, cantidad);
            BDReferencia.child(id).setValue(ingrediente);
            txtIngrediente.setText("");
            txtCantidad.setText("");
            Toast.makeText(this, "Ingrediente Agregado", Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(this, "Error al agregar ingrediente", Toast.LENGTH_SHORT).show();
        }

    }

}