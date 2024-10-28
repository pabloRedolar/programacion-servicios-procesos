package Model;

import java.util.random.RandomGenerator;

public class JugadorNumeroExacto extends Jugador{
    @Override

    public boolean apostar(int numeroGanador) {
        if (saldo < getApuestaInicial()) {
            System.out.println("El jugador " + getId() + " tiene un saldo inferior a 10€");
        } else {
            setSaldo(saldo -= getApuestaInicial());

            if (numeroGanador == 0){
                System.out.println("Ha salido el 0, el jugador: " + getId() + " ha perdido " + getApuestaInicial());
                System.out.println("El jugador " + getId() + " ahora tiene " + getSaldo() + "€");
                return false;
            }

            setNumeroApostado(RandomGenerator.getDefault().nextInt(1, 37));
            System.out.println("Numero apostado: " + getNumeroApostado());

            if (getNumeroApostado() == numeroGanador) {
                System.out.println("El jugador " + getId() + " ha ganado " + getApuestaInicial() * 36 + "€");
                return true;

            } else {
                System.out.println("El jugador " + getId() + " ha perdido " + getApuestaInicial() + "€");
                System.out.println("El jugador " + getId() + " ahora tiene " + getSaldo() + "€");

            }
        }
        return false;
    }
}
