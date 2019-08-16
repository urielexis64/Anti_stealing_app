package com.example.juegogato;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private int jugadores;

    private int[] CASILLAS;

    private Partida partida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Iniciamos el arraycasillas que identifica cada casilla

        CASILLAS = new int[9];
        CASILLAS[0] = R.id.a1;
        CASILLAS[1] = R.id.a2;
        CASILLAS[2] = R.id.a3;
        CASILLAS[3] = R.id.b1;
        CASILLAS[4] = R.id.b2;
        CASILLAS[5] = R.id.b3;
        CASILLAS[6] = R.id.c1;
        CASILLAS[7] = R.id.c2;
        CASILLAS[8] = R.id.c3;

    }

    public void comienzaJuego(View view) {
        limpiarTablero();

        jugadores = 1;

        if (view.getId() == R.id.dosJugadores)
            jugadores = 2;

        RadioGroup configDificultad = findViewById(R.id.dificultad);

        int id = configDificultad.getCheckedRadioButtonId();
        int dificultad = 0;

        if (id == R.id.normal)
            dificultad = 1;
        else if (id == R.id.imposible)
            dificultad = 2;

        partida = new Partida(dificultad);

        findViewById(R.id.unJugador).setEnabled(false);
        findViewById(R.id.dificultad).setAlpha(0);
        findViewById(R.id.dosJugadores).setEnabled(false);

    }

    public void toque(View view) {
        if (partida == null)
            return;

        int casilla = 0;
        for (int i = 0; i < CASILLAS.length; i++) {
            if (CASILLAS[i] == view.getId()) {
                casilla = i;
                break;
            }
        }


        if (!partida.compruebaCasilla(casilla))
            return;

        marca(casilla); //Jugador humano

        int resultado = partida.turno();

        if (resultado > 0) {
            termina(resultado);
            return;
        }

        if (jugadores == 1) {
            int casillaMaquina = partida.ia();

            while (!partida.compruebaCasilla(casillaMaquina))
                casillaMaquina = partida.ia();

            marca(casillaMaquina); //Máquina

            resultado = partida.turno();

            if (resultado > 0)
                termina(resultado);
        }
    }

    private void marca(int casilla) {
        ImageView imagen;
        imagen = findViewById(CASILLAS[casilla]);

        if (partida.jugador == 1)
            imagen.setImageResource(R.drawable.circulo);
        else
            imagen.setImageResource(R.drawable.cruz);
    }

    private void limpiarTablero() {
        ImageView imagen;
        for (int casilla : CASILLAS) {
            imagen = findViewById(casilla);
            imagen.setImageResource(R.drawable.casillanew);
        }
    }

    private void termina(int resultado) {
        if (resultado == 1)
            Toast.makeText(this, R.string.circulosGanan, Toast.LENGTH_LONG).show();
        else if (resultado == 2)
            Toast.makeText(this, R.string.crucesGanan, Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, R.string.empate, Toast.LENGTH_LONG).show();

        partida = null;

        findViewById(R.id.unJugador).setEnabled(true);
        findViewById(R.id.dificultad).setAlpha(1);
        findViewById(R.id.dosJugadores).setEnabled(true);

        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast,
                (ViewGroup) findViewById(R.id.toast_layout_root));

        ImageView image = (ImageView) layout.findViewById(R.id.image);
        image.setImageResource(R.drawable.circulo);
        TextView text = (TextView) layout.findViewById(R.id.text);
        text.setText("Hello! This is a custom toast!");

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();


    }
}
