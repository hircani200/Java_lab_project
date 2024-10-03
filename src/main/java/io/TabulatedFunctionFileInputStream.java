package io;

import functions.TabulatedFunction;
import functions.factory.ArrayTabulatedFunctionFactory;
import functions.factory.LinkedListTabulatedFunctionFactory;

import java.io.*;

import static io.FunctionsIO.readTabulatedFunction;

public class TabulatedFunctionFileInputStream {
    public static void main(String[] args) {
        try(BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream("input/binary function.bin"))){
            TabulatedFunction function = readTabulatedFunction(inputStream, new ArrayTabulatedFunctionFactory());
            System.out.println(function.toString());
        }catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Введите размер и значения функции: ");
        try{
            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
            TabulatedFunction consoleFunction = readTabulatedFunction(consoleReader, new LinkedListTabulatedFunctionFactory());

            System.out.println(consoleFunction.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
