package edu.dwes.conecta4_v1;

/**
 * Created by Victor on 15/11/2017.
 */

public class Game {
    static final int NFILAS = 6;
    static final int NCOLUMNAS = 7;
    static final int VACIO = 0;
    static final int MAQUINA = 1;
    static final int JUGADOR = 2;
    static final String MAQGANADOR = "1111";
    static final String JUGGANADOR = "2222";


    private int tablero[][];
    private char estado;
    private int turno = JUGADOR;


    public Game(int jugador) {
        tablero = new int[NFILAS][NCOLUMNAS];
        turno = jugador;
        for (int i = 0; i < NFILAS; i++) {
            for (int j = 0; j < NCOLUMNAS; j++) {
                tablero[i][j] = VACIO;
            }
        }
        this.estado = 'J';
    }

    /**
     * Metodos de obtencion y asignacion de turno
     */
    public void setTurno(int jugador) {this.turno = jugador;}
    public int getTurno() { return this.turno;}

    /**
     * Metodos de obtencion y asignacion de estado
     */
    public char getEstado() { return this.estado;}
    public void setEstado(char estado){this.estado = estado;}

    /**
     * Metodo que permite saber si esta vacia la casilla o no
     */
    public boolean colCompleta(int columna) {
        boolean lcompleta = false;

        if (tablero[0][columna] != VACIO)
            lcompleta = true;
        return lcompleta;
    }

    /**
     * Metodo que me decuelve la fila
     * @param columna en la que se hace click
     * @return
     */
    public int filSelect(int columna) {
        int i = NFILAS-1;
        int fil = -1;
        boolean lsel = false;

        while (i >= 0 && !lsel) {
            if(tablero[i][columna] == VACIO) {
                fil = i;
                lsel = true;
            }
            i--;
        }
        return fil;
    }

    /**
     * Metodo para cambiar el turno del jugador
     */
    public void cambiaTurno() {
        this.setTurno(this.getTurno() == MAQUINA ? JUGADOR: MAQUINA);
        return;
    }

    /**
     * Metodo para colocar la ficha
     */
    public boolean colocarFicha(Coordenada coordenada) {
        boolean lcolocado = false;
        int fila = coordenada.getFila();
        int columna = coordenada.getColumna();
        if(tablero[fila][columna] == VACIO) {
            tablero[fila][columna] = this.getTurno();
            lcolocado = true;
        }
        return lcolocado;
    }


    /**
     * Caculo de las filas
     */
    public String fila (Coordenada coordenada) {
        String cadena = "";
        int f = coordenada.getFila();
        int c;
        for (c = 0; c < NCOLUMNAS; c++){
            cadena += Integer.toString(tablero[f][c]);
        }
        return cadena;
    }

    /**
     * Caculo de las columnas
     */
    public String columna (Coordenada coordenada) {
        String cadena = "";
        int c = coordenada.getColumna();
        int f;
        for (f = 0; f < NFILAS; f++){
            cadena += Integer.toString(tablero[f][c]);
        }
        return cadena;
    }


    /**
     * Calculo de las diagonales1
     */
    public String diagonal1(Coordenada coordenada) {
        String cadena = "";
        int c = coordenada.getColumna();
        int f = coordenada.getFila();
        int i = f - Math.min(f,c);
        int j = c - Math.min(f,c);
        while (i < NFILAS && j < NCOLUMNAS) {
            cadena+= Integer.toString(tablero[i][j]);
            i++;
            j++;
        }
        return cadena;
    }

    /**
     * Calculo de las diagonales2
     */
    public String diagonal2(Coordenada coordenada) {
        String cadena = "";
        int c = 0;
        int f = 0;
        for (f = 0; f < NFILAS; f++) {
            for (c = 0; c < NCOLUMNAS; c++) {
                if (f + c == coordenada.getFila() + coordenada.getColumna()) {
                    cadena += Integer.toString(tablero[f][c]);
                }
            }
        }
        return  cadena;
    }

    /**
     * Jugada ganadora
     */
    public int jugadaGanadora(Coordenada coordenada) {
        int resultado = -1;

        if(this.estado == 'G') return -1;
        String patron = this.getTurno() == MAQUINA ? MAQGANADOR : JUGGANADOR;

        if (fila(coordenada).contains(patron)) {
            this.estado = 'G';
            resultado = 0;
        }

        if (columna(coordenada).contains(patron)) {
            this.estado = 'G';
            resultado = 1;
        }

        if (diagonal1(coordenada).contains(patron)) {
            this.estado = 'G';
            resultado = 2;
        }

        if (diagonal2(coordenada).contains(patron)) {
            this.estado = 'G';
            resultado = 3;
        }
        return resultado;
    }

    /**
     * Comprueba si el tablero esta completo
     */
    public boolean tableroLleno() {
        for (int i=0; i<NFILAS;  i++){
            for (int j=0; j<NCOLUMNAS;  j++){
                if (tablero[i][j] == VACIO){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Pasa el tablero a cadena
     * @return
     */
    public String tableroToString(){
        String cadenaTablero = "";
        for (int i = 0; i < NFILAS; i++) {
            for (int j = 0; j < NCOLUMNAS; j++) {
                cadenaTablero += tablero[i][j];
            }
        }
        return cadenaTablero;
    }

    public void stringToTablero(String cadenaTablero){
        int contador = 0;
        for (int i = 0; i < NFILAS; i++) {
            for (int j = 0; j < NCOLUMNAS; j++) {
                //Se resta el '0' para devolverlo en número, ya que caracter - caracter da un numero
                tablero[i][j] = cadenaTablero.charAt(contador++) -'0';
            }
        }
    }

    public int devolverCasilla(int i, int j){
        return tablero[i][j];
    }

}