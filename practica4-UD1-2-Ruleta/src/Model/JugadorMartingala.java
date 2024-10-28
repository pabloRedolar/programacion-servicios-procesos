package Model;

import java.util.random.RandomGenerator;

public class JugadorMartingala extends Jugador {

    @Override
    public boolean apostar(int numeroGanador) {
        if (getSaldo() >= apuestaInicial) {
            setSaldo(getSaldo() - 10);
            setNumeroApostado(RandomGenerator.getDefault().nextInt(1, 37));
            System.out.println("El jugador: " + getId() + " ha apostado " + getApuestaInicial() + "€" + " al numero: " + getNumeroApostado());

            if (numeroGanador == 0){
                System.out.println("Ha salido el 0, el jugador: " + getId() + " ha perdido " + getApuestaInicial() + "€");
                System.out.println("El jugador " + getId() + " ahora tiene " + getSaldo() + "€");
                return false;
            }

            if (numeroGanador == numeroApostado) {
                setSaldo(getSaldo() + 360);
                System.out.println("El jugador: " + getId() + " ha ganado " + 360 + " €");
                System.out.println("El jugador: " + getId() + " tiene un saldo de: " + getSaldo());
                return true;
            } else {
                System.out.println("El jugador: " + getId() + " ha perdido " + getApuestaInicial() + "€");
                System.out.println("El jugador: " + getId() + " tiene un saldo de: " + getSaldo());
                return false;
            }


        } else {
            System.out.println("El jugador " + getId() + " está sin blanca");
        }

        return false;
    }
}
