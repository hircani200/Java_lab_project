package mathapp.io;

import mathapp.functions.ArrayTabulatedFunction;
import mathapp.functions.LinkedListTabulatedFunction;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import static mathapp.io.FunctionsIO.writeTabulatedFunction;

public class TabulatedFunctionFileOutputStream {
    public static void main(String[] args) {

        ArrayTabulatedFunction arrayFunction = new ArrayTabulatedFunction(new double[]{1.0, 2.0, 3.0}, new double[]{0.0, 2.2, 0.25232332});
        LinkedListTabulatedFunction linkedListFunction = new LinkedListTabulatedFunction(new double[]{1.0, 2.0, 3.0}, new double[]{5.5, 6.0, 0.25});

        try (BufferedOutputStream bufferedOutputStream1 = new BufferedOutputStream(new FileOutputStream("output/array function.bin"));
             BufferedOutputStream bufferedOutputStream2 = new BufferedOutputStream(new FileOutputStream("output/linked list function.bin"))){

            FunctionsIO.writeTabulatedFunction(bufferedOutputStream1, arrayFunction);
            FunctionsIO.writeTabulatedFunction(bufferedOutputStream2, linkedListFunction);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
