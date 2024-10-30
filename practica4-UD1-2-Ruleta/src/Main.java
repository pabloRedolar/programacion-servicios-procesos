import Model.JugadorMartingala;
import Model.JugadorNumeroExacto;
import Model.JugadorParImpar;
import Model.Ruleta;

public class Main {
    public static void main(String[] args) {
        Ruleta ruleta = new Ruleta();

        inicializarJugadores(ruleta);

        Thread hiloRuleta = new Thread(ruleta);
        hiloRuleta.start();

        esperarJuego(hiloRuleta, 30000);
        System.out.println("Juego terminado.");
    }

    private static void inicializarJugadores(Ruleta ruleta) {
        for (int i = 0; i < 4; i++) {
            ruleta.agregarJugador(new JugadorNumeroExacto("JugadorNumExacto" + i));
        }

        for (int i = 0; i < 4; i++) {
            ruleta.agregarJugador(new JugadorParImpar("JugadorParImpar" + i));
        }

        for (int i = 0; i < 4; i++) {
            ruleta.agregarJugador(new JugadorMartingala("JugadorMartingala" + i));
        }
    }

    private static void esperarJuego(Thread hiloRuleta, int tiempo) {
        try {
            Thread.sleep(tiempo);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        hiloRuleta.interrupt();
    }
}
