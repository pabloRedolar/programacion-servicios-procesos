package TCP;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

// El usuario tiene que mandar un numero y el servidor devuelve una cuenta atras desde ese num hasta 0 (TCP)
public class ServidorTCP {
    private static final int PORT = 50000;
    private static final String HOSTNAME = "localhost";

    public static void main(String[] args) {
        try (ServerSocket servidor = new ServerSocket(PORT)) {
            System.out.println("El server con ip: " + servidor.getLocalPort());

            while (true) {
                Socket socketCliente = servidor.accept();

                System.out.println("Cliente actual:" + socketCliente.getInetAddress());

                BufferedReader entrada = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
                int mensajeCliente = Integer.parseInt(entrada.readLine());

                System.out.println(mensajeCliente);

                BufferedWriter salida = new BufferedWriter(new OutputStreamWriter(socketCliente.getOutputStream()));
                for (int i = mensajeCliente; i >= 0 ; i--) {
                    System.out.println(i);
                    salida.write(i + "\n");
                    salida.flush();
                }

                socketCliente.close();

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
