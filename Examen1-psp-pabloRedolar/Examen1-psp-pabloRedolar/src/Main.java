public class Main {
    public static void main(String[] args) {
        Almacen almacen = new Almacen();

        // Crear los clientes y los hilos de cada cliente
        for (int i = 1; i < 11; i++) {
            Cliente cliente = new Cliente(i, almacen);

            Thread thread = new Thread(cliente);
            try {
                thread.start();
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
