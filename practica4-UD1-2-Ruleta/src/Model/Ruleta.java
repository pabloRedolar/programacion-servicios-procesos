package Model;


import java.util.random.RandomGenerator;

public class Ruleta {
    private final int INTERVALO_NUMEROS = 3000;
    private int numeroGanador;
    private int banca = 50000;

    public Ruleta() {
    }

    public Ruleta(int numeroGanador, int banca) {
        this.numeroGanador = numeroGanador;
        this.banca = banca;
    }

    public int getINTERVALO_NUMEROS() {
        return INTERVALO_NUMEROS;
    }

    public int getNumeroGanador() {
        return numeroGanador;
    }

    public void setNumeroGanador(int numeroGanador) {
        this.numeroGanador = numeroGanador;
    }

    public int getBanca() {
        return banca;
    }

    public void setBanca(int banca) {
        this.banca = banca;
    }

    public void generarNumeroGanador(){
        setNumeroGanador(RandomGenerator.getDefault().nextInt(0, 36));
    }
}
