package EjerciciosIA;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class ClienteTCP {
    private static final int PORT = 12345;
    private static final String HOSTNAME = "localhost";

    public static void main(String[] args) {
        try (Socket socket = new Socket(HOSTNAME, PORT)) {

            System.out.print("Escribe un mensaje: ");
            Scanner scanner = new Scanner(System.in);

            BufferedWriter salida = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            salida.write(scanner.next());
            salida.newLine();
            salida.flush();

            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String respuestaServer = String.valueOf(entrada.readLine());
            System.out.println(respuestaServer);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
