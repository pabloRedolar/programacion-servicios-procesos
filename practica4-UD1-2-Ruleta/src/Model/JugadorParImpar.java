package Model;

import java.util.random.RandomGenerator;

public class JugadorParImpar extends Jugador {

    @Override
    public boolean apostar(int numeroGanador) {

        if (saldo < getApuestaInicial()) {
            System.out.println("El jugador " + getId() + " tiene un saldo inferior a 10€");

        } else {
            setSaldo(saldo -= getApuestaInicial());

            if (numeroGanador == 0) {
                System.out.println("Ha salido el 0, el jugador: " + getId() + " ha perdido " + getApuestaInicial());
                System.out.println("El jugador " + getId() + " ahora tiene " + getSaldo() + "€");
                return false;
            }

            int numeroApostado = RandomGenerator.getDefault().nextInt(1, 37);
            setNumeroApostado(numeroApostado);

            if (numeroApostado % 2 == 0) {
                System.out.println("El jugador: " + getId() + " apuesta al par");
            } else {
                System.out.println("El jugador: " + getId() + " apuesta al impar");
            }

            if (numeroApostado % 2 == 0 && numeroGanador % 2 == 0) {
                System.out.println("El jugador " + getId() + " ha ganado " + (getApuestaInicial() + 10) + "€");
                return true;

            } else if (numeroApostado % 2 == 1 && numeroGanador % 2 == 1) {
                System.out.println("El jugador " + getId() + " ha ganado " + (getApuestaInicial() + 10) + "€");
                return true;

            } else {
                System.out.println("El jugador " + getId() + " ha perdido " + getApuestaInicial() + "€");
                System.out.println("El jugador " + getId() + " ahora tiene " + getSaldo() + "€");
            }
        }
        return false;
    }
}
