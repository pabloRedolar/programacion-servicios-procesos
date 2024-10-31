package Model;

public abstract class Jugador {
    String id;
    int saldo = 20;
    int apuestaInicial = 10;
    int numeroApostado;
    protected int ganancia;

    public Jugador(String id) {
        this.id = id;
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

    public int getGanancia() {
        return ganancia;
    }

    public void setGanancia(int ganancia) {
        this.ganancia = ganancia;
    }

    public abstract boolean apostar(int numeroGanador);
}
