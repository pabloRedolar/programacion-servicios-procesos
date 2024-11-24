import java.util.Random;

public class Cliente implements Runnable {
    private int id;
    private Almacen almacen;
    private static final int MAX_INTENTOS = 10;
    private static final int RETRASO = 500; // Milisegundos
    private Random random;

    public Cliente(int id, Almacen almacen) {
        this.id = id;
        this.almacen = almacen;
        this.random = new Random();
    }

    @Override
    public void run() {
        int intentos = 0;

        while (intentos < MAX_INTENTOS && almacen.getStock() > 0) {
            System.out.println("Cliente " + id + " intenta acceder. Intento " + intentos);

            intentos++;
            if (random.nextInt(100) < 25) {
                System.out.println("Cliente " + id + " logró entrar en el intento " + intentos);

                if (almacen.comprarProducto()) {
                    System.out.println("Cliente " + id + " compró el producto. Stock restante: " + almacen.getStock());
                    return;
                } else {
                    System.out.println("Cliente " + id + " intentó comprar pero el stock está agotado.");
                    return;
                }
            } else {
                System.out.println("Cliente " + id + " no logró entrar en el intento " + intentos);
            }

            // Espera antes del próximo intento
            try {
                Thread.sleep(RETRASO);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Cliente " + id + " fue interrumpido.");
            }
        }
        System.out.println("Cliente " + id + " agotó sus intentos sin éxito.");
    }
}
