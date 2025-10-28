package org.iesbelen.dao;

public class FabricanteDTO {
    private int idFabricante;
    private String nombre;
    private int numProductos;

    public FabricanteDTO(int idFabricante, String nombre, int numProductos) {
        this.idFabricante = idFabricante;
        this.nombre = nombre;
        this.numProductos = numProductos;
    }
    public int getIdFabricante() {
        return idFabricante;
    }

    public String getNombre() {
        return nombre;
    }

    public  int getNumProductos() {
        return numProductos;
    }

}
