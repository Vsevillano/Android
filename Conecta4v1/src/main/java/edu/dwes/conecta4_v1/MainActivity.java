package edu.dwes.conecta4_v1;

import android.content.res.Configuration;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private final int ids[][] = {
            {R.id.a1, R.id.a2,R.id.a3,R.id.a4,R.id.a5,R.id.a6,R.id.a7},
            {R.id.b1, R.id.b2,R.id.b3,R.id.b4,R.id.b5,R.id.b6,R.id.b7},
            {R.id.c1, R.id.c2,R.id.c3,R.id.c4,R.id.c5,R.id.c6,R.id.c7},
            {R.id.d1, R.id.d2,R.id.d3,R.id.d4,R.id.d5,R.id.d6,R.id.d7},
            {R.id.e1, R.id.e2,R.id.e3,R.id.e4,R.id.e5,R.id.e6,R.id.e7},
            {R.id.f1, R.id.f2,R.id.f3,R.id.f4,R.id.f5,R.id.f6,R.id.f7}};

    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        game = new Game(2);
        dibujarTablero();
    }



    public void dibujarTablero() {
        for(int i = 0; i< Game.NFILAS;  i++){
            for (int j = 0; j < Game.NCOLUMNAS;  j++){
                findViewById(ids[i][j]).setOnClickListener(this);
                ImageButton imageButton = (ImageButton) findViewById(ids[i][j]);
                if(game.devolverCasilla(i,j) == game.JUGADOR) {
                    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
                        imageButton.setImageResource(R.drawable.c4_button_jugador_land);
                    else
                        imageButton.setImageResource(R.drawable.c4_button_jugador);
                } else if (game.devolverCasilla(i,j) == game.MAQUINA) {
                    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
                        imageButton.setImageResource(R.drawable.c4_button_maquina_land);
                    else
                        imageButton.setImageResource(R.drawable.c4_button_maquina);
                }
                else {
                    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
                        imageButton.setImageResource(R.drawable.c4_button_land);
                    else
                        imageButton.setImageResource(R.drawable.c4_button);
                }
            }
        }
    }



    /**
     * Metodo onclick que comprueba el estado
     */
    @Override
    public void onClick (View v) {
        pulsado(v);
    }

    /**
     * Si pulsamos una tecla
     * @param v
     */
    public void pulsado(View v){
        int fila, columna, id = v.getId();
        Coordenada coordenada = new Coordenada(0,0);
        ImageButton imageButton = (ImageButton) findViewById(id);
        coordenada = coorJuego(id);

        switch (game.getEstado()) {
            case 'G':
                Toast.makeText(this, "Partida terminada", Toast.LENGTH_LONG).show();
                break;
            case 'T':
                Toast.makeText(this, "Partida empatada", Toast.LENGTH_LONG).show();
                break;
            default:
                procesaJugada(coordenada);
        }

    }

    /**
     * Devuelve la corrdenada del juego
     */
    private Coordenada coorJuego(int id) {
        int fila = 0;
        int columna = 0;

        for (int i = 0; i < Game.NFILAS; i++)
            for (int j = 0; j < Game.NCOLUMNAS; j++)
                if(ids[i][j] == id) {columna = j;}

        fila = game.filSelect(columna);
        Coordenada coordenada = new Coordenada(fila, columna);
        return coordenada;
    }

    /**
     * Procesa una jugada de un turno
     */
    private void procesaJugada(Coordenada coordenada) {
        if(game.colCompleta(coordenada.getColumna())) {
            Toast.makeText(this, "Columna llena", Toast.LENGTH_LONG).show();
        }
        else {
            game.colocarFicha(coordenada);
            dibujaFicha(coordenada, game.getTurno());

            if(game.jugadaGanadora(coordenada) > -1) {
                game.setEstado('G');
                Toast.makeText(this, "Jugada ganadora", Toast.LENGTH_LONG).show();
            }
            else {
                game.cambiaTurno();

            }

            if (game.tableroLleno()) {
                game.setEstado('T');
                Toast.makeText(this, "Tablero completo", Toast.LENGTH_LONG).show();
            }
        }

    }

    /**
     * Dibuja una ficha en una coordenada en funcion del jugador
     */
    private void dibujaFicha(Coordenada coordenada, int jugador) {
        int fila = coordenada.getFila();
        int columna = coordenada.getColumna();
        int id = ids[fila][columna];
        ImageButton imageButton = (ImageButton) findViewById(id);
        if(jugador == game.JUGADOR) {
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
                imageButton.setImageResource(R.drawable.c4_button_jugador_land);
            else
                imageButton.setImageResource(R.drawable.c4_button_jugador);
        } else if (jugador == game.MAQUINA) {
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
                imageButton.setImageResource(R.drawable.c4_button_maquina_land);
            else
                imageButton.setImageResource(R.drawable.c4_button_maquina);
        }
        else {
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
                imageButton.setImageResource(R.drawable.c4_button_land);
            else
                imageButton.setImageResource(R.drawable.c4_button);
        }
    }


    /**
     * Cuando salimos de laaplicacion
     */
    protected void onPause(){
        super.onPause();
        Music.stop(this);
    }

    /**
     * Cuando volvemos a la aplicacion
     * @param savedInstanceState
     */
    public void onRestoreInstanceState (Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        game.stringToTablero(savedInstanceState.getString("TABLERO"));
        game.setEstado(savedInstanceState.getChar("ESTADO"));
        game.setTurno(savedInstanceState.getInt("TURNO"));
        dibujarTablero();
    }

    /**
     * Salva el estado de la partida
     * @param outState
     */
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putString("TABLERO", game.tableroToString());
        outState.putInt("TURNO", game.getTurno());
        outState.putChar("ESTADO", game.getEstado());
    }

    /**
     * Al volver a la aplicacion
     */
    protected void onResume(){
        super.onResume();
        Music.play(this, R.raw.funkandblues);
        dibujarTablero();
    }

}
