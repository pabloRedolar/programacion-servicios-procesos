import Model.*;

public class Main {
    public static void main(String[] args) {
        Ruleta ruleta = new Ruleta();

        for (int i = 0; i < 4; i++) {
            JugadorNumeroExacto jugadorNumeroExacto = new JugadorNumeroExacto();
            jugadorNumeroExacto.setId("JugadorNumeroExacto" + i);
            ruleta.agregarJugador(jugadorNumeroExacto);
        }

        for (int i = 0; i < 4; i++) {
            JugadorParImpar jugadorParImpar = new JugadorParImpar();
            jugadorParImpar.setId("JugadorParImpar" + i);
            ruleta.agregarJugador(jugadorParImpar);
        }

        for (int i = 0; i < 4; i++) {
            JugadorMartingala jugadorMartingala = new JugadorMartingala();
            jugadorMartingala.setId("JugadorMartingala" + i);
            ruleta.agregarJugador(jugadorMartingala);
        }

        Thread hiloRuleta = new Thread(ruleta);
        hiloRuleta.start();

        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        hiloRuleta.interrupt();
        System.out.println("Juego terminado.");

        procesarApuestas(ruleta);
    }

    private static void procesarApuestas(Ruleta ruleta) {
        // Jugadores a número específico
        System.out.println("---------Apuestas a Número Específico----------");
        for (int i = 0; i < 4; i++) {
            JugadorNumeroExacto jugadorNumeroExacto = new JugadorNumeroExacto();
            jugadorNumeroExacto.setId("JugadorNumeroExacto" + i);
            procesarApuesta(jugadorNumeroExacto, ruleta, 36);
        }

        // Jugadores par/impar
        System.out.println("---------Apuestas Par/Impar----------");
        for (int i = 0; i < 4; i++) {
            JugadorParImpar jugadorParImpar = new JugadorParImpar();
            jugadorParImpar.setId("JugadorParImpar" + i);
            procesarApuesta(jugadorParImpar, ruleta, 2);
        }

        // Jugadores martingala
        System.out.println("---------------Martingala---------------");
        for (int i = 0; i < 4; i++) {
            JugadorMartingala jugadorMartingala = new JugadorMartingala();
            jugadorMartingala.setId("JugadorMartingala" + i);
            boolean haGanado = jugadorMartingala.apostar(ruleta.getNumeroGanador());

            if (haGanado) {
                jugadorMartingala.setSaldo(jugadorMartingala.getSaldo() + 360);
                ruleta.setBanca(ruleta.getBanca() - 360);
                System.out.println("El jugador " + jugadorMartingala.getId() + " ha ganado en martingala. Ahora tiene " + jugadorMartingala.getSaldo() + "€");
            } else {
                jugadorMartingala.setSaldo(jugadorMartingala.getSaldo() - 10);
                jugadorMartingala.setApuestaInicial(jugadorMartingala.getApuestaInicial() * 2);
                ruleta.setBanca(ruleta.getBanca() + 10);
                System.out.println("El jugador " + jugadorMartingala.getId() + " ha perdido en martingala. Ahora tiene " + jugadorMartingala.getSaldo() + "€");
            }
        }
    }

    private static void procesarApuesta(Jugador jugador, Ruleta ruleta, int multiplicador) {
        boolean haGanado = jugador.apostar(ruleta.getNumeroGanador());
        if (haGanado) {
            int ganancia = jugador.getApuestaInicial() * multiplicador;
            if (ruleta.getBanca() >= ganancia) {
                ruleta.setBanca(ruleta.getBanca() - ganancia);
                jugador.setSaldo(jugador.getSaldo() + ganancia);
                System.out.println("El jugador " + jugador.getId() + " ha ganado. Ahora tiene " + jugador.getSaldo() + "€");
            } else {
                jugador.setSaldo(jugador.getSaldo() + ruleta.getBanca());
                ruleta.setBanca(0);
            }
        } else {
            ruleta.setBanca(ruleta.getBanca() + jugador.getApuestaInicial());
            System.out.println("El jugador " + jugador.getId() + " ha perdido. Ahora tiene " + jugador.getSaldo() + "€");
        }
    }

}

