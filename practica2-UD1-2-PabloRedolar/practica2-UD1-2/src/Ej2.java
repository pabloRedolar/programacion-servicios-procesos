class Tienda {
    private int stock = 3;

    public synchronized boolean comprar(String usuario) {
        System.out.println(usuario + " ha iniciado un Bizum para comprar el teléfono...");
        if (stock > 0) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            stock--;
            System.out.println(usuario + " ha comprado el teléfono. Stock restante: " + stock);
            return true;
        }
        System.out.println(usuario + " intentó comprar, pero no hay stock disponible.");
        return false;
    }
}

class Usuario extends Thread {
    private String nombre;
    private Tienda tienda;

    public Usuario(String nombre, Tienda tienda) {
        this.nombre = nombre;
        this.tienda = tienda;
    }

    @Override
    public void run() {
        if (!tienda.comprar(nombre)) {
            System.out.println(nombre + " no pudo realizar la compra. Stock agotado.");
        }
    }
}

public class Ej2 {
    public static void main(String[] args) {
        Tienda tienda = new Tienda();

        new Usuario("David", tienda).start();
        new Usuario("Jorge", tienda).start();
        new Usuario("María", tienda).start();
        new Usuario("Luisa", tienda).start();

    }
}
