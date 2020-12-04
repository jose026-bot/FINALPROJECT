package com.example.finalproject;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ListaIngredientes extends ArrayAdapter<Ingrediente> {
    private Activity context;
    List<Ingrediente> ingredientes;

    public ListaIngredientes(Context context, List<Ingrediente> lista){
        super(context, R.layout.ingredientes_reportes, lista);
        ingredientes = lista;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View listViewItem = inflater.inflate(R.layout.ingredientes_reportes, null, true);


        TextView tvIngrediente = (TextView) listViewItem.findViewById(R.id.tvIngrediente);
        TextView tvCantidad = (TextView) listViewItem.findViewById(R.id.tvCantidad);

        Ingrediente ingrediente = ingredientes.get(position);
        tvIngrediente.setText(ingrediente.getIngrediente_nombre());
        tvCantidad.setText(String.valueOf(ingrediente.getIngrediente_cantidad()));

        return listViewItem;
    }
}