import java.util.concurrent.atomic.AtomicInteger;

public class Almacen {
    private AtomicInteger stock;

    public Almacen(int initialStock) {
        this.stock = new AtomicInteger(initialStock);
    }

    public synchronized boolean comprarProducto() {
        if (stock.get() > 0) {
            stock.decrementAndGet();
            return true; // Compra realizada
        }
        return false; // Stock agotado
    }

    public int getStock() {
        return stock.get();
    }
}
