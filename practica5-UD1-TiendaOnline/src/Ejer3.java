import java.util.ArrayList;
import java.util.Arrays;

public class Ejer3 {


    public static void main(String[] args) {
        ArrayList<Integer> listaNumeros = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20));

        System.out.println(listaNumeros);
        if (listaNumeros.size() < 5){
            for (int i : listaNumeros){
                i+=i;
            }
        }
    }
}
