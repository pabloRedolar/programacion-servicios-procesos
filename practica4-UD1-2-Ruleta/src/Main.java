import Model.JugadorMartingala;
import Model.JugadorNumeroExacto;
import Model.JugadorParImpar;
import Model.Ruleta;

public class Main {
    public static void main(String[] args) {
        Ruleta ruleta = new Ruleta();
        ruleta.girarRuleta();

        System.out.println(ruleta.getNumeroGanador());

        //Jugadores a numero especifico

        for (int i = 0; i < 4; i++) {
            JugadorNumeroExacto jugadorNumeroExacto = new JugadorNumeroExacto();
            jugadorNumeroExacto.setId("jugardorNumeroExacto" + i);

            if (jugadorNumeroExacto.apostar(ruleta.getNumeroGanador())) {

                if ((ruleta.getBanca() - jugadorNumeroExacto.getApuestaInicial() * 36) > 0) {
                    ruleta.setBanca(ruleta.getBanca() - (jugadorNumeroExacto.getApuestaInicial() * 36));
                    jugadorNumeroExacto.setSaldo(jugadorNumeroExacto.getSaldo() + (jugadorNumeroExacto.getApuestaInicial() * 36));
                    System.out.println("El jugador " + jugadorNumeroExacto.getId() + " ahora tiene " + jugadorNumeroExacto.getSaldo() + "€");
                } else {
                    jugadorNumeroExacto.setSaldo(ruleta.getBanca());
                }

            } else {
                ruleta.setBanca(ruleta.getBanca() + jugadorNumeroExacto.getApuestaInicial());
            }
        }

        System.out.println("---------Apuestas Par/Impar----------");

        //Jugadores al par/impar

        for (int i = 0; i < 4; i++) {
            JugadorParImpar jugadorParImpar = new JugadorParImpar();
            jugadorParImpar.setId("jugadorParImpar" + i);

            if (jugadorParImpar.apostar(ruleta.getNumeroGanador())) {

                if ((ruleta.getBanca() - (jugadorParImpar.getApuestaInicial() * 2) > 0)) {
                    ruleta.setBanca(ruleta.getBanca() - (jugadorParImpar.getApuestaInicial() * 2));
                    jugadorParImpar.setSaldo(jugadorParImpar.getSaldo() + (jugadorParImpar.getApuestaInicial() * 2));
                    System.out.println("El jugador " + jugadorParImpar.getId() + " ahora tiene " + jugadorParImpar.getSaldo() + "€");
                } else {
                    jugadorParImpar.setSaldo(jugadorParImpar.getSaldo() + ruleta.getBanca());
                }

            } else {
                ruleta.setBanca(ruleta.getBanca() + jugadorParImpar.getApuestaInicial());
            }
        }

        //Jugadores martingala
        System.out.println("---------------Martingala---------------");

        for (int i = 0; i < 4; i++) {
            JugadorMartingala jugadorMartingala = new JugadorMartingala();
            jugadorMartingala.setId("jugadorMartingala" + i);

            if (jugadorMartingala.apostar(ruleta.getNumeroGanador())) {
                jugadorMartingala.setSaldo(jugadorMartingala.getSaldo() + 360);
                ruleta.setBanca(ruleta.getBanca() - 360);
            } else {
                jugadorMartingala.setSaldo(jugadorMartingala.getSaldo() - 10);
                jugadorMartingala.setApuestaInicial(jugadorMartingala.getApuestaInicial() * 2);
                ruleta.setBanca(ruleta.getBanca() + 10);
            }
        }

        System.out.println("La banca se queda en: " + ruleta.getBanca());

    }
}
