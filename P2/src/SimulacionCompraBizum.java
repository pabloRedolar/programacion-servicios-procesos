class Tienda {
    private int stock;

    // Constructor que inicializa el stock
    public Tienda(int stockInicial) {
        this.stock = stockInicial;
    }

    // Métod sincronizado para realizar una compra
    public synchronized boolean comprarProducto(String usuario) {
        if (stock > 0) {
            System.out.println("Usuario " + usuario + " ha iniciado un Bizum para comprar el teléfono...");
            try {
                // Simular el tiempo que tarda en realizarse el Bizum
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Reducir el stock al completar la compra
            stock--;
            System.out.println("Usuario " + usuario + " ha comprado el teléfono. Stock restante: " + stock);
            return true;
        } else {
            System.out.println("Usuario " + usuario + " intentó comprar, pero no hay stock disponible.");
            return false;
        }
    }
}

class Usuario extends Thread {
    private final Tienda tienda;
    private final String nombreUsuario;

    // Constructor que asigna la tienda y el nombre del usuario
    public Usuario(Tienda tienda, String nombreUsuario) {
        this.tienda = tienda;
        this.nombreUsuario = nombreUsuario;
    }

    @Override
    public void run() {
        // Intentar realizar la compra en la tienda
        boolean exito = tienda.comprarProducto(nombreUsuario);
        if (!exito) {
            System.out.println("Usuario " + nombreUsuario + " no pudo realizar la compra. Stock agotado.");
        }
    }
}

class SimulacionCompraBizum {
    public static void main(String[] args) {
        // Crear una tienda con 3 teléfonos en stock
        Tienda tienda = new Tienda(3);

        // Crear varios usuarios que intentarán comprar
        Usuario usuario1 = new Usuario(tienda, "David");
        Usuario usuario2 = new Usuario(tienda, "Jorge");
        Usuario usuario3 = new Usuario(tienda, "Maria");
        Usuario usuario4 = new Usuario(tienda, "Luisa");

        // Iniciar los hilos de los usuarios
        usuario1.start();
        usuario2.start();
        usuario3.start();
        usuario4.start();

        // Esperar a que los hilos terminen
        try {
            usuario1.join();
            usuario2.join();
            usuario3.join();
            usuario4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Mostrar mensaje final
        System.out.println("Simulación de compras finalizada.");
    }
}
