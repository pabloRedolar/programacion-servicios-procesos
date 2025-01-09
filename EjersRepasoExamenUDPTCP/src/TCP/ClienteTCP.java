package TCP;

import java.io.*;
import java.net.Socket;

public class ClienteTCP {
    public static void main(String[] args) {
        int port = 12345;
        String serverIP = "127.0.0.1";
        String message = "Hola servidor!";

        try (Socket socket = new Socket(serverIP, port)){
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out.println(message);
            String response = in.readLine();
            System.out.println("Respuesta del servidor: " + response);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
