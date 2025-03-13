package EjerciciosIA;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

//El servidor debe escuchar en un puerto específico (por ejemplo, 12345).
//
//El cliente debe conectarse al servidor y enviar un mensaje.
//
//El servidor debe recibir el mensaje, mostrarlo en consola y enviar una respuesta al cliente.
//
//El cliente debe recibir la respuesta del servidor y mostrarla en consola.
public class ServidorTCP {
    private static final int PORT = 12345;

    public static void main(String[] args) {
        try (ServerSocket socket = new ServerSocket(PORT)){
            System.out.println("Servidor iniciado en el puerto: " + PORT);

            while (true) {
                Socket socketCliente = socket.accept();

                BufferedReader entrada = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));

                String mensajeCliente = String.valueOf(entrada.readLine());
                System.out.println("Mensaje del cliente " + socketCliente.getInetAddress() + ": " + mensajeCliente);

                BufferedWriter salida = new BufferedWriter(new OutputStreamWriter(socketCliente.getOutputStream()));
                salida.write("Mensaje recibido con exito");
                salida.flush();

                socketCliente.close();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
