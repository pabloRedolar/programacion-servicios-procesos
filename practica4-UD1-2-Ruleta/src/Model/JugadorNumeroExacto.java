package Model;

import java.util.random.RandomGenerator;

public class JugadorNumeroExacto extends Jugador {

    public JugadorNumeroExacto(String id) {
        super(id);
    }

    @Override
    public boolean apostar(int numeroGanador) {
        System.out.println("\n---------- Jugadores numero exacto ----------\n");
        if (saldo < apuestaInicial) {
            System.out.println("El jugador " + id + " tiene un saldo insuficiente.");
            return false;
        }

        setSaldo(saldo - apuestaInicial);
        setNumeroApostado(RandomGenerator.getDefault().nextInt(1, 37));

        System.out.println("Jugador " + id + " apostó al número: " + getNumeroApostado());

        if (numeroGanador == getNumeroApostado()) {
            setGanancia(getApuestaInicial() * 36);
            System.out.println("El jugador " + id + " ganó " + ganancia + "€! Nuevo saldo: " + getSaldo() + "€");
            return true;
        } else {
            System.out.println("El jugador " + id + " perdió.");
            return false;
        }
    }
}
