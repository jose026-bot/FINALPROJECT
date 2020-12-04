package com.example.finalproject;

public class Mesa {
    private String mesa_id;
    private String estado;
    private String lugar;

    public String getMesa_id() {
        return mesa_id;
    }

    public String getEstado() {
        return estado;
    }

    public String getLugar() {
        return lugar;
    }

    public Mesa(String mesa_id, String estado, String lugar) {
        this.mesa_id = mesa_id;
        this.estado = estado;
        this.lugar = lugar;
    }

    public Mesa() {
    }
}
