package practica2.ej2;

public class Tienda {
    private int stock = 3;

    public synchronized boolean comprobarStock(Usuario usuario) {
        if (stock > 0) {
            System.out.println("Usuario " + usuario.getNombre() + " ha iniciado un Bizum para comprar el teléfono...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            stock--;
            System.out.println("Usuario " + usuario.getNombre() + " ha comprado el teléfono. Stock restante: " + stock);
            return true;
        } else {
            System.out.println("Usuario " + usuario.getNombre() + " intentó comprar, pero no hay stock disponible.");
            System.out.printf("Usuario %s no pudo realizar la compra. Stock agotado.\n", usuario.getNombre());
            return false;
        }
    }
}
