import java.util.Random;

class SensorTemperatura extends Thread {
    private int lecturas = 0;
    private boolean running = true;

    @Override
    public void run() {
        Random random = new Random();

        while (running && lecturas < 4) {
            try {
                // Simular una lectura de temperatura entre 20 y 30 grados Celsius
                double temperatura = 20 + (10 * random.nextDouble());
                System.out.println("Hilo: Sensor de Temperatura. Lectura: " + temperatura + "°C");
                lecturas++;
                Thread.sleep(5000); // Esperar 5 segundos entre lecturas
            } catch (InterruptedException e) {
                System.out.println("Hilo: Sensor de Temperatura. Interrumpido.");
                running = false;
            }
        }
    }

    public int getLecturas() {
        return lecturas;
    }
}

class SensorHumedad extends Thread {
    private int lecturas = 0;
    private boolean running = true;

    @Override
    public void run() {
        Random random = new Random();

        while (running && lecturas < 4) {
            try {
                // Simular una lectura de humedad entre 40% y 60%
                double humedad = 40 + (20 * random.nextDouble());
                System.out.println("Hilo: Sensor de Humedad. Lectura: " + humedad + "%");
                lecturas++;
                Thread.sleep(3000); // Esperar 3 segundos entre lecturas
            } catch (InterruptedException e) {
                System.out.println("Hilo: Sensor de Humedad. Interrumpido.");
                running = false;
            }
        }
    }

    public int getLecturas() {
        return lecturas;
    }
}

public class SistemaSensores {
    public static void main(String[] args) {
        // Definir el tiempo máximo de espera en segundos (parámetro del método main)
        int tiempoMaximo = 30; // Puedes cambiar este valor o recibirlo como argumento

        SensorTemperatura sensorTemperatura = new SensorTemperatura();
        SensorHumedad sensorHumedad = new SensorHumedad();

        // Iniciar los sensores
        sensorTemperatura.start();
        sensorHumedad.start();

        // Hilo principal esperando
        long startTime = System.currentTimeMillis();
        boolean sensoresFinalizados = false;

        while (!sensoresFinalizados && (System.currentTimeMillis() - startTime) / 1000 < tiempoMaximo) {
            try {
                // Mostrar mensaje de espera cada segundo
                System.out.println("Hilo: main. Esperando a que los hilos terminen...");
                Thread.sleep(1000);

                // Verificar si ambos hilos han terminado sus lecturas
                if (!sensorTemperatura.isAlive() && !sensorHumedad.isAlive()) {
                    sensoresFinalizados = true;
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Si se ha alcanzado el tiempo máximo, interrumpir los hilos que sigan corriendo
        if (!sensoresFinalizados) {
            System.out.println("Hilo: main. Tiempo máximo alcanzado. Interrumpiendo los hilos...");

            if (sensorTemperatura.isAlive()) {
                sensorTemperatura.interrupt();
            }

            if (sensorHumedad.isAlive()) {
                sensorHumedad.interrupt();
            }
        }

        // Esperar a que ambos hilos finalicen
        try {
            sensorTemperatura.join();
            sensorHumedad.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Mostrar mensaje final con las lecturas totales
        System.out.println("Hilo: main. Finalizado. Lecturas totales - Sensor de Temperatura: "
                + sensorTemperatura.getLecturas() + ", Sensor de Humedad: " + sensorHumedad.getLecturas() + ".");
    }
}
