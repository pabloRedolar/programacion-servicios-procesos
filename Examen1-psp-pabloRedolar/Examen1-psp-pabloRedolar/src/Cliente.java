import java.util.random.RandomGenerator;

public class Cliente implements Runnable {
    private final int id;
    private final Almacen almacen;

    public Cliente(int id, Almacen almacen) {
        this.id = id;
        this.almacen = almacen;
    }

    public int getId() {
        return id;
    }

    @Override
    public void run() {
        System.out.println("El cliente" + getId() + " llega a la tienda");

        int intentos = 0;

        while (intentos < 11) {
            System.out.println("El cliente" + getId() + " está intentando entrar");

            int numeroEntrada = RandomGenerator.getDefault().nextInt(0, 3);

            intentos++;

            if (numeroEntrada != 1) {
                System.out.println("El cliente" + getId() + " no ha entrado. Intentos restantes: " + (10 - intentos));
            } else if (!almacen.getListaPersonas().contains(id)) {
                almacen.getListaPersonas().add(id);
                System.out.println("El cliente" + getId() + " ha entrado\n");

                if (almacen.segurata()) {
                    int idCliente = almacen.getListaPersonas().poll();
                    almacen.setStock(almacen.getStock() - 1);
                    almacen.getListaPersonas().poll();
                    System.out.println("El cliente " + idCliente + " ha consegido comprar");
                } else {
                    int idCliente = almacen.getListaPersonas().poll();
                    System.out.println("El cliente " + idCliente + " no ha consegido comprar, stock agotado");
                }

                System.out.println("--------------------------------\n");
                break;
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
