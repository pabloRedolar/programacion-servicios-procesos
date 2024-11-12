package practica1;

import java.util.Arrays;
import java.util.List;

public class Ej2 {
    public static void main(String[] args) {
        int tiempoMaximo = 100;
        long startTime = System.currentTimeMillis();

        Thread secondThread = new Thread(new SecondThreadClass());
        secondThread.start();

        try {
            System.out.println("Hilo: " + Thread.currentThread().getName() + ". Tiempo de espera: " + tiempoMaximo + "s");
            System.out.println("Hilo: " + Thread.currentThread().getName() + ". Esperando a que el hilo " + secondThread.getName() + " termine");

            for (int i = 0; i < tiempoMaximo; i++) {
                if (secondThread.isAlive()) {
                    Thread.sleep(1000);
                    System.out.println("Hilo: " + Thread.currentThread().getName() + ". Todavía esperando...");
                } else break;
            }

            if (secondThread.isAlive()) {
                System.out.println("Hilo: " + Thread.currentThread().getName() + ". Cansado de esperar");
                secondThread.interrupt();
            }

            secondThread.join();

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        long endTime = System.currentTimeMillis();
        long executionTime = (endTime - startTime) / 1000;
        System.out.println("Hilo: " + Thread.currentThread().getName() + ". *** Finalizado. Tiempo de ejecución: " + executionTime + "s. ***");
    }


    public static class SecondThreadClass implements Runnable {
        List<String> messagesList = Arrays.asList("Programas", "Procesos", "Servicios", "Hilos");
        int mensajesMostrados = 0;

        @Override
        public void run() {
            String nombreHilo = Thread.currentThread().getName();

            try {
                for (String s : messagesList) {
                    if (Thread.currentThread().isInterrupted()) break;
                    System.out.println("Hilo: " + nombreHilo + s);
                    mensajesMostrados++;
                    Thread.sleep(4000);
                }

            } catch (InterruptedException e) {
                imprimirRestante(nombreHilo);
            }
            System.out.println("Hilo: " + nombreHilo + ". Interrumpido, imprimiendo el resto de los mensajes.");

        }

        private void imprimirRestante(String nombreHilo) {
            for (int i = mensajesMostrados; i < messagesList.size(); i++) {
                System.out.println("Hilo " + nombreHilo + messagesList.get(i));
            }
        }
    }
}



