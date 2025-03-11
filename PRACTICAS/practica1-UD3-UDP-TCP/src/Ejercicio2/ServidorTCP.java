package Ejercicio2;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorTCP {
    public static void main(String[] args) {
        final int PUERTO = 6660;

        try (ServerSocket servidor = new ServerSocket(PUERTO)) {
            System.out.println("Servidor TCP escuchando en el puerto " + PUERTO);

            while (true) {
                try (Socket cliente = servidor.accept();
                     BufferedReader entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
                     PrintWriter salida = new PrintWriter(cliente.getOutputStream(), true)) {

                    String mensaje = entrada.readLine();
                    int numero = Integer.parseInt(mensaje.trim());
                    int resultado = numero * (numero + 1);

                    salida.println(resultado);
                    System.out.println("Procesado número: " + numero + ", enviado: " + resultado);
                } catch (Exception e) {
                    System.err.println("Error procesando cliente: " + e.getMessage());
                }

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
