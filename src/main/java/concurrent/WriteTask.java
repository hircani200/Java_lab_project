package concurrent;

import functions.TabulatedFunction;

public class WriteTask implements Runnable{

    private final TabulatedFunction function;
    private final double value;

    public WriteTask(TabulatedFunction function, double value) {
        this.function = function;
        this.value = value;
    }

    @Override
    public void run() {
        for (int i = 0; i < this.function.getCount(); i++) {
            synchronized (function) {
                this.function.setY(i, this.value);
                System.out.printf("Writing for index %d complete\n", i);
            }
        }
    }

}
