import java.util.Random;

public class Peluquero implements Runnable {
    private int id;
    private Random random = new Random();

    public Peluquero(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        System.out.println("Peluquero " + id + " esta esperando clientes");

        while (true) {
            try {
                Peluqueria.clientesEsperando.acquire();
                if (!Peluqueria.peluqueriaAbierta && Peluqueria.colaClientes.isEmpty()){
                    System.out.println("Peluquero " + id + " se retira. No hay más clientes.");
                    Peluqueria.peluquerosFinalizados.release();
                    break;
                }

                Integer clienteId = Peluqueria.colaClientes.poll();
                if (clienteId != null){
                    System.out.println("Peluquero " + id + " está cortando el pelo al cliente " + clienteId);
                    Thread.sleep((4 + random.nextInt(3)) * 1000);
                    System.out.println("Peluquero " + id + " terminó de cortar el pelo al cliente " + clienteId);
                }

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
