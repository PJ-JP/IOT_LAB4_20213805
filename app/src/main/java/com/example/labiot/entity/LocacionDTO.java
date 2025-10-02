package com.example.labiot.entity;

import java.util.List;

public class LocacionDTO {

    private List<Locacion> lista;
    private String estado;

    public List<Locacion> getLista() {
        return lista;
    }

    public void setLista(List<Locacion> lista) {
        this.lista = lista;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}
