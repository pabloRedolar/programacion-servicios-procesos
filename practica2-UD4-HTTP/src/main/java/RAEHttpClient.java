import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class RAEHttpClient {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Introduce una palabra: ");
        String palabra = scanner.nextLine().trim();

        String urlString = "https://dle.rae.es/" + palabra;
        System.out.println("Consultando: " + urlString);

        try {
            URL url = new URL(urlString);
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();

            conexion.setRequestMethod("GET");
            conexion.setInstanceFollowRedirects(true);
            conexion.setRequestProperty("User-Agent", "Mozilla/5.0");

            int codigoRespuesta = conexion.getResponseCode();
            System.out.println("Código de respuesta: " + codigoRespuesta);

            InputStream inputStream = (codigoRespuesta == 200) ? conexion.getInputStream() : conexion.getErrorStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder contenido = new StringBuilder();
            String linea;
            while ((linea = reader.readLine()) != null) {
                contenido.append(linea).append("\n");
            }

            reader.close();
            conexion.disconnect();

            if (codigoRespuesta == 200) {
                try (FileWriter writer = new FileWriter(palabra + ".html")) {
                    writer.write(contenido.toString());
                    System.out.println("Página guardada como: " + palabra + ".html");
                }
            } else {
                System.out.println("Error al econtrar la página. Revisar la ortografia");
            }

        } catch (IOException e) {
            System.out.println("Error al conectar con la RAE: " + e.getMessage());
        }

        scanner.close();
    }
}


