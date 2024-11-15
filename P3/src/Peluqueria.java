import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;
import java.util.Random;

class Peluqueria {
        // Parámetros de la peluquería
        private static final int NUM_PELUQUEROS = 2;
        private static final int NUM_SILLAS = 5;
        private static final int TIEMPO_OPERACION = 30; // 30 segundos de operación

        // Semáforos
        private Semaphore clientesEsperando = new Semaphore(0); // Contador de clientes esperando
        private Semaphore sillasDisponibles = new Semaphore(NUM_SILLAS); // Semáforo de sillas disponibles
        private Semaphore peluquerosFinalizados = new Semaphore(0); // Para esperar a que terminen los peluqueros

        private boolean peluqueriaAbierta = true; // Estado de la peluquería (abierta/cerrada)

        // Cola para los clientes esperando en las sillas
        private ConcurrentLinkedQueue<Integer> colaClientes = new ConcurrentLinkedQueue<>();

        public static void main(String[] args) {
                Peluqueria peluqueria = new Peluqueria();
                peluqueria.iniciarSimulacion();
        }

        // Metodo para iniciar la simulación de la peluqueria
        public void iniciarSimulacion() {
                System.out.println("La peluquería ha abierto.");

                // Crear e iniciar hilos de los peluqueros
                for (int i = 0; i < NUM_PELUQUEROS; i++) {
                        new Thread(new Peluquero(i + 1)).start();
                }

                // Crear un hilo para gestionar el cierre de la peluquería
                new Thread(new Runnable() {
                        @Override
                        public void run() {
                                try {
                                        // Esperar los 30 segundos
                                        Thread.sleep(TIEMPO_OPERACION*1000);
                                        cerrarPeluqueria(); // Llamar al cierre de la peluquería
                                } catch (InterruptedException e) {
                                        e.printStackTrace();
                                }
                        }
                }).start();

                // Generar clientes aleatoriamente mientras la peluquería esté abierta
                int clienteID = 0;
                Random random = new Random();

                while (peluqueriaAbierta) {
                        clienteID++;
                        new Thread(new Cliente(clienteID)).start();
                        try {
                                // Los clientes llegan en intervalos aleatorios (entre 1 y 2 segundos)
                                Thread.sleep(random.nextInt(1000) + 1000);
                        } catch (InterruptedException e) {
                                e.printStackTrace();
                        }
                }

                try {
                        // Esperar a que ambos peluqueros terminen antes de cerrar definitivamente
                        peluquerosFinalizados.acquire(NUM_PELUQUEROS);
                        System.out.println("Todos los peluqueros han terminado. La peluquería cierra completamente.");
                } catch (InterruptedException e) {
                        e.printStackTrace();
                }
        }

        // Metodo para cerrar la peluquería
        public void cerrarPeluqueria() {
                peluqueriaAbierta = false; // Cambia el estado a cerrada
                System.out.println("La peluquería ha cerrado. No se aceptarán más clientes.");
        }

        // Clase interna que representa a los peluqueros
        class Peluquero implements Runnable {
                private int idPeluquero;

                public Peluquero(int id) {
                        this.idPeluquero = id;
                }

                @Override
                public void run() {
                        while (true) {
                                try {
                                        if (!peluqueriaAbierta && clientesEsperando.availablePermits() == 0) {
                                                System.out.println("Peluquero " + idPeluquero + " se retira. No hay más clientes.");
                                                peluquerosFinalizados.release(); // Indica que este peluquero ha terminado
                                                break; // El peluquero termina su turno si ya no hay más clientes en espera tras el cierre
                                        }

                                        // Los peluqueros esperan por clientes mientras haya clientes esperando o la peluquería siga abierta
                                        System.out.println("Peluquero " + idPeluquero + " está esperando clientes.");
                                        clientesEsperando.acquire(); // Espera hasta que haya un cliente

                                        // Tomar al siguiente cliente de la cola
                                        Integer clienteID = colaClientes.poll(); // Saca al primer cliente de la cola (FIFO)

                                        if (clienteID != null) {
                                                System.out.println("Peluquero " + idPeluquero + " está cortando el pelo al cliente " + clienteID);

                                                // Simula el corte de pelo que toma entre 4 y 6 segundos
                                                Thread.sleep(new Random().nextInt(2000) + 4000);

                                                System.out.println("Peluquero " + idPeluquero + " terminó de cortar el pelo al cliente " + clienteID);
                                                sillasDisponibles.release(); // Libera una silla para que otros clientes puedan sentarse
                                        }

                                } catch (InterruptedException e) {
                                        e.printStackTrace();
                                }
                        }
                }
        }

        // Clase interna que representa a los clientes
        class Cliente implements Runnable {
                private int idCliente;

                public Cliente(int id) {
                        this.idCliente = id;
                }

                @Override
                public void run() {
                        System.out.println("Cliente " + idCliente + " ha llegado a la peluquería.");
                        try {
                                // Verifica si la peluquería sigue abierta antes de intentar tomar una silla
                                if (peluqueriaAbierta && sillasDisponibles.tryAcquire()) {
                                        System.out.println("Cliente " + idCliente + " está esperando en una silla.");

                                        // Añadir el cliente a la cola
                                        colaClientes.add(idCliente);

                                        clientesEsperando.release(); // Avisa a los barberos que hay un cliente esperando

                                } else {
                                        // Si no hay sillas disponibles o la peluquería está cerrada, el cliente se va
                                        if (!peluqueriaAbierta) {
                                                System.out.println("Cliente " + idCliente + " llegó pero la peluquería está cerrada y se fue.");
                                        } else {
                                                System.out.println("Cliente " + idCliente + " no encontró una silla libre y se fue.");
                                        }
                                }

                        } catch (Exception e) {
                                e.printStackTrace();
                        }
                }
        }
}




