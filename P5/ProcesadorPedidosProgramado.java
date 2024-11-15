import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

class Pedido2 implements Runnable {
    private final int idPedido;

    public Pedido2(int idPedido) {
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

public class ProcesadorPedidosProgramado {
    public static void main(String[] args) {
        // Crear un ScheduledThreadPoolExecutor con un tamaño de 4 hilos
        ScheduledThreadPoolExecutor executor = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(4);

        // Crear y programar 10 pedidos para que se procesen con un retraso aleatorio
        for (int i = 1; i <= 10; i++) {
            Pedido1 pedido = new Pedido1(i);
            // Programar la tarea con un retraso aleatorio entre 1 y 3 segundos
            int retraso = (int) (Math.random() * 3) + 1;
            executor.schedule(pedido, retraso, TimeUnit.SECONDS);
            System.out.println("Pedido #" + i + " programado para ejecutarse en " + retraso + " segundos.");
        }

        // Cerrar el executor después de que todos los pedidos hayan sido procesados
        executor.shutdown();

        try {
            // Esperar a que todos los hilos terminen de ejecutarse
            if (!executor.awaitTermination(15, TimeUnit.SECONDS)) {
                executor.shutdownNow(); // Forzar el cierre si el tiempo de espera se excede
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }

        System.out.println("Todos los pedidos han sido procesados.");
    }
}