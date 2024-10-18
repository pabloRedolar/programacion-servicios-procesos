import java.io.File;
import java.io.IOException;

public class Ej1 {
    public static void main(String[] args) {
        File archivoOutput = new File("output.log");
        File archivoError = new File("error.log");

        try {
            ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", "batComandos.bat");

            processBuilder.redirectError(archivoError);
            processBuilder.redirectOutput(archivoOutput);
            Process process = processBuilder.start();
            process.waitFor();

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
