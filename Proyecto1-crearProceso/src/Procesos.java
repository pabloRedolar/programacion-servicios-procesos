import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Procesos {
    public static void main(String[] args) {
        try {
            Process pCalc = new ProcessBuilder("calc").start();
            System.out.println("Lanzado proceso calculadora con java. PID: " + pCalc.pid());
            String command = "tasklist";
            Process pCommand = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(pCommand.getInputStream()));

            String line = "";
            while ((line = reader.readLine()) != null) {
                if (line.contains(Long.toString(pCalc.pid()))) {
                    System.out.println(line + "\n");
                }
            }
            
            pCommand.waitFor();
            pCalc.waitFor();
            System.out.println("Programa finalizado");

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
