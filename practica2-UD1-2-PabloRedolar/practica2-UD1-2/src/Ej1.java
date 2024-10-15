import java.util.Random;

class SensorTemperatura extends Thread {
    private int lecturas = 0;
    private boolean interrumpido = false;

    public int getLecturas() {
        return lecturas;
    }

    public void interrumpirSensor() {
        interrumpido = true;
    }

    @Override
    public void run() {
        Random random = new Random();
        try {
            while (lecturas < 4 && !interrumpido) {
                double temperatura = 5 + (35 - 5) * random.nextDouble();
                System.out.printf("Hilo: Sensor de Temperatura. Lectura: %.2f°C\n", temperatura);
                lecturas++;
                Thread.sleep(5000);
            }
        } catch (InterruptedException e) {
            System.out.println("Hilo: Sensor de Temperatura. Interrumpido.");
        }
        System.out.printf("Hilo: Sensor de Temperatura. Lecturas finales: %d\n", lecturas);
    }
}

class SensorHumedad extends Thread {
    private int lecturas = 0;
    private boolean interrumpido = false;

    public int getLecturas() {
        return lecturas;
    }

    public void interrumpirSensor() {
        interrumpido = true;
    }

    @Override
    public void run() {
        Random random = new Random();
        try {
            while (lecturas < 4 && !interrumpido) {
                double humedad = 30 + (70 - 30) * random.nextDouble();
                System.out.printf("Hilo: Sensor de Humedad. Lectura: %.2f%%\n", humedad);
                lecturas++;
                Thread.sleep(3000);
            }
        } catch (InterruptedException e) {
            System.out.println("Hilo: Sensor de Humedad. Interrumpido.");
        }
        System.out.printf("Hilo: Sensor de Humedad. Lecturas finales: %d\n", lecturas);
    }
}

public class Ej1 {
    public static void main(String[] args) {
        SensorTemperatura sensorTemp = new SensorTemperatura();
        SensorHumedad sensorHum = new SensorHumedad();

        long tiempoMaximo = 20000;
        long tiempoInicio = System.currentTimeMillis();

        sensorTemp.start();
        sensorHum.start();

        try {
            while (sensorTemp.getLecturas() < 4 || sensorHum.getLecturas() < 4) {
                System.out.println("Hilo: main. Esperando a que los hilos terminen...");
                Thread.sleep(2000);
                long tiempoActual = System.currentTimeMillis();

                if (tiempoActual - tiempoInicio >= tiempoMaximo) {
                    System.out.println("Hilo: main. Tiempo máximo alcanzado. Interrumpiendo los hilos...");
                    sensorTemp.interrumpirSensor();
                    sensorHum.interrumpirSensor();
                    sensorTemp.interrupt();
                    sensorHum.interrupt();
                    break;
                }
            }

            sensorTemp.join();
            sensorHum.join();

        } catch (InterruptedException e) {
            System.out.println("Hilo: main. Interrumpido.");
        }

        System.out.printf("Hilo: main. Finalizado. Lecturas totales - Sensor de Temperatura: %d, Sensor de Humedad: %d.\n", sensorTemp.getLecturas(), sensorHum.getLecturas());
    }
}