package mathapp.io;

import java.io.*;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import com.fasterxml.jackson.databind.ObjectMapper;
import mathapp.functions.TabulatedFunction;
import mathapp.functions.Point;
import mathapp.functions.factory.TabulatedFunctionFactory;
import com.thoughtworks.xstream.security.NoTypePermission;
import com.thoughtworks.xstream.XStream;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.IOException;
import mathapp.functions.ArrayTabulatedFunction;

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

    public static void writeTabulatedFunction(BufferedOutputStream outputStream, TabulatedFunction function) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        dataOutputStream.writeInt(function.getCount());
        for (Point point : function){
            dataOutputStream.writeDouble(point.x);
            dataOutputStream.writeDouble(point.y);
        }
        dataOutputStream.flush();
    }

    public static TabulatedFunction readTabulatedFunction(BufferedInputStream inputStream, TabulatedFunctionFactory factory) throws IOException {
        DataInputStream dataInputStream = new DataInputStream(inputStream);

        int count = dataInputStream.readInt();
        double[] xValues = new double[count];
        double[] yValues = new double[count];

        for (int i = 0; i < count; i++) {
            xValues[i] = dataInputStream.readDouble();
            yValues[i] = dataInputStream.readDouble();
        }

        return factory.create(xValues, yValues);
    }

    public static void serialize(BufferedOutputStream stream, TabulatedFunction function) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(stream);

        objectOutputStream.writeObject(function);

        objectOutputStream.flush();
    }

    public static TabulatedFunction deserialize(BufferedInputStream stream) throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(stream);
        Object function = objectInputStream.readObject();
        return (TabulatedFunction) function;
    }

    public static void serializeXml(BufferedWriter writer, ArrayTabulatedFunction function) throws IOException {
        XStream xstream = new XStream();
        String xml = xstream.toXML(function);
        writer.write(xml);
        writer.flush();
    }

    public static ArrayTabulatedFunction deserializeXml(BufferedReader reader) {
        XStream xstream = new XStream();

        xstream.addPermission(NoTypePermission.NONE);
        xstream.allowTypes(new Class[] {
                ArrayTabulatedFunction.class,
                double[].class,
                Double.class
        });

        return (ArrayTabulatedFunction) xstream.fromXML(reader);
    }

    public static void serializeJson(BufferedWriter writer, ArrayTabulatedFunction function) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        writer.write(objectMapper.writeValueAsString(function));
        writer.flush();
    }

    public static ArrayTabulatedFunction deserializeJson(BufferedReader reader) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readerFor(ArrayTabulatedFunction.class).readValue(reader);
    }
}