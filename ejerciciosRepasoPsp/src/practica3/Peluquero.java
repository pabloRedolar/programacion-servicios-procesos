package practica3;

import java.util.random.RandomGenerator;

public class Peluquero implements Runnable {
    private final Peluqueria peluqueria;
    private final int id;


    public Peluquero(Peluqueria peluqueria, int id) {
        this.peluqueria = peluqueria;
        this.id = id;
    }

    @Override
    public void run() {
        System.out.println("El peluquero " + id + " está esperando clientes");

        while (this.peluqueria.isPeluqueriaAbierta() || !peluqueria.getColaClientes().isEmpty()) {
            Integer clienteId = peluqueria.getColaClientes().poll();

            if (clienteId != null) {
                System.out.println("El peluquero " + id + " está cortando el pelo al cliente " + clienteId);
                try {
                    Thread.sleep(RandomGenerator.getDefault().nextInt(4000, 6001));
                    peluqueria.getClientesEsperando().release();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                System.out.println("Peluquero " + id + " terminó de cortar el pelo al cliente " + clienteId);
                peluqueria.getSillasDisponibles().release();
            }
        }

        System.out.println("El peluquero " + id + " se retira.");
    }
}
