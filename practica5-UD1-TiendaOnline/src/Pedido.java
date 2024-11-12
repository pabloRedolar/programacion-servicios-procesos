import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.random.RandomGenerator;

public class Pedido implements Runnable {
    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Ejercicio 1

    //    public static void main(String[] args) {
    //        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(4);
    //
    //        for (int i = 0; i < 10; i++) {
    //            Pedido pedido = new Pedido();
    //            try {
    //                Thread.sleep(200);
    //            } catch (InterruptedException e) {
    //                throw new RuntimeException(e);
    //            }
    //            pedido.setId(i+1);
    //            threadPoolExecutor.execute(new Thread(pedido));
    //        }
    //
    //        threadPoolExecutor.shutdown();
    //    }

    // Ejercicio 2

    public static void main(String[] args) {
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(4);

        for (int i = 0; i < 10; i++) {
            Pedido pedido = new Pedido();
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            pedido.setId(i + 1);
            scheduledThreadPoolExecutor.schedule(new Thread(pedido), RandomGenerator.getDefault().nextInt(1000, 3000), TimeUnit.MILLISECONDS);
        }

        scheduledThreadPoolExecutor.shutdown();
    }


    @Override
    public void run() {
        try {
            System.out.println("Pedido " + this.getId() + " ejecutandose");
            Thread.sleep(new Random().nextInt(1000, 5000));
            System.out.println("Pedido " + this.getId() + " acabado");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}



