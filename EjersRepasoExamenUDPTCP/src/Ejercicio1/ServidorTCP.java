package Ejercicio1;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorTCP {
    public static void main(String[] args) {
        int port = 12345;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server esperando...");

            Socket socketCliente = serverSocket.accept();

            InputStream input = socketCliente.getInputStream();
            OutputStream ouput = socketCliente.getOutputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            PrintWriter writer = new PrintWriter(ouput, true);

            String mensaje = reader.readLine();
            int numero = Integer.parseInt(mensaje);

            int resultado = numero * 2;

            writer.println("El resultado es: " + resultado);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
