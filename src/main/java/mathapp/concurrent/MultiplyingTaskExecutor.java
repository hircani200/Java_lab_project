package mathapp.concurrent;

import mathapp.functions.TabulatedFunction;
import mathapp.functions.UnitFunction;
import mathapp.functions.LinkedListTabulatedFunction;

import java.util.ArrayList;
import java.util.List;

public class MultiplyingTaskExecutor {

    public static void main(String[] args)  {
        TabulatedFunction function = new LinkedListTabulatedFunction(
                new UnitFunction(), 1, 1000, 1000);

        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(new MultiplyingTask(function));
            threads.add(thread);
        }

        for (Thread thread : threads) {
            thread.start();
        }

        while (!threads.isEmpty()) {
            threads.removeIf(thread -> !thread.isAlive());
        }

        System.out.println(function);
    }
}