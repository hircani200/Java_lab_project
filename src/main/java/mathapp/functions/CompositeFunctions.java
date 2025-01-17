package mathapp.functions;

public class CompositeFunctions implements MathFunction {

    private final MathFunction firstFunction;
    private final MathFunction secondFunction;

    public CompositeFunctions(MathFunction firstFunction, MathFunction secondFunction) {
        this.firstFunction = firstFunction;
        this.secondFunction = secondFunction;
    }

    @Override
    public double apply(double x) { return secondFunction.apply(firstFunction.apply(x));
    }
}
