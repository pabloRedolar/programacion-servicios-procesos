package Model;

import java.util.random.RandomGenerator;

public class Jugador {
    int id;
    int saldo = 1000;
    int apuestaInicial = 10;
    int numeroApostado;

    public Jugador() {
    }

    public Jugador(int id, int saldo, int apuestaInicial, int numeroApostado) {
        this.id = id;
        this.saldo = saldo;
        this.apuestaInicial = apuestaInicial;
        this.numeroApostado = numeroApostado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    public int getApuestaInicial() {
        return apuestaInicial;
    }

    public void setApuestaInicial(int apuestaInicial) {
        this.apuestaInicial = apuestaInicial;
    }

    public int getNumeroApostado() {
        return numeroApostado;
    }

    public void setNumeroApostado(int numeroApostado) {
        this.numeroApostado = numeroApostado;
    }

    // Metodos

    public boolean hacerApuesta(int numeroGanador){
        if (this.saldo < getApuestaInicial()){
            System.out.println("El jugador " + getId() + " tiene un saldo inferior a 10€");
        } else {
            setSaldo(this.saldo -= getApuestaInicial());
            setNumeroApostado(RandomGenerator.getDefault().nextInt(0, 36));
            System.out.println("Numero apostado: " + getNumeroApostado());

            if (getNumeroApostado() == numeroGanador){
                System.out.println("El jugador " + getId() + " ha ganado " + getApuestaInicial() * 36 + "€");
                setSaldo(this.saldo += getApuestaInicial() * 36);
                System.out.println("El jugador " + getId() + " ahora tiene " + getSaldo() + "€");
                return true;

            } else {
                System.out.println("El jugador " + getId() + " ha perdido " + getApuestaInicial() + "€");
                System.out.println("El jugador " + getId() + " ahora tiene " + getSaldo() + "€");

            }
        }
        return false;
    }
}
