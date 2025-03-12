package TCP;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClienteTCP {
    private static final int PORT = 50000;
    private static final String HOSTNAME = "localhost";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Escribe el num: ");
        int numero = scanner.nextInt();

        try (Socket socket = new Socket(HOSTNAME, PORT)) {
            System.out.println("Conectado al servidor en " + HOSTNAME + ":" + PORT);

            BufferedWriter salida = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            salida.write(numero + "\n");
            salida.flush();

            BufferedReader respuesta = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while (true) {
                String mensajeRespuesta = respuesta.readLine();
                System.out.println(mensajeRespuesta);

                if (mensajeRespuesta.equals("0")) {
                    break;
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
