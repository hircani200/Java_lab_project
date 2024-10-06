package concurrent;

import functions.TabulatedFunction;

public class ReadTask implements Runnable{

    private final TabulatedFunction function;

    public ReadTask(TabulatedFunction function) {
        this.function = function;
    }

    @Override
    public void run() {
        for (int i = 0; i < this.function.getCount(); i++) {

            System.out.printf("After read: i = %d, x = %f, y = %f \n", i, this.function.getX(i), this.function.getY(i));
        }
    }
}
