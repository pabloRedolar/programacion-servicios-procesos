package TCP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorTCP {
    public static void main(String[] args) {
        int port = 12345;

        try (ServerSocket serverSocket = new ServerSocket(port)){
            System.out.println("Servidor tcp en espera");

            try (Socket clientSocket = serverSocket.accept()){
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                String message = in.readLine();
                System.out.println("Mensaje recivido: " + message);
                out.println("Hola cliente!");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
