package practica3;


import java.util.random.RandomGenerator;

public class Cliente implements Runnable {
    private final Peluqueria peluqueria;
    private int id;

    public Cliente(Peluqueria peluqueria, int clienteId) {
        this.peluqueria = peluqueria;
        this.id = clienteId;
    }

    public int getId() {
        return id;
    }

    @Override
    public void run() {
        while (this.peluqueria.isPeluqueriaAbierta()) {
            try {
                Thread.sleep(RandomGenerator.getDefault().nextInt(1000, 2001));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println("El cliente" + id + " llega a la pelu");
            peluqueria.getColaClientes().add(getId());

            if (peluqueria.getSillasDisponibles().availablePermits() > 0) {
                try {
                    peluqueria.getSillasDisponibles().acquire();
                    System.out.println("El cliente " + getId() + " está esperando en una silla.");
                    peluqueria.getClientesEsperando().acquire();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            } else {
                System.out.println("No hay sillas disponibles. El cliente " + getId() + " se ha ido");
                peluqueria.getColaClientes().poll();
            }
        }
    }


}
