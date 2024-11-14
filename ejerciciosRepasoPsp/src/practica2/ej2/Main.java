package practica2.ej2;

public class Main {
    public static void main(String[] args) {
        Tienda tienda = new Tienda();

        Usuario usuario0 = new Usuario("David", tienda);
        Usuario usuario1 = new Usuario("Jorge", tienda);
        Usuario usuario2 = new Usuario("Maria", tienda);
        Usuario usuario3 = new Usuario("Luisa", tienda);

        Thread hiloUsuario0 = new Thread(usuario0);
        Thread hiloUsuario1 = new Thread(usuario1);
        Thread hiloUsuario2 = new Thread(usuario2);
        Thread hiloUsuario3 = new Thread(usuario3);


        hiloUsuario0.start();
        hiloUsuario1.start();
        hiloUsuario2.start();
        hiloUsuario3.start();

        try {
            hiloUsuario0.join();
            hiloUsuario1.join();
            hiloUsuario2.join();
            hiloUsuario3.join();

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Simulacion finalizada");

    }
}