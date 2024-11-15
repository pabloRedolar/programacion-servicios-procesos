import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

class Pedido1 implements Runnable {
    private final int idPedido;

    public Pedido1(int idPedido) {
        this.idPedido = idPedido;
    }

    @Override
    public void run() {
        System.out.println("Procesando pedido #" + idPedido + " en el hilo " + Thread.currentThread().getName());
        try {
            // Simula el tiempo de procesamiento del pedido
            int tiempoProcesamiento = (int) (Math.random() * 5) + 1;
            TimeUnit.SECONDS.sleep(tiempoProcesamiento);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Pedido #" + idPedido + " procesado en el hilo " + Thread.currentThread().getName());
    }
}

public class ProcesadorPedidos {
    public static void main(String[] args) {
        // Crear un ThreadPoolExecutor con un tamaño de 4 hilos
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(4);

        // Crear y enviar 10 pedidos al executor
        for (int i = 1; i <= 10; i++) {
            Pedido1 pedido = new Pedido1(i);
            executor.execute(pedido); // Enviar la tarea al pool de hilos
        }

        // Cerrar el executor cuando termine de procesar todas las tareas
        executor.shutdown();

        try {
            // Esperar a que todos los hilos terminen de ejecutarse
            if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
                executor.shutdownNow(); // Forzar el cierre si el tiempo de espera se excede
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }

        System.out.println("Todos los pedidos han sido procesados.");
    }
}
