package mathapp.concurrent;

import mathapp.functions.ConstantFunction;
import mathapp.functions.LinkedListTabulatedFunction;
import mathapp.functions.TabulatedFunction;

public class ReadWriteTaskExecutor {
    public static void main(String[] args) {
        TabulatedFunction function = new LinkedListTabulatedFunction(new ConstantFunction(-1), 1, 1000, 1000);
        ReadTask readTask = new ReadTask(function);
        WriteTask writeTask = new WriteTask(function, 0.5);

        new Thread(readTask).start();
        new Thread(writeTask).start();
    }
}
