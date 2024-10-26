package mathapp.exceptions;

import java.io.Serial;

public class  DifferentLengthOfArraysException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 6118427772521685564L;

    public DifferentLengthOfArraysException() {

    }
    public DifferentLengthOfArraysException(String message) {
        super(message);
    }
}