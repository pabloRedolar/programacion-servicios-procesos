import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.Arrays;

public class Ejer3sumaRecursiva extends RecursiveTask<Integer> {
    final static int TOTAL = 20;
    final static int THRESHOLD = 5;

    int[] numbers;
    int start;
    int length;

    public Ejer3sumaRecursiva(int[] numbers, int start, int length) {
        this.numbers = numbers;
        this.start = start;
        this.length = length;
    }

    private int sumIterative() {
        int sum = 0;
        int end = start + length;
        for (int i = start; i < end; i++) {
            sum += numbers[i];
        }
        System.out.println("Suma iterativa de " + Arrays.toString(Arrays.copyOfRange(numbers, start, end)) + " = " + sum);
        return sum;
    }

    private int sumRecursive() {
        int mid = length / 2;
        Ejer3sumaRecursiva task1 = new Ejer3sumaRecursiva(numbers, start, mid);
        Ejer3sumaRecursiva task2 = new Ejer3sumaRecursiva(numbers, start + mid, length - mid);

        System.out.println("Dividiendo: " + Arrays.toString(Arrays.copyOfRange(numbers, start, start + length)));

        task1.fork();
        int resultRight = task2.compute();
        int resultLeft = task1.join();

        System.out.println(resultLeft);
        System.out.println(resultRight);

        return resultLeft + resultRight;
    }

    @Override
    protected Integer compute() {
        if (length > THRESHOLD) {
            return sumRecursive();
        } else {
            return sumIterative();
        }
    }

    static int[] generateArray() {
        int[] numbers = new int[TOTAL];
        for (int i = 0; i < TOTAL; i++) {
            numbers[i] = i + 1;
        }
        return numbers;
    }

    public static void main(String[] args) {
        int[] numbers = generateArray();

        Ejer3sumaRecursiva task = new Ejer3sumaRecursiva(numbers, 0, TOTAL);
        long start = System.currentTimeMillis();
        int sumIterative = task.sumIterative();
        long end = System.currentTimeMillis();
        System.out.println("Sum (" + sumIterative + ") iterative found in " + (end - start) + " ms");

        ForkJoinPool pool = new ForkJoinPool();
        start = System.currentTimeMillis();
        int sumParallel = pool.invoke(new Ejer3sumaRecursiva(numbers, 0, TOTAL));
        end = System.currentTimeMillis();
        System.out.println("Sum (" + sumParallel + ") parallel found in " + (end - start) + " ms");
    }
}
