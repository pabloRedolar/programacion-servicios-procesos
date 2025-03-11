package TCP;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Usuario {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduce un numero: ");
        String serverAdress = "localhost";
        int serverPort = 10069;

        try (Socket socket = new Socket(serverAdress,serverPort)){
            OutputStream output = socket.getOutputStream();
            DataOutputStream dataOut = new DataOutputStream(output);
            InputStream input = socket.getInputStream();
            DataInputStream dataIn = new DataInputStream(input);

            int numero = sc.nextInt();
            dataOut.writeInt(numero);

            System.out.println("La respuesta del servidor es: " + dataIn.readInt());


        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
