package practica3;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;

public class Peluqueria {
    public static final int NUM_PELUQUEROS = 2;
    public static final int NUM_SILLAS = 5;
    public static final int TIEMPO_OPERACION = 5;

    private final Semaphore clientesEsperando = new Semaphore(0);
    private final Semaphore sillasDisponibles = new Semaphore(NUM_SILLAS);
    private final Semaphore peluquerosFinalizados = new Semaphore(0);


    private boolean peluqueriaAbierta = false;
    private final ConcurrentLinkedQueue<Integer> colaClientes = new ConcurrentLinkedQueue<>();

    public boolean isPeluqueriaAbierta() {
        return peluqueriaAbierta;
    }

    public void setPeluqueriaAbierta(boolean peluqueriaAbierta) {
        this.peluqueriaAbierta = peluqueriaAbierta;
    }

    public Semaphore getClientesEsperando() {
        return clientesEsperando;
    }

    public Semaphore getSillasDisponibles() {
        return sillasDisponibles;
    }

    public Semaphore getPeluquerosFinalizados() {
        return peluquerosFinalizados;
    }

    public ConcurrentLinkedQueue<Integer> getColaClientes() {
        return colaClientes;
    }
}
