package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MenuEmpleadoActivity extends AppCompatActivity {
    private ImageButton imageButtonMesasAsig;
    private ImageButton imgbtnCerrarS;
    private ImageButton imgbtnRecet;
    private ImageButton imgbtnComanda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_empleado);
        setTitle("MENU EMPLEADO");
        ImageButton imageButtonMesasAsig=findViewById(R.id.btnMesasAsignadas);
        ImageButton imgbtnCerrarS=findViewById(R.id.imgbtnCerrarS);
        ImageButton imgbtnReceta=findViewById(R.id.imgbtnRecet);
        ImageButton imgbtnComanda = findViewById(R.id.btnComanda);
        ImageButton imgbtnMesas=findViewById(R.id.imgbtnMesas);

    }

    public void pasarMesasAsignadas1(View view) {
        Intent intent= new Intent(this, MesasAsignadasMeseroActivity.class);
        startActivity(intent);
    }
    public void pasarCerrarSesion(View view) {
        Intent intent= new Intent(this, CerrarSesionActivity.class);
        startActivity(intent);
    }
    public void pasarRecetario(View view) {
        Intent intent= new Intent(this, RecetarioActivity.class);
        startActivity(intent);
    }

    public void pasarComanda(View view) {
        Intent intent= new Intent(this, ComandaActivity.class);
        startActivity(intent);
    }
    public void pasarMesas(View view) {
        Intent intent= new Intent(this, MesasActivity.class);
        startActivity(intent);
    }





}