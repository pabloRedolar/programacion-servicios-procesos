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
            setApuestaInicial(10);
            return true;
        } else {
            System.out.println("El jugador " + id + " perdió " + getApuestaInicial() + "€");
            System.out.println("El jugador " + getId() + " ahora tiene " + getSaldo() + "€");
            setApuestaInicial(apuestaInicial *= 2);
            return false;
        }
    }
}
