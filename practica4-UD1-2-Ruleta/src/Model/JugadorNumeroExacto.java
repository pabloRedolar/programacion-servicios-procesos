package Model;

import java.util.random.RandomGenerator;

public class JugadorNumeroExacto extends Jugador {

    public JugadorNumeroExacto(String id) {
        super(id);
    }

    @Override
    public boolean apostar(int numeroGanador) {
        System.out.println("\n---------- Jugadores numero exacto ----------\n");

        if (saldo < getApuestaInicial()) {
            System.out.println("El jugador " + id + " tiene un saldo insuficiente.");
            return false;
        }

        setSaldo(saldo - apuestaInicial);
        setNumeroApostado(RandomGenerator.getDefault().nextInt(1, 37));

        System.out.println("Jugador " + id + " apostó al número: " + getNumeroApostado());

        if (numeroGanador == getNumeroApostado()) {
            setGanancia(getApuestaInicial() * 36);
            return true;
        } else {
            System.out.println("El jugador " + id + " perdió " + getApuestaInicial() + "€");
            System.out.println("El jugador " + getId() + " ahora tiene " + getSaldo() + "€");

            return false;
        }
    }
}
