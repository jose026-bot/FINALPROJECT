package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class InterfazRegistroEmpleado extends AppCompatActivity {
     EditText etnombreMesero, etapellidoMesero, etdniMes, etdistrito,etnumero,etdireccion;
     Button btnGuardarNewMesero;

     DatabaseReference databaseReference;

     List<Empleado> listEmpleado = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interfaz_registro_empleado);

        setTitle("REGISTRO EMPLEADO");

        etnombreMesero= findViewById(R.id.etnombreMesero);
        etapellidoMesero= findViewById(R.id.etapellidoMesero);
        etdniMes= findViewById(R.id.etdniMes);
        etdistrito= findViewById(R.id.etdistrito);
        etnumero= findViewById(R.id.etnumero);
        etdireccion= findViewById(R.id.etdireccion);
        btnGuardarNewMesero= findViewById(R.id.btnGuardarNewMesero);

        databaseReference= FirebaseDatabase.getInstance().getReference();
        btnGuardarNewMesero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarEmpleado();
            }
        });
    }
    public void agregarEmpleado(){
        listEmpleado.clear();
        Empleado empleado= new Empleado(
                etnombreMesero.getText().toString(),
                etapellidoMesero.getText().toString(),
                etdniMes.getText().toString(),
                etdistrito.getText().toString(),
                etnumero.getText().toString(),
                etdireccion.getText().toString()
        );
        databaseReference.child("empleado").push().setValue(empleado, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

                Toast.makeText(InterfazRegistroEmpleado.this, "empleado", Toast.LENGTH_SHORT).show();
            }
        });
        limpiarCampos();
    }
    public void limpiarCampos(){
        etnombreMesero.setText("");
        etapellidoMesero.setText("");
        etdniMes.setText("");
        etdistrito.setText("");
        etnumero.setText("");
    }
}