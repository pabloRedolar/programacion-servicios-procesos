package HTTP;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.print("Introduce la palabra que deseas buscar en el diccionario de la RAE: ");
        String palabra = new Scanner(System.in).nextLine().trim();
        String html = obtenerHtmlPalabra(palabra);

        if (html != null) {
            String nombreFichero = palabra + ".html";
            guardarHtmlEnFichero(html, nombreFichero);
            System.out.println("El código HTML de la palabra '" + palabra + "' se ha guardado en el fichero '" + nombreFichero + "'.");
        } else {
            System.out.println("No se pudo obtener el significado de la palabra '" + palabra + "'.");
        }
    }

    private static String obtenerHtmlPalabra(String palabra) {
        String urlBase = "https://dle.rae.es/"+palabra;
        String html = null;
        try {
            HttpClient client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).build();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(urlBase)).GET().build();
            HttpResponse<String> respuesta = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(respuesta.body());
            html = respuesta.body();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return html;
    }

    private static void guardarHtmlEnFichero(String html, String nombreFichero) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreFichero))) {
            writer.write(html);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}