package Model;

abstract public class Jugador {
    String id;
    int saldo = 1000;
    int apuestaInicial = 10;
    int numeroApostado;

    public Jugador() {
    }

    public Jugador(String id, int saldo, int apuestaInicial, int numeroApostado) {
        this.id = id;
        this.saldo = saldo;
        this.apuestaInicial = apuestaInicial;
        this.numeroApostado = numeroApostado;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public abstract boolean apostar(int numeroGanador);
}
