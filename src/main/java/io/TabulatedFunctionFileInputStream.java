package io;

import functions.TabulatedFunction;
import functions.factory.ArrayTabulatedFunctionFactory;
import functions.factory.LinkedListTabulatedFunctionFactory;
import operations.TabulatedDifferentialOperator;

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

        System.out.println("Enter the size and values of the function: ");
        try{
            TabulatedFunction consoleFunction = readTabulatedFunction(new BufferedReader(new InputStreamReader(System.in)), new LinkedListTabulatedFunctionFactory());

            System.out.println(new TabulatedDifferentialOperator(new LinkedListTabulatedFunctionFactory()).derive(consoleFunction).toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
