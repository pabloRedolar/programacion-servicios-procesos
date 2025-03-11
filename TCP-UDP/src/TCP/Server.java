package TCP;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;

public class Server {
    public static void main(String[] args) {
        int port = 10069;
        int timeOut = 10000;

        try (ServerSocket serverSocket = new ServerSocket(port);){
            System.out.println("TCP.Server started on port: " + serverSocket.getLocalPort() + " at " + LocalDateTime.now());
            serverSocket.setSoTimeout(timeOut);

            try (Socket socket = serverSocket.accept()) {
                InputStream input = socket.getInputStream();
                DataInputStream dataIn = new DataInputStream(input);
                OutputStream output = socket.getOutputStream();
                DataOutputStream dataOut = new DataOutputStream(output);

                int numero = dataIn.readInt();
                int respuesta = numero * (numero + 1);

                dataOut.writeInt(respuesta);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
