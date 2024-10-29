package Model;

import java.util.random.RandomGenerator;

public class JugadorMartingala extends Jugador {
    private int apuestaActual;

    @Override
    public boolean apostar(int numeroGanador) {
        if (saldo < apuestaInicial) {
            System.out.println("El jugador " + getId() + " está sin saldo para apostar.");
            return false;
        }

        setSaldo(getSaldo() - apuestaActual);
        System.out.println("El jugador: " + getId() + " ha apostado " + apuestaActual + "€ al número: " + getNumeroApostado());

        if (numeroGanador == 0) {
            System.out.println("Ha salido el 0, el jugador: " + getId() + " ha perdido " + apuestaActual + "€");
            System.out.println("El jugador " + getId() + " ahora tiene " + getSaldo() + "€");
            apuestaActual = apuestaInicial; // Reiniciar apuesta
            return false;
        }

        if (numeroGanador == getNumeroApostado()) {
            setSaldo(getSaldo() + 360);
            System.out.println("El jugador: " + getId() + " ha ganado " + 360 + " €");
            System.out.println("El jugador: " + getId() + " tiene un saldo de: " + getSaldo());
            apuestaActual = apuestaInicial; // Reiniciar apuesta
            return true;
        } else {
            System.out.println("El jugador: " + getId() + " ha perdido " + apuestaActual + "€");
            System.out.println("El jugador: " + getId() + " tiene un saldo de: " + getSaldo());
            apuestaActual *= 2; // Duplicar apuesta para la próxima ronda
            return false;
        }
    }
}
