package Model;

import java.util.random.RandomGenerator;

public class JugadorParImpar extends Jugador {

    public JugadorParImpar(String id) {
        super(id);
    }

    @Override
    public boolean apostar(int numeroGanador) {
        System.out.println("\n---------- Jugadores par/impar ----------\n");

        if (saldo < apuestaInicial) {
            System.out.println("El jugador " + id + " tiene un saldo insuficiente.");
            return false;
        }

        setSaldo(saldo - apuestaInicial);
        setNumeroApostado(RandomGenerator.getDefault().nextBoolean() ? 0 : 1);
        String tipoApuesta = getNumeroApostado() == 0 ? "par" : "impar";

        System.out.println("Jugador " + id + " apostó a " + tipoApuesta);

        if (numeroGanador != 0 && (numeroGanador % 2) == getNumeroApostado()) {
            setGanancia(apuestaInicial * 2);
            return true;
        } else {
            System.out.println("El jugador " + id + " perdió " + getApuestaInicial() + "€");
            System.out.println("El jugador " + getId() + " ahora tiene " + getSaldo() + "€");
            return false;
        }
    }
}
