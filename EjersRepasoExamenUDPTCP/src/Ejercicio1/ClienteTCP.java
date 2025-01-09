package Ejercicio1;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClienteTCP {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 12345)){
            InputStream input = socket.getInputStream();
            OutputStream output = socket.getOutputStream();

            Scanner scanner = new Scanner(System.in);
            System.out.println("Introduce un numero: ");
            int numero = scanner.nextInt();

            PrintWriter writer = new PrintWriter(output, true);
            writer.println(numero);

            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String respuesta = reader.readLine();
            System.out.println("Respuesta del server: " + respuesta);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
