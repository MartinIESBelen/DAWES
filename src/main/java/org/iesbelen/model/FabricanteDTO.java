package org.iesbelen.model;

public class FabricanteDTO extends Fabricante {

    private final int numProductos;

    public FabricanteDTO(int idFabricante, String nombre, int numProductos) {
        this.setIdFabricante(idFabricante);
        this.setNombre(nombre);
        this.numProductos = numProductos;
    }

    public int getNumProductos() {
        return numProductos;
    }

}
