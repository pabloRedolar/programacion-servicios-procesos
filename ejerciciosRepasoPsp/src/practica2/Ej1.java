package practica2;

import java.util.random.RandomGenerator;

public class Ej1 {
    public static void main(String[] args) {
        final int tiempoMaximo = 200000;

        Thread hiloTemperatura = new Thread(new HiloTemperatura());
        Thread hiloHumedad = new Thread(new HiloHumedad());

        try {
            hiloTemperatura.start();
            hiloHumedad.start();

            long tiempoInicio = System.currentTimeMillis();

            while (System.currentTimeMillis() - tiempoInicio < tiempoMaximo) {
                Thread.sleep(3000);
                System.out.println("Hilo principal: Esperando a que los hilos terminen...");

                if (HiloTemperatura.getLecturas() >= 4 && HiloHumedad.getLecturas() >= 4) {
                    System.out.println("Hilo principal: Ambos hilos han completado sus lecturas.");
                    break;
                }
            }

            if (System.currentTimeMillis() - tiempoInicio >= tiempoMaximo) {
                System.out.println("Hilo principal: Tiempo máximo alcanzado. Interrumpiendo los hilos...");
                hiloTemperatura.interrupt();
                hiloHumedad.interrupt();
            }

            hiloTemperatura.join();
            hiloHumedad.join();


            System.out.println("Hilo principal: Finalizado. Lecturas totales - Sensor de Temperatura: "
                    + HiloTemperatura.getLecturas()
                    + ", Sensor de Humedad: "
                    + HiloHumedad.getLecturas()
                    + ".");


        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    public static class HiloTemperatura implements Runnable {
        private static int lecturas = 0;
        private final int TIEMPO_ALTERNAR = 5000;

        public static int getLecturas() {
            return lecturas;
        }

        @Override
        public void run() {
            Thread.currentThread().setName("Hilo temperatura");
            try {
                while (lecturas < 4 && !Thread.currentThread().isInterrupted()) {
                    lecturas++;
                    float grados = Math.round(RandomGenerator.getDefault().nextFloat(5.0F, 35.1F) * 10) / 10.0F;
                    System.out.println("Hilo: " + Thread.currentThread().getName() + ". Lectura: " + grados + "ºC");
                    Thread.sleep(TIEMPO_ALTERNAR);
                }

            } catch (InterruptedException e) {
                System.out.println("Hilo temperatura interrumpido.");
            }
        }
    }

    public static class HiloHumedad implements Runnable {
        private static int lecturas = 0;
        private final int TIEMPO_ALTERNAR = 3000;

        public static int getLecturas() {
            return lecturas;
        }

        @Override
        public void run() {
            Thread.currentThread().setName("Hilo humedad");
            try {
                while (lecturas < 4 && !Thread.currentThread().isInterrupted()) {
                    lecturas++;
                    float humedad = Math.round(RandomGenerator.getDefault().nextFloat(30.0F, 70.1F) * 10) / 10.0F;
                    System.out.println("Hilo: " + Thread.currentThread().getName() + ", Lectura: " + humedad + "%");
                    Thread.sleep(TIEMPO_ALTERNAR);
                }

            } catch (InterruptedException e) {
                System.out.println("Hilo de Humedad interrumpido.");
            }
        }
    }
}
