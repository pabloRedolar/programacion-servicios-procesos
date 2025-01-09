package Ejercicio2;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClienteTCP {
    public static void main(String[] args) {
        final String SERVIDOR = "localhost";
        final int PUERTO = 6660;

        try (Socket socket = new Socket(SERVIDOR, PUERTO);
             PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             Scanner scanner = new Scanner(System.in)) {

            System.out.print("Introduce un número: ");
            String mensaje = scanner.nextLine();

            salida.println(mensaje);

            String respuesta = entrada.readLine();
            System.out.println("Respuesta del servidor: " + respuesta);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
