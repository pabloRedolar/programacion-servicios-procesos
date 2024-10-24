import Model.Jugador;
import Model.Ruleta;

public class Main {
    public static void main(String[] args) {
        Ruleta ruleta = new Ruleta();
        ruleta.generarNumeroGanador();

        System.out.println(ruleta.getNumeroGanador());

        for (int i = 0; i < 4; i++) {
            Jugador jugador = new Jugador();
            jugador.setId(i);

            if (jugador.hacerApuesta(ruleta.getNumeroGanador())){
                ruleta.setBanca(ruleta.getBanca() - (jugador.getApuestaInicial() * 36));
            } else {
                ruleta.setBanca(ruleta.getBanca() + jugador.getApuestaInicial());
            }

        }
        System.out.println("La banca queda en: " + ruleta.getBanca());
    }
}
