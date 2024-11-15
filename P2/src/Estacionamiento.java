import java.util.concurrent.Semaphore;

class Estacionamiento {
    private final Semaphore plazasDisponibles;

    // Constructor que inicializa el semáforo con el número de plazas disponibles
    public Estacionamiento(int capacidad) {
        plazasDisponibles = new Semaphore(capacidad);
    }

    // Métod para simular la entrada de un coche al estacionamiento
    public void entrarEstacionamiento(String coche) {
        try {
            System.out.println(coche + " está intentando estacionar...");
            plazasDisponibles.acquire();  // Intentar adquirir una plaza
            System.out.println(coche + " ha estacionado. Plazas disponibles: " + plazasDisponibles.availablePermits());

            // Simular el tiempo que el coche está estacionado
            Thread.sleep(2000);

            // Después de que el coche esté estacionado, sale y libera la plaza
            System.out.println(coche + " ha salido del estacionamiento.");
            plazasDisponibles.release();  // Liberar la plaza
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Coche extends Thread {
    private final Estacionamiento estacionamiento;
    private final String nombreCoche;

    public Coche(Estacionamiento estacionamiento, String nombreCoche) {
        this.estacionamiento = estacionamiento;
        this.nombreCoche = nombreCoche;
    }

    @Override
    public void run() {
        // Intentar entrar al estacionamiento
        estacionamiento.entrarEstacionamiento(nombreCoche);
    }
}

class simulacionEstacionamiento {
    public static void main(String[] args) {
        // Crear un estacionamiento con capacidad para 3 coches
        Estacionamiento estacionamiento = new Estacionamiento(3);

        // Crear varios coches que intentarán estacionar
        Coche coche1 = new Coche(estacionamiento, "Coche 1");
        Coche coche2 = new Coche(estacionamiento, "Coche 2");
        Coche coche3 = new Coche(estacionamiento, "Coche 3");
        Coche coche4 = new Coche(estacionamiento, "Coche 4");
        Coche coche5 = new Coche(estacionamiento, "Coche 5");

        // Iniciar los hilos de los coches
        coche1.start();
        coche2.start();
        coche3.start();
        coche4.start();
        coche5.start();

        // Esperar a que los hilos terminen
        try {
            coche1.join();
            coche2.join();
            coche3.join();
            coche4.join();
            coche5.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Mostrar mensaje final
        System.out.println("Simulación de estacionamiento finalizada.");
    }
}

