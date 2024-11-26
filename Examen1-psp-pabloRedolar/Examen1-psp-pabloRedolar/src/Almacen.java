import java.util.concurrent.ConcurrentLinkedQueue;

public class Almacen {
    private int stock = 5;

    private final ConcurrentLinkedQueue<Integer> listaPersonas = new ConcurrentLinkedQueue<>();

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public ConcurrentLinkedQueue<Integer> getListaPersonas() {
        return listaPersonas;
    }

// Metodo para comprobar stock como segurata

    public boolean segurata() {
        return getStock() > 0;
    }
}
