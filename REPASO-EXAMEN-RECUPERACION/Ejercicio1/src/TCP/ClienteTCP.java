package TCP;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClienteTCP {
    public static void main(String[] args) {
        final String SERVIDOR = "localhost"; // Dirección del servidor
        final int PUERTO = 5000; // Puerto donde escucha el servidor

        try (Socket socket = new Socket(SERVIDOR, PUERTO);
             BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            // 1. Pedir un número al usuario
            System.out.print("Introduce un número: ");
            String numero = scanner.nextLine();

            // 2. Enviar el número al servidor
            salida.println(numero);
            System.out.println("Número enviado al servidor...");

            // 3. Recibir la respuesta del servidor
            String respuesta = entrada.readLine();
            System.out.println("Respuesta del servidor (n * (n+1)): " + respuesta);

        } catch (Exception e) {
            System.err.println("Error en el cliente: " + e.getMessage());
        }
    }
}
