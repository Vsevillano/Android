package edu.dwes.conecta4_v1;

/**
 * Created by Victor on 15/11/2017.
 */

public class Coordenada {



    private int fila;
    private int columna;


    public Coordenada(int fila, int columna) {
        setFila(fila);
        setColumna(columna);
    }

    /**
     * Metodos de obtencion y asignacion
     */
    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getFila() {
        return this.fila;
    }

    /**
     * Metodos de obtencion y asignacion
     */
    public int getColumna() {
        return this.columna;
    }

    public void setColumna(int columna){
        this.columna = columna;
    }
}
