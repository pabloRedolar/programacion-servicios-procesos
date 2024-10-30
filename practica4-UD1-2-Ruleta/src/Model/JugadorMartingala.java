package Model;

import java.util.random.RandomGenerator;

public class JugadorMartingala extends Jugador {

    public JugadorMartingala(String id) {
        super(id);
    }

    @Override
    public boolean apostar(int numeroGanador) {
        System.out.println("\n---------- Jugadores martingala ----------\n");

        if (saldo < getApuestaInicial()) {
            System.out.println("El jugador " + id + " tiene un saldo insuficiente.");
            return false;
        }

        setSaldo(saldo - getApuestaInicial());
        setNumeroApostado(RandomGenerator.getDefault().nextInt(1, 37));
        System.out.println("Jugador " + id + " apostó " + getApuestaInicial() + "€ al número: " + getNumeroApostado());

        if (numeroGanador == getNumeroApostado()) {
            setGanancia(getApuestaInicial() * 36);

            System.out.println("El jugador " + id + " ganó " + getGanancia() + "€. Nuevo saldo: " + getSaldo() + "€");
            setApuestaInicial(10);
            return true;
        } else {
            System.out.println("El jugador " + id + " perdió " + getApuestaInicial() + "€.");
            setApuestaInicial(apuestaInicial *= 2);
            return false;
        }
    }
}
