package LISTA_UDP_RUN;

import java.util.List;

public class main {
    public static void main(String[] args) {
        String kaka = "kakatua";
        Long cuenta = List.of(kaka.split("")).stream().filter(z -> z.toLowerCase().equals("a")).count();
        System.out.println(cuenta);
        int contador = 10;

        for (int i = 0; i < 5; i++) {
            System.out.println(i);
            for (int j = i; j < i+1 ; j++) {
                System.out.println(j);

            }
        }

    }
}
