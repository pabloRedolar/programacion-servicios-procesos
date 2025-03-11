import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class Ejer3sumaRecursiva extends RecursiveTask<Long> {
    final static long TOTAL = 200000000;
    final static long THRESHOLD = 5000000;

    long[] numbers;
    long start;
    long length;

    public Ejer3sumaRecursiva(long[] numbers, long start, long length) {
        this.numbers = numbers;
        this.start = start;
        this.length = length;
    }

    static long[] generateArray() {
        long[] numbers = new long[(int) TOTAL];
        for (long i = 0; i < TOTAL; i++) {
            numbers[(int) i] = i + 1;
        }
        return numbers;
    }

    public static void main(String[] args) {
        long[] numbers = generateArray();

        Ejer3sumaRecursiva task = new Ejer3sumaRecursiva(numbers, 0, TOTAL);
        long start = System.currentTimeMillis();
        long sumIterative = task.sumIterative();
        long end = System.currentTimeMillis();
        System.out.println("Sum (" + sumIterative + ") iterative found in " + (end - start) + " ms");

        ForkJoinPool pool = new ForkJoinPool();
        start = System.currentTimeMillis();
        long sumParallel = Math.toIntExact(pool.invoke(new Ejer3sumaRecursiva(numbers, 0, TOTAL)));
        end = System.currentTimeMillis();
        System.out.println("Sum (" + sumParallel + ") parallel found in " + (end - start) + " ms");
    }

    private long sumIterative() {
        long sum = 0;
        long end = start + length;
        for (long i = start; i < end; i++) {
            sum += numbers[(int) i];
        }
        System.out.println("Suma iterativa de " + Arrays.toString(Arrays.copyOfRange(numbers, (int) start, (int) end)) + " = " + sum);
        return sum;
    }

    private long sumRecursive() {
        long mid = length / 2;
        Ejer3sumaRecursiva task1 = new Ejer3sumaRecursiva(numbers, start, mid);
        Ejer3sumaRecursiva task2 = new Ejer3sumaRecursiva(numbers, start + mid, length - mid);

        System.out.println("Dividiendo: " + Arrays.toString(Arrays.copyOfRange(numbers, (int) start, (int) (start + length))));

        task1.fork();
        Long resultRight = task2.compute();
        Long resultLeft = task1.join();

        System.out.println(resultLeft);
        System.out.println(resultRight);

        return resultLeft + resultRight;
    }

    @Override
    protected Long compute() {
        if (length > THRESHOLD) {
            return sumRecursive();
        } else {
            return sumIterative();
        }
    }
}
