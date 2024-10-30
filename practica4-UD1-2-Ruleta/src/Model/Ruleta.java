package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.random.RandomGenerator;

public class Ruleta implements Runnable {
    private final int INTERVALO_NUMEROS = 3000;
    private int banca = 50000;
    private List<Jugador> jugadores = new ArrayList<>();

    public int getBanca() {
        return banca;
    }

    public void setBanca(int banca) {
        this.banca = banca;
    }

    public void agregarJugador(Jugador jugador) {
        synchronized (jugadores) {
            jugadores.add(jugador);
        }
    }

    private void girarRuleta() {
        System.out.println("\n---------- La rule ha sacao numero ----------\n");

        int numeroGanador = RandomGenerator.getDefault().nextInt(0, 37);
        System.out.println("Número ganador: " + numeroGanador);
        notificarJugadores(numeroGanador);
    }

    private void notificarJugadores(int numeroGanador) {
        synchronized (jugadores) {
            for (Jugador jugador : jugadores) {
                setBanca(getBanca() + jugador.getApuestaInicial());
                if (jugador.apostar(numeroGanador)){
                    if (getBanca() < jugador.getGanancia()){
                        System.out.println("La banca no puede pagarle a " + jugador.getId());
                    } else {
                        jugador.setSaldo(jugador.saldo + jugador.getGanancia());
                        setBanca(getBanca() - jugador.getGanancia());
                    }
                }
            }
        }
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                System.out.println("La banca está en: " + getBanca());
                System.out.println("\n ********** La rule está girando **********");
                Thread.sleep(INTERVALO_NUMEROS);
                girarRuleta();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("La ruleta se ha detenido.");
            }
        }
    }
}