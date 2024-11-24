public class Main {
    public static void main(String[] args) {
        final int STOCK_INICIAL = 5;
        final int NUM_CLIENTES = 10;

        Almacen almacen = new Almacen(STOCK_INICIAL);
        Thread[] clientes = new Thread[NUM_CLIENTES];

        for (int i = 0; i < NUM_CLIENTES; i++) {
            clientes[i] = new Thread(new Cliente(i + 1, almacen));
            clientes[i].start();
        }

        // Esperar a que todos los clientes terminen
        for (Thread cliente : clientes) {
            try {
                cliente.join();
            } catch (InterruptedException e) {
                System.out.println("Error esperando a los clientes.");
            }
        }

        System.out.println("Simulación finalizada. Stock restante: " + almacen.getStock());
    }
}
