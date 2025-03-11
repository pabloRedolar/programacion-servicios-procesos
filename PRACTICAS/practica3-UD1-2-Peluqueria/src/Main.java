public class Main {
    public static void main(String[] args) {
        System.out.println("La peluquería ha abierto.");

        for (int i = 1; i <= Peluqueria.NUM_PELUQUEROS; i++) {
            new Thread(new Peluquero(i)).start();
        }

        new Thread(new Cliente.GeneradorClientes()).start();

        try {
            Thread.sleep(Peluqueria.TIEMPO_OPERACION * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Peluqueria.cerrarPeluqueria();
        System.out.println("La peluquería ha cerrado. No se aceptarán más clientes.");

        for (int i = 1; i <= Peluqueria.NUM_PELUQUEROS; i++) {
            try {
                Peluqueria.peluquerosFinalizados.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
