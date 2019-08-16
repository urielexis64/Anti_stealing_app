package com.example.juegogato;

import java.util.Random;

public class Partida {

    public final int dificultad;
    public int jugador;
    private int[] casillas;
    private final int[][] combinaciones = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7},
            {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};


    public Partida(int dificultad) {
        this.dificultad = dificultad;
        jugador = 1;

        casillas = new int[9];
        for (int i = 0; i < casillas.length; i++)
            casillas[i] = 0;
    }

    public int dosEnRaya(int jugadorEnTurno) {

        int casilla = -1;
        int cuantas = 0;

        for (int i = 0; i < combinaciones.length; i++) {
            for (int pos : combinaciones[i]) {
                if (casillas[pos] == jugadorEnTurno)
                    cuantas++;
                if (casillas[pos] == 0) casilla = pos;
            }
            if (cuantas == 2 && casilla != -1) return casilla;
            casilla = -1;
            cuantas = 0;
        }
        return -1;
    }

    public int ia() {
        int casilla;

        casilla = dosEnRaya(2);

        if (casilla != -1) return casilla;

        if (dificultad > 0) {

            casilla = dosEnRaya(1);

            if (casilla != -1) return casilla;
        }

        if (dificultad == 2) {
            if (casillas[4] == 0) return 4;
            if (casillas[0] == 0) return 0;
            if (casillas[2] == 0) return 2;
            if (casillas[6] == 0) return 6;
            if (casillas[8] == 0) return 8;
        }

        Random r = new Random();
        casilla = r.nextInt(9);
        return casilla;
    }

    public int turno() {

        boolean empate = true;
        boolean ultimoMovimiento = true;

        for (int i = 0; i < combinaciones.length; i++) {
            for (int pos : combinaciones[i]) {

                if (casillas[pos] != jugador)
                    ultimoMovimiento = false;

                if (casillas[pos] == 0)
                    empate = false;

            }

            if (ultimoMovimiento)
                return jugador;
            ultimoMovimiento = true;
        }
        if (empate)
            return 3;

        jugador++;
        if (jugador > 2)
            jugador = 1;


        return 0;
    }

    public boolean compruebaCasilla(int casilla) {

        if (casillas[casilla] != 0)
            return false;
        else
            casillas[casilla] = jugador;

        return true;
    }

}
