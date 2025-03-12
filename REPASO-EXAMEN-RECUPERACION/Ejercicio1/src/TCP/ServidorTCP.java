package TCP;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorTCP {
    public static void main(String[] args) {
        final int PUERTO = 5000;

        try (ServerSocket servidor = new ServerSocket(PUERTO)) {
            System.out.println("Servidor TCP en el puerto " + PUERTO);

            while (true) {
                // Aceptar conexión
                Socket socketCliente = servidor.accept();
                System.out.println("Cliente conectado desde: " + socketCliente.getInetAddress());

                // Flujos de entrada y salida
                BufferedReader entrada = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
                PrintWriter salida = new PrintWriter(socketCliente.getOutputStream(), true);

                // Leer número
                int numero = Integer.parseInt(entrada.readLine());
                System.out.println("Número recibido: " + numero);

                // Calcular producto con el siguiente número
                int resultado = numero * (numero + 1);
                System.out.println("Enviando resultado: " + resultado);

                // Enviar respuesta
                salida.println(resultado);
                socketCliente.close();
            }
        } catch (Exception e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        }
    }
}
