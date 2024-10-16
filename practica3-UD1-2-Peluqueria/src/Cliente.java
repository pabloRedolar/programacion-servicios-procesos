import java.util.Random;

public class Cliente implements Runnable {
    private int id;
    private Random random = new Random();

    public Cliente(int id) {
        this.id = id;

    }


    @Override
    public void run() {
        System.out.println("Cliente " + id + " ha entrado a la peluqueria");

        try {
            Thread.sleep((random.nextInt(2) + 1) * 1000);

            if (Peluqueria.sillasDisponibles.tryAcquire()){
                System.out.println("Cliente " + id + " esta esperando en una silla");
                Peluqueria.colaClientes.add(id);
                Peluqueria.clientesEsperando.release();
            } else {
                System.out.println("Cliente " + id + " no tiene sillas disponibles y se fue");
            }

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public static class GeneradorClientes implements Runnable {
        private Random random = new Random();
        private int clienteId = 1;

        @Override
        public void run() {
            while (Peluqueria.isPeluqueriaAbierta()) {
                Cliente cliente = new Cliente(clienteId++);
                new Thread(cliente).start();

                try {
                    Thread.sleep((random.nextInt(2) + 1) * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("La peluquería ha cerrado, no se generarán más clientes.");
        }
    }
}
