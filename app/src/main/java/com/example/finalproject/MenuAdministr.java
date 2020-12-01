package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MenuAdministr extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_administr);

        setTitle("MENU DEL ADMINISTRADOR");


    }

    public void pasarEstadistica(View view) {
        Intent intent= new Intent(this, ReportesAdminActivity.class);
        startActivity(intent);
    }

    public void pasarEmpleado(View view) {
        Intent intent= new Intent(this, EmpleadoActivity.class);
        startActivity(intent);
    }

    public void pasarIngredientes(View view) {
        Intent intent= new Intent(this, IngredientesActivity.class);
        startActivity(intent);
    }


    public void pasarAgregarReceta(View view) {
        Intent intent= new Intent(this, AgregarRecetaActivity.class);
        startActivity(intent);
    }

    public void pasarAsignacionMesaAdmi(View view) {
        Intent intent= new Intent(this, AsignacionMesasAdmiActivity.class);
        startActivity(intent);
    }

    public void pasarPlatillo(View view) {
        Intent intent= new Intent(this, PlatilloActivity.class);
        startActivity(intent);
    }

    public void pasarStock(View view) {
        Intent intent= new Intent(this, StockActivity.class);
        startActivity(intent);
    }

    public void pasarCerrarSesionAdmi(View view) {
        Intent intent= new Intent(this, CerrarSesionActivity.class);
        startActivity(intent);
    }
}