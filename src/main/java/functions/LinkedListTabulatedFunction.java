package functions;

import exceptions.InterpolationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.lang.Iterable;

public class LinkedListTabulatedFunction extends AbstractTabulatedFunction implements Insertable, Removable, Iterable<Point> {

    private Node head;

    protected static class Node{
        Node prev, next;
        double x, y;

        Node() {
            this.x = 0;
            this.y = 0;
            this.prev = null;
            this.next = null;
        }

        Node(double x, double y) {
            this.x = x;
            this.y = y;
            this.next = null;
            this.prev = null;
        }
    }

    private void addNode(double x, double y){

        if(head!=null){

            // Указателем проходимся по всему списку
            Node Pointer = head.next;
            while(Pointer.next!=head){Pointer = Pointer.next;}

            // Запоминаем последний элемент списка и создаём новый
            Node prevPointer = Pointer;
            Pointer.next = new Node(x, y);

            Pointer = Pointer.next;

            // Вставляем элемент в список
            Pointer.prev = prevPointer;
            Pointer.next = head;
            head.prev = Pointer;

        } else{
            head = new Node(x, y);
            head.prev = head;
            head.next = head;
        }
        ++count;
    }

    public LinkedListTabulatedFunction(double[] xValues, double[] yValues) {

        AbstractTabulatedFunction.checkLengthIsTheSame(xValues, yValues);
        AbstractTabulatedFunction.checkSorted(xValues);

        if (xValues.length < 2) {
            throw new IllegalArgumentException("The number of points must be at least 2");
        }

        for (int i = 0; i < xValues.length; i++) {
            this.addNode(xValues[i], yValues[i]);
        }

        this.count = xValues.length;
    }

    public LinkedListTabulatedFunction(MathFunction source, double xFrom, double xTo, int count) {
        if(xFrom>xTo){
            double temp = xFrom;
            xFrom = xTo;
            xTo = temp;
        }
        this.count = count;

        head = new Node();
        Node pointer = head;

        // Прим. если xTo = xFrom, то xDelta = 0
        double xDelta = (xTo - xFrom) / (count-1);

        for (int i = 0; i < count; i++) {
            pointer.x = xFrom + i * xDelta;
            pointer.y = source.apply(pointer.x);
            pointer.next = new Node();
            pointer.next.prev = pointer;
            pointer = pointer.next;
        }

        pointer.x = xFrom + count * xDelta;
        pointer.y = source.apply(pointer.x);
        pointer.next = head;
        head.prev = pointer;

    }

    private Node getNode(int index){

        Node pointer = head;

        if(index>0) {
            for (int i = 0; i < index; i++) { pointer = pointer.next;}
        } else{
            for (int i = index; i < 0; i++) { pointer = pointer.prev;}
        }

        return pointer;
    }

    @Override
    public int getCount(){
        return count;
    }

    @Override
    public double getX(int index){
        return getNode(index).x;
    }
    @Override
    public double getY(int index){
        return getNode(index).y;
    }

    @Override
    public void setY(int index, double value){
        getNode(index).y = value;
    }

    @Override
    public int indexOfX(double x){
        Node pointer = head;
        for (int i = 0; i < count; i++) {
            if (pointer.x == x){ return i;}
            pointer = pointer.next;
        }
        return -1;
    }
    @Override
    public int indexOfY(double y){
        Node pointer = head;
        for (int i = 0; i < count; i++) {
            if (pointer.y == y){ return i;}
            pointer = pointer.next;
        }
        return -1;
    }

    @Override
    public double leftBound(){
        if(head == null) { throw new IllegalStateException("Head is null");}
        return head.x;
    }
    @Override
    public double rightBound(){
        if(head == null) { throw new IllegalStateException("Head is null");}
        return head.prev.x;
    }

    @Override
    protected int floorIndexOfX(double x) {
        if (x < head.x) {
            throw new IllegalArgumentException("Lesser than left left bound");
        }
        Node pointer = head;
        for (int i = 0; i < count; i++) {
            if (x < pointer.next.x) { return i - 1; }
            pointer = pointer.next;
        }
        return count;
    }

    public Node floorNodeOfX(double x) {
        if (x < head.x) {
            throw new IllegalArgumentException("Lesser than left left bound");
        }
        Node pointer = head;
        for (int i = 0; i < count; i++) {
            if (x < pointer.next.x) { return pointer; }
            pointer = pointer.next;
        }
        return head.prev;
    }

    @Override
    public double interpolate(double x, int floorIndex) {
        Node floorNode = getNode(floorIndex);
        if (x < floorNode.x || x > floorNode.next.x) {
            throw new InterpolationException("x is out of interpolation range");
        }
        return interpolate(x, floorNode.x, floorNode.next.x, floorNode.y, floorNode.next.y);
    }

    public double interpolate(double x, Node floorNode) {
        if (x < floorNode.x || x > floorNode.next.x) {
            throw new InterpolationException("x is out of interpolation bounds");
        }
        return interpolate(x, floorNode.x, floorNode.next.x, floorNode.y, floorNode.next.y);
    }

    @Override
    protected double extrapolateLeft(double x) {
        if(count == 1) {
            return (head.y);
        }
        return interpolate(x, head.x, head.next.x, head.y, head.next.y);
    }

    @Override
    protected double extrapolateRight(double x) {
        if(count == 1) {
            return (head.y);
        }
        return interpolate(x, head.prev.prev.x, head.prev.x, head.prev.prev.y, head.prev.y);
    }

    @Override
    public double apply(double x) {
        if (x < leftBound()) {
            return extrapolateLeft(x);
        } else if (x > rightBound()) {
            return extrapolateRight(x);
        } else {
            int index = indexOfX(x);
            if (index != -1) {
                return getY(index);
            } else {
                return interpolate(x, floorNodeOfX(x));
            }
        }
    }

    @Override
    public void insert(double x, double y) {
        if (head == null) {
            addNode(x, y);
            return;
        }
        if (x < head.x) {
            Node newNode = new Node(x, y);
            newNode.next = head;
            newNode.prev = head.prev;
            head.prev.next = newNode;
            head.prev = newNode;
            head = newNode;
            count++;
            return;
        }
        Node pointer;
        if (x > head.prev.x) {
            pointer = head.prev;
        } else {
            pointer = head;
            while (pointer.next != head && pointer.next.x < x) {
                pointer = pointer.next;
            }
        }
        if (pointer.x == x) {
            pointer.y = y;
            return;
        }
        if (pointer.next != head && pointer.next.x == x) {
            pointer.next.y = y;
            return;
        }
        Node newNode = new Node(x, y);
        newNode.next = pointer.next;
        newNode.prev = pointer;
        pointer.next.prev = newNode;
        pointer.next = newNode;
        count++;
    }

    @Override
    public void remove(int index){

        if(count == 0){
            throw new IllegalArgumentException("Cannot remove zero list");
        }

        Node pointer = head;

        if(pointer.next!=pointer) {
            if(index>0) {for (int i = 0; i < index; i++) {pointer = pointer.next;} }
            else{for (int i = index; i < 0; i++) {pointer = pointer.prev;} }
            if(pointer == head){head = head.next;}
            (pointer.prev).next = pointer.next;
            (pointer.next).prev = pointer.prev;

        } else{
            head = null;
        }

        count--;
    }

    @Override
    public Iterator<Point> iterator() {
        return new Iterator<>() {

            private Node node = head;

            @Override
            public boolean hasNext() {
                return node.next!=head;
            }

            @Override
            public Point next() {
                if (!hasNext()){ throw new NoSuchElementException("There are no other elements");}
                Point point = (node.next != head) ? new Point(node.x, node.y) : null;
                node = node.next;
                return point;
            }
        };
    }
}
