package practica2.ej2;

public class Usuario implements Runnable {
    private final String nombre;
    private final Tienda tienda;

    public Usuario(String nombre, Tienda tienda) {
        this.nombre = nombre;
        this.tienda = tienda;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public void run() {
        tienda.comprobarStock(this);
    }

}
