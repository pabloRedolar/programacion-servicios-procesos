package TCP;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClienteTCP {
    public static void main(String[] args) {
        final String SERVIDOR = "localhost";
        final int PUERTO = 5000;

        try (Socket socket = new Socket(SERVIDOR, PUERTO);
             BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            System.out.print("Introduce un número: ");
            String numero = scanner.nextLine();
            salida.println(numero);

            // Recibir respuesta
            String respuesta = entrada.readLine();
            System.out.println("El resultado de " + numero + " * (" + numero + " + 1) es: " + respuesta);

        } catch (Exception e) {
            System.err.println("Error en el cliente: " + e.getMessage());
        }
    }
}
