import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;

public class Peluqueria {

    public static final int NUM_PELUQUEROS = 2;
    public static final int NUM_SILLAS = 5;
    public static final int TIEMPO_OPERACION = 30;

    public static Semaphore clientesEsperando = new Semaphore(0);
    public static Semaphore sillasDisponibles = new Semaphore(NUM_SILLAS);
    public static Semaphore peluquerosFinalizados = new Semaphore(0);

    public static ConcurrentLinkedQueue<Integer> colaClientes = new ConcurrentLinkedQueue<>();

    static boolean peluqueriaAbierta = true;

    public static boolean isPeluqueriaAbierta() {
        return peluqueriaAbierta;
    }

    public static void cerrarPeluqueria() {
        peluqueriaAbierta = false;
    }
}
