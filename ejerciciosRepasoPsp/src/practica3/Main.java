package practica3;

public class Main {
    public static void main(String[] args) {
        Peluqueria peluqueriaMain = new Peluqueria();
        peluqueriaMain.setPeluqueriaAbierta(true);
        System.out.println("La pelu ha abierto");

        Thread generadorClientes = new Thread(() -> {
            int clienteId = 1;
            while (peluqueriaMain.isPeluqueriaAbierta()) {
                Cliente cliente = new Cliente(peluqueriaMain, clienteId++);
                new Thread(cliente).start();

                try {
                    Thread.sleep(2001);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        generadorClientes.start();

        for (int i = 1; i <= Peluqueria.NUM_PELUQUEROS; i++) {
            new Thread(new Peluquero(peluqueriaMain, i)).start();
        }

        try {
            Thread.sleep(Peluqueria.TIEMPO_OPERACION * 1000);
            peluqueriaMain.setPeluqueriaAbierta(false);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        while (peluqueriaMain.getColaClientes().size() > 0) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("La peluquería ha cerrado.");

    }
}
