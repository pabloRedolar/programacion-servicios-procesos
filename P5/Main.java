import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

class SumaArray extends RecursiveTask<Integer> {
    private final int[] array;
    private final int inicio, fin;
    private final int umbral;

    public SumaArray(int[] array, int inicio, int fin, int umbral) {
        this.array = array;
        this.inicio = inicio;
        this.fin = fin;
        this.umbral = umbral;
    }

    @Override
    protected Integer compute() {
        int longitud = fin - inicio;

        // Mostrar el rango que está procesando esta tarea
        System.out.println("Procesando rango de índice: [" + inicio + ", " + (fin - 1) + "]");

        // Si la tarea es pequeña, calcular la suma secuencialmente
        if (longitud <= umbral) {
            int suma = 0;
            for (int i = inicio; i < fin; i++) {
                suma += array[i];
            }
            System.out.println("Suma calculada para rango [" + inicio + ", " + (fin - 1) + "] = " + suma);
            return suma;
        } else {
            // Dividir la tarea en dos subtareas
            int mitad = inicio + longitud / 2;
            SumaArray izquierda = new SumaArray(array, inicio, mitad, umbral);
            SumaArray derecha = new SumaArray(array, mitad, fin, umbral);

            // Ejecutar las subtareas en paralelo
            izquierda.fork();
            derecha.fork();

            int resultadoIzquierda = izquierda.join();
            int resultadoDerecha = derecha.join();

            // Combinar los resultados de ambas partes
            int resultadoTotal = resultadoIzquierda + resultadoDerecha;
            System.out.println("Resultado combinado para rango [" + inicio + ", " + (fin - 1) + "] = " + resultadoTotal);
            return resultadoTotal;
        }
    }
}

public class Main {
    public static void main(String[] args) {
        // Crear un array de ejemplo
        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};

        // Definir el umbral para dividir el trabajo
        int umbral = 5;

        // Crear el ForkJoinPool y la tarea principal
        ForkJoinPool pool = new ForkJoinPool();
        SumaArray tareaPrincipal = new SumaArray(array, 0, array.length, umbral);

        // Ejecutar la tarea y obtener el resultado
        int resultado = pool.invoke(tareaPrincipal);

        System.out.println("La suma total del array es: " + resultado);
    }
}
