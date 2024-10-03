package io;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import functions.TabulatedFunction;
import functions.Point;
import functions.factory.TabulatedFunctionFactory;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;

public final class FunctionsIO {
    private FunctionsIO() {
        throw new UnsupportedOperationException();
    }

    public static void writeTabulatedFunction(BufferedWriter writer, TabulatedFunction function) throws IOException {
        PrintWriter printWriter = new PrintWriter(writer);
        printWriter.println(function.getCount());
        for (Point point : function) {
            printWriter.printf("%f %f\n", point.x, point.y);
        }
        printWriter.flush();
    }

    public static TabulatedFunction readTabulatedFunction(BufferedReader reader, TabulatedFunctionFactory factory) throws IOException {
        try {
            int count = Integer.parseInt(reader.readLine());

            double[] xValues = new double[count];
            double[] yValues = new double[count];

            NumberFormat numberFormat = NumberFormat.getInstance(Locale.forLanguageTag("ru"));

            for (int i = 0; i < count; i++) {
                String line = reader.readLine();
                String[] values = line.split(" ");

                try {
                    xValues[i] = numberFormat.parse(values[0]).doubleValue();
                    yValues[i] = numberFormat.parse(values[1]).doubleValue();
                } catch (ParseException e) {
                    throw new IOException(e);
                }
            }
            return factory.create(xValues, yValues);
        } catch (IOException | NumberFormatException e) {
            throw new IOException(e);
        }
    }

    static void writeTabulatedFunction(BufferedOutputStream outputStream, TabulatedFunction function) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        dataOutputStream.writeInt(function.getCount());
        for (Point point : function){
            dataOutputStream.writeDouble(point.x);
            dataOutputStream.writeDouble(point.y);
        }
        dataOutputStream.flush();
    }
}