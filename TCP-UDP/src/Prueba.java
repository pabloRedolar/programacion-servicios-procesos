public class Prueba {
    public static void main(String[] args) {
        String palabra = "Anita lava la tina";
        String comprobar = "";
        palabra = palabra.trim().toLowerCase().replace(" ", "");

        for (int i = palabra.length() - 1; i >= 0; i--) {
            comprobar += palabra.charAt(i);
        }

        System.out.println(comprobar);

        
    }
}
