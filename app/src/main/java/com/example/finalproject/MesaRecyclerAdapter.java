package com.example.finalproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MesaRecyclerAdapter extends RecyclerView.Adapter<MesaViewHolder> {

    public static ClickListener clickListener;
    public static OnItemLongClickListener onItemLongClickListener;
    private DatabaseReference BDReferencia;
    private List<Mesa> list = new ArrayList<>();

    public MesaRecyclerAdapter(List<Mesa> list) { this.list = list; }

    public MesaRecyclerAdapter() { this.list = new ArrayList<>(); }

    @NonNull
    @Override
    public MesaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_mesa, parent, false);

        return new MesaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MesaViewHolder holder, final int position) {
        final Mesa mesa = list.get(position);
        holder.getTvMesa().setText("Mesa " + String.valueOf(position + 1));
        if (mesa.getEstado().equals("Disponible")){
            holder.getImgMes().setImageResource(R.drawable.disponible);
        } else if (mesa.getEstado().equals("Reservada")){
            holder.getImgMes().setImageResource(R.drawable.reservada);
        }else {
            holder.getImgMes().setImageResource(R.drawable.ocupada);
        }
        holder.getImgMes().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View v) {
                CharSequence[] items = {"Disponible", "Reservada", "Ocupada"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(holder.itemView.getContext());
                dialog.setTitle("Cambiar Estado de Mesa");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String mesaEstado;
                        String mesaID = mesa.getMesa_id();
                        String mesaLugar = mesa.getLugar();
                        if (which == 0){
                            mesaEstado = "Disponible";
                        }else if (which == 1){
                            mesaEstado = "Reservada";
                        }else{
                            mesaEstado = "Ocupada";
                        }
                        updateMesa(mesaID, mesaEstado, mesaLugar);
                    }
                });
                dialog.show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setAddListMesas(List<Mesa> list){
        list.addAll(list);
        notifyDataSetChanged();
    }

    public interface ClickListener{
        void onItemLongClick(int position, View view);
    }

    public void setOnItemClickListener(ClickListener clickListener){
        MesaRecyclerAdapter.clickListener = clickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener){
        MesaRecyclerAdapter.onItemLongClickListener = onItemLongClickListener;
    }
    public interface OnItemLongClickListener {
        public boolean onItemLongClicked(int position);
    }
    public void updateMesa(String id, String estado, String lugar){
        BDReferencia = FirebaseDatabase.getInstance().getReference("Mesas").child(id);
        Mesa mesa = new Mesa(id, estado, lugar);
        BDReferencia.setValue(mesa);
    }

}
