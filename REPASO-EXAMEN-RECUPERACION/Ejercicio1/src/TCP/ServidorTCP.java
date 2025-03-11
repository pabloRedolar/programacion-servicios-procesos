package TCP;

import java.io.*;
import java.net.*;

public class ServidorTCP {
    public static void main(String[] args) {
        final int PUERTO = 5000; // Puerto donde el servidor escucha

        try (ServerSocket servidor = new ServerSocket(PUERTO)) {
            System.out.println("Servidor TCP esperando conexiones en el puerto " + PUERTO);

            while (true) {
                // 1. Esperar a que un cliente se conecte
                Socket socketCliente = servidor.accept();
                System.out.println("Cliente conectado: " + socketCliente.getInetAddress());

                // 2. Crear flujos de entrada y salida para comunicarse con el cliente
                BufferedReader entrada = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
                PrintWriter salida = new PrintWriter(socketCliente.getOutputStream(), true);

                // 3. Leer el número enviado por el cliente
                String mensaje = entrada.readLine();
                int numero = Integer.parseInt(mensaje);
                System.out.println("Número recibido: " + numero);

                // 4. Calcular el resultado (número * siguiente número)
                int resultado = numero * (numero + 1);
                System.out.println("Enviando resultado: " + resultado);

                // 5. Enviar el resultado al cliente
                salida.println(resultado);

                // 6. Cerrar la conexión con el cliente
                socketCliente.close();
            }
        } catch (Exception e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        }
    }
}
