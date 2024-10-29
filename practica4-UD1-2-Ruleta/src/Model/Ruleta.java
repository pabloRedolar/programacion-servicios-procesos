package Model;


import java.util.ArrayList;
import java.util.List;
import java.util.random.RandomGenerator;

public class Ruleta implements Runnable {
    private final int INTERVALO_NUMEROS = 3000;
    private int numeroGanador;
    private int banca = 50000;
    private List<Jugador> jugadores = new ArrayList<>();

    public Ruleta(int numeroGanador, int banca, List<Jugador> jugadores) {
        this.numeroGanador = numeroGanador;
        this.banca = banca;
        this.jugadores = jugadores;
    }

    public Ruleta() {

    }

    public int getINTERVALO_NUMEROS() {
        return INTERVALO_NUMEROS;
    }

    public int getNumeroGanador() {
        return numeroGanador;
    }

    public void setNumeroGanador(int numeroGanador) {
        this.numeroGanador = numeroGanador;
    }

    public int getBanca() {
        return banca;
    }

    public void setBanca(int banca) {
        this.banca = banca;
    }

    public List<Jugador> getJugadores() {
        return jugadores;
    }

    public void setJugadores(List<Jugador> jugadores) {
        this.jugadores = jugadores;
    }

    public void girarRuleta() {
        setNumeroGanador(RandomGenerator.getDefault().nextInt(0, 37));
        System.out.println("Número ganador: " + numeroGanador);
    }

    public void agregarJugador(Jugador jugador) {
        synchronized (jugadores) {
            jugadores.add(jugador);
        }
    }

    public void notificarJugadores() {
        synchronized (jugadores) {
            for (Jugador jugador : jugadores) {
                boolean gano = jugador.apostar(numeroGanador);

                // Si el jugador gana, se ajusta el saldo y la banca
                if (gano) {
                    int ganancia = jugador instanceof Model.JugadorNumeroExacto ? 360 :
                            jugador instanceof Model.JugadorParImpar ? 20 : 360;

                    synchronized (this) {
                        if (banca >= ganancia) {
                            jugador.setSaldo(jugador.getSaldo() + ganancia);
                            banca -= ganancia;
                            System.out.println("La banca ahora tiene: " + banca + "€");
                        } else {
                            jugador.setSaldo(jugador.getSaldo() + banca);
                            banca = 0;
                            System.out.println("La banca se ha quedado sin saldo.");
                        }
                    }
                } else {
                    synchronized (this) {
                        banca += jugador.getApuestaInicial();
                    }
                }
            }
        }
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            girarRuleta();
            notificarJugadores();

            try {
                Thread.sleep(INTERVALO_NUMEROS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("La ruleta se ha detenido.");
            }
        }
    }
}
