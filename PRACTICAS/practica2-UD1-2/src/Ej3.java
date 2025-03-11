import java.util.concurrent.Semaphore;

class Parking {
    private Semaphore plazas;

    public Parking(int capacidad) {
        plazas = new Semaphore(capacidad);
    }

    public void estacionar(int cocheId) {
        try {
            System.out.println("Coche " + cocheId + " está intentando estacionar...");
            plazas.acquire();
            System.out.println("Coche " + cocheId + " ha estacionado. Plazas disponibles: " + plazas.availablePermits());

            Thread.sleep(3000);

            System.out.println("Coche " + cocheId + " ha salido del estacionamiento.");
            plazas.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Coche extends Thread {
    private int id;
    private Parking parking;

    public Coche(int id, Parking parking) {
        this.id = id;
        this.parking = parking;
    }

    @Override
    public void run() {
        parking.estacionar(id);
    }
}

public class Ej3 {
    public static void main(String[] args) {
        Parking parking = new Parking(3);

        Coche[] coches = new Coche[5];
        for (int i = 0; i < 5; i++) {
            coches[i] = new Coche(i + 1, parking);
            coches[i].start();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (Coche coche : coches) {
            try {
                coche.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Simulación de estacionamiento finalizada.");
    }
}
