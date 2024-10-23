public class Jugador implements Runnable{
    int id;
    int saldo = 1000;

    public Jugador() {
    }

    public Jugador(int id, int saldo) {
        this.id = id;
        this.saldo = saldo;
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

    @Override
    public String toString() {
        return "Jugador{" +
                "id=" + id +
                ", saldo=" + saldo +
                '}';
    }

    @Override
    public void run() {

    }
}
