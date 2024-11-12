public class Ej2 {

    public static void main(String[] args) {
        int tiempoMaximo = args.length > 0 ? Integer.parseInt(args[0]) : 100;
        System.out.println("Hilo: " + Thread.currentThread().getName() + ". Tiempo de espera: " + tiempoMaximo + "s");
        Thread hilo = new Thread(new segundoHilo());
        hilo.start();
        long empiezaEn = System.currentTimeMillis();

        try {
            System.out.println("Hilo: " + Thread.currentThread().getName() + ". Esperando a que el hilo " + hilo.getName() + " termine");

            for (int i = 0; i < tiempoMaximo; i++) {
                if (hilo.isAlive()) {
                    Thread.sleep(1000);
                    System.out.println("Hilo: " + Thread.currentThread().getName() + ". Todavía esperando...");
                } else {
                    break;
                }
            }

            if (hilo.isAlive()) {
                System.out.println("Hilo: " + Thread.currentThread().getName() + ". Cansado de esperar");
                hilo.interrupt();
            }

            hilo.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long tiempoEjecucion = (System.currentTimeMillis() - empiezaEn) / 1000;
        System.out.println("Hilo: " + Thread.currentThread().getName() + ". *** Finalizado. Tiempo de ejecución: " + tiempoEjecucion + "s. ***");
    }
}

class segundoHilo implements Runnable {
    private final String[] listaPalabras = {"Programas", "Procesos", "Servicios", "Hilos"};

    @Override
    public void run() {
        try {
            for (String mensaje : listaPalabras) {
                if (Thread.interrupted()) {
                    System.out.println("Hilo: " + Thread.currentThread().getName() + ". " + mensaje);
                    continue;
                }

                System.out.println("Hilo: " + Thread.currentThread().getName() + ". " + mensaje);

                Thread.sleep(4000);
            }
        } catch (InterruptedException e) {
            for (String mensaje : listaPalabras) {
                System.out.println("Hilo: " + Thread.currentThread().getName() + ". " + mensaje);
            }
        } finally {
            System.out.println("Hilo: " + Thread.currentThread().getName() + ". *** Finalizado ***");
        }
    }
}
